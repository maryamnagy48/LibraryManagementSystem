package dao;

import model.Category;

import java.sql.*;

public class CategoryDAO {
    private Connection connection;

    public CategoryDAO(Connection connection){
        this.connection=connection;
    }

    //create
    public void addCategory(Category category){
        String sql="Insert into category (Name) Values(?)";

        try {
            PreparedStatement statement= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,category.getName());

            statement.executeUpdate();
            ResultSet generatedkeys=statement.getGeneratedKeys();
            if (generatedkeys.next()){
                int categoryid=generatedkeys.getInt(1);
                category.setCategoryID(categoryid);
            }
            System.out.println("Category added successfully!");

        }catch (SQLException e){
            System.out.println("Failed to add Category!");
            e.printStackTrace();
        }
    }

    //read
    public void getAllCategories(){
        String sql="select * from Category";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            ResultSet result= statement.executeQuery();

            while (result.next()){
                System.out.println("Category id:"+result.getInt("CategoryID"));
                System.out.println("Name: "+result.getString("Name"));
                System.out.println("-----------------------");
            }

        }catch (SQLException e){
            System.out.println("Failed to get Categories!");
            e.printStackTrace();
        }
    }

    public void updateCategory(Category category){
        String sql="update category set "+
                "Name=?"+
                " where CategoryID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1,category.getName());
            statement.setInt(2,category.getCategoryId());

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("Category updated successfully!");
            }else {
                System.out.println("Category not found!");
            }
        }catch (SQLException e){
            System.out.println("Failed to update Category!");
            e.printStackTrace();
        }
    }
    //delete
    public void deleteCategory(int categoryid){
        String sql="delete from Category where CategoryID=?";

        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1,categoryid);

            int rows=statement.executeUpdate();
            if (rows>0){
                System.out.println("Category deleted successfully!");
            }else {
                System.out.println("Category not found!");
            }

        }catch (SQLException e){
            System.out.println("Failed to delete Category!");
            e.printStackTrace();
        }
    }

    public boolean categoryExists(int categoryID) {

        String sql = "SELECT CategoryID FROM Category WHERE CategoryID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryID);

            ResultSet result = statement.executeQuery();

            return result.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean categoryNameExists(String categoryName) {

        String sql = "SELECT CategoryID FROM Category WHERE Name = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, categoryName);

            ResultSet result = statement.executeQuery();

            return result.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean hasBookcases(int categoryID) {
       String sql="select bookcaseID from bookcase where categoryID=?";

       try {
           PreparedStatement statement = connection.prepareStatement(sql);
           statement.setInt(1,categoryID);

           ResultSet resultSet= statement.executeQuery();
           return resultSet.next();

       }catch (SQLException e){
           e.printStackTrace();
           return false;
       }
    }
}
