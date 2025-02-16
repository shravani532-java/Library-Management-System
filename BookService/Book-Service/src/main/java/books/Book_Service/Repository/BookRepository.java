package books.Book_Service.Repository;

import books.Book_Service.DTO.BookDTO;
import books.Book_Service.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Optional<Book> findByBookNameAndAuthor(String bookName,String Author);

    @Query(value = "select book_name,author,publisher,price from book",nativeQuery = true)
    List<BookDTO> findAllBooksInfo();
}
