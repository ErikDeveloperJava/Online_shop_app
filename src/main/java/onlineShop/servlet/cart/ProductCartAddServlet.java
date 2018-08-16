package onlineShop.servlet.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import onlineShop.manager.ProductCartManager;
import onlineShop.manager.ProductManager;
import onlineShop.model.Product;
import onlineShop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/product/cart/add")
public class ProductCartAddServlet extends HttpServlet {

    private ProductManager productManager;

    private ProductCartManager productCartManager;

    @Override
    public void init() throws ServletException {
        productCartManager = (ProductCartManager) getServletContext().getAttribute("productCartManager");
        productManager = (ProductManager) getServletContext().getAttribute("productManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json");
        int productId = getProductId(req);
        User user = (User) req.getSession().getAttribute("user");
        Product product;
        if(productId != -1 && (product = productManager.getById(productId)) != null && !productCartManager.exists(user.getId(),productId)){
            productCartManager.save(user.getId(),productId);
            objectMapper.writeValue(resp.getWriter(),product);
        }else {
            objectMapper.writeValue(resp.getWriter(),Product.builder().build());
        }
    }

    private int getProductId(HttpServletRequest request){
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
            if(productId <= 0){
                productId = -1;
            }
        }catch (NumberFormatException e){
            productId = -1;
        }
        return productId;
    }
}
