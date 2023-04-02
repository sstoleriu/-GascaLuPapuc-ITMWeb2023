package ro.gascalupapuc.EcoShare.rest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.gascalupapuc.EcoShare.model.Report;

import ro.gascalupapuc.EcoShare.model.User;
import ro.gascalupapuc.EcoShare.model.dto.ReportDTO;
import ro.gascalupapuc.EcoShare.model.dto.ResponseUserDTO;
import ro.gascalupapuc.EcoShare.model.dto.UserDTO;
import ro.gascalupapuc.EcoShare.rest.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public ResponseUserDTO createUser(UserDTO userDTO){
            User user = User.builder()
                    .id(userDTO.getId())
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .email(userDTO.getEmail())
                    .phoneNumber(userDTO.getPhoneNumber())
                    .role(userDTO.getRole())
                    .build();

            userRepository.save(user);
            return mapEntityToResponseDTO(user);
    }
    public static ResponseUserDTO mapEntityToResponseDTO(User user){

        List<Report> liftOfReports = user.getListOfReports();
        List<ReportDTO> reportDTOS = null;
        if(liftOfReports == null){
            reportDTOS = new ArrayList< ReportDTO >();
        } else {
            reportDTOS = user.getListOfReports().stream()
                    .map(report->ReportService.mapEntityToDTO(report))
                    .collect(Collectors.toList());
        }

        return  ResponseUserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .listOfReportsDTO(reportDTOS)
                .build();
    }

    public static UserDTO mapEntityToUserDTO(User user){
        if(user == null) return null;

        return  UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }
}
