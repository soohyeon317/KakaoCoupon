var app = angular.module('app',['ui.router','ngStorage']);

app.constant('urls', {
    COUPON_SERVICE_API : 'http://localhost:8092/api/v1'
});

app.constant('pageInfo', {
    P_SIZE : 5,
    ORDER_BY : 'id',
    SEQ : 'asc'
});

app.config(['$stateProvider', '$urlRouterProvider',
                function($stateProvider, $urlRouterProvider) {
                    $stateProvider
                        .state('home', {
                            url: '',
                            templateUrl: 'partials/list.html',
                            controller:'CouponController',
                            controllerAs:'ctrl',
                            resolve: {
                                coupons: function ($q, CouponService) {
                                    //Load coupons of first page.
                                    console.log('Loading coupons of first page..');

                                    var deferred = $q.defer();
                                    CouponService.loadCouponsOfFirstPage().then(deferred.resolve, deferred.reject);
                                    return deferred.promise;
                                }
                            }
                        });
                    $urlRouterProvider.otherwise('/');
                }
        ]);