package dao;

import model.Book;
import java.sql.*;

public class BookDAO {
    private Connection connection;

    public BookDAO(Connection connection){
        this.connection=connection;
    }

    public void addBook(Book book){
        String sql= "INSERT into Book "+
                "(ISBN, Title, Author, Publisher, PublicationYear, TotalCopies, AvailableCopies) "+
                "Values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,book.getISBN());
            statement.setString(2,book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4,book.getPublisher());
            statement.setInt(5,book.getPublicationYear());
            statement.setInt(6,book.getTotalCopies());
            statement.setInt(7,book.getAvailableCopies());

            int rows=statement.executeUpdate();
            System.out.println("Book added successfully!");

        }catch (SQLException e){
            System.out.println("Failed to add book!");
            e.printStackTrace();
        }

    }

    public void getAllBooks(){
        String sql="Select * from Book";

        try {
            PreparedStatement statement=connection.prepareStatement(sql);
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                System.out.println("ISBN: "+resultSet.getString("ISBN"));
                System.out.println("Title: "+resultSet.getString("Title"));
                System.out.println("Author: "+resultSet.getString("Author"));
                System.out.println("Publisher: "+resultSet.getString("Publisher"));
                System.out.println("PublicationYear: "+resultSet.getInt("PublicationYear"));
                System.out.println("TotalCopies: "+resultSet.getInt("TotalCopies"));
                System.out.println("AvailableCopies: "+resultSet.getInt("AvailableCopies"));
                System.out.println("------------------------------------");
            }

        }catch (SQLException e){
            System.out.println("Failed to get books!");
            e.printStackTrace();
        }
    }

    public void updateBook(Book book){
        String sql="Update Book set "+
                "Title = ?, " +
                "Author = ?, " +
                "Publisher = ?, " +
                "PublicationYear = ?, " +
                "TotalCopies = ?, " +
                "AvailableCopies = ? " +
                "WHERE ISBN = ?";

        try {
            PreparedStatement statement=connection.prepareStatement(sql);

            statement.setString(1,book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3,book.getPublisher());
            statement.setInt(4,book.getPublicationYear());
            statement.setInt(5,book.getTotalCopies());
            statement.setInt(6,book.getAvailableCopies());
            statement.setString(7,book.getISBN());

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("Book updated successfully!");
            }else{
                System.out.println("Book not found!");
            }

        }catch (SQLException e){
            System.out.println("Failed to update book!");
            e.printStackTrace();
        }
    }

    public void deleteBook(String ISBN){
        String sql="Delete from book where ISBN=?";

        try {
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,ISBN);

            int rows=statement.executeUpdate();
            if (rows > 0) {
                System.out.println("Book deleted successfully!");
            } else {
                System.out.println("Book not found!");
            }
        }catch (SQLException e){
            System.out.println("Failed to delete book!");
            e.printStackTrace();
        }
    }

    public void searchByISBN(String ISBN) {
        String sql = "SELECT * FROM Book WHERE ISBN = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ISBN);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                System.out.println("ISBN: " + result.getString("ISBN"));
                System.out.println("Title: " + result.getString("Title"));
                System.out.println("Author: " + result.getString("Author"));
                System.out.println("Publisher: " + result.getString("Publisher"));
                System.out.println("PublicationYear: " + result.getInt("PublicationYear"));
                System.out.println("TotalCopies: " + result.getInt("TotalCopies"));
                System.out.println("AvailableCopies: " + result.getInt("AvailableCopies"));
            } else {
                System.out.println("Book not found!");
            }

        } catch (SQLException e) {
            System.out.println("Failed to search for book!");
            e.printStackTrace();
        }
    }

    public void searchByTitle(String title) {
        String sql = "SELECT * FROM Book WHERE LOWER(Title) LIKE LOWER(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + title + "%");

            ResultSet result = statement.executeQuery();

            boolean found = false;

            while (result.next()) {
                found = true;

                System.out.println("ISBN: " + result.getString("ISBN"));
                System.out.println("Title: " + result.getString("Title"));
                System.out.println("Author: " + result.getString("Author"));
                System.out.println("Publisher: " + result.getString("Publisher"));
                System.out.println("PublicationYear: " + result.getInt("PublicationYear"));
                System.out.println("TotalCopies: " + result.getInt("TotalCopies"));
                System.out.println("AvailableCopies: " + result.getInt("AvailableCopies"));
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("Book not found!");
            }

        } catch (SQLException e) {
            System.out.println("Failed to search for books!");
            e.printStackTrace();
        }
    }

    public void searchByAuthor(String author) {
        String sql = "SELECT * FROM Book WHERE LOWER(Author) LIKE LOWER(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + author + "%");

            ResultSet result = statement.executeQuery();

            boolean found = false;

            while (result.next()) {
                found = true;

                System.out.println("ISBN: " + result.getString("ISBN"));
                System.out.println("Title: " + result.getString("Title"));
                System.out.println("Author: " + result.getString("Author"));
                System.out.println("Publisher: " + result.getString("Publisher"));
                System.out.println("PublicationYear: " + result.getInt("PublicationYear"));
                System.out.println("TotalCopies: " + result.getInt("TotalCopies"));
                System.out.println("AvailableCopies: " + result.getInt("AvailableCopies"));
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("Book not found!");
            }

        } catch (SQLException e) {
            System.out.println("Failed to search for books!");
            e.printStackTrace();
        }
    }

    public boolean bookExists(String ISBN) {

        String sql = "SELECT ISBN FROM Book WHERE ISBN = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ISBN);

            ResultSet result = statement.executeQuery();

            return result.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isBookAvailable(String ISBN){
        String sql="select AvailableCopies from book where ISBN=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1,ISBN);

            ResultSet result= statement.executeQuery();

            if (result.next()){
                return result.getInt("AvailableCopies")>0;
            }

            return false;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void increaseAvailableCopies(String ISBN) {
        String sql="update book "+
                "set availablecopies=availablecopies+1 "+
                "where ISBN=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1,ISBN);
            int rows = statement.executeUpdate();

            if (rows == 0) {
                throw new IllegalArgumentException("Book does not exist");
            }
        }catch (SQLException e){
            System.out.println("Failed to increase available copies!");
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public void decreaseAvailableCopies(String ISBN) {

        String sql = "UPDATE Book " +
                "SET AvailableCopies = AvailableCopies - 1 " +
                "WHERE ISBN = ? AND AvailableCopies > 0";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ISBN);
            int rows = statement.executeUpdate();

            if (rows == 0) {
                throw new IllegalArgumentException("No available copies for this book");
            }

        } catch (SQLException e) {
            System.out.println("Failed to decrease available copies!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Book getBookByISBN(String ISBN) {

        String sql = "SELECT * FROM Book WHERE ISBN = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ISBN);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Book(
                        resultSet.getString("ISBN"),
                        resultSet.getString("Title"),
                        resultSet.getString("Author"),
                        resultSet.getString("Publisher"),
                        resultSet.getInt("PublicationYear"),
                        resultSet.getInt("TotalCopies"),
                        resultSet.getInt("AvailableCopies")
                );
            }

            return null;

        } catch (SQLException e) {
            System.out.println("Failed to get book!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

