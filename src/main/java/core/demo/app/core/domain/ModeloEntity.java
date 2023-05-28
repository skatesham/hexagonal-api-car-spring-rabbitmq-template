package core.demo.app.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@EqualsAndHashCode
@Table(name = "MODELO")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModeloEntity {

    @Id
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(name = "id", nullable = false, length = 36)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "fipe_id", nullable = false, length = 100)
    private Integer fipeId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    private MarcaEntity marca;

}
