package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import db.DbConnection;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES(?,?,?,?,?)",
                item.getItemID(),
                item.getDescription(),
                item.getPackSize(),
                item.getUnitPrice(),
                item.getQtyOnHand());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemID=?",s);
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?,packSize=?,unitPrice=?,qtyOnHand=? WHERE itemID=?",
                item.getDescription(),
                item.getPackSize(),
                item.getUnitPrice(),
                item.getQtyOnHand(),
                item.getItemID());
    }

    @Override
    public Item search(String s) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM Item WHERE itemID=?", s);
        if (resultSet.next()){
            return new Item(
                    resultSet.getString("itemID"),
                    resultSet.getString("description"),
                    resultSet.getString("packSize"),
                    resultSet.getDouble("unitPrice"),
                    resultSet.getInt("qtyOnHand")
            );
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> itemArrayList = new ArrayList<>();
        while(resultSet.next()){
            itemArrayList.add(new Item(
                    resultSet.getString("itemID"),
                    resultSet.getString("description"),
                    resultSet.getString("packSize"),
                    resultSet.getDouble("unitPrice"),
                    resultSet.getInt("qtyOnHand")
            ));
        }
        return itemArrayList;
    }

    @Override
    public String getLastItemID() throws SQLException, ClassNotFoundException {
        int lastIDint;
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM Item ORDER BY itemID DESC LIMIT 1");
        if(resultSet.next()){
            lastIDint = (Integer.parseInt(resultSet.getString(1).substring(2)));
            return String.format("I-%03d",++lastIDint);
        }else{
            return "I-001";
        }
    }

    @Override
    public List<String> getAllIDs() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT itemID FROM Item");
        List<String> itemIDs = new ArrayList<>();
        while (resultSet.next()){
            itemIDs.add(resultSet.getString(1));
        }

        return itemIDs;
    }

    @Override
    public String getItemDescription(String itemID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT description FROM Item WHERE itemID=?", itemID);
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public String getItemName(String itemID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT description FROM Item WHERE itemID=?", itemID);
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public int getItemQuantityOnHand(String itemID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT qtyOnHand FROM Item WHERE itemID=?", itemID);
        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return -1;
    }

    @Override
    public boolean updateItemQuantity(String itemID, int qtyOnHand) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET qtyOnHand=? WHERE itemID=?",qtyOnHand,itemID);
    }
}
