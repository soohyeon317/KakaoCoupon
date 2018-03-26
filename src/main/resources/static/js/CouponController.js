angular.module('app',[]).controller('CouponController', function($scope, $http) {
    var API_URI = 'http://localhost:8092/api/v1/coupons';

    // 이메일 입력 시, 엔터키를 누르는 경우에는 '쿠폰 발급' 버튼 실행
    var txtBoxEmail = document.getElementById('txtEmail');
    var btnEmail = document.getElementById('btnEmail');
    txtBoxEmail.onkeydown = function (ev) {
        if(ev.keyCode == 13) {
            btnEmail.onclick();
        }
    }
    btnEmail.onclick = function (ev) {
        createCoupon();
    }

    // 처음 접속 시, 현재 페이지 번호는 1로 설정
    $scope.pNum=1;

    // 처음 접속 시, 페이지 1번에 해당하는 쿠폰 리스트 출력
    getCouponsByPgNum($scope.pNum);

    $scope.getCouponsByPgNum = getCouponsByPgNum;
    $scope.createCoupon = createCoupon;

    // 해당 페이지 번호에 대한 쿠폰 리스트 조회
    function getCouponsByPgNum(pNum) {
        // 현재 페이지 번호 갱신
        $scope.pNum = pNum;

        // 현재 페이지 번호에 해당하는 쿠폰 리스트 조회
        $http.get(API_URI+'?p_num='+pNum+'&p_size=5&order_by=id&seq=asc').then(function(response) {
            $scope.coupons = [];
            $scope.coupons = response.data.content;
            $scope.pages = [];

            var totalPages = response.data.totalPages;
            for (i=1; i<=totalPages;i++) {
                $scope.pages.push(i);
            }
        }).catch(function(error) {
            alert(error);
        });
    }

    // 쿠폰 생성
    function createCoupon() {
        var email = document.getElementById("txtEmail").value;

        if( email == "" || !isEmailFormat(email)) {
            alert("올바른 이메일 주소를 입력해주세요");
            return;
        }else {
            var postData = {
                email: email
            }

            // 쿠폰 데이터 생성
            $http.post(API_URI, postData).then(function(response) {
                // 현재 페이지 번호 가져옴
                var pNum = document.getElementById("pgNum").value;

                // 현재 페이지 번호에 해당하는 쿠폰 리스트 출력
                getCouponsByPgNum(pNum);

                alert("쿠폰이 발급되었습니다.")

                // 현재 페이지 번호 갱신
                $scope.pNum = pNum;
            }).catch(function(error) {
                alert("이미 쿠폰이 발급된 이메일입니다.");
            });
        }
    }

    // 이메일 형식 검증
    function isEmailFormat(email) {
        var regexp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
        return regexp.test(email);
    }
});
