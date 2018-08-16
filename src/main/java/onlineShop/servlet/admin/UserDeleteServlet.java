package onlineShop.servlet.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import onlineShop.manager.UserManager;
import onlineShop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/user/delete")
public class UserDeleteServlet extends HttpServlet {

    private UserManager userManager;

    @Override
    public void init() throws ServletException {
        userManager = (UserManager) getServletContext().getAttribute("userManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json");
        int userId = getUserId(req);
        User user;
        if(userId  != -1 && (user = userManager.getById(userId)) != null){
            userManager.deleteById(user);
            objectMapper.writeValue(resp.getWriter(),true);
        }else {
            objectMapper.writeValue(resp.getWriter(),false);
        }
    }

    private int getUserId(HttpServletRequest request){
        int id;
        try {
            id = Integer.parseInt(request.getParameter("userId"));
            if(id <= 0){
                id = -1;
            }
        }catch (NumberFormatException e){
            id = -1;
        }
        return id;
    }
}
