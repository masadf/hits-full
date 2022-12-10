package itmo.serverhitsapp.interaction;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiError {
    private int statusNum;
    private String message;
}
