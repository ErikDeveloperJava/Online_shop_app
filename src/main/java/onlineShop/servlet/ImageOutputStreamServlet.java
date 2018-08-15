package onlineShop.servlet;

import onlineShop.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/resources/images/*")
public class ImageOutputStreamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageName = getImageName(req);
        if(imageName == null || !ImageUtil.isExists(imageName)){
            resp.sendRedirect("/");
        }else {
            resp.getOutputStream().write(ImageUtil.getBytes(imageName));
        }
    }

    private String getImageName(HttpServletRequest request){
        String[] array = request.getRequestURI().split("/");
        if(array.length == 5){
            return array[3] + "\\" + array[4];
        }else if(array.length == 6){
            return array[3] + "\\" + array[4] + "\\" + array[5];
        }else {
            return null;
        }
    }
}
