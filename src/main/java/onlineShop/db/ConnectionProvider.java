package onlineShop.db;

import onlineShop.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

    private static final String FILE_NAME = "C:\\Users\\Erik\\IdeaProjects\\java-web\\OnlineShop\\src\\main\\resources\\db.properties";
    private static ConnectionProvider instance;

    private Connection connection;
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    private ConnectionProvider(){
        Properties properties = PropertiesUtil.load(FILE_NAME);
        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            System.out.println("failed load dirver class");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        URL = properties.getProperty("url");
        USERNAME = properties.getProperty("username");
        PASSWORD = properties.getProperty("password");
    }

    public synchronized static ConnectionProvider getInstance(){
        if(instance == null){
            instance = new ConnectionProvider();
        }
        return instance;
    }

    public Connection getConnection(){
        try {
            if(connection == null || connection.isClosed()){
                connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void clear(){
        try {
            if(connection != null && connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("connection failed close");
            e.printStackTrace();
        }
    }
}
