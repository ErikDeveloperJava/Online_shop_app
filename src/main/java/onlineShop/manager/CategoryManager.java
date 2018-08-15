package onlineShop.manager;

import lombok.Cleanup;
import onlineShop.db.ConnectionProvider;
import onlineShop.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {

    private static final String INSERT = "insert into category(category_name) values(?)";
    private static final String SELECT_ALL = "select * from category";
    private static final String SELECT_BY_ID = "select * from category where cat_id=?";
    private static final String SELECT_ALL_BY_PRODUCT_ID = "select cat_id,category_name from category c inner join product_category pc " +
            "inner join product p on c.cat_id=pc.category_id and p.product_id=pc.product_id and p.product_id=?";

    private Connection connection;

    public CategoryManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public List<Category> getAll(){
        List<Category> categories = new ArrayList<Category>();
        try {
            @Cleanup Statement statement = connection.createStatement();
            @Cleanup ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                categories.add(getCategory(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return categories;
    }

    public Category getById(int id){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1,id);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return getCategory(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    public void save(Category category){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1,category.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Category> getAllByProductId(int productId){
        List<Category> categories = new ArrayList<Category>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_PRODUCT_ID);
            preparedStatement.setInt(1,productId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                categories.add(getCategory(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return categories;
    }

    static Category getCategory(ResultSet resultSet)throws SQLException{
        return Category.builder()
                .id(resultSet.getInt("cat_id"))
                .name(resultSet.getString("category_name"))
                .build();
    }
}
