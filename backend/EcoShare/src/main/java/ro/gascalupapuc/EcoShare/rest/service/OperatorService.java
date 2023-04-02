package ro.gascalupapuc.EcoShare.rest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.gascalupapuc.EcoShare.model.Operator;
import ro.gascalupapuc.EcoShare.model.Report;
import ro.gascalupapuc.EcoShare.model.dto.ReportDTO;
import ro.gascalupapuc.EcoShare.model.dto.ResponseUserDTO;
import ro.gascalupapuc.EcoShare.model.dto.UserDTO;
import ro.gascalupapuc.EcoShare.rest.repository.OperatorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperatorService {
    private final OperatorRepository operatorRepository;

    public void createUser(UserDTO userDTO){
        Operator user = Operator.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .role(userDTO.getRole())
                .build();

        operatorRepository.save(user);
    }

    public static ResponseUserDTO mapEntityToResponseDTO(Operator operator) {
        List<Report> liftOfReports = operator.getListOfReports();
        List<ReportDTO> reportDTOS = null;
        if(liftOfReports == null){
            reportDTOS = new ArrayList< ReportDTO >();
        } else {
            reportDTOS = operator.getListOfReports().stream()
                    .map(report->ReportService.mapEntityToDTO(report))
                    .collect(Collectors.toList());
        }

        return  ResponseUserDTO.builder()
                .id(operator.getId())
                .firstName(operator.getFirstName())
                .lastName(operator.getLastName())
                .email(operator.getEmail())
                .phoneNumber(operator.getPhoneNumber())
                .role(operator.getRole())
                .listOfReportsDTO(reportDTOS)
                .build();
    }
}
