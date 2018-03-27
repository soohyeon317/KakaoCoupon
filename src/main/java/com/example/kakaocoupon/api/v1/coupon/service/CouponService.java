package com.example.kakaocoupon.api.v1.coupon.service;

import com.example.kakaocoupon.api.v1.coupon.dao.CouponDao;
import com.example.kakaocoupon.api.v1.coupon.dto.Coupon;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service("couponServiceV1")
public class CouponService {
    private static final Logger logger = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private CouponDao couponDao;

    // 페이지 정보에 해당하는 쿠폰 리스트 조회
    public Page<Coupon> getCouponsByPageInfo(Coupon.GetParam pageInfo) {
        logger.info("getCouponsByPageInfo : {}", pageInfo);

        Sort.Direction seq = pageInfo.getSeq().toLowerCase().startsWith("d") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(pageInfo.getP_num() - 1, pageInfo.getP_size(), seq, pageInfo.getOrder_by());

        return couponDao.findAll(pageRequest);
    }

    // 이메일 중복 체크
    public Boolean isEmailExist(String email) {
        logger.info("isEmailExist : {}", email);

        return couponDao.isEmailExist(email);
    }

    // 쿠폰 생성
    public Coupon saveCoupon(String email) {
        logger.info("saveCoupon : {}", email);

        List<String> couponNumList = null;
        String couponNum = null;

        int coupNumLength = 16;
        String coupNumSeprt = "-";
        int pos = 4;

        do {
            // 현재 쿠폰아이디 리스트 조회
            couponNumList = couponDao.findAll().stream().map(c->c.getCoupon_num()).collect(Collectors.toList());
            // 쿠폰아이디 랜덤 생성
            couponNum = createRandomCouponNum(coupNumLength, coupNumSeprt, pos);
        }while(couponNumList.contains(couponNum));


        // 쿠폰 생성일시 정하기 위해 현재 날짜와 시간 구함
        String datetime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Coupon coupon = Coupon.builder()
                            .email(email)
                            .coupon_num(couponNum)
                            .datetime(datetime)
                            .build();

        return couponDao.save(coupon);
    }

    // 쿠폰번호 랜덤 생성
    public String createRandomCouponNum(int strLength, String Separator, int pos) {
        logger.info("createRandomCouponNum : {},{},{}", strLength, Separator, pos);

        final char[] characters = {
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
                ,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
                ,'0','1','2','3','4','5','6','7','8','9'
        };
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for(int i = 0; i < strLength; i++) {
            sb.append( characters[ random.nextInt( characters.length ) ] );
            if (i%pos == pos-1 && i != strLength-1) sb.append(Separator);
        }

        return sb.toString();
    }
}
