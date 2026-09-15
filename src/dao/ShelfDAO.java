package dao;

import model.Shelf;

import java.sql.*;

public class ShelfDAO {
    private Connection connection;

    public ShelfDAO(Connection connection) {
        this.connection = connection;
    }

    //create
    public void addShelf(Shelf shelf){
        String sql="Insert into Shelf"+
                "(ShelfNumber,Capacity,BookcaseID)"+
                "values(?,?,?)";

        try {
            PreparedStatement statement= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,shelf.getShelfNumber());
            statement.setInt(2,shelf.getCapacity());
            statement.setInt(3,shelf.getBookcaseID());

            statement.executeUpdate();
            ResultSet generatedKeys=statement.getGeneratedKeys();
            if (generatedKeys.next()){
                int shelfID=generatedKeys.getInt(1);
                shelf.setShelfID(shelfID);
            }
            System.out.println("Shelf added successfully!");
        }catch (SQLException e){
            System.out.println("Failed to add shelf!");
            e.printStackTrace();
        }
    }

    public void getAllShelves(){
        String sql="select * from shelf";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            ResultSet result=statement.executeQuery();

            while (result.next()){
                System.out.println("ShelfId: "+result.getInt("ShelfID"));
                System.out.println("ShelfNumber: "+result.getInt("ShelfNumber"));
                System.out.println("Capacity: "+result.getInt("Capacity"));
                System.out.println("BookCaseId: "+result.getInt("BookcaseID"));
                System.out.println("-------------------------");
            }

        }catch (SQLException e){
            System.out.println("Failed to get shelves!");
            e.printStackTrace();
        }
    }

    public void updateShelf(Shelf shelf){
        String sql="update Shelf set "+
                "ShelfNumber=?,"+
                "Capacity=?,"+
                "BookcaseID=? "+
                "where ShelfId=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,shelf.getShelfNumber());
            statement.setInt(2,shelf.getCapacity());
            statement.setInt(3,shelf.getBookcaseID());
            statement.setInt(4,shelf.getShelfID());

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("Shelf updated successfully!");
            }else{
                System.out.println("Shelf not found!");
            }
        }catch (Exception e){
            System.out.println("Failed to update shelf!");
            e.printStackTrace();
        }
    }

    public void deleteShelf(int ShelfID){
        String sql="delete from Shelf where ShelfId=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,ShelfID);

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("Shelf deleted successfully!");
            }else{
                System.out.println("Shelf not found!");
            }

        }catch (Exception e){
            System.out.println("Failed to delete shelf!");
            e.printStackTrace();
        }
    }

    public boolean shelfExists(int shelfID){
        String sql="select shelfID from shelf where shelfID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,shelfID);
            ResultSet resultSet=statement.executeQuery();

            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to check shelf!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasBooks(int shelfID){
        String sql="select shelfID from Bookshelf where shelfID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,shelfID);
            ResultSet resultSet= statement.executeQuery();

            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to check books on shelf!");
            e.printStackTrace();
            return false;
        }
    }

    public Shelf getShelfByID(int shelfID) {
        String sql = "SELECT * FROM Shelf WHERE ShelfID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, shelfID);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Shelf(
                        resultSet.getInt("ShelfNumber"),
                        resultSet.getInt("Capacity"),
                        resultSet.getInt("BookcaseID")
                );
            }

            return null;

        } catch (SQLException e) {
            System.out.println("Failed to get Shelf!");
            e.printStackTrace();
            return null;
        }
    }
}
