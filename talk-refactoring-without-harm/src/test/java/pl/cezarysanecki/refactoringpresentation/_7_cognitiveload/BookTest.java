package pl.cezarysanecki.refactoringpresentation._7_cognitiveload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.cezarysanecki.refactoringpresentation._7_cognitiveload.BookStatus.Reserved;

class BookTest {

    BookId bookId = anyBookId();
    ClientId clientId = anyClientId();

    @Test
    void shouldReserveBookWhenIsAvailable() {
        Book book = Book.createAvailableBook(bookId);

        book.reserve(clientId);

        assertEquals(Reserved, book.getStatus());
        assertEquals(clientId, book.getClientId());
    }

    @Test
    void shouldFailWhenRentNotReservedBook() {
        Book book = Book.createAvailableBook(anyBookId());

        assertThrows(IllegalStateException.class, book::rent);
    }

    private BookId anyBookId() {
        return new BookId(2);
    }

    private ClientId anyClientId() {
        return new ClientId(77);
    }

}
