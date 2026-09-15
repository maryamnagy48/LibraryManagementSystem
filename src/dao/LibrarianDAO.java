package dao;

import model.Librarian;

import java.sql.*;

public class LibrarianDAO {
    private Connection connection;

    public LibrarianDAO(Connection connection) {
        this.connection = connection;
    }

    public void addLibrarian(Librarian librarian){
        String sql="Insert into Librarian"+
                "(username,passwordHash)"+
                "values(?,?)";

        try {
            PreparedStatement statement= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,librarian.getUsername());
            statement.setString(2,librarian.getPasswordHash());

            statement.executeUpdate();
            ResultSet generatedKeys=statement.getGeneratedKeys();
            if (generatedKeys.next()){
               int librarianID =generatedKeys.getInt(1);
               librarian.setLibrarianID(librarianID);
            }
            System.out.println("librarian added successfully!");

        }catch (SQLException e){
            System.out.println("Failed to add librarian!");
            e.printStackTrace();
        }
    }

    public void getAllLibrarian(){
        String sql="select * from librarian";
        try {
            PreparedStatement statement= connection.prepareStatement(sql);

            ResultSet result=statement.executeQuery();
            while (result.next()){
                System.out.println("librarian id: "+result.getInt("librarianID"));
                System.out.println("username: "+result.getString("username"));
                System.out.println("passwordHash: "+result.getString("passwordHash"));
                System.out.println("----------------------------------------");
            }
        }catch (SQLException e){
            System.out.println("Failed to get librarian!");
            e.printStackTrace();
        }
    }

    public void updateLibrarian(Librarian librarian){
        String sql="update Librarian set "+
                "username=?,"+
                "passwordHash=? "+
                "where librarianid=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1,librarian.getUsername());
            statement.setString(2,librarian.getPasswordHash());
            statement.setInt(3,librarian.getLibrarianID());


            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("librarian updated successfully!");
            }else {
                System.out.println("librarian not found!");
            }
        }catch (SQLException e){
            System.out.println("Failed to update librarian!");
            e.printStackTrace();
        }
    }

    public void deleteLibrarian(int librarianID){
        String sql="delete from Librarian where LibrarianID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,librarianID);

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("librarian deleted successfully!");
            }else {
                System.out.println("librarian not found!");
            }
        }catch (SQLException e){
            System.out.println("Failed to delete librarian!");
            e.printStackTrace();
        }
    }

    public boolean librarianExists(int librarianID) {
        String sql="select librarianID from librarian where librarianID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, librarianID);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Failed to check librarian!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String username, String password) {
        String sql="select librarianID "+
                "from Librarian "+
                "where username=? AND passwordHash=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);

            ResultSet resultSet= statement.executeQuery();
            return resultSet.next();

        }catch (SQLException e){
            System.out.println("Failed to login!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean usernameExists(String username){
        String sql="select username from librarian where username=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1,username);
            ResultSet resultSet= statement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to check username!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean usernameExistsForAnotherLibrarian(String username, int librarianID) {
        String sql="select librarianID from librarian "+
                "where username=? AND librarianID<>?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setInt(2, librarianID);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Failed to check username!");
            e.printStackTrace();
            return false;
        }
    }
}
