package online.awet.springapi.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ApiResponse {
    @NonNull private String returnCode;
    @NonNull private String message;
    private Map<String, Object> data;
}