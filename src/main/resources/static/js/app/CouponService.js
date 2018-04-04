'use strict'; // 정확한 오류 검사에 도움

angular.module('app').factory('CouponService',
    ['$localStorage', '$http', '$q', 'urls', 'pageInfo',
        function ($localStorage, $http, $q, urls, pageInfo) {
            var factory = {
                loadCouponsOfFirstPage: loadCouponsOfFirstPage,
                getCouponsByPgNum: getCouponsByPgNum,
                getCoupons: getCoupons,
                getPages: getPages,
                createCoupon: createCoupon
            };
            return factory;

            // 첫페이지에 해당하는 쿠폰리스트 조회
            function loadCouponsOfFirstPage() {
                console.log('Fetching coupons of first page..');

                var firstPgNum = 1;
                var deferred = $q.defer();
                $http.get(urls.COUPON_SERVICE_API + '/coupons?p_num=' + firstPgNum + '&p_size=' + pageInfo.P_SIZE + '&order_by=' + pageInfo.ORDER_BY + '&seq=' + pageInfo.SEQ)
                    .then(
                        function (response) {
                            console.log('Fetched successfully coupons of first page.');

                            $localStorage.coupons = response.data.content;
                            $localStorage.pNum = firstPgNum;

                            // 페이지 개수 갱신
                            updatePages(response.data.totalPages);

                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading coupons of first page.');

                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            // 해당 페이지 번호에 대한 쿠폰 리스트 조회
            function getCouponsByPgNum(pNum) {
                console.log('Fetching coupons of p.' + pNum + '..');

                // 현재 페이지 번호에 해당하는 쿠폰 리스트 조회
                $http.get(urls.COUPON_SERVICE_API + '/coupons?p_num=' + pNum + '&p_size=' + pageInfo.P_SIZE + '&order_by=' + pageInfo.ORDER_BY + '&seq=' + pageInfo.SEQ)
                    .then(function(response) {
                        console.log('Fetched successfully coupons of p.' + pNum + '..');

                        $localStorage.coupons = response.data.content;
                        $localStorage.pNum = pNum;

                        // 페이지 개수 갱신
                        updatePages(response.data.totalPages);
                    }).catch(function(error) {
                        alert(error);
                    });
            }

            // 현재 페이지 번호에 해당하는 쿠폰 리스트 가져오기
            function getCoupons() {
                console.log('Fetching coupons.. (coupons: ' + $localStorage.coupons + ")");

                return $localStorage.coupons;
            }

            // 페이지 번호들 가져오기
            function getPages() {
                console.log('Fetching pages.. (pages: ' + $localStorage.pages + ")");

                return $localStorage.pages;
            }

            // 쿠폰 생성하기
            function createCoupon(email) {
                console.log('Creating a coupon.. (email: ' + email + ")");

                if( email == "" || !isEmailFormat(email)) {
                    alert("올바른 이메일 주소를 입력해 주세요");
                    return;
                }else {
                    var postData = {
                        email: email
                    }

                    // 쿠폰 데이터 생성
                    $http.post(urls.COUPON_SERVICE_API + '/coupons', postData)
                        .then(function() {
                            // 현재 페이지 번호에 해당하는 쿠폰 리스트 출력 및 페이지 개수 갱신
                            getCouponsByPgNum($localStorage.pNum);
                            alert("쿠폰이 발급되었습니다.")
                        }).catch(function() {
                            alert("이미 쿠폰이 발급된 이메일입니다.");
                        });
                }
            }

            // 페이지 개수 갱신
            function updatePages(totalPages) {
                console.log('Updating pages.. (totalPages: ' + totalPages + ")");

                var pages = [];
                for (var i=1; i<=totalPages;i++) {
                    pages.push(i);
                }
                $localStorage.pages = pages;
            }

            // 이메일 형식 검사
            function isEmailFormat(email) {
                console.log('Checking the email format.. (email: ' + email + ")");

                var regexp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;

                return regexp.test(email);
            }
        }
    ]);