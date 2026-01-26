package Adimin_Cond.core.infra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MessageRestError {

    private LocalDateTime timeStamp;
    private String message;
    private HttpStatus status;

    private Map<String,String> fieldErros = new HashMap<>();

    public MessageRestError(HttpStatus status,String message)
    {
        this.timeStamp=LocalDateTime.now();
        this.message=message;
        this.status=status;
    }

    public MessageRestError(HttpStatus status,String message, Map<String,String>fieldErros)
    {
        this.timeStamp=LocalDateTime.now();
        this.message=message;
        this.status=status;
        this.fieldErros=fieldErros;
    }

}
