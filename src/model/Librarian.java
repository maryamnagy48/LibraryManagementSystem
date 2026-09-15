package model;

public class Librarian {
    private int librarianID;
    private String username;
    private String passwordHash;

    private static final int MIN_VALID_ID = 1;
    public Librarian(String username,String passwordHash){
        if (username==null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if(passwordHash==null || passwordHash.trim().isEmpty()){
            throw new IllegalArgumentException("Librarian password cannot be empty");
        }

        this.username=username;
        this.passwordHash=passwordHash;
    }

    public int getLibrarianID() {
        return librarianID;
    }

    public void setLibrarianID(int librarianID) {
        if (librarianID<MIN_VALID_ID){
            throw new IllegalArgumentException("Librarian ID must be greater than 0");
        }
        this.librarianID = librarianID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username==null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        if(passwordHash==null || passwordHash.trim().isEmpty()){
            throw new IllegalArgumentException("Librarian password cannot be empty");
        }
        this.passwordHash = passwordHash;
    }
}
