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
@Table(name = "user_account")
public class User {
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

    @OneToMany(mappedBy = "createByUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Report> listOfReports;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;
}
