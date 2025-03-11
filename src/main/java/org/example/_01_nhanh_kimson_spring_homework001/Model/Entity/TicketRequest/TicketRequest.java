package org.example._01_nhanh_kimson_spring_homework001.Model.Entity.TicketRequest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {
    String passengerName;
    String travelDate;
    String sourceStation;
    String destinationStation;
    double price;
    String paymentStatus;
    String ticketStatus;
    int seatNumber;
}
