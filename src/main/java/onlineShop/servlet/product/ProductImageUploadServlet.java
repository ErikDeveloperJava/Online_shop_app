package onlineShop.servlet.product;

import onlineShop.form.ImageForm;
import onlineShop.manager.ImageManager;
import onlineShop.manager.ProductManager;
import onlineShop.model.Image;
import onlineShop.model.Product;
import onlineShop.model.User;
import onlineShop.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/product/image/upload")
public class ProductImageUploadServlet extends HttpServlet {

    private ImageManager imageManager;

    private ProductManager productManager;

    @Override
    public void init() throws ServletException {
        imageManager = (ImageManager) getServletContext().getAttribute("imageManager");
        productManager = (ProductManager) getServletContext().getAttribute("productManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String> params = (Map<String, String>) req.getAttribute("params");
        ImageForm form = (ImageForm) req.getAttribute("form");
        Product product;
        int productId = getProductId(params);
        if(productId == -1 || (product = productManager.getById(productId)) == null || form.getBytes().length == 0 ||
                !ImageUtil.isValidFormat(form.getContentType())){
            resp.sendRedirect("/");
        }else {
            User user = (User) req.getSession().getAttribute("user");
            product.setUser(user);
            form.setName(System.currentTimeMillis() + form.getName().trim());
            String imageName = user.getUsername() + "/" + product.getId() + "/" + form.getName();
            imageManager.save(new Image(0,imageName,product),form);
            resp.sendRedirect("/product/one/" + productId);
        }
    }

    private int getProductId(Map<String,String> params){
        String productIdStr = params.get("productId");
        int productId;
        try {
            productId = Integer.parseInt(productIdStr);
            if(productId <= 0){
                productId = -1;
            }
        }catch (NumberFormatException e){
            productId = -1;
        }
        return productId;
    }
}
