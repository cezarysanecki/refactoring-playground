package pl.cezarysanecki.refactoringpresentation._7_cognitiveload;

import lombok.Value;

@Value
class AvailableBook implements BookInterface {

    BookId bookId;
    Book book;

    AvailableBook(Book book) {
        if (book.getStatus() != BookStatus.Available) {
            throw new IllegalStateException("available book must be available");
        }
        this.bookId = book.getBookId();
        this.book = book;
    }

    public Book reserve(ClientId clientId) {
        book.reserve(clientId);
        return book;
    }

    public Book withdraw() {
        book.withdraw();
        return book;
    }

}

@Value
class ReservedBook implements BookInterface {

    BookId bookId;
    Book book;

    ReservedBook(Book book) {
        if (book.getStatus() != BookStatus.Reserved) {
            throw new IllegalStateException("reserved book must be reserved");
        }
        this.bookId = book.getBookId();
        this.book = book;
    }

    public Book cancel() {
        book.cancel();
        return book;
    }

    public Book rent() {
        book.rent();
        return book;
    }

}

@Value
class RentedBook implements BookInterface {

    BookId bookId;
    Book book;

    RentedBook(Book book) {
        if (book.getStatus() != BookStatus.Rented) {
            throw new IllegalStateException("rented book must be rented");
        }
        this.bookId = book.getBookId();
        this.book = book;
    }

    public Book endRental() {
        book.endRental();
        return book;
    }

}

@Value
class WithdrawnBook implements BookInterface {

    BookId bookId;
    Book book;

    WithdrawnBook(Book book) {
        if (book.getStatus() != BookStatus.Withdrawn) {
            throw new IllegalStateException("withdrawn book must be withdrawn");
        }
        this.bookId = book.getBookId();
        this.book = book;
    }

    public Book redo() {
        book.redo();
        return book;
    }

}
