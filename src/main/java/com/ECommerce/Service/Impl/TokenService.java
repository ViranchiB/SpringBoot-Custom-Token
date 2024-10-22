package com.ECommerce.Service.Impl;

import com.ECommerce.Entity.Customer;
import com.ECommerce.Entity.Token;
import com.ECommerce.Repository.TokenRepo;
import com.ECommerce.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private CustomerService customerService;

    // 1. Generate Token
    public Token generateToken(Customer customer){

        // a. Generate the random token and take the first part of it
        UUID uuid = UUID.randomUUID();
        String randomId = uuid.toString().split("-")[0];

        // b.  Set the creation and expiration time of the token
        LocalTime createdOn = LocalTime.now();
        Token token = getToken(customer, randomId, createdOn);

        // 2. Save it in DB.
        this.tokenRepo.save(token);

        return token;
    }

    private static Token getToken(Customer customer, String randomId, LocalTime createdOn) {
        LocalTime expiredOn = LocalTime.now().plusMinutes(1);

        // Create the token with mix values
        String tokenId = randomId + "|" + createdOn + "|" + expiredOn + "|" + customer.getCustomerId();

        // c. Set all the above data in token object to save it in db
        Token token = new Token();
        token.setToken(tokenId);
        token.setCreationTime(createdOn);
        token.setExpirationTime(expiredOn);
        token.set_loggedOut(false);
        token.setCustomer(customer);
        return token;
    }

    // 2. Is token exist in DB.
    public boolean isTokenExist(String token){
        return this.tokenRepo.existsByToken(token);
    }

    // 3. Check token is expired or not
    public boolean isTokenValid(String token){

        LocalTime creationTime = LocalTime.parse(token.split("\\|")[1]);
        LocalTime expirationTime = LocalTime.parse(token.split("\\|")[2]);
        LocalTime currentTime = LocalTime.now();

//        logger.info("Creation Time : {}", creationTime);
//        logger.info("Expiration Time : {}", expirationTime);
//        logger.info("Current Time : {}", currentTime);
//
//        logger.info("Result : {}", expirationTime.isAfter(currentTime));

        return expirationTime.isAfter(currentTime);
    }

    public void deleteExpiredToken(String token){

        Token byToken = this.tokenRepo.findByToken(token);

        LocalTime expirationTime = LocalTime.parse(token.split("\\|")[2]);
        LocalTime currentTime = LocalTime.now();

        if(isTokenExist(token) && expirationTime.isBefore(currentTime)){
            this.tokenRepo.delete(byToken);
        }
    }
}
