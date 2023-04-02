package ro.gascalupapuc.EcoShare.model.dto;

import lombok.Builder;
import lombok.Data;
import ro.gascalupapuc.EcoShare.model.Role;

@Data
@Builder
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
}
