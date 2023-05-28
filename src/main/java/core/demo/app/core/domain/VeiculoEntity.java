package core.demo.app.core.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode
@Table(name = "VEICULO")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoEntity {

    @Id
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(name = "id", nullable = false, length = 36)
    private UUID id;

    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @Column(name = "preco_anuncio", nullable = false)
    private BigInteger precoAnuncio;

    @Setter
    @Column(name = "preco_fipe", nullable = false)
    private BigInteger precoFipe;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    private MarcaEntity marca;

    @ManyToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    private ModeloEntity modelo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
