package onlineShop.servlet.product;

import onlineShop.manager.ProductManager;
import onlineShop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/product/delete")
public class ProductDeleteServlet extends HttpServlet {

    private ProductManager productManager;

    @Override
    public void init() throws ServletException {
        productManager = (ProductManager) getServletContext().getAttribute("productManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = getProductId(req);
        User user = (User) req.getSession().getAttribute("user");
        if(productId != -1){
            productManager.delete(user,productId);
        }
        resp.sendRedirect("/");
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
