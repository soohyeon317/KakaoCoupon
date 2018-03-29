package com.demo.kakaocoupon.api.v1.repository;

import com.demo.kakaocoupon.api.v1.entity.Coupon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CouponRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    public void isEmailExist() throws Exception {
        Coupon coupon11 = Coupon.builder()
                .email("soohyeon317@gmail.com")
                .coupon_num("21sw-aaad-zzvd-werw")
                .datetime("2017-12-25 12:50:30")
                .build();
        entityManager.persist(coupon11);
        entityManager.flush();

        Boolean found = couponRepository.isEmailExist(coupon11.getEmail());

        String result = null;
        if(found) result = "soohyeon317@gmail.com";

        assertThat(result).isEqualTo(coupon11.getEmail());

    }



}