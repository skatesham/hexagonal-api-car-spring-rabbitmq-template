package core.demo.app.core.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@EqualsAndHashCode
@Table(name = "MARCA")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarcaEntity {

    @Id
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(name = "id", nullable = false, length = 36)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "fipe_id", nullable = false, length = 100)
    private Integer fipeId;

}
