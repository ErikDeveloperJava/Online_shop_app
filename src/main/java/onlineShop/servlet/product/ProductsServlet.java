package onlineShop.servlet.product;

import onlineShop.manager.ProductManager;
import onlineShop.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/products")
public class ProductsServlet extends HttpServlet implements Pages {

    private static final int PAGE_SIZE = 8;

    private ProductManager productManager;

    @Override
    public void init() throws ServletException {
        productManager = (ProductManager) getServletContext().getAttribute("productManager");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int length = getPaginationLength();
        int pageNumber = getPageNumber(req,length);
        req.setAttribute("products",productManager.getAllByLimit(pageNumber * PAGE_SIZE,PAGE_SIZE));
        req.setAttribute("pageNumber",pageNumber);
        req.setAttribute("length",length);
        req.getRequestDispatcher(PRODUCTS).forward(req,resp);

    }

    private int getPaginationLength() {
        int count = productManager.countALl();
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
}
