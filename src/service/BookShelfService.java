package service;

import dao.BookDAO;
import dao.BookShelfDAO;
import dao.ShelfDAO;
import model.Book;
import model.BookShelf;
import model.Shelf;

public class BookShelfService {
    private BookShelfDAO bookShelfDAO;
    private BookDAO bookDAO;
    private ShelfDAO shelfDAO;

    private static final int MIN_VALID_ID = 1;

    public BookShelfService(BookShelfDAO bookShelfDAO, BookDAO bookDAO, ShelfDAO shelfDAO) {
        this.bookShelfDAO = bookShelfDAO;
        this.bookDAO = bookDAO;
        this.shelfDAO = shelfDAO;
    }


    public void addBookShelf(BookShelf bookShelf){
        if (bookShelf==null){
            throw new IllegalArgumentException("BookShelf cannot be null");
        }
        if (!bookDAO.bookExists(bookShelf.getBook().getISBN())){
            throw new IllegalArgumentException( "Book does not exist" );
        }
        if (!shelfDAO.shelfExists(bookShelf.getShelf().getShelfID())){
            throw new IllegalArgumentException( "Shelf does not exist" );
        }

        int shelfID=bookShelf.getShelf().getShelfID();
        int currentCopies=bookShelfDAO.getCurrentCopies(shelfID);
        int availableSpace=bookShelf.getShelf().getCapacity() - currentCopies;

        if (bookShelf.getCopies()>availableSpace){
            throw new IllegalArgumentException( "Not enough space on the shelf" );
        }

        bookShelfDAO.addBookShelf(bookShelf);
    }

    public void updateBookshelf(BookShelf bookShelf){
        if (bookShelf==null){
            throw new IllegalArgumentException("BookShelf cannot be null");
        }
        if (!bookShelfDAO.bookShelfExists(bookShelf.getBookShelfID())){
            throw new IllegalArgumentException( "BookShelf does not exist" );
        }
        if (!bookDAO.bookExists(bookShelf.getBook().getISBN())){
            throw new IllegalArgumentException( "Book does not exist" );
        }
        if (!shelfDAO.shelfExists(bookShelf.getShelf().getShelfID())){
            throw new IllegalArgumentException( "Shelf does not exist" );
        }


        int oldShelf=bookShelfDAO.getShelfIDByBookShelfID(bookShelf.getBookShelfID());
        int newShelf=bookShelf.getShelf().getShelfID();

        if (oldShelf==newShelf){
            //oldcopies: القيمة الموجود فى DB قبل update

            int currentCopies=bookShelfDAO.getCurrentCopies(newShelf);
            int oldCopies=bookShelfDAO.getCopiesByBookShelfID(bookShelf.getBookShelfID());
            int availableSpace= bookShelf.getShelf().getCapacity() - currentCopies +oldCopies;

            if (bookShelf.getCopies()>availableSpace){
                throw new IllegalArgumentException( "Not enough space on the shelf" );
            }
        }
        else {
            int currentCopies=bookShelfDAO.getCurrentCopies(newShelf);
            int availableSpace= bookShelf.getShelf().getCapacity() - currentCopies ;
            if (bookShelf.getCopies()>availableSpace){
                throw new IllegalArgumentException( "Not enough space on the shelf" );
            }
        }

        bookShelfDAO.updateBookShelf(bookShelf);
    }

    public void deleteBookShelf(int bookShelfID){
        if (bookShelfID<MIN_VALID_ID){
            throw new IllegalArgumentException( "BookShelf ID must be greater than 0" );
        }
        if (!bookShelfDAO.bookShelfExists(bookShelfID)) {
            throw new IllegalArgumentException( "BookShelf does not exist" );
        }
        bookShelfDAO.deleteBookShelf(bookShelfID);
    }

    public void getAllBookShelves(){
        bookShelfDAO.getAllBookShelves();
    }
}
