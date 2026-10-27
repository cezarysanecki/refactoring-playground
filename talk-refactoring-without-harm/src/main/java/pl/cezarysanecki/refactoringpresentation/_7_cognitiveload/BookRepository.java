package pl.cezarysanecki.refactoringpresentation._7_cognitiveload;

interface BookRepository {

    Book findBy(BookId bookId);

    Book save(Book book);

    BookInterface findBookInterfaceBy(BookId bookId);

    BookInterface save(BookInterface book);

    class BookRepositoryImpl implements BookRepository {

        @Override
        public Book findBy(final BookId bookId) {
            return null;
        }

        @Override
        public Book save(final Book book) {
            return null;
        }

        @Override
        public BookInterface findBookInterfaceBy(BookId bookId) {
            Book book = findBy(bookId);

            return switch (book.getStatus()) {
                case Available -> new AvailableBook(book);
                case Reserved -> new ReservedBook(book);
                case Rented -> new RentedBook(book);
                case Withdrawn -> new WithdrawnBook(book);
            };
        }

        @Override
        public BookInterface save(BookInterface book) {
            Book oldBook = findBy(book.getBookId());

            if (book instanceof AvailableBook availableBook) {
                // update book
            } else if (book instanceof ReservedBook reservedBook) {
                // update book
            } else if (book instanceof RentedBook rentedBook) {
                // update book
            } else if (book instanceof WithdrawnBook withdrawnBook) {
                // update book
            }

            return book;
        }

    }

}
