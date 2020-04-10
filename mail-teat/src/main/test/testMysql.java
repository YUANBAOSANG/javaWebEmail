import action.SaveUserAction;
import pojo.User;

public class testMysql {
    public static void main(String[] args) {
        User user = new User();
        user.setEmail("");
        user.setPassword("1111");
        user.setUserName("wwwww");
        SaveUserAction saveUserAction = new SaveUserAction();
        saveUserAction.save(user);
    }
}
