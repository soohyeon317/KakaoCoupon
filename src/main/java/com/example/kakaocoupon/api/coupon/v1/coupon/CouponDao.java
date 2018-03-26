package com.example.kakaocoupon.api.coupon.v1.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponDao extends JpaRepository<Coupon, Long> {
    // 위치 매핑이 아닌 파라미터명 매핑의 쿼리는 nativeQuery는 true여야 매핑 가능
    @Query(value = "SELECT CASE WHEN COUNT(email) > 0 THEN TRUE ELSE FALSE END FROM tb_coupon WHERE email = :email", nativeQuery = true)
    Boolean isEmailExist(@Param("email") String email);
}
