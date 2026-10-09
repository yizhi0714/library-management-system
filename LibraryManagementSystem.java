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
