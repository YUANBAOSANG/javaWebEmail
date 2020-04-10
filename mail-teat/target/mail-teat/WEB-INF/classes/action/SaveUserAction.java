package action;
import DAO.DAOException;
import DAO.DAOFactory;
import DAO.UserDAO;
import pojo.User;

public class SaveUserAction {
    public static void save(User user){
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            userDAO.insert(user);
        }catch (DAOException e){
            e.printStackTrace();
        }
    }
}
