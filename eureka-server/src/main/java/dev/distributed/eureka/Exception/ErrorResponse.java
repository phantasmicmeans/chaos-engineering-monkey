package dev.distributed.eureka.Exception;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

@Getter @Setter
public class ErrorResponse {

    private Date timeStamp;
    private String message;
    @JsonCreator
    public ErrorResponse(
            @JsonProperty("date") Date timeStamp,
            @JsonProperty("message") String message) {
        this.timeStamp = timeStamp;
        this.message = message;
    }

}
