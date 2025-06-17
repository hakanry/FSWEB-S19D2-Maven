package com.workintech.s18d4.dto;

import com.workintech.s18d4.entity.Account;

import java.util.List;

public record CustomerResponse(long id,String email,double salary) {
}
