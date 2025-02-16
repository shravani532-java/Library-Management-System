package UserAuth.Authentication_Service.Controller;

import UserAuth.Authentication_Service.DTO.UserDTO;
import UserAuth.Authentication_Service.Exception.UserNotExsistException;
import UserAuth.Authentication_Service.Mapper.UserMapper;
import UserAuth.Authentication_Service.Model.User;
import UserAuth.Authentication_Service.Service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired(required = false)
    UserMapper userMapper;
    User user;
    Optional<User> exsist,userProfile;

    @Value("($user_name)")
    String name;
    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "registerFallback")
    @PostMapping("/register")
    public String register(@RequestBody UserDTO userdto){
        System.out.println(name);
        System.out.println("userdto "+userdto);
        String r = null;

        user = userMapper.userDtoToModel(userdto);
        exsist = userService.IsExsist(userdto);


        if(exsist==null){
            User u = userService.register(user);
            if(u!=null){
                r=user.getUsername()+" Registered Successfully";
            }
        }
        else{
            r = "User already has an account with this email";
        }
        return r;
    }
    //fallback
    // Fallback method for register
    public String registerFallback(UserDTO userdto, Throwable t) {
        System.out.println("Fallback method called for registration. Reason: " + t.getMessage());
        return "Service is currently unavailable. Please try again later.";
    }

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "loginFallback")
    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) throws UserNotExsistException {
        System.out.println(name);
        String l=null;
        user = userService.login(email,password);
        if(user!=null)
            l="login successfull";
        else
            throw new UserNotExsistException(email+" doesnot exsist please, create account");
        return l;
    }
    //fallback
    public String loginFallback(String email, String password, Throwable t) {
        System.out.println("Fallback method called for login. Reason: " + t.getMessage());
        return "Service is currently unavailable. Please try again later.";
    }

    @CircuitBreaker(name="${spring.application.name}",fallbackMethod = "profileFallback")
    @GetMapping("/Profile/{uid}")
    public User userProfile(@PathVariable("uid") int  user_id){
        //int uid = Integer.parseInt(user_id);
        System.out.println(name);
        userProfile = userService.userbyId(user_id);
        return userProfile.orElse(null);
        /*
        if(userprofile.isPresent())
           return userProfile.get();
        else
           return null;

        is return in single line as above
         */
    }
    //fallback
    public User profileFallback(int user_id, Throwable t){
       return new User(user_id,null,"Default user",null);
    }
}
