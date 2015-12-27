var ums;
(function (ums) {
    var Constants = (function () {
        function Constants() {
        }
        Object.defineProperty(Constants, "Default", {
            get: function () {
                return {
                    gender: {
                        'M': 'Male',
                        'F': 'Female'
                    }
                };
            },
            enumerable: true,
            configurable: true
        });
        return Constants;
    })();
    ums.UMS = angular.module('UMS', [
        'ngRoute', 'ui.bootstrap', 'ui.router', 'oc.lazyLoad'
    ]).constant("appConstant", Constants.Default);
    ums.UMS.config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/userHome");
        $stateProvider
            .state('dashBoard', {
            url: "/dashBoard",
            templateUrl: 'templates/states/main.html',
            controller: 'MainController',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/jquery-tablesorter/jquery.tablesorter.min.js',
                                'vendors/calendar/zabuto_calendar.min.js',
                                'vendors/flot-chart/jquery.flot.categories.js',
                                'vendors/flot-chart/jquery.flot.pie.js',
                                'vendors/flot-chart/jquery.flot.tooltip.js',
                                'vendors/flot-chart/jquery.flot.resize.js',
                                'vendors/flot-chart/jquery.flot.fillbetween.js',
                                'vendors/flot-chart/jquery.flot.stack.js',
                                'vendors/flot-chart/jquery.flot.spline.js']
                        });
                    }]
            }
        })
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
            .state('createSemester', {
            url: "/createSemester",
            controller: 'NewSemesterController',
            templateUrl: 'views/semester/new-semester.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/jquery-validate/jquery.validate.min.js',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                            ]
                        });
                    }]
            }
        })
            .state('createSyllabus', {
            url: "/createSyllabus",
            controller: 'NewSyllabusController',
            templateUrl: 'views/syllabus/new-syllabus.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/jquery-validate/jquery.validate.min.js',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                            ]
                        });
                    }]
            }
        })
            .state('showSyllabusList', {
            url: "/showSyllabusList",
            templateUrl: 'views/syllabus/list-syllabus.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/jquery-validate/jquery.validate.min.js',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                            ]
                        });
                    }]
            }
        })
            .state('viewFullSyllabus', {
            url: "/viewFullSyllabus",
            controller: 'FullSyllabusController',
            templateUrl: 'views/syllabus/view-full-syllabus.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/jquery-validate/jquery.validate.min.js',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                            ]
                        });
                    }]
            }
        })
            .state('createStudent', {
            url: "/createStudent",
            controller: 'NewStudentController',
            templateUrl: 'views/student/new-student.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/jquery-validate/jquery.validate.min.js',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                            ]
                        });
                    }]
            }
        })
            .state('createCourse', {
            url: "/createCourse",
            controller: 'NewCourseController',
            templateUrl: 'views/course/new-course.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/jquery-validate/jquery.validate.min.js',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                            ]
                        });
                    }]
            }
        })
            .state('semesterSyllabusMap', {
            url: "/semesterSyllabusMap",
            controller: 'SemesterSyllabusMapController',
            templateUrl: 'views/semester/semester-syllabus-map.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/jquery-validate/jquery.validate.min.js',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                            ]
                        });
                    }]
            }
        })
            .state('changePassword', {
            url: "/changePassword",
            controller: 'ChangePassword',
            templateUrl: 'views/common/change-password.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/jquery-validate/jquery.validate.min.js'
                            ]
                        });
                    }]
            }
        });
    });
})(ums || (ums = {}));
//# sourceMappingURL=ums.js.map