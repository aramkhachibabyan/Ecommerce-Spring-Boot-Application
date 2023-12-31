package com.smartCode.ecommerce.model.dto.user.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAuthDto {
    private Integer userId;
    private String token;
}
