package com.ECommerce.Service;

import com.ECommerce.Entity.Customer;
import com.ECommerce.Entity.Dto.LoginDto;
import com.ECommerce.Entity.Dto.RegisterDto;
import com.ECommerce.Exception.ResourceNotFoundException;

import java.security.NoSuchAlgorithmException;

public interface Login_RegisterService {

    public String register(RegisterDto registerDto);

    public Customer login(LoginDto loginDto, String token) throws ResourceNotFoundException;

}
