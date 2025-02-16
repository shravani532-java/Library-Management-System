package books.Book_Service.Exception;

public class BookNotFoundException extends Exception{

    public BookNotFoundException(String s) {
        super(s);
        System.out.println(s);
    }
}
