package onlineShop.filter;

import onlineShop.model.User;
import onlineShop.model.UserRole;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/product/add/1","/product/add/2","/admin/*",
"/review/add","/product/order/add","/product/cart/add","/product/image/upload","/product/cart/delete",
"/user/orders","/product/order/delete","/product/delete"},
        filterName = "filter1")
public class AuthorizationFilter extends AbstractFilter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            response.sendRedirect("/login-register");
        }else {
            if(request.getRequestURI().split("/")[1].equals("admin")){
                if(user.getRole().equals(UserRole.USER)) {
                    response.sendRedirect("/");
                }else {
                    filterChain.doFilter(request,response);
                }
            }else {
                if(user.getRole().equals(UserRole.ADMIN)) {
                    response.sendRedirect("/admin");
                }else {
                    filterChain.doFilter(request,response);
                }
            }
        }
    }

    public void destroy() {

    }
}
