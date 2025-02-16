package UserAuth.Authentication_Service.Service;

import UserAuth.Authentication_Service.DTO.UserDTO;
import UserAuth.Authentication_Service.Mapper.UserMapper;
import UserAuth.Authentication_Service.Model.User;
import UserAuth.Authentication_Service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;
    @Autowired(required = false)
    UserMapper userMapper;

    Optional<User> user;
    public Optional<User> IsExsist(UserDTO userdto) {
        userRepo.findByEmail(userdto.getEmail()).orElse(null);
        return user;
    }

    public User register(User user) {
      return userRepo.save(user);
    }

    public User login(String email, String password) {
        return userRepo.findByEmailAndPassword(email,password).orElse(null);
    }

    public Optional<User> userbyId(int userId) {
        return userRepo.findById(userId);

    }
}
