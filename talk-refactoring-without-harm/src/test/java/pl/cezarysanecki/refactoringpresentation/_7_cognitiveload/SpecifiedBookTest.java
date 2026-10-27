package pl.cezarysanecki.refactoringpresentation._7_cognitiveload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.cezarysanecki.refactoringpresentation._7_cognitiveload.BookStatus.Reserved;

class SpecifiedBookTest {

    BookId bookId = anyBookId();
    ClientId clientId = anyClientId();

    @Test
    void shouldReserveBookWhenIsAvailable() {
        AvailableBook availableBook = new AvailableBook(Book.createAvailableBook(bookId));

        availableBook.reserve(clientId);

        assertEquals(Reserved, availableBook.getBook().getStatus());
        assertEquals(clientId, availableBook.getBook().getClientId());
    }

    @Test
    void shouldFailWhenRentNotReservedBook() {
        AvailableBook availableBook = new AvailableBook(Book.createAvailableBook(anyBookId()));

//        assertThrows(IllegalStateException.class, availableBook::rent);
    }

    private BookId anyBookId() {
        return new BookId(2);
    }

    private ClientId anyClientId() {
        return new ClientId(77);
    }

}
