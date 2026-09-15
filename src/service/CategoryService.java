package service;

import dao.CategoryDAO;
import model.Category;

public class CategoryService {
    private CategoryDAO categoryDAO;

    private static final int MIN_VALID_ID = 1;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public void addCategory(Category category){
        if (category==null){
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (categoryDAO.categoryNameExists(category.getName())){
            throw new IllegalArgumentException("Category name already exists");
        }
        categoryDAO.addCategory(category);
    }

    public void updateCategory(Category category){
        if (category==null){
            throw new IllegalArgumentException("Category cannot be null");
        }

        if(!categoryDAO.categoryExists(category.getCategoryId())){
            throw new IllegalArgumentException("Category does not exist");
        }
        categoryDAO.updateCategory(category);
    }

    public void deleteCategory(int categoryID){
        if (categoryID<MIN_VALID_ID){
            throw new IllegalArgumentException("Category ID must be greater than 0");
        }
        if(!categoryDAO.categoryExists(categoryID)){
            throw new IllegalArgumentException("Category does not exist");
        }
        if (categoryDAO.hasBookcases(categoryID)){
            throw new IllegalArgumentException("Cannot delete category because it has bookcases");
        }

        categoryDAO.deleteCategory(categoryID);
    }

    public void getAllCategories(){
        categoryDAO.getAllCategories();
    }
}
