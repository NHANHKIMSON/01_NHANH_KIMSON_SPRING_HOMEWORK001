package org.example._01_nhanh_kimson_spring_homework001.Model.Entity;


import jakarta.validation.Payload;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private String status;
    private Payload<T> payload;


    @Data
    @AllArgsConstructor
    public static class Payload<T>{
        private List<T> items;
    }

}
