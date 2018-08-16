package onlineShop.servlet;

import onlineShop.manager.ProductCartManager;
import onlineShop.manager.ProductManager;
import onlineShop.manager.ProductOrderManager;
import onlineShop.model.Product;
import onlineShop.model.User;
import onlineShop.model.UserRole;
import onlineShop.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "")
public class MainServlet extends HttpServlet implements Pages {


    private ProductManager productManager;

    private ProductCartManager productCartManager;

    private ProductOrderManager productOrderManager;

    @Override
    public void init() throws ServletException {
        productManager = (ProductManager) getServletContext().getAttribute("productManager");
        productCartManager = (ProductCartManager) getServletContext().getAttribute("productCartManager");
        productOrderManager = (ProductOrderManager) getServletContext().getAttribute("productOrderManager");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null && user.getRole().equals(UserRole.ADMIN)){
            resp.sendRedirect("/admin");
            return;
        }

        if(user != null){
            req.setAttribute("cartCount",productCartManager.countByUserId(user.getId()));
            req.setAttribute("ordersCount",productOrderManager.countByUserId(user.getId()));
            List<Product> cartProducts = productManager.getAllCartProductByUserId(user.getId());
            req.setAttribute("cartProducts",cartProducts);
            req.setAttribute("sum",getAllProductsPriceSum(cartProducts));
        }
        req.getRequestDispatcher(INDEX).forward(req,resp);
    }



    private int getAllProductsPriceSum(List<Product> products){
        int sum=0;
        for (Product product : products) {
            sum+=product.getPrice();
        }
        return sum;
    }
}
