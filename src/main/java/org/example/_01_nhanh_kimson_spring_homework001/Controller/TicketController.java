package org.example._01_nhanh_kimson_spring_homework001.Controller;

import org.example._01_nhanh_kimson_spring_homework001.Model.Entity.ApiResponse;
import org.example._01_nhanh_kimson_spring_homework001.Model.Entity.Ticket;
import org.example._01_nhanh_kimson_spring_homework001.Model.Entity.TicketRequest.TicketRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
class TicketController{

    ArrayList<Ticket> tickets = new ArrayList<>();


    @GetMapping("/getAllUsers")
    public List<Ticket> getTickets(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int limit){
        int start = Math.min(offset, tickets.size());
        int end = Math.min(offset + limit, tickets.size());
        return tickets.subList(start, end);

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Ticket>> addTicket(@RequestBody TicketRequest ticketRequest) {
        // Create a new ticket object
        Ticket newTicket = new Ticket(ticketRequest.getPassengerName(), ticketRequest.getTravelDate(),
                ticketRequest.getSourceStation(), ticketRequest.getDestinationStation(),
                ticketRequest.getPrice(), ticketRequest.isPaymentStatus(),
                ticketRequest.getTicketStatus(), ticketRequest.getSeatNumber());

        tickets.add(newTicket);

        ApiResponse<Ticket> response = new ApiResponse<>(
                true,
                "Ticket created successfully",
                "100 CONTIME",
                new ApiResponse.Payload<>(Collections.singletonList(newTicket)) // Fix: Use a single object
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PutMapping("/{id}")
    public ResponseEntity<String> updateTicket(@PathVariable @RequestBody int id, @RequestBody TicketRequest ticketRequest){
        ArrayList<Ticket> newTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if(ticket.getTicketId() == id){
                ticket.setPassengerName(ticketRequest.getPassengerName());
                ticket.setTravelDate(ticketRequest.getTravelDate());
                ticket.setSourceStation(ticketRequest.getSourceStation());
                ticket.setDestinationStation(ticketRequest.getDestinationStation());
                ticket.setPrice(ticketRequest.getPrice());
                ticket.setPaymentStatus(ticketRequest.isPaymentStatus());
                ticket.setTicketStatus(ticketRequest.getTicketStatus());
                ticket.setSeatNumber(ticketRequest.getSeatNumber());
            }
        }
        return new ResponseEntity<>("Ticket updated successfully", HttpStatus.OK);
    }
}