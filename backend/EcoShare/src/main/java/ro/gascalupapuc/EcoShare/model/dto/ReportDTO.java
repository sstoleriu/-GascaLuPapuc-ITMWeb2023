package ro.gascalupapuc.EcoShare.model.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
//import ro.gascalupapuc.EcoShare.model.Category;
import ro.gascalupapuc.EcoShare.model.Characteristics;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReportDTO {
    private Integer id;
    private List<String> category;
    private String description;
    private Characteristics characteristics;
    private LocalDateTime createDate;
    private LocalDateTime resolveDate;
    private Boolean isResolve;
    private Boolean isAnon;
    private String lat;
    private String lon;
    private String bitmap;
}
