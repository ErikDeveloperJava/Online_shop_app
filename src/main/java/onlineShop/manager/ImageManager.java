package onlineShop.manager;

import lombok.Cleanup;
import onlineShop.db.ConnectionProvider;
import onlineShop.form.ImageForm;
import onlineShop.model.Image;
import onlineShop.util.ImageUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageManager {

    private static final String INSERT = "insert into image(image,product_id)values(?,?)";
    private static final String SELECT_ALL_BY_PRODUCT_ID = "select * from image where product_id=?";

    private Connection connection;

    public ImageManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public void save(Image image, ImageForm form){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,image.getImage());
            preparedStatement.setInt(2,image.getProduct().getId());
            try {
                ImageUtil.save(image.getProduct().getUser().getUsername() + "\\" + image.getProduct().getId(),
                        form.getName(),form.getBytes());
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    image.setId(resultSet.getInt(1));
                }else {
                    throw new RuntimeException("failed load generated keys");
                }
            }catch (Exception e){
                ImageUtil.delete(image.getImage());
                ImageUtil.delete(image.getProduct().getUser().getUsername() + "\\" + image.getProduct().getId());
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Image> getAllByProductId(int productId){
        List<Image> images = new ArrayList<Image>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_PRODUCT_ID);
            preparedStatement.setInt(1,productId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                images.add(getImage(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return images;
    }

    static Image getImage(ResultSet resultSet)throws SQLException{
        return Image.builder()
                .id(resultSet.getInt("id"))
                .image(resultSet.getString("image"))
                .build();
    }
}
