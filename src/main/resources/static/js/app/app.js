var app = angular.module('app',['ui.router','ngStorage']);
app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('home', {
                url: '',
                templateUrl: 'list.html',
                controller:'CouponController',
                controllerAs:'ctrl',
                resolve: {
                    coupons: function ($q, CouponService) {
                        console.log('Load coupons');
                        var deferred = $q.defer();
                        CouponService.loadCouponsOfFirstPage().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);