package onlineShop.servlet.cart;

import onlineShop.manager.ProductCartManager;
import onlineShop.manager.ProductManager;
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
        int productId = getProductId(req);
        User user = (User) req.getSession().getAttribute("user");
        if(productId != -1 && !productCartManager.exists(user.getId(),productId)){
            productCartManager.save(user.getId(),productId);
            resp.getWriter().print(true);
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
