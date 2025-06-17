package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> findAll(){
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable long id){
        Customer cus = customerService.find(id);
        return new CustomerResponse(cus.getId(),cus.getEmail(),cus.getSalary());
    }

    @PostMapping
    public CustomerResponse save(@RequestBody Customer customer){
        Customer cus =  customerService.save(customer);
        return new CustomerResponse(cus.getId(),cus.getEmail(),cus.getSalary());
    }


    @DeleteMapping("/{id}")
    public CustomerResponse delete(@PathVariable long id){
        Customer cus = customerService.delete(id);
        return new CustomerResponse(cus.getId(),cus.getEmail(),cus.getSalary());
    }
}
