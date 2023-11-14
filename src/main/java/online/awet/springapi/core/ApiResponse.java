package online.awet.springapi.core;

public class ApiResponse<T> {
    private String returnCode;
    private String message;
    private T data;

    public ApiResponse(){}
    public ApiResponse(String returnCode, String message, T data) {
        this.returnCode = returnCode;
        this.message = message;
        this.data = data;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
