package onlineShop.manager;

import lombok.Cleanup;
import onlineShop.db.ConnectionProvider;
import onlineShop.model.Reviews;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReviewsManager {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String INSERT = "insert into reviews(review_text,user_id,product_id,rating,send_date)values(?,?,?,?,?)";
    private static final String COUNT_BY_PRODUCT_ID = "select count(*) from reviews where product_id=?";
    private static final String SELECT_ALL_BY_PRODUCT_ID = "select * from reviews r inner join user u on " +
            "r.product_id=? and u.user_id=r.user_id";

    private Connection connection;

    public ReviewsManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public int countByProductId(int productId) {
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(COUNT_BY_PRODUCT_ID);
            preparedStatement.setInt(1, productId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return 0;
    }

    public List<Reviews> getAllByProductId(int productId) {
        List<Reviews> reviewsList = new ArrayList<Reviews>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_PRODUCT_ID);
            preparedStatement.setInt(1, productId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Reviews reviews = getReviews(resultSet);
                reviews.setUser(UserManager.getUser(resultSet));
                reviewsList.add(reviews);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return reviewsList;
    }

    public void save(Reviews reviews){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1,reviews.getReviewText());
            preparedStatement.setInt(2,reviews.getUser().getId());
            preparedStatement.setInt(3,reviews.getProduct().getId());
            preparedStatement.setDouble(4,reviews.getRating());
            preparedStatement.setString(5,DATE_FORMAT.format(reviews.getSendDate()));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    static Reviews getReviews(ResultSet resultSet) throws SQLException {
        return Reviews.builder()
                .id(resultSet.getInt("id"))
                .reviewText(resultSet.getString("review_text"))
                .rating(resultSet.getInt("rating"))
                .sendDate(resultSet.getDate("send_date"))
                .build();
    }
}
