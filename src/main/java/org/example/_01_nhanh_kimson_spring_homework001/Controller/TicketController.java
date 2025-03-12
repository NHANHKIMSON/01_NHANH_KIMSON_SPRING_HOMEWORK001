package org.example._01_nhanh_kimson_spring_homework001.Controller;

import org.example._01_nhanh_kimson_spring_homework001.Model.Entity.ApiResponse;
import org.example._01_nhanh_kimson_spring_homework001.Model.Entity.Ticket;
import org.example._01_nhanh_kimson_spring_homework001.Model.Entity.TicketRequest.TicketRequest;
import org.example._01_nhanh_kimson_spring_homework001.Model.Entity.TicketRequest.UpdateTicketRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/tickets")
class TicketController{
    private final ArrayList<Ticket> tickets = new ArrayList<>();
    // Add Static add with autoIncrement of id
    public TicketController() {
        // List of TicketRequest objects
        List<TicketRequest> ticketRequests = new ArrayList<>();
        ticketRequests.add(new TicketRequest("Boy Loy", "2025-06-15", "Phnom Penh", "Siem Reap", 30.0, true, "Confirmed", "A1"));

        // Adding predefined Ticket objects
        tickets.add(new Ticket("Sokha", "2025-07-01", "Battambang", "Kampot", 25.5, false, "Pending", "2"));
        tickets.add(new Ticket("Dara", "2025-07-10", "Takeo", "Phnom Penh", 15.0, true, "Confirmed", "3"));
        tickets.add(new Ticket("Vuthy", "2025-08-05", "Sihanoukville", "Kampong Cham", 40.0, false, "Canceled", "4"));

        // Convert TicketRequest to Ticket and add to tickets list
        for (TicketRequest ticketRequest : ticketRequests) {
            tickets.add(new Ticket(
                    ticketRequest.getPassengerName(),
                    ticketRequest.getTravelDate(),
                    ticketRequest.getSourceStation(),
                    ticketRequest.getDestinationStation(),
                    ticketRequest.getPrice(),
                    ticketRequest.isPaymentStatus(),
                    ticketRequest.getTicketStatus(),
                    ticketRequest.getSeatNumber()
            ));
        }
    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<ApiResponse<Ticket>> getTickets(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int limit){
        int start = Math.min(offset, tickets.size());
        int end = Math.min(offset + limit, tickets.size());
        List<Ticket> paginatedTickets = tickets.subList(start, end);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "All tickets retrieved successful",
                HttpStatus.OK,
                new ApiResponse.Payload<>(paginatedTickets),
                LocalDateTime.now()
        ));
    }


//    public ResponseEntity<ApiResponse<Ticket>> updateTicketStatus()

    @GetMapping("/search/{id}")
    public ResponseEntity<ApiResponse<Ticket>> getTicketsById(@PathVariable Integer id) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == id) {
                return ResponseEntity.ok(new ApiResponse<>(
                        true,
                        "Ticket retrieved successfully.",
                        HttpStatus.OK,
                        new ApiResponse.Payload<>(Collections.singletonList(ticket)),
                        LocalDateTime.now()
                ));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                false,
                "Ticket not found.",
                HttpStatus.OK,
                new ApiResponse.Payload<>(Collections.emptyList()),
                LocalDateTime.now()
        ));
    }

//    Update by id
    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<Ticket>> updateTicket(@PathVariable @RequestBody int id, @RequestBody TicketRequest ticketRequest){
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
                return ResponseEntity.ok(new ApiResponse<>(
                        true,
                        "Deleted Successfully",
                        HttpStatus.OK,
                        null,
                        LocalDateTime.now()
                ));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                false,
                 id +"NOT_FOUND",
                HttpStatus.OK,
                null,
                LocalDateTime.now()
        ));
    }
//    Delete by id
    @DeleteMapping("remove/{id}")
    public ResponseEntity<ApiResponse<Ticket>> deleteTicket(@PathVariable Integer id, TimeZone timeZone){
        for (Ticket ticket : tickets) {
            if(ticket.getTicketId() == id){
                tickets.remove(ticket);
                return ResponseEntity.ok(
                        new ApiResponse<>(
                                true,
                                "Deleted Successful!",
                                HttpStatus.OK,
                                null,
                                LocalDateTime.now()
                        )
                );
            }
        }
        ApiResponse<Ticket> response = new ApiResponse<>(
                false,
                "No ticket found with ID: " + id,
                HttpStatus.NOT_FOUND,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //    Create New Ticket
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Ticket>> addTicket(@RequestBody TicketRequest ticketRequest, TimeZone timeZone) {
        // Create a new ticket object
        Ticket newTicket = new Ticket(ticketRequest.getPassengerName(), ticketRequest.getTravelDate(),
                ticketRequest.getSourceStation(), ticketRequest.getDestinationStation(),
                ticketRequest.getPrice(), ticketRequest.isPaymentStatus(),
                ticketRequest.getTicketStatus(), ticketRequest.getSeatNumber());

        tickets.add(newTicket);
        ApiResponse<Ticket> response = new ApiResponse<>(
                true,
                "Ticket created successfully",
                HttpStatus.CONTINUE,
                new ApiResponse.Payload<>(Collections.singletonList(newTicket)),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("search/name")
    public ResponseEntity<ApiResponse<Ticket>> searchByName(@RequestParam String name) {
        List<Ticket> searchList = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getPassengerName().equalsIgnoreCase(name)) {
                searchList.add(ticket);
            }
        }

        if (!searchList.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    "Tickets searched successfully.",
                    HttpStatus.OK,
                    new ApiResponse.Payload<>(searchList),
                    LocalDateTime.now()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    false,
                    "No tickets found for name: " + name,
                    HttpStatus.NOT_FOUND,
                    null,
                    LocalDateTime.now()
            ));
        }
    }


    public enum TicketStatus {
        COMPLETED, IN_PROGRESS, FAILED;
    }
    @GetMapping("/bytype")
    public ResponseEntity<ApiResponse<Ticket>> searchTicketsByType(@RequestParam TicketStatus ticketStatus, @RequestParam String name  ) {
        for(Ticket ticket : tickets){
            if(ticket.getPassengerName().equals(name) && ticket.getTicketStatus().equals(ticketStatus.toString())) {
                return ResponseEntity.ok(new ApiResponse<>(
                        true,
                        "Tickets filtered successfully.",
                        HttpStatus.OK,
                        new ApiResponse.Payload<>(Collections.singletonList(ticket)),
                        LocalDateTime.now()
                ));
            }
        }
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                ".",
                HttpStatus.BAD_REQUEST,
                null,
                LocalDateTime.now()
        ));
    }



    //    Create New Ticket
    @PostMapping("/create-multi")
    public ResponseEntity<ApiResponse<Ticket>> addTicketMulti(@RequestBody List<TicketRequest> ticketRequests, TimeZone timeZone) {
        List<Ticket> newTickets = new ArrayList<>();
        for (TicketRequest ticketRequest : ticketRequests) {
            Ticket newTicket = new Ticket(
                    ticketRequest.getPassengerName(),
                    ticketRequest.getTravelDate(),
                    ticketRequest.getSourceStation(),
                    ticketRequest.getDestinationStation(),
                    ticketRequest.getPrice(),
                    ticketRequest.isPaymentStatus(),
                    ticketRequest.getTicketStatus(),
                    ticketRequest.getSeatNumber()
            );
            newTickets.add(newTicket);
        }
        synchronized (tickets) {
        }
        ApiResponse<Ticket> response = new ApiResponse<>(
                true,
                "Tickets created successfully",
                HttpStatus.CREATED,
                new ApiResponse.Payload<>(newTickets),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/update-multiple")
    public ResponseEntity<ApiResponse<Ticket>> updateTicketMulti(@RequestBody UpdateTicketRequest updateTicketRequest, TimeZone timeZone) {
        List<Ticket> updatedTickets = new ArrayList<>();
        for (int id : updateTicketRequest.getTicketIds()) {
            for (Ticket ticket : tickets) {
                if (ticket.getTicketId() == id) {
                    ticket.setPaymentStatus(updateTicketRequest.isPaymentStatus());
                    updatedTickets.add(ticket);
                }
            }
        }
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Updated successfully",
                HttpStatus.OK,
                new ApiResponse.Payload<>(updatedTickets),
                LocalDateTime.now()
        ));
    }
}