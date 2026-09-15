package model;

public class ReadingSessionDetails {
    private int readingSessionDetailsId;
    private int readingSessionID;
    private String ISBN;
    private static final int MIN_VALID_ID = 1;
    public ReadingSessionDetails(int readingSessionID,String ISBN) {
        if (readingSessionID <MIN_VALID_ID)
            throw new IllegalArgumentException(
                    "Reading Session ID must be greater than 0");

        if (ISBN == null || ISBN.trim().isEmpty())
            throw new IllegalArgumentException(
                    "ISBN cannot be empty");

        this.readingSessionID = readingSessionID;
        this.ISBN = ISBN;
    }

    public int getReadingSessionDetailsId() {
        return readingSessionDetailsId;
    }

    public void setReadingSessionDetailsId(int readingSessionDetailsId) {
        if (readingSessionDetailsId <MIN_VALID_ID){
            throw new IllegalArgumentException("Reading session details id must be greater than 0");
        }
        this.readingSessionDetailsId = readingSessionDetailsId;
    }

    public int getReadingSessionID() {
        return readingSessionID;
    }

    public String getISBN() {
        return ISBN;
    }
}
