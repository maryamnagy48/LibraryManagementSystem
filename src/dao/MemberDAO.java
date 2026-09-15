package dao;
import model.Member;

import java.sql.*;

public class MemberDAO {

    private Connection connection;

    public MemberDAO(Connection connection){
        this.connection=connection;
    }

    //create
    public void addMember(Member member){
        String sql="Insert into Member "+
                "(Name,Phone,Email)"+
                "Values(?,?,?)";

        try {
            PreparedStatement statement= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, member.getName());
            statement.setString(2, member.getPhone());
            statement.setString(3,member.getEmail());

            statement.executeUpdate();
            ResultSet generatedKeys=statement.getGeneratedKeys();
            if (generatedKeys.next()){
                int memberId=generatedKeys.getInt(1);
                member.setMemberId(memberId);
            }
            System.out.println("Member added successfully!");
        }catch (SQLException e){
            System.out.println("Failed to add Member!");
            e.printStackTrace();
        }
    }

    //read
    public void getAllMembers(){
        String sql="select * from Member";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            ResultSet result =statement.executeQuery();

            while (result.next()){
                System.out.println("member id:"+result.getInt("MemberID"));
                System.out.println("name: "+result.getString("Name"));
                System.out.println("phone: "+result.getString("Phone"));
                System.out.println("Email: "+result.getString("Email"));
                System.out.println("--------------------------------");
            }


        }catch (SQLException e){
            System.out.println("Failed to get Member!");
            e.printStackTrace();
        }
    }

    //update
    public void updateMember(Member member){
        String sql="update Member set "+
                "Name=?,"+
                "Phone=?,"+
                "Email=? "+
                "where MemberID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);

            statement.setString(1, member.getName());
            statement.setString(2, member.getPhone());
            statement.setString(3, member.getEmail());
            statement.setInt(4,member.getMemberId());

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("Member updated successfully!");
            }else{
                System.out.println("Member not found!");
            }
        }catch (SQLException e){
            System.out.println("Failed to update Member!");
            e.printStackTrace();
        }
    }

    public void deleteMember(int memberid){
        String sql="Delete from Member where MemberID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,memberid);

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("Member deleted successfully!");
            }else{
                System.out.println("Member not found!");
            }

        }catch (SQLException e){
            System.out.println("Failed to delete Member!");
            e.printStackTrace();
        }
    }

    public boolean memberExists(int memberID) {
        String sql="select memberID from member where memberID=?";
        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,memberID);
            ResultSet resultSet= statement.executeQuery();

            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to check Member!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasBorrowing(int memberID){
        String sql="select borrowingID from borrowing where memberID=?";
        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,memberID);
            ResultSet resultSet= statement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to check borrowings!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExists(String email){
        String sql="select email from member where LOWER(email)=LOWER(?)";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet resultSet= statement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Failed to check email!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExistsForAnotherMember(String email, int memberID) {

        String sql = "SELECT MemberID FROM Member " +
                "WHERE LOWER(email)=LOWER(?) AND MemberID <> ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setInt(2, memberID);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Failed to check email!");
            e.printStackTrace();
            return false;
        }
    }
}
