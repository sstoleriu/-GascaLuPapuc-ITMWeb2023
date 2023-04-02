package ro.gascalupapuc.EcoShare.rest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ro.gascalupapuc.EcoShare.model.Operator;
import ro.gascalupapuc.EcoShare.model.Role;
import ro.gascalupapuc.EcoShare.model.User;
import ro.gascalupapuc.EcoShare.model.dto.ResponseUserDTO;
import ro.gascalupapuc.EcoShare.model.dto.UserDTO;
import ro.gascalupapuc.EcoShare.rest.repository.OperatorRepository;
import ro.gascalupapuc.EcoShare.rest.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    private final OperatorRepository operatorRepository;

    public ResponseUserDTO createUser(String role, UserDTO userDTO) {
        var userFromDB = userRepository.findAll().stream()
                .max(Comparator.comparing(User::getId))
                .get();
        Integer maxID = userFromDB.getId();



        if(Role.valueOf(role).equals(Role.OPERATOR)){
            var user = Operator.builder()
                    .id(maxID+1)
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .email(userDTO.getEmail())
                    .phoneNumber(userDTO.getPhoneNumber())
                    .role(Role.valueOf(role))
                    .build();
            operatorRepository.save(user);
            return OperatorService.mapEntityToResponseDTO(user);

        }else{
            var user = User.builder()
                    .id(maxID+1)
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .email(userDTO.getEmail())
                    .phoneNumber(userDTO.getPhoneNumber())
                    .role(Role.valueOf(role))
                    .build();
            userRepository.save(user);
            return UserService.mapEntityToResponseDTO(user);
        }
    }

    public List<ResponseUserDTO> getAllOperators() {
        return operatorRepository.findAll().stream()
                .map(o ->OperatorService.mapEntityToResponseDTO(o))
                .collect(Collectors.toList());
    }
}
