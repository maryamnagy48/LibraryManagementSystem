package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReadingSession {
    private int readingSessionId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int librarianID;
    private int memberID;

    private List<ReadingSessionDetails> readingSessionDetailsList;

    private static final int MIN_VALID_ID = 1;
    public ReadingSession(LocalDateTime startTime, int librarianID, int memberID) {
        if (memberID <MIN_VALID_ID){
            throw new IllegalArgumentException("Member ID must be greater than 0");
        }
        if (librarianID <MIN_VALID_ID){
            throw new IllegalArgumentException("Librarian ID must be greater than 0");
        }
        if (startTime ==null){
            throw new IllegalArgumentException("start time cannot be null");
        }

        this.startTime = startTime;
        this.endTime = null;
        this.librarianID = librarianID;
        this.memberID = memberID;

        this.readingSessionDetailsList=new ArrayList<>();
    }

    public void addBook(ReadingSessionDetails details){
        if (details==null){
            throw new IllegalArgumentException("ReadingSessionDetails cannot be empty");
        }
        if (!isActive()){
            throw new IllegalArgumentException("session has been ended");
        }
        readingSessionDetailsList.add(details);
    }

    public void endSession(){
        if(!isActive()){
            throw new IllegalArgumentException("session has been ended");
        }
        endTime =LocalDateTime.now();
    }

    public boolean isActive(){
        return endTime ==null;
    }


    public int getReadingSessionId() {
        return readingSessionId;
    }

    public void setReadingSessionId(int readingSessionId) {
        if (readingSessionId<MIN_VALID_ID){
            throw new IllegalArgumentException("ReadingSession Id must be greater than 0");
        }
        this.readingSessionId = readingSessionId;
    }

    public LocalDateTime getStartTime() {
        return startTime ;
    }

    public void setStartTime(LocalDateTime startTime) {
        if (startTime ==null){
            throw new IllegalArgumentException("start time cannot be null");
        }
        if (endTime !=null && endTime.isBefore(startTime)){
            throw new IllegalArgumentException("end time cannot be before start time");
        }
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        if (endTime !=null && endTime.isBefore(startTime)){
            throw new IllegalArgumentException("end time cannot be before start time");
        }
        this.endTime = endTime;
    }

    public int getLibrarianID() {
        return librarianID;
    }

    public void setLibrarianID(int librarianID) {

        if (librarianID <MIN_VALID_ID)
            throw new IllegalArgumentException("Librarian ID must be greater than 0");

        this.librarianID = librarianID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {

        if (memberID <MIN_VALID_ID)
            throw new IllegalArgumentException("Member ID must be greater than 0");

        this.memberID = memberID;
    }
    public List<ReadingSessionDetails> getReadingSessionDetailsList() {
        return readingSessionDetailsList;
    }
}
