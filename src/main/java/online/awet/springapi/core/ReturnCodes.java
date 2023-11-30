package online.awet.springapi.core;

public enum ReturnCodes {
    OK("API001"),
    CREATE_OK("API002"),
    DELETE_OK("API003"),
    UPDATE_OK("API004"),
    RESOURCE_NOT_FOUND("ERR001"),
    CREATE_ERROR("ERR002"),
    DELETE_ERROR("ERR003"),
    UPDATE_ERROR("ERR004"),
    INVALID_BODY("ERR005"),
    BAD_REQUEST("ERR006"),
    SERVER_ERROR("ERR500");

    private final String code;
    ReturnCodes(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
