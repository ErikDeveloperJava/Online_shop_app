package onlineShop.servlet.admin;

import onlineShop.manager.CategoryManager;
import onlineShop.model.Category;
import onlineShop.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/category/add")
public class CategoryAddServlet extends HttpServlet implements Pages {

    private CategoryManager categoryManager;

    @Override
    public void init() throws ServletException {
        categoryManager = (CategoryManager) getServletContext().getAttribute("categoryManager");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(CATEGORY_ADD).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if(name == null || name.length() < 4 || name.length() > 255){
            req.setAttribute("nameError","in field name wrong data");
            req.getRequestDispatcher(CATEGORY_ADD).forward(req,resp);
        }else {
            categoryManager.save(new Category(0,name));
            resp.sendRedirect("/admin");
        }
    }
}
