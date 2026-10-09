# Library Management System - Complete Assignment Package

## 1. Project Title
Library Management System (LMS)

## 2. Introduction
The Library Management System is a desktop-based application designed to automate the operations of a library. It allows the administrator to manage books, library members, issue/return transactions, and overall reporting. The system reduces manual errors, saves time, and provides a better experience for both librarians and users.

## 3. Objectives
- Manage book inventory records
- Register members
- Issue books to students or members
- Record returns and calculate fines
- Maintain borrowing history
- Generate summary statistics
- Provide an easy and efficient management system

## 4. Problem Statement
Manual library systems are time-consuming and error-prone. A librarian must handle book records, member details, and transaction records manually, which can create confusion, duplicate entries, and delays in processing returns. The Library Management System solves this by automating these tasks.

## 5. Scope of the Project
The system covers:
- Add, view, and track books
- Add and view member details
- Issue a book to a member
- Return a book and calculate fines
- Display transaction reports
- Show dashboard summary

## 6. Functional Requirements
1. Admin can add new books.
2. Admin can view the complete book list.
3. Admin can add new members.
4. Admin can view all members.
5. Admin can issue a book to a member.
6. System should prevent issuing books that are unavailable.
7. Admin can return borrowed books.
8. System should calculate late return fines.
9. System should store issue/return transaction records.
10. System should provide summary dashboard data.
11. System should find books and members by their IDs.
12. System should maintain available copies of each book.

## 7. Non-Functional Requirements
- User-friendly interface
- Easy to use and understand
- Fast response and processing
- Accurate calculations
- Secure handling of data
- Maintainable code structure
- Portable Java-based application

## 8. User Roles
### Admin
- Manage books
- Manage members
- Issue and return books
- View reports

### Member
- Can borrow books through the system
- Can be tracked by ID, name, and contact details

## 9. System Features
- Add book
- View all books
- Add member
- View all members
- Issue book
- Return book
- Fine calculation
- Transaction history
- Dashboard summary

## 10. Inputs and Outputs
### Inputs
- Book ID, title, author, category, ISBN, total copies
- Member ID, name, email, phone, address
- Loan details including book ID and member ID

### Outputs
- Book inventory list
- Member list
- Issue/return transaction report
- Total books, total members, issued books, available books, overdue books
- Fine amount for late return

## 11. Assumptions
- One member can borrow multiple books if available.
- Each issued book has a due date of 14 days.
- Fine is calculated as Rs. 5 per day late.
- Data is stored temporarily in memory during runtime.

## 12. Entity Details
### Book
- id
- title
- author
- category
- isbn
- totalCopies
- availableCopies

### Member
- id
- name
- email
- phone
- address

### Loan
- id
- bookId
- memberId
- issueDate
- dueDate
- returnDate
- fineAmount
- returned

## 13. Use Case Summary
- User adds a new book
- User registers a new member
- System stores book and member information
- User issues a book to a member
- User returns the book
- System updates stock count
- System calculates late fee if applicable
- User views dashboard and reports

## 14. Software Requirements
- Java JDK 8 or later
- Any IDE such as IntelliJ IDEA, Eclipse, or VS Code
- Terminal or command prompt

## 15. Implementation Approach
The project is implemented in Java using object-oriented programming. It uses classes such as Book, Member, and Loan, with an in-memory data structure to manage all records. The system is designed to be simple and understandable for academic use.

## 16. Source Code
```java
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);

    private static final ArrayList<Book> books = new ArrayList<>();
    private static final ArrayList<Member> members = new ArrayList<>();
    private static final ArrayList<Loan> loans = new ArrayList<>();

    public static void main(String[] args) {
        seedSampleData();

        while (true) {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Add Member");
            System.out.println("4. View Members");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Transactions");
            System.out.println("8. Dashboard Summary");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    viewBooks();
                    break;
                case "3":
                    addMember();
                    break;
                case "4":
                    viewMembers();
                    break;
                case "5":
                    issueBook();
                    break;
                case "6":
                    returnBook();
                    break;
                case "7":
                    viewTransactions();
                    break;
                case "8":
                    dashboardSummary();
                    break;
                case "9":
                    System.out.println("Thank you for using the Library Management System.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void seedSampleData() {
        books.add(new Book("B001", "Java Programming", "John Smith", "Programming", "978-1-2345-6789-0", 5, 5));
        books.add(new Book("B002", "Database Systems", "Maria Lopez", "Database", "978-1-2345-6789-1", 3, 3));
        books.add(new Book("B003", "Operating Systems", "Alex Brown", "Technology", "978-1-2345-6789-2", 4, 4));

        members.add(new Member("M001", "Ravi Kumar", "ravi@gmail.com", "9876543210", "Delhi"));
        members.add(new Member("M002", "Sneha Patel", "sneha@gmail.com", "9123456780", "Mumbai"));
    }

    private static void addBook() {
        System.out.print("Book ID: ");
        String id = scanner.nextLine();
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Category: ");
        String category = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Total Copies: ");
        int totalCopies = Integer.parseInt(scanner.nextLine());

        if (findBookById(id) != null) {
            System.out.println("Book ID already exists.");
            return;
        }

        books.add(new Book(id, title, author, category, isbn, totalCopies, totalCopies));
        System.out.println("Book added successfully.");
    }

    private static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        System.out.println("\nBOOK LIST");
        System.out.printf("%-8s %-25s %-20s %-15s %-15s %-10s\n",
                "ID", "Title", "Author", "Category", "ISBN", "Available");
        for (Book book : books) {
            System.out.printf("%-8s %-25s %-20s %-15s %-15s %-10d\n",
                    book.id, book.title, book.author, book.category, book.isbn, book.availableCopies);
        }
    }

    private static void addMember() {
        System.out.print("Member ID: ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();

        if (findMemberById(id) != null) {
            System.out.println("Member ID already exists.");
            return;
        }

        members.add(new Member(id, name, email, phone, address));
        System.out.println("Member added successfully.");
    }

    private static void viewMembers() {
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        System.out.println("\nMEMBER LIST");
        System.out.printf("%-8s %-18s %-25s %-15s %-20s\n", "ID", "Name", "Email", "Phone", "Address");
        for (Member member : members) {
            System.out.printf("%-8s %-18s %-25s %-15s %-20s\n",
                    member.id, member.name, member.email, member.phone, member.address);
        }
    }

    private static void issueBook() {
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();
        Book book = findBookById(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.availableCopies <= 0) {
            System.out.println("This book is currently unavailable.");
            return;
        }

        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine();
        Member member = findMemberById(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        String loanId = "L" + (loans.size() + 1);
        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(14);

        loans.add(new Loan(loanId, bookId, memberId, issueDate.toString(), dueDate.toString(), null, 0.0, false));
        book.availableCopies--;

        System.out.println("Book issued successfully to " + member.name + ".");
    }

    private static void returnBook() {
        System.out.print("Enter Loan ID: ");
        String loanId = scanner.nextLine();

        Loan loan = findLoanById(loanId);
        if (loan == null) {
            System.out.println("Loan not found.");
            return;
        }

        if (loan.returned) {
            System.out.println("This book has already been returned.");
            return;
        }

        LocalDate returnDate = LocalDate.now();
        loan.returnDate = returnDate.toString();
        loan.returned = true;
        loan.fineAmount = calculateFine(loan.dueDate, returnDate);

        Book book = findBookById(loan.bookId);
        if (book != null) {
            book.availableCopies++;
        }

        System.out.println("Book returned successfully.");
        System.out.println("Fine: Rs. " + loan.fineAmount);
    }

    private static void viewTransactions() {
        if (loans.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        System.out.println("\nTRANSACTION REPORT");
        System.out.printf("%-8s %-12s %-12s %-12s %-12s %-12s %-10s %-8s\n",
                "Loan ID", "Book ID", "Member ID", "Issue", "Due", "Return", "Status", "Fine");

        for (Loan loan : loans) {
            System.out.printf("%-8s %-12s %-12s %-12s %-12s %-12s %-10s %-8.2f\n",
                    loan.id,
                    loan.bookId,
                    loan.memberId,
                    loan.issueDate,
                    loan.dueDate,
                    loan.returnDate != null ? loan.returnDate : "-",
                    loan.returned ? "Returned" : "Issued",
                    loan.fineAmount);
        }
    }

    private static void dashboardSummary() {
        int totalBooks = books.size();
        int totalMembers = members.size();
        int issuedBooks = 0;
        int availableBooks = 0;

        for (Book book : books) {
            availableBooks += book.availableCopies;
        }

        for (Loan loan : loans) {
            if (!loan.returned) {
                issuedBooks++;
            }
        }

        System.out.println("\nDASHBOARD SUMMARY");
        System.out.println("Total Books: " + totalBooks);
        System.out.println("Total Members: " + totalMembers);
        System.out.println("Issued Books: " + issuedBooks);
        System.out.println("Available Copies: " + availableBooks);
        System.out.println("Overdue Books: " + countOverdueBooks());
    }

    private static int countOverdueBooks() {
        int count = 0;
        LocalDate today = LocalDate.now();

        for (Loan loan : loans) {
            if (!loan.returned) {
                LocalDate dueDate = LocalDate.parse(loan.dueDate);
                if (today.isAfter(dueDate)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static double calculateFine(String dueDate, LocalDate returnDate) {
        LocalDate due = LocalDate.parse(dueDate);
        if (!returnDate.isAfter(due)) {
            return 0.0;
        }
        long daysLate = ChronoUnit.DAYS.between(due, returnDate);
        return daysLate * 5.0;
    }

    private static Book findBookById(String id) {
        for (Book book : books) {
            if (book.id.equalsIgnoreCase(id)) {
                return book;
            }
        }
        return null;
    }

    private static Member findMemberById(String id) {
        for (Member member : members) {
            if (member.id.equalsIgnoreCase(id)) {
                return member;
            }
        }
        return null;
    }

    private static Loan findLoanById(String id) {
        for (Loan loan : loans) {
            if (loan.id.equalsIgnoreCase(id)) {
                return loan;
            }
        }
        return null;
    }

    static class Book {
        String id;
        String title;
        String author;
        String category;
        String isbn;
        int totalCopies;
        int availableCopies;

        Book(String id, String title, String author, String category, String isbn, int totalCopies, int availableCopies) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.category = category;
            this.isbn = isbn;
            this.totalCopies = totalCopies;
            this.availableCopies = availableCopies;
        }
    }

    static class Member {
        String id;
        String name;
        String email;
        String phone;
        String address;

        Member(String id, String name, String email, String phone, String address) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
        }
    }

    static class Loan {
        String id;
        String bookId;
        String memberId;
        String issueDate;
        String dueDate;
        String returnDate;
        double fineAmount;
        boolean returned;

        Loan(String id, String bookId, String memberId, String issueDate, String dueDate, String returnDate, double fineAmount, boolean returned) {
            this.id = id;
            this.bookId = bookId;
            this.memberId = memberId;
            this.issueDate = issueDate;
            this.dueDate = dueDate;
            this.returnDate = returnDate;
            this.fineAmount = fineAmount;
            this.returned = returned;
        }
    }
}
```

## 17. How to Run
1. Save the source code as `LibraryManagementSystem.java`
2. Open terminal or command prompt
3. Compile:
```bash
javac LibraryManagementSystem.java
```
4. Run:
```bash
java LibraryManagementSystem
```

## 18. Sample Menu
- Add Book
- View Books
- Add Member
- View Members
- Issue Book
- Return Book
- View Transactions
- Dashboard Summary
- Exit

## 19. Future Enhancements
- Add database support using MySQL or SQLite
- Create GUI using Java Swing or JavaFX
- Add login system for admin and librarian
- Add search functionality
- Add book reservation feature
- Add due date alerts
- Add PDF report generation
- Add password protection for admin access
- Add book category statistics and charts

## 20. Conclusion
The Library Management System is a practical and efficient solution for managing library operations. It helps librarians maintain accurate records, reduce manual effort, and provide timely service to library members. This Java implementation is simple, beginner-friendly, and suitable for academic assignments and mini-project submissions.

## 21. Assignment Notes
This project is suitable for:
- college mini project
- semester assignment
- software engineering project
- OOP-based Java application demonstration

## 22. Optional Final Submission Format
A typical final assignment can include:
- Introduction
- Problem Statement
- Requirements
- System Design
- Code Implementation
- Testing
- Conclusion
- Future Enhancements

This document can be copied into a project report or used as a final assignment submission.
