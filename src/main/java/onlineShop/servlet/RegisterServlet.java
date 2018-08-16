package onlineShop.servlet;

import onlineShop.form.ImageForm;
import onlineShop.manager.UserManager;
import onlineShop.model.User;
import onlineShop.model.UserRole;
import onlineShop.pages.Pages;
import onlineShop.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet implements Pages {

    private UserManager userManager;

    @Override
    public void init() throws ServletException {
        userManager = (UserManager) getServletContext().getAttribute("userManager");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ImageForm form = (ImageForm) req.getAttribute("form");
        Map<String,String> params = (Map<String, String>) req.getAttribute("params");
        User user = getCheckedUser(params,req);
        if(user == null){
            req.getRequestDispatcher(LOGIN).forward(req,resp);
        }else if(userManager.getByUsername(user.getUsername()) != null){
            req.setAttribute("usernameError","user with username '" + user.getUsername() +"' already exists");
            req.getRequestDispatcher(LOGIN).forward(req,resp);
        }else if(form.getBytes().length == 0){
            req.setAttribute("imageError","image file is empty");
            req.getRequestDispatcher(LOGIN).forward(req,resp);
        }else if(!ImageUtil.isValidFormat(form.getContentType())){
            req.setAttribute("imageError","wrong image format");
            req.getRequestDispatcher(LOGIN).forward(req,resp);
        }else {
            form.setName(System.currentTimeMillis() + form.getName());
            user.setRole(UserRole.USER);
            user.setBalance(10000);
            user.setImgUrl(user.getUsername() + "/" + form.getName());
            userManager.save(user,form);
            resp.sendRedirect("/login-register");
        }
    }

    private User getCheckedUser(Map<String,String> params,HttpServletRequest request){
        User user = new User();
        String name = params.get("name");
        String surname = params.get("surname");
        String username = params.get("username");
        String password = params.get("password");
        if(name == null || name.length() < 4 || name.length() > 255){
            request.setAttribute("nameError","in field name wrong data");
            return null;
        }else {
            user.setName(name);
        }
        if(surname == null || surname.length() < 4 || surname.length() > 255){
            request.setAttribute("surnameError","in field surname wrong data");
            return null;
        }else {
            user.setSurname(surname);
        }
        if(username == null || username.length() < 4 || username.length() > 255){
            request.setAttribute("usernameError","in field username wrong data");
            return null;
        }else {
            user.setUsername(username);
        }
        if(password == null || password.length() < 4 || password.length() > 255){
            request.setAttribute("passwordError","in field password wrong data");
            return null;
        }else {
            user.setPassword(password);
        }
        return user;
    }
}
