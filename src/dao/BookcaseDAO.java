package dao;

import java.sql.*;

import model.Bookcase;

public class BookcaseDAO {
    private Connection connection;

    public BookcaseDAO(Connection connection) {
        this.connection = connection;
    }

    public void addBookCase(Bookcase bookcase){
        String sql="Insert into BookCase"+
                "(Name,CategoryID)"+
                "values(?,?)";

        try {
            PreparedStatement statement= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,bookcase.getName());
            statement.setInt(2,bookcase.getCategoryID());

            statement.executeUpdate();
            ResultSet result=statement.getGeneratedKeys();
            if (result.next()){
                int BookCaseId=result.getInt(1);
                bookcase.setBookcaseID(BookCaseId);
            }
            System.out.println("BookCase added successfully!");
        }catch (SQLException e){
            System.out.println("Failed to get BookCase!");
            e.printStackTrace();
        }
    }

    public void getAllBookcases(){
        String sql="select * from Bookcase";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            ResultSet result= statement.executeQuery();
            while (result.next()){
                System.out.println("BookCase id: "+result.getInt("bookcaseID"));
                System.out.println("Name: "+result.getString("Name"));
                System.out.println("Category id" +result.getInt("CategoryID"));
                System.out.println("-----------------------------");
            }
        }catch (SQLException e){
            System.out.println("Failed to get BookCase!");
            e.printStackTrace();
        }
    }

    public void updateBookCase(Bookcase bookcase){
        String sql="update Bookcase set "+
                "Name=?,"+
                "CategoryID=? "+
                "where BookcaseID=?";
        try {
            PreparedStatement statement= connection.prepareStatement(sql);

            statement.setString(1,bookcase.getName());
            statement.setInt(2,bookcase.getCategoryID());
            statement.setInt(3,bookcase.getBookcaseID());
            int rows= statement.executeUpdate();
            if (rows>0){
                System.out.println("BookCase updated successfully!");
            }else{
                System.out.println("BookCase not found!");
            }

        }catch (SQLException e){
            System.out.println("Failed to update BookCase!");
            e.printStackTrace();
        }
    }

    public void deleteBookCase(int bookCaseID){
        String sql="delete from Bookcase where BookcaseId=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,bookCaseID);
            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("BookCase deleted successfully!");
            }else{
                System.out.println("BookCase not found!");
            }

        }catch (SQLException e){
            System.out.println("Failed to delete BookCase!");
            e.printStackTrace();
        }
    }

    public boolean bookcaseExists(int bookcaseID){
        String sql="select bookcaseID from bookcase where bookCaseID=? ";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,bookcaseID);

            ResultSet resultSet=statement.executeQuery();

            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to Found BookCase!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasShelves(int bookcaseID){
        String sql="select shelfId from shelf where bookcaseID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,bookcaseID);
            ResultSet resultSet= statement.executeQuery();

            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to check shelves!");

            e.printStackTrace();
            return false;
        }
    }
}
