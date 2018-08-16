package onlineShop.filter;

import onlineShop.form.ImageForm;
import onlineShop.manager.CategoryManager;
import onlineShop.util.PropertiesUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebFilter(urlPatterns = {"/register","/product/add/2","/product/image/upload"},filterName = "filter2")
public class MultipartRequestParseFilter extends AbstractFilter {

    private static final String FILE_NAME = "C:\\Users\\Erik\\IdeaProjects\\java-web\\OnlineShop\\src\\main\\resources\\file-config.properties";

    private Properties properties;


    public void init(FilterConfig filterConfig) throws ServletException {
        properties = PropertiesUtil.load(FILE_NAME);
    }

    public void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (request.getMethod().equals("GET")) {
            filterChain.doFilter(request, response);
        } else {
            if (!ServletFileUpload.isMultipartContent(request)) {
                request.setAttribute("imageError", "request does not multipart");
                request.getRequestDispatcher(LOGIN).forward(request, response);
            } else {
                ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
                upload.setSizeMax(Integer.parseInt(properties.getProperty("request.max.size")));
                upload.setFileSizeMax(Integer.parseInt(properties.getProperty("file.max.size")));
                try {
                    List<FileItem> items = upload.parseRequest(request);
                    Map<String, String> params = new HashMap<String, String>();
                    ImageForm form = new ImageForm();
                    for (FileItem item : items) {
                        if (item.isFormField()) {
                            params.put(item.getFieldName(), item.getString());
                        } else {
                            form.setBytes(item.get());
                            form.setContentType(item.getContentType());
                            form.setName(item.getName());
                        }
                    }
                    request.setAttribute("params",params);
                    request.setAttribute("form",form);
                    filterChain.doFilter(request,response);
                } catch (FileUploadException e) {
                    request.setAttribute("imageError", "invalid multipart request");
                    request.getRequestDispatcher(LOGIN).forward(request, response);
                }
            }
        }
    }

    public void destroy() {

    }
}
