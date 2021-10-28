package bo.custom;

import bo.SuperBO;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    Item searchItem(String itemID) throws SQLException,ClassNotFoundException;

    boolean updateItem(Item item) throws SQLException,ClassNotFoundException;

    boolean saveItem(Item item) throws SQLException,ClassNotFoundException;

    ArrayList<Item> getAllItems() throws SQLException,ClassNotFoundException;

    boolean deleteItem(String itemID) throws SQLException,ClassNotFoundException;

    String getLastItemID() throws SQLException,ClassNotFoundException;
}
