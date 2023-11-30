package online.awet.springapi.core.exceptions;

import online.awet.springapi.core.ApiResponse;
import online.awet.springapi.core.ReturnCodes;
import online.awet.springapi.core.exceptions.types.InvalidRequestDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Global exception handler for all controllers
@ControllerAdvice
public class GlobalExceptionsHandler {

    private final String REQUEST_URI = "requestURI";
    private final String EXCEPTION_CLASS = "exceptionClass";
    private final String REQUEST_METHOD = "requestMethod";

    // Default exception handler
     @ExceptionHandler
     public ResponseEntity<ApiResponse> handleException(Exception exception) {
         ApiResponse error = new ApiResponse();
         error.setReturnCode(ReturnCodes.SERVER_ERROR.getCode());
         error.setMessage(exception.getMessage());
         error.setData(null);
         return new ResponseEntity<>(error, HttpStatus.OK);
     }

     // Handle Invalid Request Body Data, this exception is thrown by the controller when
     // the .isValid() method of the request body object returns false
     @ExceptionHandler(InvalidRequestDataException.class)
     public ResponseEntity<ApiResponse> handleInvalidRequestDataException(InvalidRequestDataException exception) {
         ApiResponse error = new ApiResponse();

         // Specific data for this exception
         Map<String, Object> data = new HashMap<>();
         data.put(REQUEST_URI, exception.getRequest().getRequestURI());
         data.put(EXCEPTION_CLASS, exception.getClass().getCanonicalName());
         data.put(REQUEST_METHOD, exception.getRequest().getMethod());

         error.setReturnCode(ReturnCodes.BAD_REQUEST.getCode());
         error.setMessage(exception.getMessage());
         error.setData(data);
         return new ResponseEntity<>(error, HttpStatus.OK);
     }
}
