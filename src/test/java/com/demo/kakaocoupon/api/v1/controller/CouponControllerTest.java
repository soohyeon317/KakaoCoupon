package com.demo.kakaocoupon.api.v1.controller;

import com.demo.kakaocoupon.api.v1.entity.Coupon;
import com.demo.kakaocoupon.api.v1.service.CouponService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(CouponController.class)
public class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    @Test
    public void createCoupon() {
        Coupon.ParamCreateCoupon paramCreateCoupon = new Coupon.ParamCreateCoupon("soohyeon317@gmail.com");

        try {
            Mockito.when(couponService.saveCoupon(paramCreateCoupon).getEmail()).thenReturn(paramCreateCoupon.getEmail());

            RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/coupons", paramCreateCoupon).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();

            assertThat(result.getResponse().getContentAsString().contains(paramCreateCoupon.getEmail())).isEqualTo(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCouponsByPageInfo() {
        int p_num = 1;
        int p_size = 5;
        String order_by = "id";
        String seq = "asc";
        Coupon.ParamPageInfo paramPageInfo = new Coupon.ParamPageInfo(p_num, p_size, order_by, seq);

        try {
            Mockito.when(couponService.getCouponsByPageInfo(paramPageInfo).getContent().get(0).getEmail()).thenReturn("soohyeon317@gmail.com");

            RequestBuilder requestBuilder = MockMvcRequestBuilders
                                            .get("/api/v1/coupons?p_num="+p_num+"&p_size="+p_size+"&order_by="+order_by+"&seq="+seq)
                                            .accept(MediaType.APPLICATION_JSON);
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();

            assertThat(result.getResponse().getContentAsString().contains("soohyeon317@gmail.com")).isEqualTo(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}