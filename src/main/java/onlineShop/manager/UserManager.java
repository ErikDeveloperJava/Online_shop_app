package onlineShop.manager;

import lombok.Cleanup;
import onlineShop.db.ConnectionProvider;
import onlineShop.form.ImageForm;
import onlineShop.model.Image;
import onlineShop.model.User;
import onlineShop.model.UserRole;
import onlineShop.util.ImageUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final String INSERT = "insert into user(name,surname,username,password,img_url,role,balance)values(?,?,?,?,?,?,?)";
    private static final String SELECT_BY_USERNAME = "select * from user where username=?";
    private static final String SELECT_BY_ID = "select * from user where user_id=?";
    private static final String SELECT_ALL = "select * from user";
    private static final String UPDATE_BALANCE = "update user set balance=? where user_id=?";
    private static final String DELETE = "delete from user where user_id=?";

    private Connection connection;

    public UserManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public User getByUsername(String username){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USERNAME);
            preparedStatement.setString(1,username);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return getUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }


    public User getById(int id){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1,id);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return getUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<User> getAll(){
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            @Cleanup ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                users.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return users;
    }

    public void save(User user, ImageForm form){
        try {
            connection.setAutoCommit(false);

            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getSurname());
            preparedStatement.setString(3,user.getUsername());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setString(5,user.getImgUrl());
            preparedStatement.setString(6,user.getRole().name());
            preparedStatement.setDouble(7,user.getBalance());
            preparedStatement.execute();

            try {
                ImageUtil.save(user.getUsername(),form.getName(),form.getBytes());
                connection.commit();
            }catch (Exception e){
                ImageUtil.delete(form.getName());
                ImageUtil.delete(user.getUsername());
                throw e;
            }
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

    public void updateBalance(int id,double balance){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BALANCE);
            preparedStatement.setDouble(1,balance);
            preparedStatement.setInt(2,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    public void deleteById(User user){
        try {
            connection.setAutoCommit(false);
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1,user.getId());
            preparedStatement.execute();
            ImageUtil.deleteAll(user.getUsername());
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

    static User getUser(ResultSet resultSet)throws SQLException {
        return User.builder()
                .id(resultSet.getInt("user_id"))
                .name(resultSet.getString("name"))
                .surname(resultSet.getString("surname"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .balance(resultSet.getDouble("balance"))
                .imgUrl(resultSet.getString("img_url"))
                .role(UserRole.valueOf(resultSet.getString("role")))
                .build();
    }
}