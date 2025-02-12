package com.example.webapi.controller;

import com.example.webapi.request.rent.CreateRentRequest;
import com.example.webapi.response.RentResponse;
import com.example.webapi.service.RentedService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rent")
public class RentedController {

    private final RentedService rentedService;

    public RentedController(RentedService rentedService) {
        this.rentedService = rentedService;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public RentResponse findRentById(@PathVariable Long id){
        return rentedService.findRentById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    public List<RentResponse> findAllRentsByUserId(@RequestParam Long renterId){
        return rentedService.findAllRentsByUserId(renterId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentResponse createRent(@RequestBody CreateRentRequest createRentRequest){
        return rentedService.createRent(createRentRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRentById(@PathVariable Long id){
        rentedService.deleteRentById(id);
    }
}
