package org.example._01_nhanh_kimson_spring_homework001.Model.Entity;


import jakarta.validation.Payload;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private HttpStatus status;
    private Payload<T> payload;
    private LocalDateTime timestamp;



    @Data
    @AllArgsConstructor
    public static class Payload<T>{
        private List<T> items;
    }

}
