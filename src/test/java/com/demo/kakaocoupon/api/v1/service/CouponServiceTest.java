package com.demo.kakaocoupon.api.v1.service;

import com.demo.kakaocoupon.api.v1.entity.Coupon;
import com.demo.kakaocoupon.api.v1.repository.CouponRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CouponServiceTest {

    @TestConfiguration
    static class CouponServiceTestContextConfiguration {
        @Bean
        public CouponService couponService() {
            return new CouponService();
        }
    }

    @Autowired
    private CouponService couponService;

    @MockBean
    private CouponRepository couponRepository;

    Coupon new_coupon = Coupon.builder()
            .email("soohyeon317@gmail.com")
            .coupon_num("21sw-aaad-zzvd-werw")
            .datetime("2017-12-25 12:50:30")
            .build();

    List<Coupon> coupons = Arrays.asList(new_coupon);

    int p_num = 1;
    int p_size = 5;
    Sort.Direction seq = Sort.Direction.ASC;
    String order_by = "id";
    PageRequest pageRequest = PageRequest.of(p_num - 1, p_size, seq, order_by);

    Coupon.ParamCreateCoupon paramCreateCoupon = new Coupon.ParamCreateCoupon("soohyeon317@gmail.com");

    int strLength = 16;
    String separator = "-";
    int pos = 4;

    @Before
    public void setUp() throws Exception {
        Mockito.when(couponRepository.save(new_coupon).getEmail())
                .thenReturn(new_coupon.getEmail());

        Mockito.when(couponRepository.findAll(pageRequest).getContent())
                .thenReturn(coupons);

        Mockito.when(couponRepository.isEmailExist(new_coupon.getEmail()))
                .thenReturn(true);
    }

    @Test
    public void getCouponsByPageInfo() {
        Coupon.ParamPageInfo param = new Coupon.ParamPageInfo(p_num, p_size, order_by,"asc");
        List<Coupon> result = couponService.getCouponsByPageInfo(param).getContent();

        assertThat(result).isEqualTo(coupons);
    }

    @Test
    public void isEmailExist() {
        String param = "soohyeon317@gmail.com";
        Boolean result = couponService.isEmailExist(param);

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void saveCoupon() {
        Coupon result = couponService.saveCoupon(paramCreateCoupon);

        assertThat(result.getEmail()).isEqualTo("soohyeon317@gmail.com");
    }

    @Test
    public void createRandomCouponNum() {
        String couponNum = null;
        List<String> couponNumList = new ArrayList<>();

        int totalRepeatCount = 100;
        for (int i = 0; i < totalRepeatCount; i++ ) {
            couponNum = couponService.createRandomCouponNum(strLength,separator,pos);

            if(!couponNumList.contains(couponNum)) {
                couponNumList.add(couponNum);
                System.out.println((i+1)+"번째 쿠폰번호: " + couponNum);
            }
        }
    }

}