package com.example.kakaocoupon.api.coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_COUPON")
public class Coupon {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "COUPON_NUM", nullable = false, unique = true)
    private String coupon_num;

    @Column(name = "DATETIME", nullable = false)
    private String datetime;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostParam {
        private String email;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetParam {
        private Integer p_num;
        private Integer p_size;
        private String order_by;
        private String seq;

    }
}
