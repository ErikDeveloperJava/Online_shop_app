package onlineShop.manager;

import lombok.Cleanup;
import onlineShop.db.ConnectionProvider;
import onlineShop.model.Category;
import onlineShop.model.CategoryAttribute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryAttributeManager {

    private static final String INSERT = "insert into category_attribute(name,category_id) values(?,?)";
    private static final String SELECT_ALL_BY_CATEGORY_ID = "select * from category_attribute where category_id=?";

    private Connection connection;

    public CategoryAttributeManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public List<CategoryAttribute> getAllByCategories(List<Integer> categories){
        List<CategoryAttribute> categoryAttributes = new ArrayList<CategoryAttribute>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_CATEGORY_ID);
            for (Integer id : categories) {
                preparedStatement.setInt(1,id);
                @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    categoryAttributes.add(getCategoryAttribute(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return categoryAttributes;
    }

    public void saveAll(List<CategoryAttribute> attributes){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            for (CategoryAttribute attribute : attributes) {
                preparedStatement.setString(1,attribute.getName());
                preparedStatement.setInt(2,attribute.getCategory().getId());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    static CategoryAttribute getCategoryAttribute(ResultSet resultSet)throws SQLException{
        return CategoryAttribute.builder()
                .id(resultSet.getInt("attr_id"))
                .name(resultSet.getString("name"))
                .category(Category.builder().id(resultSet.getInt("category_id")).build())
                .build();
    }
}
