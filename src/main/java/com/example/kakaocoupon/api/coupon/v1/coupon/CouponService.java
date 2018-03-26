package com.example.kakaocoupon.api.coupon.v1.coupon;

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

@Service("couponServiceV1")
public class CouponService {
    @Autowired
    CouponDao couponDao;

    // 페이지 정보에 따라 쿠폰 리스트 조회
    public Page<Coupon> getCouponsByPageInfo(Coupon.GetParam pageInfo) {
        Sort.Direction seq = pageInfo.getSeq().toLowerCase().startsWith("d") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(pageInfo.getP_num() - 1, pageInfo.getP_size(), seq, pageInfo.getOrder_by());

        return couponDao.findAll(pageRequest);
    }

    // 이메일 중복 체크
    public Boolean isEmailExist(String email) {
        return couponDao.isEmailExist(email);
    }

    // 쿠폰 생성
    public Coupon saveCoupon(String email) {
        List<String> couponNumList = null;
        String couponNum = null;

        do {
            // 현재 쿠폰아이디 리스트 조회
            couponNumList = couponDao.findAll().stream().map(c->c.getCoupon_num()).collect(Collectors.toList());
            // 쿠폰아이디 랜덤 생성
            couponNum = createRandomCouponNum();
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
    public String createRandomCouponNum(){
        char[] characters = {
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
                ,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
                ,'0','1','2','3','4','5','6','7','8','9'
        };

        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for( int i = 0 ; i < 16 ; i++ ){
            sb.append( characters[ random.nextInt( characters.length ) ] );

            // 4자리마다 구분자 추가, 맨 마직막에는 생략
            if ( i%4==3 && i != 15) sb.append("-");
        }

        return sb.toString();
    }
}
