package mate.academy.bookstore.mapper;

import java.util.Set;
import mate.academy.bookstore.config.MapperConfig;
import mate.academy.bookstore.dto.user.UserRegistrationRequestDto;
import mate.academy.bookstore.dto.user.UserResponseDto;
import mate.academy.bookstore.model.Role;
import mate.academy.bookstore.model.User;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toUserResponse(User savedUser);

    default User toModel(UserRegistrationRequestDto requestDto,
                         PasswordEncoder passwordEncoder, Role role) {
        if (requestDto == null) {
            return null;
        }
        User user = new User();
        if (requestDto.getEmail() != null) {
            user.setEmail(requestDto.getEmail());
        }
        if (requestDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }
        if (requestDto.getFirstName() != null) {
            user.setFirstName(requestDto.getFirstName());
        }
        if (requestDto.getLastName() != null) {
            user.setLastName(requestDto.getLastName());
        }
        if (requestDto.getShippingAddress() != null) {
            user.setShippingAddress(requestDto.getShippingAddress());
        }
        user.setRoles(Set.of(role));
        return user;
    }
}
