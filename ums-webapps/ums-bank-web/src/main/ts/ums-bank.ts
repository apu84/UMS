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
  ]).run(function(ExpireToken){

  });

  UMS.config(['BaseUriProvider', (baseUriProvider: BaseUriProvider) => {
    baseUriProvider.setServicePath('/ums-webservice-bank/');
  }]);

  UMS.constant("appConstants", Constants.Default());

  UMS.config(($stateProvider, $urlRouterProvider, $locationProvider) => {
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
        .state('logout', {
          url: "/logout",
          controller: 'Logout'
        })
        .state('admissionFee', {
          url: "/admissionFee",
          templateUrl: 'views/admission/admission-fee.html',
          controller: 'AdmissionFee'
        })
        .state('receive', {
          url: "/receive",
          controller: 'ReceivePaymentController',
          controllerAs: 'vm',
          templateUrl: 'views/fee/receive.payment.html'
        })
        .state('payments', {
          url: "/payments",
          controller: 'PaymentStatusController',
          controllerAs: 'vm',
          templateUrl: 'views/fee/payment.status.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/select2/select2-madmin.css',
                  'vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js'
                ]
              });
            }]
          }
        })
        .state('banks', {
          url: "/banks",
          controller: 'BankController',
          controllerAs: 'vm',
          templateUrl: 'views/admin/bank.list.html'
        })
        .state('branches', {
          url: "/branches/:1",
          controller: 'BranchController',
          controllerAs: 'vm',
          templateUrl: 'views/admin/branch.list.html'
        })
        .state('designations', {
          url: "/designations",
          controller: 'BankDesignationController',
          controllerAs: 'vm',
          templateUrl: 'views/admin/designation.list.html'
        })
  });
}
