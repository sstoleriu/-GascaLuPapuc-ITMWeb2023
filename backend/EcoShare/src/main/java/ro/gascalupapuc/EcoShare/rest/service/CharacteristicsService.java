package ro.gascalupapuc.EcoShare.rest.service;

import org.springframework.stereotype.Service;
import ro.gascalupapuc.EcoShare.model.Characteristics;
import ro.gascalupapuc.EcoShare.model.dto.CharacteristicsDTO;

@Service
public class CharacteristicsService {
    public CharacteristicsDTO mapEntityToDTO(Characteristics characteristics){
        return CharacteristicsDTO.builder()
                .impact(characteristics.getImpact())
                .id(characteristics.getId())
                .weight(characteristics.getWeight())
                .build();
    }
}
