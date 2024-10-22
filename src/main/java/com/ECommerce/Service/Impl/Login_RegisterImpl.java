package com.ECommerce.Service.Impl;

import com.ECommerce.Entity.Customer;
import com.ECommerce.Entity.Dto.LoginDto;
import com.ECommerce.Entity.Dto.RegisterDto;
import com.ECommerce.Entity.Enums.Roles;
import com.ECommerce.Entity.Token;
import com.ECommerce.Exception.ResourceNotFoundException;
import com.ECommerce.Repository.CustomerRepo;
import com.ECommerce.Repository.TokenRepo;
import com.ECommerce.Service.CustomerService;
import com.ECommerce.Service.Login_RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class Login_RegisterImpl implements Login_RegisterService {

    private static final Logger logger = LoggerFactory.getLogger(Login_RegisterImpl.class);

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private TokenService tokenService;


    @Override
    public String register(RegisterDto registerDto) {

        Customer customer = new Customer();
        customer.setFirstName(registerDto.getFirstName());
        customer.setLastName(registerDto.getLastName());
        customer.setEmail(registerDto.getEmail());
        customer.setPassword(registerDto.getPassword());
        customer.setMobileNo(registerDto.getMobileNo());
        customer.setRole(Roles.USER);

        this.customerRepo.save(customer);

        // 1. Pass the customer to the newly register. By this it will generate token and we can save it DB.
        Token token = this.tokenService.generateToken(customer);

        // return the newly generated token.
        return token.getToken();
    }

    @Override
    public Customer login(LoginDto loginDto, String token) throws ResourceNotFoundException {

        Optional<Customer> byEmailAndPassword = this.customerRepo.
                findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());

        Integer tokeCustomerId = Integer.valueOf(token.split("\\|")[3]);
        Integer dbCustomerId = byEmailAndPassword.get().getCustomerId();

        boolean verifyCustomer = tokeCustomerId.equals(dbCustomerId);

//        logger.info("Token Customer ID : {}", tokeCustomerId);
//        logger.info("DB Customer ID : {}", dbCustomerId);
//        logger.info("Result : {}", verifyCustomer);

        // Delete the expired Token
        try{
            tokenService.deleteExpiredToken(token);
        } catch (Exception e) {
            logger.info("No Such token found : {} ", e.getMessage());
        }

        if(
                byEmailAndPassword.isPresent()
                && this.tokenService.isTokenExist(token)
                && this.tokenService.isTokenValid(token)
                && verifyCustomer
        ){
            return byEmailAndPassword.get();
        }else {
            throw new ResourceNotFoundException("No Customer Found!");
        }
    }
}
