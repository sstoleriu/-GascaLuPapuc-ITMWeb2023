package ro.gascalupapuc.EcoShare.model.dto;

import lombok.Builder;
import lombok.Data;
import ro.gascalupapuc.EcoShare.model.Role;

import java.util.List;

@Data
@Builder
public class ResponseUserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<ReportDTO> listOfReportsDTO;
    private Role role;
}
