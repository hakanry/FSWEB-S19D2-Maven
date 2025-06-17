package com.workintech.s18d4.service;

import com.workintech.s18d4.repository.AddressRepository;
import com.workintech.s18d4.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }



    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address find(long id) {
        Optional<Address> fAddress = addressRepository.findById(id);
        if (fAddress.isPresent()){
            return fAddress.get();
        }
        else //THROW NEW ADDRESS EXCEPTION
            return null;
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }



    @Override
    public Address delete(long id) {
        Address fAddress = find(id);
        addressRepository.delete(fAddress);
        return fAddress;
    }
}
