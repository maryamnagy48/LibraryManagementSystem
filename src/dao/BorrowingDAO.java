package dao;

import model.Borrowing;

import java.sql.*;

public class BorrowingDAO {
    private Connection connection;

    public BorrowingDAO(Connection connection) {
        this.connection = connection;
    }

    public void addBorrowing(Borrowing borrowing){
        String sql="Insert into Borrowing"+
                "(borrowingdate,duedate,memberID,librarianID)"+
                "values(?,?,?,?)";
        try {
            PreparedStatement statement= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(borrowing.getBorrowingDate()));
            statement.setDate(2,Date.valueOf(borrowing.getDueDate()));
            statement.setInt(3,borrowing.getMemberID());
            statement.setInt(4,borrowing.getLibrarianID());

            statement.executeUpdate();
            ResultSet generatedkeys= statement.getGeneratedKeys();
            if (generatedkeys.next()){
                int BorrowingID= generatedkeys.getInt(1);
                borrowing.setBorrowingID(BorrowingID);
            }
            System.out.println("Borrowing added successfully!");

        }catch (SQLException e){
            System.out.println("Failed to add Borrowing!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void getAllBorrowings(){
        String sql="select * from Borrowing";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                System.out.println("BorrowingID: "+resultSet.getInt("BorrowingID"));
                System.out.println("borrowingdate: "+resultSet.getDate("borrowingdate"));
                System.out.println("duedate: "+resultSet.getDate("duedate"));
                System.out.println("memberID: "+resultSet.getInt("memberID"));
                System.out.println("librarianID: "+resultSet.getInt("librarianID"));
                System.out.println("--------------------------------");
            }
        }catch (SQLException e){
            System.out.println("Failed to get Borrowing!");
            e.printStackTrace();
        }
    }

    public void updateBorrowing(Borrowing borrowing){
        String sql="Update Borrowing set "+
                "borrowingdate=?,"+
                "duedate=?,"+
                "memberID=?, "+
                "librarianID=? "+
                "where BorrowingID=?";
        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setDate(1,Date.valueOf(borrowing.getBorrowingDate()));
            statement.setDate(2,Date.valueOf(borrowing.getDueDate()));
            statement.setInt(3,borrowing.getMemberID());
            statement.setInt(4,borrowing.getLibrarianID());
            statement.setInt(5,borrowing.getBorrowingID());


            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("Borrowing updated successfully!");
            }else {
                System.out.println("Borrowing not found!");
            }
        }catch (SQLException e){
            System.out.println("Failed to update Borrowing!");
            e.printStackTrace();
        }
    }

    public boolean borrowingExists(int borrowingID) {

        String sql = "SELECT BorrowingID " +
                "FROM Borrowing " +
                "WHERE BorrowingID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, borrowingID);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Failed to check borrowing!");
            e.printStackTrace();
            return false;
        }
    }

    public void deleteBorrowing(int borrowingID){
        String sql="delete from Borrowing where BorrowingID=?";
        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,borrowingID);

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("Borrowing deleted successfully!");
            }else {
                System.out.println("Borrowing not found!");
            }
        }catch (SQLException e){
            System.out.println("Failed to delete Borrowing!");
            e.printStackTrace();
        }
    }

    public Borrowing getBorrowingByID(int borrowingID) {

        String sql = "SELECT * FROM Borrowing WHERE BorrowingID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, borrowingID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Borrowing borrowing = new Borrowing(
                        resultSet.getDate("BorrowingDate").toLocalDate(),
                        resultSet.getDate("DueDate").toLocalDate(),
                        resultSet.getInt("MemberID"),
                        resultSet.getInt("LibrarianID")
                );

                borrowing.setBorrowingID(
                        resultSet.getInt("BorrowingID")
                );

                return borrowing;
            }

            return null;

        } catch (SQLException e) {
            System.out.println("Failed to get borrowing!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void getCurrentBorrowings() {

        String sql =
                "SELECT DISTINCT b.* " +
                        "FROM Borrowing b " +
                        "JOIN BorrowingDetails bd " +
                        "ON b.BorrowingID = bd.BorrowingID " +
                        "WHERE bd.ReturnDate IS NULL";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("BorrowingID: " + resultSet.getInt("BorrowingID"));
                System.out.println("BorrowingDate: " + resultSet.getDate("BorrowingDate"));
                System.out.println("DueDate: " + resultSet.getDate("DueDate"));
                System.out.println("MemberID: " + resultSet.getInt("MemberID"));
                System.out.println("LibrarianID: " + resultSet.getInt("LibrarianID"));
                System.out.println("-------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Failed to get current borrowings!");
            e.printStackTrace();
        }
    }

    public void getOverdueBorrowings() {

        String sql =
                "SELECT DISTINCT b.* " +
                        "FROM Borrowing b " +
                        "JOIN BorrowingDetails bd " +
                        "ON b.BorrowingID = bd.BorrowingID " +
                        "WHERE bd.ReturnDate IS NULL " +
                        "AND b.DueDate < CURDATE()";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("BorrowingID: " + resultSet.getInt("BorrowingID"));
                System.out.println("BorrowingDate: " + resultSet.getDate("BorrowingDate"));
                System.out.println("DueDate: " + resultSet.getDate("DueDate"));
                System.out.println("MemberID: " + resultSet.getInt("MemberID"));
                System.out.println("LibrarianID: " + resultSet.getInt("LibrarianID"));
                System.out.println("-------------------------");
            }
        }catch (SQLException e){
            System.out.println("Failed to get overdue borrowings!");
            e.printStackTrace();

        }

    }
}
