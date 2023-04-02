package ro.gascalupapuc.EcoShare.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Operator {
    @Id
    private Integer id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    @Column(unique = true)
    private String phoneNumber;

    @OneToMany()
    @JoinTable(name = "operator_report",
            joinColumns =  @JoinColumn(name = "operator_id"),
            inverseJoinColumns =  @JoinColumn(name = "report_id")
    )
    private List<Report> listOfReports;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;
}
