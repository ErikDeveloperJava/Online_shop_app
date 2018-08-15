package onlineShop.filter;

import onlineShop.manager.CategoryManager;
import onlineShop.manager.UserManager;
import onlineShop.model.User;
import onlineShop.model.UserRole;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/login")
public class AuthenticationFilter extends AbstractFilter {

    private UserManager userManager;

    private CategoryManager categoryManager;

    public void init(FilterConfig filterConfig) throws ServletException {
        userManager = (UserManager) filterConfig.getServletContext().getAttribute("userManager");
        categoryManager = (CategoryManager) filterConfig.getServletContext().getAttribute("categoryManager");
    }

    public void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if(request.getMethod().equals("GET")){
            filterChain.doFilter(request,response);
        }else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = userManager.getByUsername(username);
            if(user == null || !user.getPassword().equals(password)){
                request.setAttribute("categories",categoryManager.getAll());
                request.setAttribute("loginError","you entered wrong username or password");
                request.getRequestDispatcher(LOGIN).forward(request,response);
            }else{
                request.getSession().setAttribute("user",user);
                if(user.getRole().equals(UserRole.ADMIN)){
                    response.sendRedirect("/admin");
                }else {
                    response.sendRedirect("/");
                }
            }
        }
    }

    public void destroy() {

    }
}
