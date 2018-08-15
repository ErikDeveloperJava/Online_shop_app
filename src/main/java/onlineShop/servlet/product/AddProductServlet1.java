package onlineShop.servlet.product;

import onlineShop.manager.CategoryAttributeManager;
import onlineShop.manager.CategoryManager;
import onlineShop.model.Category;
import onlineShop.model.CategoryAttribute;
import onlineShop.model.Product;
import onlineShop.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = "/product/add/1")
public class AddProductServlet1 extends HttpServlet implements Pages {

    private CategoryManager categoryManager;

    private CategoryAttributeManager attributeManager;

    @Override
    public void init() throws ServletException {
        categoryManager = (CategoryManager) getServletContext().getAttribute("categoryManager");
        attributeManager = (CategoryAttributeManager) getServletContext().getAttribute("categoryAttributeManager");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories",categoryManager.getAll());
        req.setAttribute("pageNumber",1);
        req.getRequestDispatcher(PRODUCT_ADD_PAGE1).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = getCheckedProduct(req);
        List<Integer> categories;
        if(product == null){
            req.setAttribute("categories",categoryManager.getAll());
            req.setAttribute("pageNumber",1);
            req.getRequestDispatcher(PRODUCT_ADD_PAGE1).forward(req,resp);
        }else if((categories = getCheckedCategories(req)).isEmpty()){
            req.setAttribute("categories",categoryManager.getAll());
            req.setAttribute("pageNumber",1);
            req.getRequestDispatcher(PRODUCT_ADD_PAGE1).forward(req,resp);
        }else {
            HttpSession session = req.getSession();
            session.setAttribute("product",product);
            session.setAttribute("categories",categories);
            List<CategoryAttribute> attributes = attributeManager.getAllByCategories(categories);
            session.setAttribute("attributes",attributes);
            req.setAttribute("categories",categoryManager.getAll());
            req.setAttribute("pageNumber",2);
            req.setAttribute("attributes",attributes);
            req.getRequestDispatcher(PRODUCT_ADD_PAGE1).forward(req,resp);
        }
    }

    private Product getCheckedProduct(HttpServletRequest request){
        Product product = new Product();
        double price;
        int quantity;
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");
        if(title == null || title.length() < 2 || title.length() >255){
            request.setAttribute("titleError","in field title wrong data");
            return null;
        }else {
            product.setTitle(title);
        }
        if(description == null || description.length() < 8){
            request.setAttribute("descriptionError","in field description wrong data");
            return null;
        }else {
            product.setDescription(description);
        }
        try {
            if((price = Double.parseDouble(priceStr)) <= 40 || price > 100000000){
                request.setAttribute("priceError","in field price wrong data");
                return null;
            }else {
                product.setPrice(price);
            }
        }catch (NumberFormatException e){
            request.setAttribute("priceError","in field price wrong data");
            return null;
        }
        try {
            if((quantity = Integer.parseInt(quantityStr)) <= 0 || quantity > 100000000){
                request.setAttribute("quantityError","in field quantity wrong data");
                return null;
            }else {
                product.setQuantity(quantity);
            }
        }catch (NumberFormatException e){
            request.setAttribute("quantityError","in field quantity wrong data");
            return null;
        }
        return product;
    }

    private List<Integer> getCheckedCategories(HttpServletRequest request){
        List<Integer> categories = new ArrayList<Integer>();
        String[] categoriesStr = request.getParameterValues("categories");
        if (categoriesStr == null || categoriesStr.length == 0){
            request.setAttribute("categoryError","please choose a category");
            return new ArrayList<Integer>();
        }
        for (String category : categoriesStr) {
            try {
                int id = Integer.parseInt(category);
                if(categoryManager.getById(id) == null){
                    return new ArrayList<Integer>();
                }else {
                    categories.add(id);
                }
            }catch (NumberFormatException e){
                return new ArrayList<Integer>();
            }
        }
        return categories;
    }
}
