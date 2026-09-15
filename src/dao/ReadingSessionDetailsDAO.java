package dao;

import model.ReadingSessionDetails;

import java.sql.*;

public class ReadingSessionDetailsDAO {

    private Connection connection;

    public ReadingSessionDetailsDAO(Connection connection) {
        this.connection = connection;
    }

    public void addReadingSessionDetails(ReadingSessionDetails details) {

        String sql = "Insert into ReadingSessionDetails" +
                "(ReadingSessionID, ISBN)" +
                "values(?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, details.getReadingSessionID());
            statement.setString(2, details.getISBN());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                int readingSessionDetailsID = generatedKeys.getInt(1);
                details.setReadingSessionDetailsId(readingSessionDetailsID);
            }

            System.out.println("ReadingSessionDetails added successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to add ReadingSessionDetails!");
            e.printStackTrace();
        }
    }


    public void getAllReadingSessionDetails() {

        String sql = "select * from ReadingSessionDetails";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            while (result.next()) {

                System.out.println("ReadingSessionDetailsID: " + result.getInt("ReadingSessionDetailsID"));
                System.out.println("ReadingSessionID: " + result.getInt("ReadingSessionID"));
                System.out.println("ISBN: " + result.getString("ISBN"));
                System.out.println("-------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Failed to get ReadingSessionDetails!");
            e.printStackTrace();
        }
    }


    public void updateReadingSessionDetails(ReadingSessionDetails details) {

        String sql = "update ReadingSessionDetails set " +
                "ReadingSessionID=?," +
                "ISBN=? " +
                "where ReadingSessionDetailsID=?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, details.getReadingSessionID());
            statement.setString(2, details.getISBN());
            statement.setInt(3, details.getReadingSessionDetailsId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println(
                        "ReadingSessionDetails updated successfully!");
            } else {
                System.out.println(
                        "ReadingSessionDetails not found!");
            }

        } catch (SQLException e) {
            System.out.println("Failed to update ReadingSessionDetails!");
            e.printStackTrace();
        }
    }


    public void deleteReadingSessionDetails(int readingSessionDetailsID) {

        String sql = "delete from ReadingSessionDetails " +
                        "where ReadingSessionDetailsID=?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, readingSessionDetailsID);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println(
                        "ReadingSessionDetails deleted successfully!");
            } else {
                System.out.println(
                        "ReadingSessionDetails not found!");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Failed to delete ReadingSessionDetails!");
            e.printStackTrace();
        }
    }

    public boolean hasBook(String ISBN) {

        String sql = "SELECT ISBN FROM readingsessiondetails WHERE ISBN = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ISBN);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Failed to check book in reading sessions!");
            e.printStackTrace();
            return false;
        }
    }
}