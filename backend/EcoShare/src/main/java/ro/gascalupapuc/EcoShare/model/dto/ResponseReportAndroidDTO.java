package ro.gascalupapuc.EcoShare.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseReportAndroidDTO {
    private String description;
    private Boolean isResolve;
}
