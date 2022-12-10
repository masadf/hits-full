package itmo.serverhitsapp.hits;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hits")
public class Hit {
    @Id
    @GeneratedValue
    private long id;
    private double xVal;
    private double yVal;
    private double rVal;
    private Date bornDate;
    private long executionTime;
    private boolean isHit;
    @JoinColumn
    private String owner;
}