package com.demo.kakaocoupon.api.v1.repository;

import com.demo.kakaocoupon.api.v1.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(email) > 0 THEN TRUE ELSE FALSE END FROM tb_coupon WHERE email = :email", nativeQuery = true)
    Boolean isEmailExist(@Param("email") String email);

}
