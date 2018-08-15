package onlineShop.manager;

import lombok.Cleanup;
import onlineShop.db.ConnectionProvider;
import onlineShop.model.Product;
import onlineShop.model.ProductOrder;
import onlineShop.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductOrderManager {

    private static final String COUNT_BY_USER_ID = "select count(*) from product_order where user_id=?";
    private static final String INSERT = "insert into product_order(user_id,product_id,count)values(?,?,?)";
    private static final String UPDATE_COUNT = "update product_order set count=? where id=?";
    private static final String SELECT_BY_PRODUCT_ID_AND_USER_ID = "select * from product_order where user_id=? and product_id=?";
    private static final String SELECT_ALL_BY_USER_ID = "select * from product_order po inner join product p on " +
            "p.product_id=po.product_id and po.user_id=?";
    private static final String DELETE_BY_USER_ID_AND_PRODUCT_ID = "delete from product_order where user_id=? and product_id=?";

    private Connection connection;

    public ProductOrderManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public int countByUserId(int userId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(COUNT_BY_USER_ID);
            preparedStatement.setInt(1,userId);
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

    public ProductOrder getByUserIdAndProductId(int userId,int productId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_PRODUCT_ID_AND_USER_ID);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return getProductOrder(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return null;
    }

    public void save(ProductOrder productOrder,UserManager userManager,ProductManager productManager){
        try {
            connection.setAutoCommit(false);
            double balance = productOrder.getUser().getBalance() - productOrder.getProduct().getPrice();
            userManager.updateBalance(productOrder.getUser().getId(),balance);
            productManager.updateQuantity(productOrder.getProduct().getId(),productOrder.getProduct().getQuantity() - 1);
            ProductOrder order = getByUserIdAndProductId(productOrder.getUser().getId(),productOrder.getProduct().getId());
            if(order == null){
                productOrder.setCount(1);
                @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
                preparedStatement.setInt(1,productOrder.getUser().getId());
                preparedStatement.setInt(2,productOrder.getProduct().getId());
                preparedStatement.setInt(3,productOrder.getCount());
                preparedStatement.execute();
            }else {
                updateCount(order.getId(),order.getCount() + 1);
            }
            productOrder.getUser().setBalance(balance);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException();
        }
    }

    public void updateCount(int id,int count){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COUNT);
            preparedStatement.setInt(1,count);
            preparedStatement.setInt(2,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<ProductOrder> getAllByUserId(int userId){
        List<ProductOrder> productOrders = new ArrayList<ProductOrder>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_USER_ID);
            preparedStatement.setInt(1,userId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ProductOrder productOrder = getProductOrder(resultSet);
                productOrder.setProduct(ProductManager.getProduct(resultSet));
                productOrders.add(productOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return productOrders;
    }

    private ProductOrder getProductOrder(ResultSet resultSet)throws SQLException{
        return ProductOrder.builder()
                .id(resultSet.getInt("id"))
                .user(User.builder().id(resultSet.getInt("user_id")).build())
                .product(Product.builder().id(resultSet.getInt("product_id")).build())
                .count(resultSet.getInt("count"))
                .build();
    }

    public void delete(int userId,int productId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_USER_ID_AND_PRODUCT_ID);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
