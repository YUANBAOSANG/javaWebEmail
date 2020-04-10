package DAO;

public class DAOException extends Exception {
    private static final long serialVersionUID = 10102L;
    private String message;

    public DAOException(){}
    public DAOException(String message){
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
