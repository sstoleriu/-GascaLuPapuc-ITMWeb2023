package ro.gascalupapuc.EcoShare.model.dto;

import lombok.Builder;
import lombok.Data;
import ro.gascalupapuc.EcoShare.model.Impact;


@Builder
@Data
public class CharacteristicsDTO {
    private Integer id;

    private Integer weight;

    private Impact impact;

}
