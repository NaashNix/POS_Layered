package bo.custom.impl;
import bo.custom.LoginBO;
import dao.DAOFactory;
import dao.custom.LoginDAO;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {

    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public String getTheUserRoll(String userID) throws SQLException, ClassNotFoundException {
        return loginDAO.getUserRollByID(userID);
    }

    @Override
    public String getPasswordByID(String userID) throws SQLException, ClassNotFoundException {
        return loginDAO.getUserPasswordByID(userID);
    }
}
