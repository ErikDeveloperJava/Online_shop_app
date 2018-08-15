package onlineShop.manager;

import lombok.Cleanup;
import onlineShop.db.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCartManager {

    private static final String COUNT = "select count(*) from product_cart where user_id=? and product_id=?";
    private static final String DELETE = "delete from product_cart where user_id=? and product_id=?";
    private static final String INSERT = "insert into product_cart(user_id,product_id)values(?,?)";
    private static final String COUNT_BY_USER_ID = "select count(*) from product_cart where user_id=?";

    private Connection connection;

    public ProductCartManager() {
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

    public void save(int userId,int productId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(int userId,int productId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean exists(int userId,int productId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(COUNT);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1) == 0 ? false : true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return false;
    }
}
