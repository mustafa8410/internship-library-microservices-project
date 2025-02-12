package com.example.batch.response;

import com.example.batch.entity.Rented;
import lombok.Data;

import java.util.Date;

@Data
public class RentResponse {
    Long id;
    Date startDate;
    Date endDate;
    Long renterId;
    Long bookId;

    public RentResponse(Rented rented){
        this.id = rented.getId();
        this.startDate = rented.getStartDate();
        this.endDate = rented.getEndDate();
        this.renterId = rented.getRenter().getId();
        this.bookId = rented.getBook().getId();
    }
}
