package onlineShop.servlet.product;

import onlineShop.form.ImageForm;
import onlineShop.manager.AttributeValueManager;
import onlineShop.manager.ImageManager;
import onlineShop.manager.ProductCategoryManager;
import onlineShop.manager.ProductManager;
import onlineShop.model.AttributeValue;
import onlineShop.model.CategoryAttribute;
import onlineShop.model.Product;
import onlineShop.model.User;
import onlineShop.pages.Pages;
import onlineShop.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/product/add/2")
public class AddProductServlet2 extends HttpServlet implements Pages {


    private ProductManager productManager;

    private ProductCategoryManager productCategoryManager;

    private AttributeValueManager attributeValueManager;

    private ImageManager imageManager;

    @Override
    public void init() throws ServletException {
        productManager = (ProductManager) getServletContext().getAttribute("productManager");
        productCategoryManager = (ProductCategoryManager) getServletContext().getAttribute("productCategoryManager");
        attributeValueManager = (AttributeValueManager) getServletContext().getAttribute("attributeValueManager");
        imageManager = (ImageManager) getServletContext().getAttribute("imageManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Product product = (Product) session.getAttribute("product");
        List<Integer> categories = (List<Integer>) session.getAttribute("categories");
        List<CategoryAttribute> attributes = (List<CategoryAttribute>) session.getAttribute("attributes");
        if (product == null || categories == null || attributes == null) {
            resp.sendRedirect("/product/add/1");
        } else {
            Map<String, String> params = (Map<String, String>) req.getAttribute("params");
            ImageForm form = (ImageForm) req.getAttribute("form");
            List<AttributeValue> attributeValues = getAttributeValues(params, attributes, req);
            if (attributeValues.isEmpty()) {
                req.setAttribute("attributes", attributes);
                req.setAttribute("pageNumber", 2);
                req.getRequestDispatcher(PRODUCT_ADD_PAGE1).forward(req, resp);
            } else if (form.getBytes().length == 0) {
                req.setAttribute("imageError", "image file is empty");
                req.setAttribute("attributes", attributes);
                req.setAttribute("pageNumber", 2);
                req.getRequestDispatcher(PRODUCT_ADD_PAGE1).forward(req, resp);
            } else if (!ImageUtil.isValidFormat(form.getContentType())) {
                req.setAttribute("imageError", "invalid image format");
                req.setAttribute("attributes", attributes);
                req.setAttribute("pageNumber", 2);
                req.getRequestDispatcher(PRODUCT_ADD_PAGE1).forward(req, resp);
            } else {
                User user = (User) session.getAttribute("user");
                form.setName(System.currentTimeMillis() + form.getName().trim());
                product.setUser(user);
                product.setImgUrl("");
                product.setCreatedDate(new Date());
                try {
                    productManager
                            .save(product, form, categories, attributeValues,
                                    productCategoryManager, attributeValueManager, imageManager);
                } catch (Exception e) {
                    //
                }
                session.removeAttribute("product");
                session.removeAttribute("categories");
                session.removeAttribute("attributes");
                resp.sendRedirect("/product/one/" + product.getId());

            }
        }
    }

    private List<AttributeValue> getAttributeValues
            (Map<String, String> params, List<CategoryAttribute> attributes,
             HttpServletRequest request) {
        if (params.isEmpty() || params.size() != attributes.size()) {
            request.setAttribute("attrError", "invalid data");
            return new ArrayList<AttributeValue>();
        }
        List<AttributeValue> attributeValues = new ArrayList<AttributeValue>();
        for (CategoryAttribute categoryAttribute : attributes) {
            String value = params.get(String.valueOf(categoryAttribute.getId()));
            if (value == null || value.equals("")) {
                value = "************";
            }
            AttributeValue attributeValue = AttributeValue.builder()
                    .categoryAttribute(categoryAttribute)
                    .value(value)
                    .build();
            attributeValues.add(attributeValue);
        }
        return attributeValues;
    }
}
