package ro.gascalupapuc.EcoShare.model.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
//import ro.gascalupapuc.EcoShare.model.Category;
import ro.gascalupapuc.EcoShare.model.Characteristics;
import ro.gascalupapuc.EcoShare.model.Operator;
import ro.gascalupapuc.EcoShare.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ResponseReportDTO {
    private Integer id;
    private List<String> category;
    private UserDTO createByUserDTO;
    private String description;
    private CharacteristicsDTO characteristicsDTO;
    private LocalDateTime createDate;
    private LocalDateTime resolveDate;
    private Boolean isResolve;
    private String base64ph;
    private Boolean isAnon;

    private String lat;
    private String lon;
}
