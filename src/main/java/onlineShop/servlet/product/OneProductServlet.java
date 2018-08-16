package onlineShop.servlet.product;

import onlineShop.manager.*;
import onlineShop.model.Product;
import onlineShop.model.User;
import onlineShop.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/product/one/*")
public class OneProductServlet extends HttpServlet implements Pages {

    private CategoryManager categoryManager;

    private ProductManager productManager;

    private ImageManager imageManager;

    private ReviewsManager reviewsManager;

    private AttributeValueManager attributeValueManager;

    private ProductCartManager productCartManager;

    private ProductOrderManager productOrderManager;

    @Override
    public void init() throws ServletException {
        productManager = (ProductManager) getServletContext().getAttribute("productManager");
        categoryManager = (CategoryManager) getServletContext().getAttribute("categoryManager");
        imageManager = (ImageManager) getServletContext().getAttribute("imageManager");
        reviewsManager = (ReviewsManager) getServletContext().getAttribute("reviewsManager");
        attributeValueManager = (AttributeValueManager) getServletContext().getAttribute("attributeValueManager");
        productCartManager = (ProductCartManager) getServletContext().getAttribute("productCartManager");
        productOrderManager = (ProductOrderManager) getServletContext().getAttribute("productOrderManager");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = getProductId(req);
        Product product;
        User user = (User) req.getSession().getAttribute("user");
        if(productId == -1 || (product = productManager.getById(productId)) == null){
            resp.sendRedirect("/");
        }else {
            req.setAttribute("product",product);
            req.setAttribute("productCategories",categoryManager.getAllByProductId(productId));
            req.setAttribute("images",imageManager.getAllByProductId(productId));
            req.setAttribute("reviewsCount",reviewsManager.countByProductId(productId));
            req.setAttribute("attributes",attributeValueManager.getAllByProductId(productId));
            req.setAttribute("likeProducts",productManager.getLikeProducts(productId,String.valueOf(product.getTitle().charAt(0))));
            req.setAttribute("reviewsList",reviewsManager.getAllByProductId(productId));
            if(user != null){
                req.setAttribute("cartCount",productCartManager.countByUserId(user.getId()));
                req.setAttribute("ordersCount",productOrderManager.countByUserId(user.getId()));
                List<Product> cartProducts = productManager.getAllCartProductByUserId(user.getId());
                req.setAttribute("cartProducts",cartProducts);
                req.setAttribute("sum",getAllProductsPriceSum(cartProducts));
            }
            req.getRequestDispatcher(PRODUCT).forward(req,resp);
        }
    }

    private int getProductId(HttpServletRequest request){
        String[] array = request.getRequestURI().split("/");
        if(array.length != 4){
            return -1;
        }else {
            int id;
            try {
                id = Integer.parseInt(array[3]);
                if(id <= 0){
                    id = -1;
                }
            }catch (NumberFormatException e){
                id = -1;
            }
            return id;
        }
    }


    private int getAllProductsPriceSum(List<Product> products){
        int sum=0;
        for (Product product : products) {
            sum+=product.getPrice();
        }
        return sum;
    }
}
