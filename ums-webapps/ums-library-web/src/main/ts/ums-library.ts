module ums {
    export var UMS = angular.module('UMS', [
        'ngRoute',
        'ui.bootstrap',
        'ui.router',
        'oc.lazyLoad',
        'LocalStorageModule',
        'ngCookies',
        'ngSanitize',
        'scrollable-table',
        'amChartsDirective',
        'ui.sortable',
        'ngHandsontable',
        "angularUtils.directives.dirPagination"
    ]);
//https://localhost//ums-webservice-academic/academic/course/all/ipp/5/page/1

    UMS.config(['BaseUriProvider', (baseUriProvider: BaseUriProvider) => {
        baseUriProvider.setServicePath('/ums-webservice-library/');
    }]);

    UMS.constant("libConstants", Constants.LibConstant());

    UMS.config(($stateProvider, $urlRouterProvider, $locationProvider) => {
        $urlRouterProvider.when('/cataloging', '/cataloging/search/new');
        //$urlRouterProvider.otherwise("/userHome");
        $stateProvider
            .state('userHome', {
                url: "/userHome",
                templateUrl: 'views/user-home.html',
                controller: 'MainController',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-select/bootstrap-select.min.js',
                                'vendors/bootstrap-select/bootstrap-select.css'
                            ]
                        });
                    }]
                }
            })
            .state('cataloging.search', {
                url: "/search/:1",
                controller: 'RecordSearch',
                templateUrl: 'views/admin/cataloging/catalog-search.html',

                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-select/bootstrap-select.min.js',
                                'vendors/bootstrap-select/bootstrap-select.css'
                            ]
                        });
                    }]
                }
            })
            .state('cataloging.record', {
                url: "/:1/record/:2",
                controller: 'Cataloging',
                templateUrl: 'views/admin/cataloging/catalog.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'https://sliptree.github.io/bootstrap-tokenfield/dist/css/bootstrap-tokenfield.css',
                                'https://sliptree.github.io/bootstrap-tokenfield/dist/bootstrap-tokenfield.js',
                                // 'https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.css',
                                // 'https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.full.js',


                                'vendors/select2/select2-madmin.css',
                                'vendors/select2/select2.min.js',


                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js'

                            ]
                        });
                    }]
                }
            })
            .state('cataloging', {
                url: "/cataloging",
                templateUrl: 'views/admin/cataloging/catalog-home.html'
            })
            .state('cataloging.thesis', {
                url: "/thesis",
                templateUrl: 'views/admin/cataloging/catalog-thesis.html'
            })
            .state('cataloging.journal', {
                url: "/journal",
                templateUrl: 'views/admin/cataloging/catalog-journal.html'
            })
            .state('cataloging.map', {
                url: "/map",
                templateUrl: 'views/admin/cataloging/catalog-map.html'
            })
            .state('cataloging.cdDvd', {
                url: "/cdDvd",
                templateUrl: 'views/admin/cataloging/catalog-cdDvd.html'
            })
            .state('cataloging.magazine', {
                url: "/magazine",
                templateUrl: 'views/admin/cataloging/catalog-magazine.html'
            })
            .state('circulation', {
                url: "/circulation",
                templateUrl: 'views/admin/circulation/circulation-home.html'
            })
            .state('patrons', {
                url: "/patrons",
                templateUrl: 'views/admin/patron/patron-home.html',
                controller: 'PatronHome'
            })
            .state('circulation.checkOut', {
                url: "/checkOut",
                templateUrl: 'views/admin/circulation/circulation-checkout.html'
            })
            .state('circulation.checkIn', {
                url: "/checkIn",
                templateUrl: 'views/admin/circulation/circulation-checkin.html'
            })
            .state('circulation.checkOut.patronCheckOut', {
                url: "/patronCheckOut",
                templateUrl: 'views/admin/patron/patron-checkout.html'
            })
            .state('circulation.checkOut.patronDetail', {
                url: "/patronDetail",
                templateUrl: 'views/admin/patron/patron-detail.html'
            })
            .state('logout', {
                url: "/logout",
                controller: 'Logout'
            })
    });
}
