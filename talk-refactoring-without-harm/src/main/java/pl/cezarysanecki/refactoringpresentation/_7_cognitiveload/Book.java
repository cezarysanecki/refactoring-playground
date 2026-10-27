package pl.cezarysanecki.refactoringpresentation._7_cognitiveload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * Problems:
 * - complex logic
 * - tangled business and infrastructure logic
 * <p>
 * Solution - create dedicated classes
 */
@Getter
@AllArgsConstructor
class Book {

    private BookId bookId;
    @Nullable
    private ClientId clientId;
    private BookStatus status;

    static Book createAvailableBook(BookId bookId) {
        return new Book(bookId, null, BookStatus.Available);
    }

    public void reserve(ClientId clientId) {
        if (status != BookStatus.Available) {
            throw new IllegalStateException();
        }
        if (clientId != null) {
            throw new IllegalStateException("book is already reserved");
        }
        this.status = BookStatus.Reserved;
        this.clientId = clientId;
    }

    public void cancel() {
        if (status != BookStatus.Reserved) {
            throw new IllegalStateException();
        }
        this.status = BookStatus.Available;
        this.clientId = null;
    }

    public void rent() {
        if (status != BookStatus.Reserved) {
            throw new IllegalStateException();
        }
        if (clientId == null) {
            throw new IllegalStateException("book should be firstly reserved for someone");
        }
        this.status = BookStatus.Rented;
    }

    public void endRental() {
        if (status != BookStatus.Rented) {
            throw new IllegalStateException();
        }
        this.status = BookStatus.Available;
        this.clientId = null;
    }

    public void withdraw() {
        if (status != BookStatus.Available) {
            throw new IllegalStateException();
        }
        this.status = BookStatus.Withdrawn;
        this.clientId = null;
    }

    public void redo() {
        if (status != BookStatus.Withdrawn) {
            throw new IllegalStateException();
        }
        this.status = BookStatus.Available;
        this.clientId = null;
    }

}
