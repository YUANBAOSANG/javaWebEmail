package DAO;

import java.sql.Connection;

public interface DAO {
    Connection getConection() throws DAOException;
}
