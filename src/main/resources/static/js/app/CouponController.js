'use strict';

angular.module('app').controller('CouponController',
    ['CouponService', '$scope',
        function( CouponService) {
            var self = this;
            self.coupons = [];
            self.pNum = {};
            self.pages = [];

            self.getCouponsByPgNum = getCouponsByPgNum;
            self.getCoupons = getCoupons;
            self.getPages = getPages;

            // 현재 페이지에 해당하는 쿠폰 리스트 가져오기
            function getCoupons(){
                console.log('Getting coupons..');

                return CouponService.getCoupons();
            }

            // 해당 페이지 번호에 대한 쿠폰 리스트 가져오기
            function getCouponsByPgNum(pNum){
                console.log('Getting coupons by a page number..');

                return CouponService.getCouponsByPgNum(pNum);
            }

            // 페이지 번호들 가져오기
            function getPages() {
                console.log('Getting pages..');

                return CouponService.getPages();
            }

            // 쿠폰 생성하기
            function createCoupon(email) {
                console.log('Creating a coupon.. (email: ' + email + ")");

                CouponService.createCoupon(email);
            }

            // 이메일 입력 시, 엔터키를 누르는 경우에는 '쿠폰 발급' 버튼 실행
            var txtBoxEmail = document.getElementById('txtEmail');
            var btnEmail = document.getElementById('btnEmail');
            txtBoxEmail.onkeydown = function (ev) {
                if(ev.keyCode == 13) {
                    console.log('onKeyDown event(Enter key)');

                    btnEmail.onclick();
                }
            }
            btnEmail.onclick = function (ev) {
                console.log('onClick event(btnEmail)');

                createCoupon(txtBoxEmail.value);
            }
        }
    ]);
