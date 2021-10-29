package bo.custom;

import bo.SuperBO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    String getTheUserRoll(String userID) throws SQLException,ClassNotFoundException;

    String getPasswordByID(String userID) throws SQLException,ClassNotFoundException;
}
