package service;

import dao.LibrarianDAO;
import model.Librarian;

public class LibrarianService {
    private LibrarianDAO librarianDAO;
    private static final int MIN_VALID_ID = 1;
    public LibrarianService(LibrarianDAO librarianDAO) {
        this.librarianDAO = librarianDAO;
    }

    public void addLibrarian(Librarian librarian){
        if (librarian==null){
            throw new IllegalArgumentException( "Librarian cannot be null" );
        }
        if (librarianDAO.usernameExists(librarian.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        librarianDAO.addLibrarian(librarian);
    }

    public void updateLibrarian(Librarian librarian){
        if (librarian==null){
            throw new IllegalArgumentException( "Librarian cannot be null" );
        }
        if (!librarianDAO.librarianExists(librarian.getLibrarianID())){
            throw new IllegalArgumentException( "Librarian does not exist" );
        }
        if (librarianDAO.usernameExistsForAnotherLibrarian(librarian.getUsername(), librarian.getLibrarianID())){
            throw new IllegalArgumentException("Username already exists");
        }
        librarianDAO.updateLibrarian(librarian);
    }

    public void deleteLibrarian(int librarianID){
        if (librarianID<MIN_VALID_ID){
            throw new IllegalArgumentException( "Librarian ID must be greater than 0" );
        }
        if (!librarianDAO.librarianExists(librarianID)){
            throw new IllegalArgumentException( "Librarian does not exist" );
        }

        librarianDAO.deleteLibrarian(librarianID);
    }

    public boolean login(String username, String password) {
        if (username==null || username.trim().isEmpty()){
            throw new IllegalArgumentException( "Username cannot be empty" );
        }
        if (password==null || password.trim().isEmpty()){
            throw new IllegalArgumentException( "Password cannot be empty" );
        }

        return librarianDAO.login(username,password);

    }

    public void getAllLibrarian(){
        librarianDAO.getAllLibrarian();
    }
}
