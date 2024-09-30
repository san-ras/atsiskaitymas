package lt.techin.library.pt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class BookCatalogSandraR implements BookCatalog {

    private ArrayList<Book> books;

    public BookCatalogSandraR() {
        this.books = new ArrayList<>();
    }

    @Override
    public void addBook(Book book) {
        if (book == null
                || book.getIsbn() == null
                || book.getTitle() == null
                || book.getIsbn().isEmpty()
                || book.getTitle().isEmpty()) {
            throw new IllegalArgumentException();
        } else if (!isBookInCatalog(book.getIsbn())) {
            books.add(book);
        }
    }

    @Override
    public int getTotalNumberOfBooks() {
        return books.size();
    }

    @Override
    public void printBookTitles() {
        books.forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printTitlesOfBooksPublishedAfter(int year) {
        books.stream().filter(book -> book.getPublicationYear() > year).forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return books.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst().orElseThrow(() -> new BookNotFoundException("Book with ISBN " + isbn + " not found."));
    }

    @Override
    public List<Book> searchBooksByAuthor(String author) {
        return books.stream().filter(book -> book.getAuthor().getName().equals(author)).collect(Collectors.toList());
    }

    @Override
    public boolean isBookInCatalog(String isbn) {
        return books.stream().anyMatch(book -> book.getIsbn().equals(isbn));
    }

    @Override
    public boolean isBookAvailable(String isbn) {
        return books.stream().anyMatch(book -> book.getIsbn().equals(isbn) && book.isAvailable());
    }

    @Override
    public double calculateTotalPrice() {
        return books.stream().mapToDouble(Book::getPrice).sum();
    }

    @Override
    public double calculateAveragePrice() {
        return books.stream().mapToDouble(Book::getPrice).average().orElse(0.0);
    }

    @Override
    public List<Book> getSortedBooks() {
        return books.stream().sorted(Comparator.comparingInt(Book::getPublicationYear)).collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBooksByTitleContaining(String text) {
        if (!text.isEmpty()) {
            return books.stream().filter(book -> book.getTitle().toLowerCase().contains(text.toLowerCase())).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public Book findNewestBookByPublisher(String publisher) {
        return books.stream().filter(book -> book.getPublisher().equals(publisher)).max(Comparator.comparingInt(Book::getPublicationYear)).orElseThrow(() -> new BookNotFoundException("Book published by " + publisher + " not found."));
    }

    @Override
    public List<Book> filterBooks(Predicate<Book> predicate) {
        return books.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<Book>> groupBooksByPublisher() {
        return books.stream().collect(Collectors.groupingBy(Book::getPublisher));
    }

}