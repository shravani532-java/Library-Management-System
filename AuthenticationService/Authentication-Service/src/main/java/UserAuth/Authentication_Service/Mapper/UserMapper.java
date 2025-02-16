package UserAuth.Authentication_Service.Mapper;

import UserAuth.Authentication_Service.DTO.UserDTO;
import UserAuth.Authentication_Service.Model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	
	public User userDtoToModel(UserDTO userdto) {
		System.out.println("userDTO :"+userdto);
		return new User(userdto.getId(),userdto.getEmail(),userdto.getUsername(),userdto.getPassword());
	}
	public UserDTO userModelToDto(User user){
		System.out.println("user");
		return new UserDTO(user.getUserID(),user.getEmail(),user.getUsername(),user.getPassword());
	}
}
