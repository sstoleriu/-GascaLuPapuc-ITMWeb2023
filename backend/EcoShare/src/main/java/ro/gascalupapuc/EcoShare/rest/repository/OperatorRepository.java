package ro.gascalupapuc.EcoShare.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.gascalupapuc.EcoShare.model.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer> {
}
