package onlineShop.manager;

import lombok.Cleanup;
import onlineShop.db.ConnectionProvider;
import onlineShop.form.ImageForm;
import onlineShop.model.AttributeValue;
import onlineShop.model.Image;
import onlineShop.model.Product;
import onlineShop.model.User;
import onlineShop.util.ImageUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final String INSERT = "insert into product(title,description,price,quantity,product_img_url,user_id,created_date)values(?,?,?,?,?,?,?)";
    private static final String UPDATE_IMG_URL = "update product set product_img_url=? where product_id=?";
    private static final String COUNT_ALL = "select count(*) from product";
    private static final String SELECT_ALL_BY_LIMIT = "select * from product order by created_date desc limit ?,?";
    private static final String SELECT_ALL_CART_BY_USER_ID = "select * from product p inner join product_cart c on " +
            "p.product_id=c.product_id and c.user_id=?";
    private static final String SELECT_BY_Id = "select * from product p inner join user u on " +
            " p.user_id=u.user_id and p.product_id=?";
    private static final String SELECT_ALL_EXCEPT_PRODUCT_ID = "select * from product where title like ? and product_id!=? limit 0,8";
    private static final String UPDATE_QUANTITY = "update product set quantity=? where product_id=?";
    private static final String DELETE = "delete from product where product_id=?";
    private static final String SELECT_ALL_BY_CATEGORY_ID = "select * from product p inner join product_category pc on " +
            "p.product_id=pc.product_id  and pc.category_id=? order by p.created_date desc limit ?,?";
    private static final String COUNT_BY_CATEGORY_ID = "select count(*) from product p inner join product_category pc on " +
            "p.product_id=pc.product_id  and pc.category_id=?";
    private static final String SELECT_ALL_BY_TITLE_LIKE = "select * from product where title like ?  order by created_date desc limit ?,?";
    private static final String COUNT_ALL_BY_TITLE_LIKE = "select count(*) from product where title like ?";

    private Connection connection;

    public ProductManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public int countALl(){
        try {
            @Cleanup Statement statement = connection.createStatement();
            @Cleanup ResultSet resultSet = statement.executeQuery(COUNT_ALL);
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return 0;
    }

    public List<Product> getAllByLimit(int pageNumber,int size){
        List<Product> products = new ArrayList<Product>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_LIMIT);
            preparedStatement.setInt(1,pageNumber);
            preparedStatement.setInt(2,size);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = getProduct(resultSet);
                product.setUser(User.builder().id(resultSet.getInt("user_id")).build());
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return products;
    }

    public void save(Product product, ImageForm form, List<Integer> categories,
                     List<AttributeValue> attributeValues, ProductCategoryManager productCategoryManager,
                     AttributeValueManager attributeValueManager, ImageManager imageManager){
        String imgUrl;
        try {
            connection.setAutoCommit(false);

            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,product.getTitle());
            preparedStatement.setString(2,product.getDescription());
            preparedStatement.setDouble(3,product.getPrice());
            preparedStatement.setInt(4,product.getQuantity());
            preparedStatement.setString(5,product.getImgUrl());
            preparedStatement.setInt(6,product.getUser().getId());
            preparedStatement.setString(7,DATE_FORMAT.format(product.getCreatedDate()));
            preparedStatement.execute();
            @Cleanup ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                product.setId(resultSet.getInt(1));
            }else {
                throw new RuntimeException("failed load generated key");
            }
            imgUrl = product.getUser().getUsername() + "/" + product.getId() + "/" + form.getName();
            preparedStatement = connection.prepareStatement(UPDATE_IMG_URL);
            preparedStatement.setString(1,imgUrl);
            preparedStatement.setInt(2,product.getId());
            preparedStatement.execute();
            productCategoryManager.save(categories,product.getId());
            attributeValueManager.save(attributeValues,product.getId());
            imageManager.save(new Image(0,imgUrl,product),form);
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    public Product getById(int id){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_Id);
            preparedStatement.setInt(1,id);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Product product = getProduct(resultSet);
                product.setUser(UserManager.getUser(resultSet));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    static Product getProduct(ResultSet resultSet)throws SQLException{
        return Product.builder()
                .id(resultSet.getInt("product_id"))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .price(resultSet.getDouble("price"))
                .quantity(resultSet.getInt("quantity"))
                .imgUrl(resultSet.getString("product_img_url"))
                .createdDate(resultSet.getDate("created_date"))
                .user(User.builder().id(resultSet.getInt("user_id")).build())
                .build();
    }

    public List<Product> getAllCartProductByUserId(int userId){
        List<Product> products = new ArrayList<Product>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CART_BY_USER_ID);
            preparedStatement.setInt(1,userId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                products.add(getProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return products;
    }

    public void updateQuantity(int id,int quantity){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUANTITY);
            preparedStatement.setInt(1,quantity);
            preparedStatement.setInt(2,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Product> getLikeProducts(int productId,String title){
        List<Product> products = new ArrayList<Product>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EXCEPT_PRODUCT_ID);
            preparedStatement.setString(1,"%" + title + "%");
            preparedStatement.setInt(2,productId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                products.add(getProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return products;
    }

    public void delete(User user,int id){
        try {
            connection.setAutoCommit(false);
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
            ImageUtil.deleteAll(user.getUsername() + "\\" + id);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    public List<Product> getAllByCategoryId(int catId,int pageNumber,int size){
        List<Product> products = new ArrayList<Product>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_CATEGORY_ID);
            preparedStatement.setInt(1,catId);
            preparedStatement.setInt(2,pageNumber);
            preparedStatement.setInt(3,size);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                products.add(getProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return products;
    }


    public List<Product> getAllByTitleLike(String title,int pageNumber,int size){
        List<Product> products = new ArrayList<Product>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_TITLE_LIKE);
            preparedStatement.setString(1,"%" + title + "%");
            preparedStatement.setInt(2,pageNumber);
            preparedStatement.setInt(3,size);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                products.add(getProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return products;
    }

    public int countByCategoryId(int catId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(COUNT_BY_CATEGORY_ID);
            preparedStatement.setInt(1,catId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return 0;
    }

    public int countByTitleLike(String title){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_BY_TITLE_LIKE);
            preparedStatement.setString(1,"%" + title + "%");
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return 0;
    }
}
