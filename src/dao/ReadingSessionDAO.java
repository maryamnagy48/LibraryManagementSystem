package dao;

import model.ReadingSession;

import java.sql.*;

public class ReadingSessionDAO {

    private Connection connection;

    public ReadingSessionDAO(Connection connection) {
        this.connection = connection;
    }

    public void addReadingSession(ReadingSession readingSession) {

        String sql = "Insert into ReadingSession" +
                "(StartTime,EndTime,LibrarianID,MemberID)" +
                "values(?,?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setTimestamp(1, Timestamp.valueOf(readingSession.getStartTime()));

            if (readingSession.getEndTime() != null) {
                statement.setTimestamp(2, Timestamp.valueOf(readingSession.getEndTime()));
            } else {
                statement.setNull(2, Types.TIMESTAMP);
            }

            statement.setInt(3, readingSession.getLibrarianID());
            statement.setInt(4, readingSession.getMemberID());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                int readingSessionID = generatedKeys.getInt(1);
                readingSession.setReadingSessionId(readingSessionID);
            }

            System.out.println("ReadingSession added successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to add ReadingSession!");
            e.printStackTrace();
        }
    }


    public void getAllReadingSessions() {

        String sql = "select * from ReadingSession";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            while (result.next()) {

                System.out.println("ReadingSessionID: " + result.getInt("ReadingSessionID"));

                System.out.println("StartTime: " + result.getTimestamp("StartTime"));

                System.out.println("EndTime: " + result.getTimestamp("EndTime"));

                System.out.println("LibrarianID: " + result.getInt("LibrarianID"));

                System.out.println("MemberID: " + result.getInt("MemberID"));

                System.out.println("-------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Failed to get ReadingSessions!");
            e.printStackTrace();
        }
    }


    public void updateReadingSession(ReadingSession readingSession) {

        String sql = "update ReadingSession set " +
                "StartTime=?," +
                "EndTime=?," +
                "LibrarianID=?," +
                "MemberID=? " +
                "where ReadingSessionID=?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setTimestamp(1, Timestamp.valueOf(readingSession.getStartTime()));

            if (readingSession.getEndTime() != null) {
                statement.setTimestamp(2, Timestamp.valueOf(readingSession.getEndTime()));
            } else {
                statement.setNull(2, Types.TIMESTAMP);
            }

            statement.setInt(3, readingSession.getLibrarianID());
            statement.setInt(4, readingSession.getMemberID());
            statement.setInt(5, readingSession.getReadingSessionId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("ReadingSession updated successfully!");
            } else {
                System.out.println("ReadingSession not found!");
            }

        } catch (SQLException e) {
            System.out.println("Failed to update ReadingSession!");
            e.printStackTrace();
        }
    }


    public void deleteReadingSession(int readingSessionID) {

        String sql = "delete from ReadingSession where ReadingSessionID=?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, readingSessionID);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("ReadingSession deleted successfully!");
            } else {
                System.out.println("ReadingSession not found!");
            }

        } catch (SQLException e) {
            System.out.println("Failed to delete ReadingSession!");
            e.printStackTrace();
        }
    }

    public boolean readingSessionExists(int readingSessionID){
        String sql="select readingSessionID from readingSession where readingSessionID=? ";
        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1, readingSessionID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to check ReadingSession!");
            e.printStackTrace();
            return false;
        }
    }

    public ReadingSession getReadingSessionByID(int readingSessionID) {

        String sql =
                "SELECT * FROM ReadingSession " +
                        "WHERE ReadingSessionID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, readingSessionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ReadingSession session = new ReadingSession(
                        resultSet.getTimestamp("StartTime").toLocalDateTime(),
                        resultSet.getInt("LibrarianID"),
                        resultSet.getInt("MemberID")

                );

                Timestamp endTime = resultSet.getTimestamp("EndTime");

                if (endTime != null) {
                    session.setEndTime(endTime.toLocalDateTime());
                }
                session.setReadingSessionId(resultSet.getInt("ReadingSessionID"));
                return session;
            }

            return null;

        } catch (SQLException e) {
            System.out.println("Failed to get ReadingSession!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void getActiveReadingSessions(){
        String sql="select * from readingSession where endTime is null";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("ReadingSessionID: " + resultSet.getInt("ReadingSessionID"));
                System.out.println("StartTime: " + resultSet.getTimestamp("StartTime"));
                System.out.println("EndTime: " + resultSet.getTimestamp("EndTime"));
                System.out.println("LibrarianID: " + resultSet.getInt("LibrarianID"));
                System.out.println("MemberID: " + resultSet.getInt("MemberID"));
                System.out.println("-------------------------");
            }
        }catch (SQLException e){
            System.out.println("Failed to get active ReadingSessions!");
            e.printStackTrace();
        }
    }
}