package service;

import dao.BookShelfDAO;
import dao.BookcaseDAO;
import dao.ShelfDAO;
import model.Shelf;

public class ShelfService {
    private ShelfDAO shelfDAO;
    private BookcaseDAO bookcaseDAO;
    private BookShelfDAO bookShelfDAO;
    private static final int MIN_VALID_ID = 1;

    public ShelfService(ShelfDAO shelfDAO,BookcaseDAO bookcaseDAO,BookShelfDAO bookShelfDAO) {
        this.shelfDAO = shelfDAO;
        this.bookcaseDAO=bookcaseDAO;
        this.bookShelfDAO=bookShelfDAO;
    }

    public void addShelf(Shelf shelf){
        if (shelf==null){
            throw new IllegalArgumentException("Shelf cannot be null");
        }

        if (!bookcaseDAO.bookcaseExists(shelf.getBookcaseID())){
            throw new IllegalArgumentException("Bookcase does not exist");
        }

        shelfDAO.addShelf(shelf);
    }

    public void updateShelf(Shelf shelf){

        if (shelf==null){
            throw new IllegalArgumentException("Shelf cannot be null");
        }

        if (!shelfDAO.shelfExists(shelf.getShelfID())){
            throw new IllegalArgumentException("Shelf does not exist");
        }

        if (!bookcaseDAO.bookcaseExists(shelf.getBookcaseID())){
            throw new IllegalArgumentException("Bookcase does not exist");
        }

        shelfDAO.updateShelf(shelf);
    }

    public void deleteShelf(int shelfID){
        if (shelfID<MIN_VALID_ID){
            throw new IllegalArgumentException("Shelf ID must be greater than 0");
        }

        if (!shelfDAO.shelfExists(shelfID)){
            throw new IllegalArgumentException("Shelf does not exist");
        }

        if (shelfDAO.hasBooks(shelfID)){
            throw new IllegalArgumentException("Cannot delete shelf because it has books");
        }

        shelfDAO.deleteShelf(shelfID);
    }

    public void getAllShelves(){
        shelfDAO.getAllShelves();
    }

    public boolean hasAvailableSpace(Shelf shelf, int copies){
        if (shelf==null){
            throw new IllegalArgumentException("Shelf cannot be null");
        }
        if (shelf.getShelfID() <= 0) {
            throw new IllegalArgumentException("Shelf ID must be greater than 0");
        }
        if (!shelfDAO.shelfExists(shelf.getShelfID())) {
            throw new IllegalArgumentException("Shelf does not exist");
        }
        if (copies <= 0) {
            throw new IllegalArgumentException("Copies must be greater than 0");
        }

        int currentCopies = bookShelfDAO.getCurrentCopies(shelf.getShelfID());
        int availableSpace = shelf.getCapacity() - currentCopies;

        return copies <= availableSpace;
    }

    public int getAvailableSpace(Shelf shelf) {
        if (shelf==null){
            throw new IllegalArgumentException("Shelf cannot be null");
        }
        if (shelf.getShelfID() <= 0) {
            throw new IllegalArgumentException("Shelf ID must be greater than 0");
        }

        if (!shelfDAO.shelfExists(shelf.getShelfID())) {
            throw new IllegalArgumentException("Shelf does not exist");
        }

        int currentCopies = bookShelfDAO.getCurrentCopies(shelf.getShelfID());
        int availableSpace =shelf.getCapacity()-currentCopies;

        return availableSpace;
    }
}
