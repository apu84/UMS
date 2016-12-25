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
    'ui.sortable'
  ]);

  UMS.config(['BaseUriProvider', (baseUriProvider: BaseUriProvider) => {
    baseUriProvider.setServicePath('/ums-webservice-library/');
  }]);

  UMS.config(($stateProvider, $urlRouterProvider, $locationProvider) => {
    $urlRouterProvider.when('/cataloging','/cataloging/search');
    $urlRouterProvider.otherwise("/userHome");
    $stateProvider
        .state('userHome', {
          url: "/userHome",
          templateUrl: 'views/user-home.html',
          controller: 'MainController',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
              });
            }]
          }
        })
        .state('cataloging', {
          url: "/cataloging",
          templateUrl: 'views/admin/cataloging/catalog-home.html'
        })
        .state('cataloging.search', {
          url: "/search",
          templateUrl: 'views/admin/cataloging/catalog-search.html'

        })
        .state('cataloging.newBook', {
          url: "/search",

          templateUrl: 'views/admin/cataloging/catalog-new-book.html'
        })
        .state('storeBook', {
          url: "/storeBook",
          templateUrl: 'views/storage/library-book-store.html',
          controller: 'LibraryBookEntry',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
              });
            }]
          }
        })
        .state('searchLibrary', {
          url: "/searchLibrary",
          templateUrl: 'views/searchLibrary/library-search.html',
          controller: 'LibrarySearch',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
              });
            }]
          }
        })
        .state('logout', {
          url: "/logout",
          controller: 'Logout'
        })
  });
}
