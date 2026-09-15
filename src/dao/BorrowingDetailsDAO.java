package dao;

import model.BorrowingDetails;

import java.sql.*;
import java.time.LocalDate;

public class BorrowingDetailsDAO {
    private Connection connection;

    public BorrowingDetailsDAO(Connection connection) {
        this.connection = connection;
    }

    public void addBorrowingDetails(BorrowingDetails borrowingDetails){
        String sql="Insert into BorrowingDetails"+
                "(returnDate,borrowingID,ISBN)"+
                "values(?,?,?)";

        try {
            PreparedStatement statement= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (borrowingDetails.getReturnDate() != null) {
                statement.setDate(1, Date.valueOf(borrowingDetails.getReturnDate()));
            } else {
                statement.setNull(1, Types.DATE);
            }
            statement.setInt(2,borrowingDetails.getBorrowingID());
            statement.setString(3,borrowingDetails.getISBN());
            statement.executeUpdate();

            ResultSet generatedkeys=statement.getGeneratedKeys();
            if (generatedkeys.next()){
                int BorrowingDetailsID=generatedkeys.getInt(1);
                borrowingDetails.setBorrowingDetailsID(BorrowingDetailsID);
            }
            System.out.println("BorrowingDetails added successfully!");
        }catch (SQLException e){
            System.out.println("Failed to add BorrowingDetails!");
            e.printStackTrace();
            throw new RuntimeException(e);
           // SQL Error -> DAO throws exception -> Service يعرف إن فيه failure->ROLLBACK
        }
    }

    public void getAllBorrowingDetails(){
        String sql="select * from BorrowingDetails";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            ResultSet result= statement.executeQuery();
            while (result.next()){
                System.out.println("borrowingDetails id: "+result.getInt("BorrowingDetailsID"));
                System.out.println("returnDate: "+result.getDate("returnDate"));
                System.out.println("borrowingID: "+result.getInt("borrowingID"));
                System.out.println("ISBN: "+result.getString("ISBN"));
                System.out.println("-------------------------------");
            }
        }catch (SQLException e){
            System.out.println("Failed to get BorrowingDetails!");
            e.printStackTrace();
        }
    }

    public void updateBorrowingDetails(BorrowingDetails borrowingDetails){
        String sql="update BorrowingDetails set "+
                "returnDate=?,"+
                "borrowingID=?,"+
                "ISBN=? "+
                "where BorrowingDetailsID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            if (borrowingDetails.getReturnDate() != null) {
                statement.setDate(1, Date.valueOf(borrowingDetails.getReturnDate()));
            } else {
                statement.setNull(1, Types.DATE);
            }
            statement.setInt(2,borrowingDetails.getBorrowingID());
            statement.setString(3,borrowingDetails.getISBN());
            statement.setInt(4,borrowingDetails.getBorrowingDetailsID());

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("BorrowingDetails updated successfully!");
            }else {
                System.out.println("BorrowingDetails not found!");
            }

        }catch (SQLException e){
            System.out.println("Failed to update BorrowingDetails!");
            e.printStackTrace();
        }

    }

    public void deleteBorrowingDetails(int borrowingDetailsID){
        String sql="delete from BorrowingDetails where BorrowingDetailsID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,borrowingDetailsID);

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("BorrowingDetails deleted successfully!");
            }else {
                System.out.println("BorrowingDetails not found!");
            }

        }catch (SQLException e){
            System.out.println("Failed to delete BorrowingDetails!");
            e.printStackTrace();
        }
    }

    public boolean hasBook(String ISBN) {

        String sql = "SELECT ISBN FROM borrowingdetails WHERE ISBN = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ISBN);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Failed to check book in borrowing history!");
            e.printStackTrace();
            return false;
        }
    }



    public void updateReturnDate(int borrowingDetailsID, LocalDate returnDate) {
        String sql="update borrowingdetails "+
                "set returnDate=? "+
                "where borrowingDetailsID=?";

        try {
           PreparedStatement statement= connection.prepareStatement(sql);
           statement.setDate(1,Date.valueOf(returnDate));
           statement.setInt(2,borrowingDetailsID);

           int rows=statement.executeUpdate();
           if (rows==0){
               throw new IllegalArgumentException("BorrowingDetails not found");
           }
        }catch (SQLException e){
            System.out.println("Failed to update return date!");
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public boolean borrowingDetailsExists(int borrowingDetailsID) {
        String sql="select borrowingDetailsID from borrowingdetails where borrowingDetailsID=? ";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, borrowingDetailsID);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to check BorrowingDetails!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasBorrowing(int borrowingID) {

        String sql = "SELECT BorrowingDetailsID " +
                "FROM BorrowingDetails " +
                "WHERE BorrowingID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, borrowingID);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Failed to check borrowing details!");
            e.printStackTrace();
            return false;
        }
    }

    public BorrowingDetails getBorrowingDetailsByID(int borrowingDetailsID) {

        String sql = "SELECT * FROM BorrowingDetails " +
                "WHERE BorrowingDetailsID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, borrowingDetailsID);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                BorrowingDetails details = new BorrowingDetails(
                        resultSet.getInt("BorrowingID"),
                        resultSet.getString("ISBN")
                );

                details.setBorrowingDetailsID(resultSet.getInt("BorrowingDetailsID"));

                Date returnDate = resultSet.getDate("ReturnDate");

                if (returnDate != null) {
                    details.setReturnDate(returnDate.toLocalDate());
                }

                return details;
            }

            return null;

        } catch (SQLException e) {
            System.out.println("Failed to get borrowing details!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}