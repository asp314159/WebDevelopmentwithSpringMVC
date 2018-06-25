package aka.springframework.webdevelopmentwithspringmvc.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by AP
 */
@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob //will create clob field in the DB
    private String notes;

    @OneToOne
    private Recipe recipe;
}
