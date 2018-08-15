package onlineShop.servlet;

import onlineShop.manager.ProductManager;
import onlineShop.manager.ReviewsManager;
import onlineShop.model.Product;
import onlineShop.model.Reviews;
import onlineShop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = "/review/add")
public class ReviewsAddServlet extends HttpServlet {

    private ReviewsManager reviewsManager;

    private ProductManager productManager;

    @Override
    public void init() throws ServletException {
        productManager = (ProductManager) getServletContext().getAttribute("productManager");
        reviewsManager = (ReviewsManager) getServletContext().getAttribute("reviewsManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Reviews reviews = getCheckedReviews(req);
        if(reviews == null){
            resp.sendRedirect("/");
        }else {
            reviews.setSendDate(new Date());
            reviewsManager.save(reviews);
            resp.sendRedirect("/product/one/" + reviews.getProduct().getId());
        }
    }

    private Reviews getCheckedReviews(HttpServletRequest request){
        Reviews reviews=  new Reviews();
        String productIdStr = request.getParameter("productId");
        User user = (User) request.getSession().getAttribute("user");
        try {
            int id = Integer.parseInt(productIdStr);
            Product product;
            if(id <= 0 || (product = productManager.getById(id)) == null || product.getUser().getId() == user.getId()){
                return null;
            }else {
                reviews.setUser(user);
                reviews.setProduct(product);
            }
        }catch (NumberFormatException e){
            return null;
        }
        String text = request.getParameter("text");
        if(text == null || text.length() < 2){
            return null;
        }else {
            reviews.setReviewText(text);
        }
        String ratingStr = request.getParameter("score");
        try {
            if("".equals(ratingStr)){
                ratingStr = "0";
            }
            double rating = Double.parseDouble(ratingStr);
            if(rating <= 0 || rating > 5){
                return null;
            }else {
                reviews.setRating(rating);
            }
        }catch (NumberFormatException e){
            return null;
        }
        return reviews;
    }
}
