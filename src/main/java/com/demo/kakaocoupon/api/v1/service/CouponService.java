package com.demo.kakaocoupon.api.v1.service;

import com.demo.kakaocoupon.api.v1.entity.Coupon;
import com.demo.kakaocoupon.api.v1.repository.CouponRepository;
import lombok.extern.slf4j.Slf4j;
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

@Service
@Slf4j
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    // 페이지 정보에 해당하는 쿠폰 리스트 조회
    public Page<Coupon> getCouponsByPageInfo(Coupon.ParamPageInfo pageInfo) {
        log.info("getCouponsByPageInfo : {}", pageInfo);

        Sort.Direction seq = pageInfo.getSeq().toLowerCase().startsWith("d") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(pageInfo.getP_num() - 1, pageInfo.getP_size(), seq, pageInfo.getOrder_by());

        return couponRepository.findAll(pageRequest);
    }

    // 이메일 중복 체크
    public Boolean isEmailExist(String email) {
        log.info("isEmailExist : {}", email);

        return couponRepository.isEmailExist(email);
    }

    // 쿠폰 생성
    public Coupon saveCoupon(Coupon.ParamCreateCoupon param) {
        log.info("saveCoupon : {}", param);

        List<String> couponNumList = null;
        String couponNum = null;

        int coupNumLength = 16;
        String coupNumSeprt = "-";
        int pos = 4;

        do {
            // 쿠폰번호 랜덤 생성
            couponNum = createRandomCouponNum(coupNumLength, coupNumSeprt, pos);

            // 현재 쿠폰번호 리스트 조회
            couponNumList = couponRepository.findAll().stream().map(c->c.getCoupon_num()).collect(Collectors.toList());
        }while(couponNumList.contains(couponNum));


        // 쿠폰 생성일시 정하기 위해 현재 날짜와 시간 구함
        String datetime = LocalDateTime.now() // LocalDateTime을 사용한 이유: Date와 SimpleDateFormatter는 thread safe하지 못하지만 LocalDateTime는 thread safe하다.
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Coupon coupon = Coupon.builder()
                            .email(param.getEmail())
                            .coupon_num(couponNum)
                            .datetime(datetime)
                            .build();

        return couponRepository.save(coupon);
    }

    // 쿠폰번호 랜덤 생성
    public String createRandomCouponNum(int strLength, String separator, int pos) {
        log.info("createRandomCouponNum : {},{},{}", strLength, separator, pos);

        final char[] characters = {
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
                ,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
                ,'0','1','2','3','4','5','6','7','8','9'
        };
        StringBuffer sb = new StringBuffer(); // StringBuffer 사용한 이유: String은 immutable해서 내용이 변경될때마다 객체를 새로 생성하지만 StringBuffer는 mutable해서 객체는 1번만 생성하고 내용을 변경할 수 있다.
        Random random = new Random();

        for(int i = 0; i < strLength; i++) {
            sb.append( characters[ random.nextInt( characters.length ) ] );
            if (i%pos == pos-1 && i != strLength-1) sb.append(separator);
        }

        return sb.toString();
    }

}
