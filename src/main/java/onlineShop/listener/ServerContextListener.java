package onlineShop.listener;

import onlineShop.db.ConnectionProvider;
import onlineShop.manager.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServerContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute("categoryManager",new CategoryManager());
        servletContextEvent.getServletContext().setAttribute("imageManager",new ImageManager());
        servletContextEvent.getServletContext().setAttribute("productCategoryManager",new ProductCategoryManager());
        servletContextEvent.getServletContext().setAttribute("productCartManager",new ProductCartManager());
        servletContextEvent.getServletContext().setAttribute("productManager",new ProductManager());
        servletContextEvent.getServletContext().setAttribute("productOrderManager",new ProductOrderManager());
        servletContextEvent.getServletContext().setAttribute("reviewsManager",new ReviewsManager());
        servletContextEvent.getServletContext().setAttribute("userManager",new UserManager());
        servletContextEvent.getServletContext().setAttribute("categoryAttributeManager",new CategoryAttributeManager());
        servletContextEvent.getServletContext().setAttribute("attributeValueManager",new AttributeValueManager());
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionProvider.getInstance().clear();
    }
}
