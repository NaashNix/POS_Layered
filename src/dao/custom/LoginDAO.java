package dao.custom;

import dao.SuperDAO;

import java.sql.SQLException;

public interface LoginDAO extends SuperDAO {
    String getUserRollByID(String userID) throws SQLException,ClassNotFoundException;

    String getUserPasswordByID(String userID) throws SQLException,ClassNotFoundException;
}
