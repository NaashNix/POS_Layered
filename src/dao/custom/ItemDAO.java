package dao.custom;

import dao.CrudDAO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item,String> {
    String getLastItemID() throws SQLException,ClassNotFoundException;

    List<String> getAllIDs() throws SQLException,ClassNotFoundException;

    String getItemDescription(String itemID) throws SQLException,ClassNotFoundException;
}
