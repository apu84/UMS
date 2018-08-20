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
        'angularAccounting',
        'ngHandsontable'
    ]).run(function (ExpireToken) {

    });


    UMS.config(['BaseUriProvider', (baseUriProvider: BaseUriProvider) => {
        baseUriProvider.setServicePath('/ums-webservice-account/');
    }]);

    UMS.constant("accountConstants", Constants.AccountConstant());
    UMS.constant("registrarConstants", Constants.RegistrarConstant());
    UMS.constant("libConstants", Constants.LibConstant());

    UMS.config(($stateProvider, $urlRouterProvider, $locationProvider) => {
        $urlRouterProvider.otherwise("/userHome");
        $stateProvider
            .state('userHome', {
                url: "/userHome",
                templateUrl: 'views/user-home.html',
                controller: 'AccountsMainController',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
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
            .state('systemGroupMap', {
              url: "/systemGroupMap",
              controller: 'SystemGroupMapController',
              controllerAs: 'vm',
              templateUrl: 'views/definitions/system.group.map/system-group-map.html'
            })
            .state('systemAccountMap', {
              url: "/systemAccountMap",
              controller: 'SystemAccountMapController',
              controllerAs: 'vm',
              templateUrl: 'views/definitions/system.account.map/system-account-map.html'
            })
            .state('account', {
              url: "/account",
              controller: 'AccountController',
              controllerAs: 'vm',
              templateUrl: 'views/definitions/account/account.html'
            })
            .state('company', {
              url: "/company",
              controller: 'CompanyController',
              controllerAs: 'vm',
              templateUrl: 'views/definitions/company/company.html'
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
            .state('financialAccountYear.financialAccountYearClosing', {
                url: "/financialAccountYearClosing",
                controller: 'FinancialAccountYearClosingController',
                controllerAs: 'vm',
                templateUrl: 'views/definitions/financial.account.year/financial.account.year.closing.html',
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
            .state('balanceSheetReport', {
                url: "/balanceSheetReport",
                controller: 'BalanceSheetReportController',
                controllerAs: 'vm',
                templateUrl: 'views/balance-sheet/report/balance-sheet.html',
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
            .state('employeeProfile', {
                url: "/employeeProfile",
                templateUrl: 'views/ems/profile-management/employee-profile.html',
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
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.css',
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.js'
                            ]
                        });
                    }]
                }
            })
            .state('employeeProfile.personal', {
                url: "/personal",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/personal/personal-information.html',
                controller: 'PersonalInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.academic', {
                url: "/academic",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/academic/academic-information.html',
                controller: 'AcademicInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.publication', {
                url: "/publication",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/publication/publication-information.html',
                controller: 'PublicationInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.training', {
                url: "/training",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/training/training-information.html',
                controller: 'TrainingInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.award', {
                url: "/award",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/award/award-information.html',
                controller: 'AwardInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.experience', {
                url: "/experience",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/experience/experience-information.html',
                controller: 'ExperienceInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.additional', {
                url: "/additional",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/additional/additional-information.html',
                controller: 'AdditionalInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.service', {
                url: "/service",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/service/service-information.html',
                controller: 'ServiceInformation',
                controllerAs: 'vm'
            })



            // Temporary. Will remove after 1 month

            .state('employeeSearch', {
                url: "/employeeInformation",
                templateUrl: 'views/ems/employee-information-for-aao.html',
                controller: 'EmployeeSearch',
                controllerAs: 'vm',
                resolve: {
                    allUsers: ['employeeService', function (employeeService) {
                        return employeeService.getAll().then((data) => {
                            return data;
                        });
                    }]
                }
            })
            .state('employeeSearch.employeeProfile', {
                url: "/employeeProfile",
                params: {
                    'id' : null
                },
                templateUrl: 'views/ems/profile-management/employee-profile.html',
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
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.css',
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.js'
                            ]
                        });
                    }]
                }
            })
            .state('employeeSearch.employeeProfile.personal', {
                url: "/personal",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/personal/personal-information.html',
                controller: 'PersonalInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.academic', {
                url: "/academic",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/academic/academic-information.html',
                controller: 'AcademicInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.publication', {
                url: "/publication",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/publication/publication-information.html',
                controller: 'PublicationInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.training', {
                url: "/training",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/training/training-information.html',
                controller: 'TrainingInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.award', {
                url: "/award",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/award/award-information.html',
                controller: 'AwardInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.experience', {
                url: "/experience",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/experience/experience-information.html',
                controller: 'ExperienceInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.additional', {
                url: "/additional",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/additional/additional-information.html',
                controller: 'AdditionalInformation',
                controllerAs: 'vm'
            })
            .state('logout', {
                url: "/logout",
                controller: 'Logout'
            })
    });
}
