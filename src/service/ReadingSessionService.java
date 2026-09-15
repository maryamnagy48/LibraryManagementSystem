package service;

import dao.*;
import model.Librarian;
import model.ReadingSession;
import model.ReadingSessionDetails;

public class ReadingSessionService {
    private ReadingSessionDAO readingSessionDAO;
    private ReadingSessionDetailsDAO readingSessionDetailsDAO;
    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private LibrarianDAO librarianDAO;

    public ReadingSessionService(ReadingSessionDAO readingSessionDAO, ReadingSessionDetailsDAO readingSessionDetailsDAO,
                                 BookDAO bookDAO, MemberDAO memberDAO, LibrarianDAO librarianDAO) {
        this.readingSessionDAO = readingSessionDAO;
        this.readingSessionDetailsDAO = readingSessionDetailsDAO;
        this.bookDAO = bookDAO;
        this.memberDAO = memberDAO;
        this.librarianDAO = librarianDAO;
    }

    public void startSession(ReadingSession session){
        if (session==null){
            throw new IllegalArgumentException("Reading session cannot be null");
        }
        if (!session.isActive()){
            throw new IllegalArgumentException("Session must be active when started");
        }
        if (!memberDAO.memberExists(session.getMemberID())){
            throw new IllegalArgumentException("Member does not exist");
        }
        if (!librarianDAO.librarianExists(session.getLibrarianID())){
            throw new IllegalArgumentException("Librarian does not exist");
        }

        readingSessionDAO.addReadingSession(session);
    }

    public void endSession(ReadingSession session) {

        if (session == null) {
            throw new IllegalArgumentException("Reading session cannot be null");
        }

        if (!session.isActive()) {
            throw new IllegalArgumentException("Session has already ended");
        }

        session.endSession();
        readingSessionDAO.updateReadingSession(session);
    }

    public void getAllSessions(){
        readingSessionDAO.getAllReadingSessions();
    }

    public void addBookToSession(ReadingSessionDetails details) {
        if (details==null){
            throw new IllegalArgumentException("ReadingSessionDetails cannot be null");
        }
        if (!readingSessionDAO.readingSessionExists(details.getReadingSessionID())){
            throw new IllegalArgumentException("Reading session does not exist");
        }
        if (!bookDAO.bookExists(details.getISBN()))
        {
            throw new IllegalArgumentException("Book does not exist");
        }
        ReadingSession session=readingSessionDAO.getReadingSessionByID(details.getReadingSessionID());

        if (!session.isActive()) {
            throw new IllegalArgumentException("Cannot add book to an ended session");
        }

        readingSessionDetailsDAO.addReadingSessionDetails(details);
    }

    public void getActiveSessions(){
        readingSessionDAO.getActiveReadingSessions();
    }

    }
