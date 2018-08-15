package onlineShop.servlet.admin;

import com.mysql.cj.xdevapi.Session;
import onlineShop.manager.CategoryAttributeManager;
import onlineShop.manager.CategoryManager;
import onlineShop.model.Category;
import onlineShop.model.CategoryAttribute;
import onlineShop.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/admin/category/attr/2")
public class AddCategoryAttributeServlet2 extends HttpServlet implements Pages {

    private CategoryAttributeManager attributeManager;

    private CategoryManager categoryManager;

    @Override
    public void init() throws ServletException {
        attributeManager = (CategoryAttributeManager) getServletContext().getAttribute("categoryAttributeManager");
        categoryManager = (CategoryManager) getServletContext().getAttribute("categoryManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CategoryAttribute> attributes = getCheckedCategoryAttributes(req);
        if(attributes != null){
            attributeManager.saveAll(attributes);
        }
        resp.sendRedirect("/admin");
    }

    private List<CategoryAttribute> getCheckedCategoryAttributes(HttpServletRequest request){
        Integer catId = (Integer) request.getSession().getAttribute("catId");
        if(categoryManager.getById(catId) == null){
            return null;
        }
        List<CategoryAttribute> categoryAttributes = new ArrayList<CategoryAttribute>();
        String[] attributes = request.getParameterValues("attr");
        if(attributes.length == 0 || attributes.length > 10){
            return null;
        }else {
            for (String attr : attributes) {
                CategoryAttribute attribute = CategoryAttribute.builder()
                        .category(Category.builder().id(catId).build())
                        .name(attr)
                        .build();
                categoryAttributes.add(attribute);
            }
        }
        return categoryAttributes;
    }
}
