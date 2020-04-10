package DAO;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDAO implements DAO {
    @Override
    public Connection getConection() throws DAOException {
        //获取连接池资源
        DataSource dataSource = DataSourceCache.getInstance().getDataSource();
        try{
            //如果成功获取返回连接资源
            return dataSource.getConnection();
        }catch (Exception e){
            e.printStackTrace();
            throw new DAOException();
        }
    }
    protected void closeDBObject(ResultSet resultSet, Statement statement, Connection connection){
        //关闭所有资源
        if(resultSet != null){
            try{
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
