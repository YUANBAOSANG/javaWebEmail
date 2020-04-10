package util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import pojo.User;

import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;


public class GetHead {
    private User user = new User();
    public User getUser() {
        return user;
    }
    private HttpServletRequest req;
    private String path;
    private InputStream inputStream;
    public GetHead(HttpServletRequest req,String path){
        this.req = req;
        this.path = path;
        getHead();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    private void getHead(){
        if(!ServletFileUpload.isMultipartContent(req)){
            //普通表单不处理
            return;
        }
        try {
            //创建对应文件夹
            String path = creatFolder("temp");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //设置临时存放目录，防止存入默认系统盘
            factory.setRepository(new File(path));
            //设置内存缓存区，超过则写入临时文件
            factory.setSizeThreshold(4096);
            ServletFileUpload upload = new ServletFileUpload(factory);
            //设置编码格式，防止中文乱码
            upload.setHeaderEncoding("utf-8");
            List<FileItem> fileItems = upload.parseRequest(req);
            for (FileItem fileItem : fileItems) {
                String uploadFileName = fileItem.getName();
                if(uploadFileName==null){
                    String fled = fileItem.getFieldName();
                    String value = fileItem.getString("utf-8");
                    selectSet(fled,value);
                }
                if(uploadFileName==null||uploadFileName.trim().equals("")||!uploadFileName.substring(uploadFileName.lastIndexOf(".")+1).equals("jpg")){
                    continue;
                }
                inputStream = fileItem.getInputStream();
                //saveImage(inputStream);
                //删除临时文件
                fileItem.delete();
                break;
            }
        } catch (FileUploadException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectSet(String fled,String value){
        if(fled.equals("username")){
            req.setAttribute("username",value);
            user.setUserName(value);
        }else if("password".equals(fled)){
            user.setPassword(value);
        }else if("email".equals(fled)){
            user.setEmail(value);
        }
    }

    private String creatFolder(String path_1){
        String path = this.path+"/"+path_1;
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        return path;
    }

    private void saveImage(InputStream inputStream) throws IOException {
        //如想保存下上传图片可以在上面调用该类
        FileOutputStream fos = new FileOutputStream(path+"image.jpg");
        byte[] buffer = new byte[1024*1024];
        int len =0;
        while((len=inputStream.read(buffer))>0){
            fos.write(buffer,0,len);
        }
        fos.close();
        inputStream.close();
    }

}
