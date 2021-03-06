package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "medicao")
@NoArgsConstructor
@ToString
public class Medicao {

    public Medicao(ZonedDateTime data, String unidade, float temperatura, float umidade) {
        this.data = data;
        this.unidade = unidade;
        this.temperatura = temperatura;
        this.umidade = umidade;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "data")
    public ZonedDateTime data;

    @Column(name="unidade")
    public String unidade;

    @Column(name = "temperatura")
    public float temperatura;

    @Column(name = "umidade")
    public float umidade;
}
