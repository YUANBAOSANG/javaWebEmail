package servlet;
import action.SaveUserAction;
import pojo.User;
import util.GetHead;
import util.SendEmail;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(urlPatterns = {"/RegisterServlet.do"})
public class RegisterServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //将request交给getHead工具类处理获取信息
        GetHead getHead = new  GetHead(request,getServletContext().getRealPath("/WEB-INF"));
        User user = getHead.getUser();
        SendEmail sendEmail = new SendEmail(getHead.getInputStream(),user);
        //为了用户体验所以这里开了一个线程

        sendEmail.start();
        //将用户存入数据库
        SaveUserAction.save(user);
        //转发到成功页面
        request.getRequestDispatcher("WEB-INF/Sucese.jsp").forward(request,response);
    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
