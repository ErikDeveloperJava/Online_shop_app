package onlineShop.manager;

import lombok.Cleanup;
import onlineShop.db.ConnectionProvider;
import onlineShop.model.AttributeValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttributeValueManager {

    private static final String INSERT = "insert into attribute_value(value,category_attribute_id,product_id)values(?,?,?)";
    private static final String SELECT_ALL_BY_PRODUCT_ID = "select * from attribute_value a inner join category_attribute c on " +
            "a.product_id=? and a.category_attribute_id = c.attr_id";

    private Connection connection;

    public AttributeValueManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public void save(List<AttributeValue> attributeValues,int productId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            for (AttributeValue attributeValue : attributeValues) {
                preparedStatement.setString(1,attributeValue.getValue());
                preparedStatement.setInt(2,attributeValue.getCategoryAttribute().getId());
                preparedStatement.setInt(3,productId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<AttributeValue> getAllByProductId(int productId){
        List<AttributeValue> attributeValues = new ArrayList<AttributeValue>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_PRODUCT_ID);
            preparedStatement.setInt(1,productId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                AttributeValue attributeValue = getAttributeValue(resultSet);
                attributeValue.setCategoryAttribute(CategoryAttributeManager.getCategoryAttribute(resultSet));
                attributeValues.add(attributeValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return attributeValues;
    }

    static AttributeValue getAttributeValue(ResultSet resultSet)throws SQLException{
        return AttributeValue.builder()
                .id(resultSet.getInt("id"))
                .value(resultSet.getString("value"))
                .build();
    }
}
