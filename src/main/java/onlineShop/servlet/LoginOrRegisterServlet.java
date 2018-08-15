package onlineShop.servlet;

import onlineShop.manager.CategoryManager;
import onlineShop.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login-register")
public class LoginOrRegisterServlet extends HttpServlet implements Pages {

    private CategoryManager categoryManager;

    @Override
    public void init() throws ServletException {
        categoryManager = (CategoryManager) getServletContext().getAttribute("categoryManager");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories",categoryManager.getAll());
        req.getRequestDispatcher(LOGIN).forward(req,resp);
    }

}
