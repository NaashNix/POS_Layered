package bo.custom.impl;

import bo.custom.ItemBO;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    private final ItemDAO itemDAO = new ItemDAOImpl();


    @Override
    public Item searchItem(String itemID) throws SQLException, ClassNotFoundException {
        return itemDAO.search(itemID);
    }

    @Override
    public boolean updateItem(Item item) throws SQLException, ClassNotFoundException {
        return itemDAO.update(item);
    }

    @Override
    public boolean saveItem(Item item) throws SQLException, ClassNotFoundException {
        return itemDAO.add(item);
    }

    @Override
    public ArrayList<Item> getAllItems() throws SQLException, ClassNotFoundException {
        return itemDAO.getAll();
    }

    @Override
    public boolean deleteItem(String itemID) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemID);
    }

    @Override
    public String getLastItemID() throws SQLException, ClassNotFoundException {
        return itemDAO.getLastItemID();
    }
}
