package itmo.serverhitsapp.hits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class Point {
    @NonNull
    private Double xVal;
    @NonNull
    private Double yVal;
    @NonNull
    private Double rVal;
}
