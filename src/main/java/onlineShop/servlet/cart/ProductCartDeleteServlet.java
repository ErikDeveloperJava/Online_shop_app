package onlineShop.servlet.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import onlineShop.manager.ProductCartManager;
import onlineShop.manager.ProductManager;
import onlineShop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/product/cart/delete")
public class ProductCartDeleteServlet extends HttpServlet {

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
        if(productId == 1 || !productCartManager.exists(user.getId(),productId)){
            objectMapper.writeValue(resp.getWriter(),false);
        }else {
            productCartManager.delete(user.getId(),productId);
            objectMapper.writeValue(resp.getWriter(),true);
        }
    }

    private int getProductId(HttpServletRequest request){
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
            if(productId <= 0 || productManager.getById(productId) == null){
                productId = -1;
            }
        }catch (NumberFormatException e){
            productId = -1;
        }
        return productId;
    }
}
