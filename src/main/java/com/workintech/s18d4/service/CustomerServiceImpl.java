package com.workintech.s18d4.service;

import com.workintech.s18d4.repository.CustomerRepository;
import com.workintech.s18d4.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer find(long id) {
        Optional<Customer> fCustomer = customerRepository.findById(id);
        if (fCustomer.isPresent()){
            return fCustomer.get();
        }
        else //THROW NEW CUSTOMER EXCEPTION
            return null;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }



    @Override
    public Customer delete(long id) {
        Customer fCustomer = find(id);
        customerRepository.delete(fCustomer);
        return fCustomer;
    }
}
