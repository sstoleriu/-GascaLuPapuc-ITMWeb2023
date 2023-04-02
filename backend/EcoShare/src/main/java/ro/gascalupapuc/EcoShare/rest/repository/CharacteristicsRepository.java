package ro.gascalupapuc.EcoShare.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.gascalupapuc.EcoShare.model.Characteristics;

@Repository
public interface CharacteristicsRepository extends JpaRepository<Characteristics,Integer> {
}
