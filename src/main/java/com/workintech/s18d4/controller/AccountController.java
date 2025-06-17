package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountService accountService;
    private CustomerService customerService;
    @Autowired
    public AccountController(AccountService accountService,CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }
    @GetMapping
    public List<Account> findAll(){
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public AccountResponse findById(@PathVariable long id){
        Account acc = accountService.find(id);
        return  new AccountResponse(acc.getId(), acc.getAccountName(), acc.getMoneyAmount(), new CustomerResponse(acc.getCustomer().getId(),acc.getCustomer().getEmail(),acc.getCustomer().getSalary()));
    }

    @PostMapping("/{customerId}")
    public AccountResponse save(@PathVariable long customerId,@RequestBody Account account){
        Customer customer = customerService.find(customerId);
        if(customer != null){
            customer.getAccounts().add(account);
            account.setCustomer(customer);
            accountService.save(account);
        }
        else {
            throw new RuntimeException("Customer not found");
        }
        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(),
                new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary()));
    }

    @PutMapping("/{customerId}")
    public AccountResponse update(@PathVariable long customerId,@RequestBody Account account){
        Customer customer = customerService.find(customerId);
        Account toBeUpdatedAccount = null;

        for (Account account1 : customer.getAccounts()){
            if(account1.getId() == account.getId()){
                toBeUpdatedAccount = account1;
            }
        }
        if(toBeUpdatedAccount  == null){
            throw new RuntimeException("Account not found for this customer."+customerId);
        }
        int indexOfToBeUpdated = customer.getAccounts().indexOf(toBeUpdatedAccount);
        customer.getAccounts().set(indexOfToBeUpdated,account);
        account.setCustomer(customer);
        accountService.save(account);
        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(),
                new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary()));
    }

    @DeleteMapping("/{id}")
    public AccountResponse delete(@PathVariable long id){

        Account acc =  accountService.find(id);
        accountService.delete(id);
        return new AccountResponse(acc.getId(), acc.getAccountName(), acc.getMoneyAmount(),
                new CustomerResponse(acc.getCustomer().getId(),acc.getCustomer().getEmail(),acc.getCustomer().getSalary()));
    }
}
