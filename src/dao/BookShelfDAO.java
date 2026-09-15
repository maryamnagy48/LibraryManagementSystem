package dao;

import model.BookShelf;

import java.sql.*;

public class BookShelfDAO {
    private Connection connection;

    public BookShelfDAO(Connection connection) {
        this.connection = connection;
    }

    //create
    public void addBookShelf(BookShelf bookShelf){
        String sql="Insert into BookShelf"+
                "(ISBN,ShelfID,Copies)"+
                "values(?,?,?)";

        try {
            PreparedStatement statement= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,bookShelf.getBook().getISBN());
            statement.setInt(2,bookShelf.getShelf().getShelfID());
            statement.setInt(3,bookShelf.getCopies());

            statement.executeUpdate();
            ResultSet generatedKeys=statement.getGeneratedKeys();
            if (generatedKeys.next()){
                int bookShelfID=generatedKeys.getInt(1);
                bookShelf.setBookShelfID(bookShelfID);

            }

            System.out.println("BookShelf added successfully!");
        }catch (Exception e){
            System.out.println("Failed to add BookShelf!");
            e.printStackTrace();
        }
    }

    //read
    public void getAllBookShelves(){
        String sql="select * from BookShelf";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();
            while (result.next()){
                System.out.println("BookShelf id: "+result.getInt("BookShelfID"));
                System.out.println("ISBN: "+result.getString("ISBN"));
                System.out.println("Shelf id: "+result.getInt("ShelfID"));
                System.out.println("Copies: "+result.getInt("Copies"));
                System.out.println("-------------------------");

            }
        }catch (SQLException e){
            System.out.println("Failed to get BookShelves!");
            e.printStackTrace();
        }
    }

    //update
    public void updateBookShelf(BookShelf bookShelf){
        String sql="update BookShelf set "+
                "ISBN = ?,"+
                "ShelfID = ?,"+
                "Copies = ? "+
                "where BookShelfID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1,bookShelf.getBook().getISBN());
            statement.setInt(2,bookShelf.getShelf().getShelfID());
            statement.setInt(3,bookShelf.getCopies());
            statement.setInt(4,bookShelf.getBookShelfID());

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("BookShelf updated successfully!");
            }else {
                System.out.println("BookShelf not found!");
            }

        }catch (Exception e){
            System.out.println("Failed to update BookShelves!");
            e.printStackTrace();
        }
    }

    //delete
    public void deleteBookShelf(int bookShelfID){
        String sql="delete from BookShelf where bookShelfID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,bookShelfID);
            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("BookShelf deleted successfully!");
            }else{
                System.out.println("BookShelf not found!");
            }

        }catch (Exception e){
            System.out.println("Failed to delete BookShelves!");
            e.printStackTrace();
        }
    }

    public int getCurrentCopies(int shelfID) {

        //COALESCE:lw mfesh  htkhlt ans=0 bdl null
        String sql = "SELECT COALESCE(SUM(Copies), 0) AS CurrentCopies " +
                "FROM BookShelf WHERE ShelfID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, shelfID);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("CurrentCopies");
            }

            return 0;

        } catch (SQLException e) {
            System.out.println("Failed to get current copies!");
            e.printStackTrace();
            return 0;
        }
    }

    public boolean bookShelfExists(int bookShelfID){
        String sql="select bookshelfID from bookShelf where bookShelfID=?";
        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,bookShelfID);
            ResultSet resultSet= statement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to check BookShelf!");
            e.printStackTrace();
            return false;
        }
    }

    public int getCopiesByBookShelfID(int bookShelfID){
        String sql="select copies from bookshelf where bookshelfID=?";
        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,bookShelfID);
            ResultSet resultSet= statement.executeQuery();

            if (resultSet.next()){
                return  resultSet.getInt("Copies");
            }
            return 0;
        }catch (SQLException e){
            System.out.println("Failed to get BookShelf copies!");
            e.printStackTrace();
            return 0;
        }
    }

    public boolean hasBook(String isbn) {
        String sql="select ISBN from  bookshelf where ISBN=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1,isbn);
            ResultSet resultSet= statement.executeQuery();

            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to check book!");
            e.printStackTrace();
            return false;
        }
    }
    public int getShelfIDByBookShelfID(int bookShelfID) {

        String sql = "SELECT ShelfID FROM BookShelf WHERE BookShelfID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookShelfID);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ShelfID");
            }

            return 0;

        } catch (SQLException e) {
            System.out.println("Failed to get Shelf ID!");
            e.printStackTrace();
            return 0;
        }
    }


}
