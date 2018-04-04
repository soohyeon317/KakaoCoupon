package com.demo.kakaocoupon.api.v1.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponApiError {

    private String errorMessage;

}
