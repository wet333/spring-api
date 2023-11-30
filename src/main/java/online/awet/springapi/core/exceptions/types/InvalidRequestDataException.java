package online.awet.springapi.core.exceptions.types;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidRequestDataException extends Exception {

    HttpServletRequest request;

    public InvalidRequestDataException(String message, HttpServletRequest request) {
        super(message);
        this.request = request;
    }
}
