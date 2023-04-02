package ro.gascalupapuc.EcoShare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String photoBase46;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "category",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private List<String> category;
//    @OneToMany
//    private List<Category> listCategoryOfObject;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User createByUser;

//    @ManyToOne()
//    @JoinTable(name = "operator_report",
//        joinColumns =  @JoinColumn(name = "report_id"),
//            inverseJoinColumns =  @JoinColumn(name = "operator_id")
//    )
//    private Operator assignedOperator;

    @NonNull
    private String description;

    @NonNull
    @OneToOne(mappedBy = "report")
    private Characteristics characteristics;

    @NonNull
    private LocalDateTime createDate;

    private LocalDateTime resolveDate;

    @NonNull
    @Column(columnDefinition = "boolean default false")
    private Boolean isResolve;

    private Boolean isAnon;

    private String lat;
    private String lon;
}
