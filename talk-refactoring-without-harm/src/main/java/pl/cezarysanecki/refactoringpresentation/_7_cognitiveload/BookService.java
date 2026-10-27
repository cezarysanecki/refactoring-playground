package pl.cezarysanecki.refactoringpresentation._7_cognitiveload;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BookService {

    private final BookRepository bookRepository;

    public void reserve(BookId bookId, ClientId clientId) {
        Book book = bookRepository.findBy(bookId);
        AvailableBook availableBook = new AvailableBook(book);

        availableBook.reserve(clientId);

        bookRepository.save(availableBook.getBook());
    }

    public void rent(BookId bookId) {
        Book book = bookRepository.findBy(bookId);
        ReservedBook reservedBook = new ReservedBook(book);

        reservedBook.rent();

        bookRepository.save(reservedBook.getBook());
    }

}
