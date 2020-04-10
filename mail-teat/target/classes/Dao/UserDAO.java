package DAO;


import pojo.User;

import java.util.List;

public interface UserDAO extends DAO {
    List<User> getProducts()throws DAOException;
    void insert(User product) throws DAOException;
}
