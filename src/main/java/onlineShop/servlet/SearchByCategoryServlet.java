package onlineShop.servlet;

import onlineShop.manager.CategoryManager;
import onlineShop.manager.ProductCartManager;
import onlineShop.manager.ProductManager;
import onlineShop.manager.ProductOrderManager;
import onlineShop.model.Category;
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

@WebServlet(urlPatterns = "/category/*")
public class SearchByCategoryServlet extends HttpServlet implements Pages {

    private static final int PAGE_SIZE = 8;

    private CategoryManager categoryManager;

    private ProductManager productManager;

    private ProductCartManager productCartManager;

    private ProductOrderManager productOrderManager;

    @Override
    public void init() throws ServletException {
        categoryManager = (CategoryManager) getServletContext().getAttribute("categoryManager");
        productManager = (ProductManager) getServletContext().getAttribute("productManager");
        productCartManager = (ProductCartManager) getServletContext().getAttribute("productCartManager");
        productOrderManager = (ProductOrderManager) getServletContext().getAttribute("productOrderManager");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int catId = getCategoryId(req);
        Category category;
        if(catId == -1 || (category = categoryManager.getById(catId))== null){
            resp.sendRedirect("/");
            return;
        }
        User user = (User) req.getSession().getAttribute("user");
        int length = getPaginationLength(catId);
        int pageNumber = getPageNumber(req,length);
        req.setAttribute("products",productManager.getAllByCategoryId(catId,pageNumber * PAGE_SIZE,PAGE_SIZE));
        req.setAttribute("title","search by category: " + category.getName());
        req.setAttribute("pageNumber",pageNumber);
        req.setAttribute("length",length);
        req.setAttribute("categories",categoryManager.getAll());
        if(user != null){
            req.setAttribute("cartCount",productCartManager.countByUserId(user.getId()));
            req.setAttribute("ordersCount",productOrderManager.countByUserId(user.getId()));
            List<Product> cartProducts = productManager.getAllCartProductByUserId(user.getId());
            req.setAttribute("cartProducts",cartProducts);
            req.setAttribute("sum",getAllProductsPriceSum(cartProducts));
        }
        req.getRequestDispatcher(SEARCH_PRODUCT).forward(req,resp);
    }

    private int getPaginationLength(int catId) {
        int count = productManager.countByCategoryId(catId);
        int length;
        if(count <= PAGE_SIZE){
            length = 1;
        } else if(count % PAGE_SIZE != 0){
            length = (count/PAGE_SIZE) + 1;
        }else {
            length = (count/PAGE_SIZE);
        }
        return length;
    }

    private int getPageNumber(HttpServletRequest req,int length) {
        String strNumber = req.getParameter("page");
        int pageNumber;
        try {
            pageNumber = Integer.parseInt(strNumber);
            if(pageNumber < 0 || pageNumber >= length){
                pageNumber = 0;
            }
        }catch (NumberFormatException e){
            pageNumber =0;
        }
        return pageNumber;
    }

    private int getAllProductsPriceSum(List<Product> products){
        int sum=0;
        for (Product product : products) {
            sum+=product.getPrice();
        }
        return sum;
    }

    private int getCategoryId(HttpServletRequest req){
        int id;
        try {
            id = Integer.parseInt(req.getRequestURI().split("/")[2]);
            if(id <= 0){
                return -1;
            }
        }catch (NumberFormatException e){
            id = -1;
        }
        return id;
    }
}
