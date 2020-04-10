package DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceCache {
    private static DataSourceCache instance;
    private DataSource dataSource;
    static {
        instance = new DataSourceCache();
    }
    private DataSourceCache(){
        Context context = null;
        try {
            context = new InitialContext();
            //解析连接池
            context = (Context) context.lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("jdbc/mysqll");
        } catch (NamingException e) {
            System.out.println("获取失败");
            e.printStackTrace();
        }
    }
    public static DataSourceCache getInstance(){
        return instance;
    }

    public DataSource getDataSource(){
        return dataSource;
    }
}
