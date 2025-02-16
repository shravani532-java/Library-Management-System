package books.Book_Service.Mapper;

import books.Book_Service.DTO.BookDTO;
import books.Book_Service.DTO.BookDetails;
import books.Book_Service.Model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book bookDtoToModel(BookDTO bookDTO,byte[] fileData){
        Book b = new Book();
        b.setBookName(bookDTO.getBookName());
        b.setAuthor(bookDTO.getAuthor());
        b.setPublisher(bookDTO.getPublisher());
        b.setPrice(bookDTO.getPrice());
        b.setData(fileData);
        return b;
    }

    public BookDetails convertToDetailsDTO(Book book) {
        return new BookDetails(book.getBook_id(),book.getBookName(),book.getAuthor(),book.getPublisher(),book.getPrice());
    }

  /*  public Book bookDtoToModel(BookDTO bookDTO){
        Book b = new Book();
        b.setBookName(bookDTO.getBookName());
        b.setAuthor(bookDTO.getAuthor());
        b.setPublisher(bookDTO.getPublisher());
        b.setPrice(bookDTO.getPrice());
        b.setData(bookDTO.getData());
        return b;
    }*/

//    public BookDTO BookModelToDto(Book book){
//       return new BookDTO(book.getBookName(), book.getAuthor(),book.getPublisher(),book.getPrice(),book.getData());
//    }
}
