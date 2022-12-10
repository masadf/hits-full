package itmo.serverhitsapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Point {
    private double xVal;
    private double yVal;
    private double rVal;
    private String owner;
}
