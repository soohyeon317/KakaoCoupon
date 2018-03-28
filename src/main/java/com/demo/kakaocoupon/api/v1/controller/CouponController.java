package com.demo.kakaocoupon.api.v1.controller;

import com.demo.kakaocoupon.api.v1.entity.Coupon;
import com.demo.kakaocoupon.api.v1.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CouponController {

    @Resource(name = "couponServiceV1")
    private CouponService couponService;

    /**
     * 쿠폰 생성 API
     *
     * @param couponParam
     * @return
     */
    @RequestMapping(value = "/coupons", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon.ParamCreateCoupon couponParam)  {
        log.info("Creating Coupon : {}", couponParam.getEmail());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        if (couponService.isEmailExist(couponParam.getEmail())) {
            log.error("Unable to create. A coupon with {} already exist", couponParam.getEmail());

            return new ResponseEntity<>(null, httpHeaders,HttpStatus.CONFLICT);
        }

        Coupon coupon = couponService.saveCoupon(couponParam.getEmail());
        if(coupon == null) {
            log.error("Unable to create. Error occured while creating a coupon : param = {}", couponParam.getEmail());

            return new ResponseEntity<>(null, httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(coupon, httpHeaders, HttpStatus.OK);
    }

    /**
     * 페이지 정보에 해당하는 쿠폰 리스트 조회 API
     *
     * @return
     */
    @RequestMapping(value = "/coupons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<Coupon>> getCouponsByPageInfo(Coupon.ParamPageInfo pageInfo)  {
        log.info("Fetching Coupons : {}", pageInfo);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Page<Coupon> coupons = couponService.getCouponsByPageInfo(pageInfo);
        if(coupons == null) {
            log.error("Unable to fetch. Error occured while fetching coupons : pageInfo = {}", pageInfo);

            return new ResponseEntity<>(null, httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(coupons, httpHeaders, HttpStatus.OK);
    }

}