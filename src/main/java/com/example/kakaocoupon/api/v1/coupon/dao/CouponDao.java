package com.example.kakaocoupon.api.v1.coupon.dao;

import com.example.kakaocoupon.api.v1.coupon.dto.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponDao extends JpaRepository<Coupon, Long> {
    @Query(value = "SELECT CASE WHEN COUNT(email) > 0 THEN TRUE ELSE FALSE END FROM tb_coupon WHERE email = :email", nativeQuery = true)
    Boolean isEmailExist(@Param("email") String email);
}
