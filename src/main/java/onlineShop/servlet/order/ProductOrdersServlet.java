package onlineShop.servlet.order;

import onlineShop.manager.*;
import onlineShop.model.Product;
import onlineShop.model.ProductOrder;
import onlineShop.model.User;
import onlineShop.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/user/orders")
public class ProductOrdersServlet extends HttpServlet implements Pages {

    private ProductManager productManager;


    private ProductCartManager productCartManager;

    private ProductOrderManager productOrderManager;

    private AttributeValueManager attributeValueManager;

    @Override
    public void init() throws ServletException {
        productManager = (ProductManager) getServletContext().getAttribute("productManager");
        productCartManager = (ProductCartManager) getServletContext().getAttribute("productCartManager");
        productOrderManager = (ProductOrderManager) getServletContext().getAttribute("productOrderManager");
        attributeValueManager = (AttributeValueManager) getServletContext().getAttribute("attributeValueManager");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("cartCount",productCartManager.countByUserId(user.getId()));
        req.setAttribute("ordersCount",productOrderManager.countByUserId(user.getId()));
        List<Product> cartProducts = productManager.getAllCartProductByUserId(user.getId());
        req.setAttribute("cartProducts",cartProducts);
        req.setAttribute("sum",getAllProductsPriceSum(cartProducts));
        List<ProductOrder> orders = productOrderManager.getAllByUserId(user.getId());
        setAttributeValue(orders);
        req.setAttribute("orders",orders);
        req.getRequestDispatcher(ORDERS).forward(req,resp);
    }


    private void setAttributeValue(List<ProductOrder> productOrders){
        for (ProductOrder productOrder : productOrders) {
            productOrder.getProduct().setValues(attributeValueManager.getAllByProductId(productOrder.getProduct().getId()));
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
