package com.ECommerce.Entity.Dto;

import com.ECommerce.Entity.Enums.Roles;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String firstName;
    private String lastName;
    private String mobileNo;
    private String email;
    private String password;
}
