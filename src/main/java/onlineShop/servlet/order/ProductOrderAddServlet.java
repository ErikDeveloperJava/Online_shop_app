package onlineShop.servlet.order;

import onlineShop.manager.ProductManager;
import onlineShop.manager.ProductOrderManager;
import onlineShop.manager.UserManager;
import onlineShop.model.Product;
import onlineShop.model.ProductOrder;
import onlineShop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/product/order/add")
public class ProductOrderAddServlet extends HttpServlet {

    private ProductOrderManager productOrderManager;

    private ProductManager productManager;

    private UserManager userManager;

    @Override
    public void init() throws ServletException {
        productOrderManager = (ProductOrderManager) getServletContext().getAttribute("productOrderManager");
        productManager = (ProductManager) getServletContext().getAttribute("productManager");
        userManager = (UserManager) getServletContext().getAttribute("userManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = getProductId(req);
        Product product;
        User user = (User) req.getSession().getAttribute("user");
        if(productId == -1 || (product = productManager.getById(productId))==null || product.getQuantity() == 0
                || user.getBalance() < product.getPrice()){
            resp.sendRedirect("/");
        }else {
            productOrderManager.save(new ProductOrder(0,user,product,0),
                    userManager,productManager);
            resp.sendRedirect("/user/orders");
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
