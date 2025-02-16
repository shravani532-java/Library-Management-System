package books.Book_Service.Service;

import books.Book_Service.DTO.BookDTO;
import books.Book_Service.DTO.BookDetails;
import books.Book_Service.Exception.BookNotFoundException;
import books.Book_Service.Mapper.BookMapper;
import books.Book_Service.Model.Book;
import books.Book_Service.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired(required = false)
    BookMapper bookMapper;
    @Autowired
    BookRepository bookRepository;
    Book b;
    public Book addBook(BookDTO bookDTO, byte[] file) throws IOException {
       // Optional<Book> isBookAvailable = bookRepository.findByBookNameAndAuthor(bookDTO.getBookName(),bookDTO.getAuthor());
        System.out.println("In service method");
        b = bookMapper.bookDtoToModel(bookDTO,file);

        return bookRepository.save(b);
    }

    public Book getBookById(int bid) throws BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(bid);
        return optionalBook.orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bid));
    }

    public BookDetails getBookDetailsById(int bid) throws BookNotFoundException {
        Book book = getBookById(bid);
        return bookMapper.convertToDetailsDTO(book);
    }

    public List<BookDTO> getBooks() {

          List<BookDTO> bookslu = bookRepository.findAllBooksInfo();
          System.out.println("books lu"+bookslu);
          return bookslu;
        //return new ArrayList<BookDTO>();
    }

//    public void UpdateBook() {
//        bookRepository.findById(id).map(book->book.setPrice(Price));
//    }
    public Book updateBook(int id, BookDTO updatedBook) throws BookNotFoundException {
    return bookRepository.findById(id).map(book -> {
        book.setBookName(updatedBook.getBookName() != null ? updatedBook.getBookName() : book.getBookName());
        book.setAuthor(updatedBook.getAuthor()!=null ? updatedBook.getAuthor() : book.getAuthor());
        book.setPublisher(updatedBook.getPublisher()!=null ? updatedBook.getPublisher(): book.getPublisher());
        book.setPrice(updatedBook.getPrice()!=0 ? updatedBook.getPrice(): book.getPrice());
        return bookRepository.save(book);
    }).orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
}

    public void deleteBook(Integer bid) {
        bookRepository.deleteById(bid);
    }
}
