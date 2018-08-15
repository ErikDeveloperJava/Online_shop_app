package onlineShop.manager;

import lombok.Cleanup;
import onlineShop.db.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductCategoryManager {

    private static final String INSERT = "insert into product_category(product_id,category_id)values(?,?)";

    private Connection connection;

    public ProductCategoryManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public void save(List<Integer> categories,int productId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            for (Integer id : categories) {
                preparedStatement.setInt(1,productId);
                preparedStatement.setInt(2,id);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
