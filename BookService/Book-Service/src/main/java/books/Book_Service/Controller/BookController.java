package books.Book_Service.Controller;

import books.Book_Service.DTO.BookDTO;
import books.Book_Service.DTO.BookDetails;
import books.Book_Service.Exception.BookNotFoundException;
import books.Book_Service.Model.Book;
import books.Book_Service.Service.BookService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    Book book;
    BookDetails bookDetails;
    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "addBookFallback")
    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestPart("bookDTO") BookDTO bookDTO, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("in addbook method");
        byte[] file_bytes = file.getBytes();
        try{
            System.out.println("in try method");
            System.out.println(bookDTO.getBookName());
            bookService.addBook(bookDTO,file_bytes);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Book added Successfully");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("book not added");
        }
    }
    //fallback method
    public ResponseEntity<String> addBookFallback(BookDTO bookDTO, MultipartFile file, Throwable t) {
        System.out.println("Fallback method called due to: " + t.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Book not added due to a system error. Please try again later.");
    }

    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "getBookByIdFallback")
    @GetMapping("/getbookbyid/{book_id}")
    public ResponseEntity<byte[]> getBookById(@PathVariable("book_id") int bid) throws BookNotFoundException {
        try {
            book = bookService.getBookById(bid);
            byte[] file_bytes = book.getData();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", book.getBookName() + ".pdf");
            return new ResponseEntity<>(file_bytes, headers, HttpStatus.OK);
            /*we can also do like this
             return ResponseEntity.ok()
                                  .headers(headers)
                                  .body(file_bytes)
             */
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    //fallback method
    public ResponseEntity<byte[]> getBookByIdFallback(int bid, Throwable t) {
        System.out.println("Fallback method called due to: " + t.getMessage());
        return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "getContentByIdFallback")
    @GetMapping("/getbookContent/{book_id}")
    public ResponseEntity<byte[]> getContentById(@PathVariable("book_id") int bid) throws BookNotFoundException {
        try {
            book = bookService.getBookById(bid);
            byte[] file_bytes = book.getData();
            return ResponseEntity.ok(file_bytes);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    //fallback method
    public ResponseEntity<byte[]> getContentByIdFallback(int bid, Throwable t){
        return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "getBookDetailsFallback")
    @GetMapping("/getBookDetailsById/{book_id}")
    public ResponseEntity<BookDetails> getBookDetails(@PathVariable("book_id") int bid){
        try{
            bookDetails = bookService.getBookDetailsById(bid);
            return ResponseEntity.status(HttpStatus.OK).body(bookDetails);
        }
        catch (Exception e){

        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    //fallback method
    public ResponseEntity<BookDetails> getBookDetailsFallback(int bid, Throwable t) {
        bookDetails = new BookDetails();
        bookDetails.setBook_id(0);
        bookDetails.setBookName("No Book");
        bookDetails.setAuthor("Unknown");
        bookDetails.setPublisher("unknown");
        return ResponseEntity.status(HttpStatus.OK).body(bookDetails);
    }


    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "getBooksFallback")
    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookDTO>> getBooks(){
        try {
            List<BookDTO> booksInfo = bookService.getBooks();
            if (booksInfo.isEmpty()) {
                System.out.println("found books");
                return ResponseEntity.noContent().build();
            }
            System.out.println("notfound books");
            return ResponseEntity.ok(booksInfo);
            /* also
            new ResponseEntity<>(body,status)->new ResponseEntity<>(booksInfo,HttpStatus.OK)
            also
            ResponseEntity.status(HttpStatus.OK).body(BooksInfo)
             */
        }
        catch (Exception e){
            System.out.println(" exception");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //fallback method
    public ResponseEntity<List<BookDTO>> getBooksFallback(Throwable t) {
        System.out.println("Fallback method called due to: " + t.getMessage());
        List<BookDTO> fallbackBooksInfo = new ArrayList<>();
        BookDTO fallbackBook = new BookDTO();
        fallbackBook.setBookName("Service Unavailable");
        fallbackBook.setAuthor("N/A");
        fallbackBook.setPublisher("N/A");
        fallbackBook.setPrice(0.0);
        fallbackBooksInfo.add(fallbackBook);
        return ResponseEntity.ok(fallbackBooksInfo);
    }

 /*   @PostMapping("/updateBookPrice")
    public void updateBook(@RequestParam("Price") Double Price){
        try{
            bookService.UpdateBook();
        }
        catch(Exception e){

        }
    }
  */
    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "updateBookFallback")
    @PutMapping("/updateBook/{bid}")
    public ResponseEntity<String> updateBook(@PathVariable("bid") Integer bid, @RequestBody BookDTO bookDTO) throws BookNotFoundException {
        System.out.println(bookDTO.toString());
        try{
            bookService.updateBook(bid,bookDTO);
            return ResponseEntity.ok("Book Updated Successfully");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book Not Found with ID "+bid);
        }

    }
    //fallback method
    public ResponseEntity<String> updateBookFallback(Integer bid, BookDTO bookDTO, Throwable t) {
        System.out.println("Fallback method called due to: " + t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("The book service is currently experiencing issues. The update for book ID " + bid + " has been temporarily saved and will be retried later.");
    }

    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "deleteBookFallback")
    @DeleteMapping("/deleteBook/{bid}")
    public ResponseEntity<String> deleteBook(@PathVariable("bid") Integer bid){
        try{
            bookService.deleteBook(bid);
            return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("book Not deleted try again");
        }
    }
    //fallback
    public ResponseEntity<String> deleteBookFallback(Integer bid, Throwable t) {
        System.out.println("Fallback method called due to: " + t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("The book service is currently experiencing issues. The request to delete the book with ID " + bid + " has been noted and will be processed once the service is available.");
    }

    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "bookPriceFallback")
    @GetMapping("/price/{book_id}")
    public double bookPrice(@PathVariable("book_id") int bid) throws BookNotFoundException {
       return bookService.getBookById(bid).getPrice();
    }
    //fallback method
    public double bookPriceFallback(int bid, Throwable t) {
        System.out.println("Fallback method called due to: " + t.getMessage());
        // You can return a default price or any other meaningful value. For example, -1 indicates an error.
        return -1;
    }
}
