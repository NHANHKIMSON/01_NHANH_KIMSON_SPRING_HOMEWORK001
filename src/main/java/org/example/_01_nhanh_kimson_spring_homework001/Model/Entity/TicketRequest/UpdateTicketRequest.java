package org.example._01_nhanh_kimson_spring_homework001.Model.Entity.TicketRequest;

import java.util.List;

public class UpdateTicketRequest {
    private List<Integer> ticketIds;
    private boolean paymentStatus;


    public List<Integer> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Integer> ticketIds) {
        this.ticketIds = ticketIds;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}