package ro.gascalupapuc.EcoShare.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.gascalupapuc.EcoShare.model.Report;
import ro.gascalupapuc.EcoShare.model.User;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
}
