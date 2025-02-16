package UserAuth.Authentication_Service.Exception;

public class UserNotExsistException extends Exception{
    String message;
    public UserNotExsistException(String message) {
        super(message);
        System.out.println(message);
    }
}
