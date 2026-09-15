import DatabaseConnection.DatabaseConnection;
import dao.*;
import model.*;
import service.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {

            // =====================================================
            // 1. DATABASE CONNECTION
            // =====================================================

            Connection connection = DatabaseConnection.getConnection();

            System.out.println("======================================");
            System.out.println("DATABASE CONNECTED SUCCESSFULLY");
            System.out.println("======================================");


            // =====================================================
            // 2. DAOs
            // =====================================================

            CategoryDAO categoryDAO = new CategoryDAO(connection);
            BookcaseDAO bookcaseDAO = new BookcaseDAO(connection);
            ShelfDAO shelfDAO = new ShelfDAO(connection);
            BookShelfDAO bookShelfDAO = new BookShelfDAO(connection);
            BookDAO bookDAO = new BookDAO(connection);
            MemberDAO memberDAO = new MemberDAO(connection);
            LibrarianDAO librarianDAO = new LibrarianDAO(connection);
            BorrowingDAO borrowingDAO = new BorrowingDAO(connection);
            BorrowingDetailsDAO borrowingDetailsDAO = new BorrowingDetailsDAO(connection);
            ReadingSessionDAO readingSessionDAO = new ReadingSessionDAO(connection);
            ReadingSessionDetailsDAO readingSessionDetailsDAO = new ReadingSessionDetailsDAO(connection);


            // =====================================================
            // 3. SERVICES
            // =====================================================

            CategoryService categoryService = new CategoryService(categoryDAO);

            BookCaseService bookCaseService =
                    new BookCaseService(
                            bookcaseDAO,
                            categoryDAO
                    );

            ShelfService shelfService =
                    new ShelfService(
                            shelfDAO,
                            bookcaseDAO,
                            bookShelfDAO
                    );

            BookShelfService bookShelfService =
                    new BookShelfService(
                            bookShelfDAO,
                            bookDAO,
                            shelfDAO
                    );

            BookService bookService =
                    new BookService(
                            bookDAO,
                            bookShelfDAO,
                            borrowingDetailsDAO,
                            readingSessionDetailsDAO
                    );

            MemberService memberService = new MemberService(memberDAO);
            LibrarianService librarianService = new LibrarianService(librarianDAO);
            BorrowingService borrowingService =
                    new BorrowingService(
                            borrowingDAO,
                            borrowingDetailsDAO,
                            librarianDAO,
                            memberDAO,
                            bookDAO,
                            connection
                    );

            ReadingSessionService readingSessionService =
                    new ReadingSessionService(
                            readingSessionDAO,
                            readingSessionDetailsDAO,
                            bookDAO,
                            memberDAO,
                            librarianDAO
                    );


            System.out.println("\nAll DAOs and Services initialized successfully!");


            // =====================================================
            // MAIN MENU
            // =====================================================

            boolean running = true;

            while (running) {

                System.out.println("\n======================================");
                System.out.println("     LIBRARY MANAGEMENT SYSTEM");
                System.out.println("======================================");
                System.out.println("1. Category");
                System.out.println("2. Bookcase");
                System.out.println("3. Shelf");
                System.out.println("4. Book");
                System.out.println("5. BookShelf");
                System.out.println("6. Member");
                System.out.println("7. Librarian");
                System.out.println("8. Borrowing");
                System.out.println("9. Reading Session");
                System.out.println("0. Exit");
                System.out.println("======================================");

                int choice = readInt("Choose: ");

                try {

                    switch (choice) {

                        case 1:
                            categoryMenu(categoryService);
                            break;

                        case 2:
                            bookcaseMenu(bookCaseService);
                            break;

                        case 3:
                            shelfMenu(shelfService);
                            break;

                        case 4:
                            bookMenu(bookService,bookDAO);
                            break;

                        case 5:
                            bookShelfMenu(
                                    bookShelfService,
                                    bookDAO,
                                    shelfDAO
                            );
                            break;

                        case 6:
                            memberMenu(memberService);
                            break;

                        case 7:
                            librarianMenu(librarianService);
                            break;

                        case 8:
                            borrowingMenu(
                                    borrowingService,
                                    bookDAO,
                                    borrowingDAO,
                                    borrowingDetailsDAO
                            );
                            break;

                        case 9:
                            readingSessionMenu(
                                    readingSessionService,
                                    readingSessionDAO,
                                    bookDAO
                            );
                            break;

                        case 0:
                            running = false;
                            System.out.println("Exiting system...");
                            break;

                        default:
                            System.out.println("Invalid choice.");

                    }

                } catch (IllegalArgumentException e) {

                    System.out.println("\nOperation failed:");
                    System.out.println(e.getMessage());

                } catch (Exception e) {

                    System.out.println("\nUnexpected error:");
                    e.printStackTrace();
                }
            }


            connection.close();
            scanner.close();

            System.out.println("\nDatabase connection closed.");
            System.out.println("Program finished.");

        } catch (Exception e) {

            System.out.println("\n======================================");
            System.out.println("UNEXPECTED ERROR");
            System.out.println("======================================");

            e.printStackTrace();
        }
    }


    // =============================================================
    // CATEGORY MENU
    // =============================================================

    public static void categoryMenu(CategoryService categoryService) {

        boolean back = false;

        while (!back) {

            System.out.println("\n========== CATEGORY ==========");
            System.out.println("1. Add Category");
            System.out.println("2. Update Category");
            System.out.println("3. Delete Category");
            System.out.println("4. Get All Categories");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");

            try {

                switch (choice) {

                    case 1:
                        String name = readString("Enter category name: ");
                        Category category = new Category(name);
                        categoryService.addCategory(category);
                        System.out.println("Generated Category ID: " + category.getCategoryId());
                        break;


                    case 2:

                        int categoryID = readInt("Enter category ID: ");
                        String newName = readString("Enter new category name: ");
                        Category categoryToUpdate = new Category(newName);
                        categoryToUpdate.setCategoryID(categoryID);
                        categoryService.updateCategory(categoryToUpdate);
                        break;

                    case 3:

                        int deleteID = readInt("Enter category ID: ");
                        categoryService.deleteCategory(deleteID);
                        break;

                    case 4:

                        categoryService.getAllCategories();
                        break;


                    case 0:

                        back = true;
                        break;


                    default:

                        System.out.println("Invalid choice.");
                }

            } catch (IllegalArgumentException e) {

                System.out.println("Operation failed: " + e.getMessage());

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }


    // =============================================================
    // BOOKCASE MENU
    // =============================================================

    public static void bookcaseMenu(BookCaseService service) {

        boolean back = false;

        while (!back) {

            System.out.println("\n========== BOOKCASE ==========");
            System.out.println("1. Add Bookcase");
            System.out.println("2. Update Bookcase");
            System.out.println("3. Delete Bookcase");
            System.out.println("4. Get All Bookcases");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");

            try {

                switch (choice) {

                    case 1:

                        String name = readString("Enter bookcase name: ");
                        int categoryID = readInt("Enter category ID: ");
                        Bookcase bookcase = new Bookcase(name, categoryID);
                        service.addBookCase(bookcase);
                        System.out.println("Generated Bookcase ID: " + bookcase.getBookcaseID());
                        break;


                    case 2:

                        int bookcaseID = readInt("Enter bookcase ID: ");
                        String newName = readString("Enter new name: ");
                        int newCategoryID = readInt("Enter new category ID: ");
                        Bookcase updatedBookcase = new Bookcase(
                                        newName,
                                        newCategoryID
                                );

                        updatedBookcase.setBookcaseID(bookcaseID);
                        service.updateBookCase(updatedBookcase);
                        break;

                    case 3:

                        int deleteID = readInt("Enter bookcase ID: ");
                        service.deleteBookCase(deleteID);
                        break;

                    case 4:

                        service.getAllBookCases();
                        break;

                    case 0:

                        back = true;
                        break;

                    default:

                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {

                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }


    // =============================================================
    // SHELF MENU
    // =============================================================

    public static void shelfMenu(ShelfService service) {

        boolean back = false;

        while (!back) {

            System.out.println("\n========== SHELF ==========");
            System.out.println("1. Add Shelf");
            System.out.println("2. Update Shelf");
            System.out.println("3. Delete Shelf");
            System.out.println("4. Get All Shelves");
            System.out.println("5. Check Available Space");
            System.out.println("6. Get Available Space");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");

            try {

                switch (choice) {

                    case 1:

                        int shelfNumber = readInt("Enter shelf number: ");
                        int capacity = readInt("Enter capacity: ");
                        int bookcaseID = readInt("Enter bookcase ID: ");
                        Shelf shelf = new Shelf(
                                        shelfNumber,
                                        capacity,
                                        bookcaseID
                                );

                        service.addShelf(shelf);
                        System.out.println("Generated Shelf ID: " + shelf.getShelfID());
                        break;


                    case 2:

                        int shelfID = readInt("Enter shelf ID: ");
                        int newShelfNumber = readInt("Enter new shelf number: ");
                        int newCapacity = readInt("Enter new capacity: ");
                        int newBookcaseID = readInt("Enter new bookcase ID: ");
                        Shelf updatedShelf = new Shelf(
                                        newShelfNumber,
                                        newCapacity,
                                        newBookcaseID
                                );

                        updatedShelf.setShelfID(shelfID);
                        service.updateShelf(updatedShelf);
                        break;


                    case 3:

                        int deleteID = readInt("Enter shelf ID: ");
                        service.deleteShelf(deleteID);
                        break;

                    case 4:

                        service.getAllShelves();
                        break;

                    case 5:

                        int checkShelfID = readInt("Enter shelf ID: ");
                        int checkShelfNumber = readInt("Enter shelf number: ");
                        int checkCapacity = readInt("Enter shelf capacity: ");
                        int checkBookcaseID = readInt("Enter bookcase ID: ");
                        int copies = readInt("Enter number of copies: ");
                        Shelf checkShelf = new Shelf(
                                        checkShelfNumber,
                                        checkCapacity,
                                        checkBookcaseID
                                );

                        checkShelf.setShelfID(checkShelfID);
                        System.out.println("Has available space? " + service.hasAvailableSpace(checkShelf, copies));
                        break;

                    case 6:

                        int availableShelfID = readInt("Enter shelf ID: ");
                        int availableShelfNumber = readInt("Enter shelf number: ");
                        int availableCapacity = readInt("Enter shelf capacity: ");
                        int availableBookcaseID = readInt("Enter bookcase ID: ");
                        Shelf availableShelf = new Shelf(
                                        availableShelfNumber,
                                        availableCapacity,
                                        availableBookcaseID
                                );

                        availableShelf.setShelfID(availableShelfID);
                        System.out.println("Available space: " + service.getAvailableSpace(availableShelf)
                        );
                        break;


                    case 0:

                        back = true;
                        break;


                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {

                System.out.println("Operation failed: "
                        + e.getMessage());
            }
        }
    }


    // =============================================================
    // BOOK MENU
    // =============================================================

    public static void bookMenu(BookService service,BookDAO bookDAO) {

        boolean back = false;

        while (!back) {

            System.out.println("\n========== BOOK ==========");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Get All Books");
            System.out.println("5. Search By ISBN");
            System.out.println("6. Search By Title");
            System.out.println("7. Search By Author");
            System.out.println("8. Check Availability");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");

            try {

                switch (choice) {

                    case 1:

                        String isbn = readString("Enter ISBN: ");
                        String title = readString("Enter title: ");
                        String author = readString("Enter author: ");
                        String publisher = readString("Enter publisher: ");

                        int year = readInt("Enter publication year: ");
                        int totalCopies = readInt("Enter total copies: ");
                        int availableCopies = readInt("Enter available copies: ");
                        Book book = new Book(
                                        isbn,
                                        title,
                                        author,
                                        publisher,
                                        year,
                                        totalCopies,
                                        availableCopies
                                );

                        service.addBook(book);
                        System.out.println("Book added successfully!");
                        break;


                    case 2:

                        String updateISBN = readString("Enter ISBN: ");
                        String updateTitle = readString("Enter new title: ");
                        String updateAuthor = readString("Enter new author: ");
                        String updatePublisher = readString("Enter new publisher: ");
                        int updateYear = readInt("Enter new publication year: ");
                        int updateTotalCopies = readInt("Enter new total copies: ");

                        /*
                         * We get the current book first because
                         * BookService calculates AvailableCopies
                         * based on borrowed copies.
                         */

                        Book currentBook = bookDAO.getBookByISBN(updateISBN);

                        if (currentBook == null) {
                            System.out.println("Book does not exist.");
                            break;
                        }

                        Book updatedBook = new Book(
                                        updateISBN,
                                        updateTitle,
                                        updateAuthor,
                                        updatePublisher,
                                        updateYear,
                                        updateTotalCopies,
                                        currentBook.getAvailableCopies()
                                );

                        service.updateBook(updatedBook);
                        break;


                    case 3:

                        String deleteISBN = readString("Enter ISBN: ");
                        service.deleteBook(deleteISBN);
                        break;


                    case 4:

                        service.getAllBooks();
                        break;


                    case 5:

                        String searchISBN = readString("Enter ISBN: ");
                        service.searchByISBN(searchISBN);
                        break;


                    case 6:

                        String searchTitle = readString("Enter title: ");
                        service.searchByTitle(searchTitle);
                        break;


                    case 7:

                        String searchAuthor = readString("Enter author: ");
                        service.searchByAuthor(searchAuthor);
                        break;


                    case 8:

                        String availabilityISBN = readString("Enter ISBN: ");
                        System.out.println("Book available: " + service.isBookAvailable(availabilityISBN));
                        break;


                    case 0:

                        back = true;
                        break;


                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {

                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }


    // =============================================================
    // BOOKSHELF MENU
    // =============================================================

    public static void bookShelfMenu(
            BookShelfService service,
            BookDAO bookDAO,
            ShelfDAO shelfDAO) {

        boolean back = false;

        while (!back) {

            System.out.println("\n========== BOOKSHELF ==========");
            System.out.println("1. Add BookShelf");
            System.out.println("2. Update BookShelf");
            System.out.println("3. Delete BookShelf");
            System.out.println("4. Get All BookShelves");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");

            try {

                switch (choice) {

                    case 1:

                        String isbn = readString("Enter ISBN: ");
                        int shelfID = readInt("Enter shelf ID: ");
                        int copies = readInt("Enter number of copies: ");
                        Book book = bookDAO.getBookByISBN(isbn);
                        if (book == null) {
                            System.out.println("Book does not exist.");
                            break;
                        }

                        Shelf shelf = shelfDAO.getShelfByID(shelfID);

                        if (shelf == null) {
                            System.out.println("Shelf does not exist.");
                            break;
                        }

                        BookShelf bookShelf = new BookShelf(
                                        book,
                                        shelf,
                                        copies
                                );

                        service.addBookShelf(bookShelf);
                        System.out.println("Generated BookShelf ID: " + bookShelf.getBookShelfID());
                        break;


                    case 2:

                        int bookShelfID = readInt("Enter BookShelf ID: ");
                        String updateISBN = readString("Enter ISBN: ");
                        int updateShelfID = readInt("Enter new shelf ID: ");
                        int updateCopies = readInt("Enter new copies: ");
                        Book updateBook = bookDAO.getBookByISBN(updateISBN);
                        if (updateBook == null) {
                            System.out.println("Book does not exist.");
                            break;
                        }

                        Shelf updateShelf = shelfDAO.getShelfByID(updateShelfID);

                        if (updateShelf == null) {
                            System.out.println("Shelf does not exist.");
                            break;
                        }

                        BookShelf updatedBookShelf = new BookShelf(
                                        updateBook,
                                        updateShelf,
                                        updateCopies
                                );

                        updatedBookShelf.setBookShelfID(bookShelfID);
                        service.updateBookshelf(updatedBookShelf);
                        break;


                    case 3:

                        int deleteID = readInt("Enter BookShelf ID: ");
                        service.deleteBookShelf(deleteID);
                        break;


                    case 4:

                        service.getAllBookShelves();
                        break;


                    case 0:

                        back = true;
                        break;


                    default:

                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {

                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }


    // =============================================================
    // MEMBER MENU
    // =============================================================

    public static void memberMenu(MemberService service) {

        boolean back = false;

        while (!back) {

            System.out.println("\n========== MEMBER ==========");
            System.out.println("1. Add Member");
            System.out.println("2. Update Member");
            System.out.println("3. Delete Member");
            System.out.println("4. Get All Members");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");

            try {

                switch (choice) {

                    case 1:

                        String name = readString("Enter name: ");
                        String phone = readString("Enter phone: ");
                        String email = readString("Enter email: ");
                        Member member = new Member(
                                        name,
                                        phone,
                                        email
                                );

                        service.addMember(member);
                        System.out.println("Generated Member ID: " + member.getMemberId());
                        break;


                    case 2:

                        int memberID = readInt("Enter member ID: ");
                        String newName = readString("Enter new name: ");
                        String newPhone = readString("Enter new phone: ");
                        String newEmail = readString("Enter new email: ");
                        Member updatedMember = new Member(
                                        newName,
                                        newPhone,
                                        newEmail
                                );

                        updatedMember.setMemberId(memberID);
                        service.updateMember(updatedMember);
                        break;


                    case 3:

                        int deleteID = readInt("Enter member ID: ");
                        service.deleteMember(deleteID);
                        break;


                    case 4:

                        service.getAllMembers();
                        break;


                    case 0:

                        back = true;
                        break;


                    default:

                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {

                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }


    // =============================================================
    // LIBRARIAN MENU
    // =============================================================

    public static void librarianMenu(
            LibrarianService service) {

        boolean back = false;

        while (!back) {

            System.out.println("\n========== LIBRARIAN ==========");
            System.out.println("1. Add Librarian");
            System.out.println("2. Update Librarian");
            System.out.println("3. Delete Librarian");
            System.out.println("4. Get All Librarians");
            System.out.println("5. Login");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");

            try {

                switch (choice) {

                    case 1:

                        String username = readString("Enter username: ");
                        String password = readString("Enter password: ");
                        Librarian librarian = new Librarian(
                                        username,
                                        password
                                );

                        service.addLibrarian(librarian);
                        System.out.println("Generated Librarian ID: " + librarian.getLibrarianID());
                        break;


                    case 2:

                        int librarianID = readInt("Enter librarian ID: ");
                        String newUsername = readString("Enter new username: ");
                        String newPassword = readString("Enter new password: ");
                        Librarian updatedLibrarian = new Librarian(
                                        newUsername,
                                        newPassword
                                );

                        updatedLibrarian.setLibrarianID(librarianID);
                        service.updateLibrarian(updatedLibrarian);
                        break;


                    case 3:

                        int deleteID = readInt("Enter librarian ID: ");
                        service.deleteLibrarian(deleteID);
                        break;


                    case 4:

                        service.getAllLibrarian();
                        break;


                    case 5:

                        String loginUsername = readString("Enter username: ");
                        String loginPassword = readString("Enter password: ");
                        System.out.println("Login result: " + service.login(loginUsername, loginPassword));
                        break;


                    case 0:

                        back = true;
                        break;


                    default:

                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {

                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }


    // =============================================================
    // BORROWING MENU
    // =============================================================

    public static void borrowingMenu(
            BorrowingService service,
            BookDAO bookDAO,
            BorrowingDAO borrowingDAO,
            BorrowingDetailsDAO borrowingDetailsDAO) {

        boolean back = false;

        while (!back) {

            System.out.println("\n========== BORROWING ==========");
            System.out.println("1. Create Borrowing");
            System.out.println("2. Add Book To Borrowing");
            System.out.println("3. Return Book");
            System.out.println("4. Get All Borrowings");
            System.out.println("5. Get Current Borrowings");
            System.out.println("6. Get Overdue Borrowings");
            System.out.println("7. Check Overdue");
            System.out.println("8. Calculate Overdue Days");
            System.out.println("9. Delete Borrowing");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");

            try {

                switch (choice) {

                    case 1:
                        int memberID = readInt("Enter member ID: ");
                        int librarianID = readInt("Enter librarian ID: ");
                        String borrowingDateInput = readString("Enter borrowing date (YYYY-MM-DD): ");
                        String dueDateInput = readString("Enter due date (YYYY-MM-DD): ");
                        LocalDate borrowingDate = LocalDate.parse(borrowingDateInput);
                        LocalDate dueDate = LocalDate.parse(dueDateInput);
                        Borrowing borrowing = new Borrowing(
                                        borrowingDate,
                                        dueDate,
                                        memberID,
                                        librarianID
                                );

                        service.createBorrowing(borrowing);
                        System.out.println("Generated Borrowing ID: " + borrowing.getBorrowingID());
                        break;


                    case 2:

                        int borrowingID = readInt("Enter borrowing ID: ");
                        String isbn = readString("Enter ISBN: ");
                        BorrowingDetails details = new BorrowingDetails(
                                        borrowingID,
                                        isbn
                                );
                        service.addBookToBorrowing(details);
                        System.out.println("Generated BorrowingDetails ID: " + details.getBorrowingDetailsID());
                        break;


                    case 3:

                        int detailsID = readInt("Enter BorrowingDetails ID: ");
                        BorrowingDetails returnDetails = borrowingDetailsDAO.getBorrowingDetailsByID(detailsID);

                        if (returnDetails != null) {
                            service.returnBook(returnDetails);
                        }

                        break;


                    case 4:

                        service.getAllBorrowings();
                        break;


                    case 5:

                        service.getCurrentBorrowings();
                        break;


                    case 6:

                        service.getOverdueBorrowings();
                        break;


                    case 7:

                        int overdueDetailsID = readInt("Enter BorrowingDetails ID: ");
                        BorrowingDetails overdueDetails = borrowingDetailsDAO.getBorrowingDetailsByID(overdueDetailsID);

                        if (overdueDetails == null) {
                            System.out.println("BorrowingDetails does not exist");
                            break;
                        }

                        System.out.println("Is overdue: " + service.isOverdue(overdueDetails));

                    case 8:

                        int daysDetailsID = readInt("Enter BorrowingDetails ID: ");
                        BorrowingDetails daysDetails = borrowingDetailsDAO.getBorrowingDetailsByID(daysDetailsID);
                        if (daysDetails == null) {
                            System.out.println("BorrowingDetails does not exist");
                            break;
                        }

                        System.out.println("Overdue days: " + service.calculateOverdueDays(daysDetails));


                    case 9:

                        int deleteID = readInt("Enter borrowing ID: ");
                        service.deleteBorrowing(deleteID);
                        break;


                    case 0:

                        back = true;
                        break;


                    default:

                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {

                System.out.println("Operation failed: "
                        + e.getMessage());
            }
        }
    }


    // =============================================================
    // READING SESSION MENU
    // =============================================================

    public static void readingSessionMenu(
            ReadingSessionService service,
            ReadingSessionDAO readingSessionDAO,
            BookDAO bookDAO) {

        boolean back = false;

        while (!back) {

            System.out.println("\n========== READING SESSION ==========");

            System.out.println("1. Start Session");
            System.out.println("2. Add Book To Session");
            System.out.println("3. End Session");
            System.out.println("4. Get All Sessions");
            System.out.println("5. Get Active Sessions");
            System.out.println("0. Back");

            int choice = readInt("Choose: ");

            try {

                switch (choice) {

                    case 1:

                        int librarianID = readInt("Enter librarian ID: ");
                        int memberID = readInt("Enter member ID: ");
                        ReadingSession session = new ReadingSession(
                                        LocalDateTime.now(),
                                        librarianID,
                                        memberID
                                );

                        service.startSession(session);
                        System.out.println("Generated Reading Session ID: " + session.getReadingSessionId());
                        break;


                    case 2:

                        int sessionID = readInt("Enter reading session ID: ");
                        String isbn = readString("Enter ISBN: ");
                        ReadingSessionDetails details = new ReadingSessionDetails(
                                        sessionID,
                                        isbn
                                );

                        service.addBookToSession(details);
                        System.out.println("Generated ReadingSessionDetails ID: " + details.getReadingSessionDetailsId());
                        break;


                    case 3:

                        int endSessionID = readInt("Enter reading session ID: ");
                        ReadingSession sessionToEnd = readingSessionDAO.getReadingSessionByID(endSessionID);

                        if (sessionToEnd == null) {
                            System.out.println("Reading session does not exist.");
                            break;
                        }

                        service.endSession(sessionToEnd);
                        break;


                    case 4:

                        service.getAllSessions();
                        break;


                    case 5:

                        service.getActiveSessions();
                        break;


                    case 0:

                        back = true;
                        break;


                    default:

                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {

                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }


    // =============================================================
    // HELPER METHODS
    // =============================================================

    public static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);
                int value = Integer.parseInt(scanner.nextLine());
                return value;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }


    public static String readString(String message) {

        System.out.print(message);
        return scanner.nextLine();
    }



}