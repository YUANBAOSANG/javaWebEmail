package DAO;
import pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl extends BaseDAO implements UserDAO {
    private static final String GET_PRODUCTS_SQL = "SELECT username,password,email FROM users";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO users (username,password,email) VALUES(?,?,?)";
    @Override
    //查找
    public List<User> getProducts() throws DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConection();
            //预编译
            pStatement = connection.prepareStatement(GET_PRODUCTS_SQL);
            //执行
            resultSet = pStatement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
                System.out.println(user);
            }
        }catch (SQLException e){
            throw new DAOException("错误信息为："+e.getMessage());
        }finally {
            closeDBObject(resultSet,pStatement,connection);
        }
        return users;
    }

    @Override
    //插入
    public void insert(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getConection();
            pStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);
            pStatement.setString(1, user.getUserName());
            pStatement.setString(2, user.getPassword());
            pStatement.setString(3, user.getEmail());
            pStatement.execute();
        }catch (SQLException e){
            throw new DAOException("插入时出错 "+e.getMessage());
        }finally {
            closeDBObject(null,pStatement,connection);
        }

    }

}
