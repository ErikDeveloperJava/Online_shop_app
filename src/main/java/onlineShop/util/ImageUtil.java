package onlineShop.util;

import lombok.Cleanup;

import java.io.*;

public class ImageUtil {

    private static final String FILE_NAME = "C:\\Users\\Erik\\IdeaProjects\\java-web\\OnlineShop\\src\\main\\resources\\file-config.properties";
    private static final String ROOT_PATH;
    private static final String IMAGE_FORMAT[] = {"image/jpeg","image/png"};

    static {
        ROOT_PATH = PropertiesUtil.load(FILE_NAME).getProperty("root.path");
        File file = new File(ROOT_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public static boolean isValidFormat(String format){
        for (String imgFormat : IMAGE_FORMAT) {
            if(imgFormat.equals(format)){
               return true;
            }
        }
        return false;
    }

    public static void delete(String fileName){
        File file = new File(ROOT_PATH,fileName);
        if(file.exists()){
            file.delete();
        }
    }

    public static boolean isExists(String  fileName){
        File file = new File(ROOT_PATH,fileName);
        return file.exists();
    }

    public static void save(String parent,String imageName,byte[] bytes){
        File file = new File(ROOT_PATH,parent);
        if(!file.exists()){
            if(!file.mkdir()){
                throw new RuntimeException("file failed create");
            }
        }
        try {
            @Cleanup FileOutputStream out = new FileOutputStream(new File(file,imageName));
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static byte[] getBytes(String imageName){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[8000];
        int length;
        try {
            @Cleanup FileInputStream input = new FileInputStream(new File(ROOT_PATH,imageName));
            while ((length = input.read(buff)) != -1){
                byteArrayOutputStream.write(buff,0,length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void deleteAll(String parent){
        File file = new File(ROOT_PATH,parent);
        delete(file);
    }

    private static void delete(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File file1 : files) {
                delete(file1);
            }
            file.delete();
        }else {
            file.delete();
        }
    }
}
