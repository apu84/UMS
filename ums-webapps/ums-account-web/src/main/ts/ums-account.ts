module ums {
  export var UMS = angular.module('UMS', [
    'ngRoute',
    'ngAnimate',
    'ngTouch',
    'ui.bootstrap',
    'ui.router',
    'oc.lazyLoad',
    'LocalStorageModule',
    'ngCookies',
    'ngSanitize',
    'scrollable-table',
    'amChartsDirective',
    'ui.sortable',
    "angularUtils.directives.dirPagination",
    'smart-table',
    'ui.grid',
    'ui.select',
    'angularAccounting'
  ]);


  UMS.config(['BaseUriProvider', (baseUriProvider: BaseUriProvider) => {
    baseUriProvider.setServicePath('/ums-webservice-account/');
  }]);

  UMS.constant("accountConstants", Constants.RegistrarConstant());

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
        .state('profile', {
          url: "/profile",
          templateUrl: 'views/employee/employee-profile.html',
          controller: 'EmployeeProfile',
          controllerAs: 'vm',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/select2/select2-madmin.css',
                  'vendors/bootstrap-select/bootstrap-select.min.css',
                  'vendors/multi-select/css/multi-select-madmin.css',
                  'vendors/select2/select2.min.js',
                  'vendors/bootstrap-select/bootstrap-select.min.js',
                  'vendors/multi-select/js/jquery.multi-select.js',
                  'vendors/bootstrap-switch/css/bootstrap-switch.css',
                  'vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                  'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                ]
              });
            }]
          }
        })

        .state('group', {
          url: "/group",
          controller: 'GroupController',
          controllerAs: 'vm',
          templateUrl: 'views/definitions/group/account.group.html'
        })
        .state('account', {
          url: "/account",
          controller: 'AccountController',
          controllerAs: 'vm',
          templateUrl: 'views/definitions/account/account.html'
        })
        .state('budgetAllocation', {
          url: "/budgetAllocation",
          controller: 'BudgetAllocationController',
          controllerAs: 'vm',
          templateUrl: 'views/definitions/budget-allocation/budget.allocation.html'
        })
        .state('periodClose', {
          url: "/periodClose",
          controller: 'PeriodCloseController',
          controllerAs: 'vm',
          templateUrl: 'views/definitions/period-close/period.close.html'
        })
        .state('narration', {
          url: "/narration",
          controller: 'NarrationController',
          controllerAs: 'vm',
          templateUrl: 'views/definitions/narration/narration.html'
        })
        .state('voucherNumberControl', {
          url: "/voucherNumberControl",
          controller: 'VoucherNumberController',
          controllerAs: 'vm',
          templateUrl: 'views/definitions/voucher/voucher.number.control.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/jquery-tablesorter/themes/blue/style-custom.css',
                  'vendors/jquery-tablesorter/jquery.tablesorter.js'
                ]
              });

            }]
          }
        })
        .state('financialAccountYear', {
          url: "/financialAccountYear",
          controller: 'FinancialAccountYearController',
          controllerAs: 'vm',
          templateUrl: 'views/definitions/financial.account.year/financial.account.year.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/bootstrap-switch/css/bootstrap-switch.css',
                  'vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                  'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                ]
              });
            }]
          }
        })
        .state('journalVoucher', {
          url: "/journalVoucher",
          controller: 'JournalVoucherController',
          controllerAs: 'vm',
          templateUrl: 'views/general-ledger/transactions/journal-voucher/journal-voucher.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
              });
            }]
          }
        })
        .state('generalLedgerReport', {
          url: "/generalLedgerReport",
          controller: 'GeneralLedgerReportController',
          controllerAs: 'vm',
          templateUrl: 'views/general-ledger/report/general-ledger-report.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
              });
            }]
          }
        })
        .state('contraVoucher', {
          url: "/contraVoucher",
          controller: 'ContraVoucherController',
          controllerAs: 'vm',
          templateUrl: 'views/general-ledger/transactions/contra-voucher/contra-voucher.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
              });
            }]
          }
        })
        .state('paymentVoucher', {
          url: "/paymentVoucher",
          controller: 'PaymentVoucherController',
          controllerAs: 'vm',
          templateUrl: 'views/general-ledger/transactions/payment-voucher/payment-voucher.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
              });
            }]
          }
        })
        .state('receiptVoucher', {
          url: "/receiptVoucher",
          controller: 'ReceiptVoucherController',
          controllerAs: 'vm',
          templateUrl: 'views/general-ledger/transactions/receipt-voucher/receipt-voucher.html',
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