package onlineShop.servlet.admin;

import onlineShop.manager.CategoryManager;
import onlineShop.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/category/attr/1")
public class AddCategoryAttributeServlet1 extends HttpServlet implements Pages {

    private CategoryManager categoryManager;

    @Override
    public void init() throws ServletException {
        categoryManager = (CategoryManager) getServletContext().getAttribute("categoryManager");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories",categoryManager.getAll());
        req.setAttribute("page",1);
        req.getRequestDispatcher(CATEGORY_ATRR_ADD).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("catId"));
            if(categoryManager.getById(id) != null){
                int count = Integer.parseInt(req.getParameter("count"));
                if(count > 0 && count <= 10){
                    req.getSession().setAttribute("catId",id);
                    req.setAttribute("page",2);
                    req.setAttribute("count",count);
                    req.getRequestDispatcher(CATEGORY_ATRR_ADD).forward(req,resp);
                    return;
                }
            }
        }catch (NumberFormatException e){ }
        resp.sendRedirect("/admin/category/attr/1");
    }
}
