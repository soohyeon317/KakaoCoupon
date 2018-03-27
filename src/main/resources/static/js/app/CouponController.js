'use strict';
angular.module('app').controller('CouponController',
    ['CouponService', '$scope',  function( CouponService) {
        var self = this;
        self.coupons = [];
        self.pNum = {};
        self.pages = [];

        self.getCouponsByPgNum = getCouponsByPgNum;
        self.getCoupons = getCoupons;
        self.getPages = getPages;

        function getCoupons(){
            return CouponService.getCoupons();
        }

        function getCouponsByPgNum(pNum){
            return CouponService.getCouponsByPgNum(pNum);
        }

        function getPages() {
            return CouponService.getPages();
        }

        function createCoupon(email) {
            CouponService.createCoupon(email);
        }

        // 이메일 입력 시, 엔터키를 누르는 경우에는 '쿠폰 발급' 버튼 실행
        var txtBoxEmail = document.getElementById('txtEmail');
        var btnEmail = document.getElementById('btnEmail');
        txtBoxEmail.onkeydown = function (ev) {
            if(ev.keyCode == 13) {
                btnEmail.onclick();
            }
        }
        btnEmail.onclick = function (ev) {
            createCoupon(txtBoxEmail.value);
        }
    }
]);
