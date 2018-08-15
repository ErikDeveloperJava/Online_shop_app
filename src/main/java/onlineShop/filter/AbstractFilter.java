package onlineShop.filter;

import onlineShop.pages.Pages;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractFilter implements Filter,Pages {
    public abstract void init(FilterConfig filterConfig) throws ServletException ;

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filter((HttpServletRequest)servletRequest,(HttpServletResponse)servletResponse,filterChain);
    }

    public abstract void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;

    public abstract void destroy() ;
}
