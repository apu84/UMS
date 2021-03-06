module ums {
    export var FILEMANAGER_CONFIG: any = {};

    UMS.config(['BaseUriProvider', (baseUriProvider: BaseUriProvider) => {
        baseUriProvider.setServicePath('/ums-webservice-academic/');
    }]);

    UMS.config(['$ocLazyLoadProvider', ($ocLazyLoadProvider) => {
        $ocLazyLoadProvider.config({
            modules: [
                {
                    name: 'FileManagerApp',
                    files: [
                        'js/lib/angular-filemanager/app.js',
                        'js/lib/angular-filemanager/directives/directives.js',
                        'js/lib/angular-filemanager/filters/filters.js',
                        'js/lib/angular-filemanager/providers/config.js',
                        'js/lib/angular-filemanager/entities/chmod.js',
                        'js/lib/angular-filemanager/entities/item.js',
                        'js/lib/angular-filemanager/services/apihandler.js',
                        'js/lib/angular-filemanager/services/apimiddleware.js',
                        'js/lib/angular-filemanager/services/filenavigator.js',
                        'js/lib/angular-filemanager/providers/translations.js',
                        'js/lib/angular-filemanager/controllers/main.js',
                        'js/lib/angular-filemanager/controllers/selector-controller.js',
                        'js/lib/angular-translate.min.js',
                        'css/angular-filemanager.min.css',
                        'vendors/bootstrap-datepicker/css/datepicker.css',
                        'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                        'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
                        'vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js'
                    ]
                },
                {
                    name: 'amCharts',
                    files: [
                        'vendors/amcharts/amcharts.js'
                    ]
                },
                {
                    name: 'serial',
                    files: [
                        'vendors/amcharts/serial.js'
                    ]
                },
                {
                    name: 'amChartsDirective',
                    files: [
                        'js/lib/amChartsDirective.js'
                    ]
                }]
        });
    }]);

    angular.module('ngHandsontableApp', ['ngHandsontable']);
    angular.module('dirPaginationApp', ['angularUtils.directives.dirPagination']);

    UMS.constant("appConstants", Constants.Default());
    UMS.constant("registrarConstants", Constants.RegistrarConstant());
    UMS.constant("libConstants", Constants.LibConstant());

    UMS.filter('$split', function () {
        return function (input) {
            return input.split(',');
        }
    });
    /* Filter used in course-teacher.html, it returns the total size of the keys in a json object */
    UMS.filter('numKeys', function () {
        return function (json) {
            var keys = Object.keys(json);
            return keys.length;
        }
    });

    UMS.filter('nth', function () {
        return function (serial) {
            switch (serial) {
                case 1:
                    return serial + "st";
                case 2:
                    return serial + "st";
                case 3:
                    return serial + "rd";
                case 4:
                    return serial + "th";
                case 5:
                    return serial + "th";
            }
            return serial;
        }
    });

    UMS.config(($stateProvider, $urlRouterProvider, $locationProvider) => {
        //
        // For any unmatched url, redirect to /state1
        //$locationProvider.html5Mode(true);
        $urlRouterProvider.when('/passwordReport', '/passwordReport/singleUserPassword');
        $urlRouterProvider.when('/mailBox', '/mailBox/inbox');
        $urlRouterProvider.otherwise("/userHome");

        //
        // Now set up the states
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
            .state('logout', {
                url: "/logout",
                controller: 'Logout'
            })
            .state('createSemester', {
                url: "/createSemester",
                controller: 'NewSemester',
                templateUrl: 'views/semester/new-semester.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/jquery-notify/jquery.notify.js'
                            ]
                        });
                    }]
                }
            })
            .state('showSemesterList', {
                url: "/showSemesterList",
                controller: "SemesterInfo",
                templateUrl: "views/semester/list-semester.html"
            })
            .state('createSyllabus', {
                url: "/createSyllabus",
                controller: 'NewSyllabus',
                templateUrl: 'views/syllabus/new-syllabus.html',
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
            .state('gradeSubmissionDeadLine', {
                url: "/gradeSubmissionDeadLine",
                controller: 'GradeSubmissionDeadLine',
                templateUrl: 'views/grade/grade-submission-dead-line.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-daterangepicker/daterangepicker-bs3.css',
                                'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-daterangepicker/daterangepicker.js',
                                'vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
                                'vendors/jquery-maskedinput/jquery-maskedinput.js',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js'
                            ]
                        });
                    }]
                }
            })
            .state('examSeatPlan', {
                url: "/examSeatPlan",
                controller: 'ExamSeatPlan',
                templateUrl: 'views/semester/exam-seat-plan.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/bootstrap-daterangepicker/daterangepicker.js'
                            ]
                        });
                    }]
                }
            })
            .state('modifySeatPlan', {
                url: "/modifySeatPlan",
                controller: 'ModifySeatPlan',
                controllerAs: 'vm',
                templateUrl: 'views/semester/modify-seat-plan.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/bootstrap-daterangepicker/daterangepicker.js'
                            ]
                        });
                    }]
                }
            })
            .state('teachersRoutine', {
                url: "/teachersRoutine",
                controller: 'TeachersRoutine',
                templateUrl: 'views/dept/teachers-routine.html',
                controllerAs: 'vm',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/bootstrap-daterangepicker/daterangepicker.js'
                            ]
                        });
                    }]
                }
            })
            .state('roomBasedRoutine', {
                url: "/roomBasedRoutine",
                controller: 'RoomBasedRoutine',
                templateUrl: 'views/dept/room-based-routine.html'
            })
            .state('studentAdviser', {
                url: "/studentAdviser",
                controller: 'StudentAdviser',
                templateUrl: 'views/dept/student-adviser.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/select2/select2-madmin.css',
                                'vendors/bootstrap-select/bootstrap-select.min.css',
                                'vendors/multi-select/css/multi-select-madmin.css',
                                'vendors/select2/select2.min.js',
                                'vendors/bootstrap-select/bootstrap-select.min.js',
                                'vendors/multi-select/js/jquery.multi-select.js'
                            ]
                        });
                    }]
                }
            }).state('sectionAssignment', {
            url: "/sectionAssignment",
            controller: 'SectionAssignment',
            templateUrl: 'views/section-assign/section-assign.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/select2/select2-madmin.css',
                            'vendors/bootstrap-select/bootstrap-select.min.css',
                            'vendors/multi-select/css/multi-select-madmin.css',
                            'vendors/select2/select2.min.js',
                            'vendors/bootstrap-select/bootstrap-select.min.js',
                            'vendors/multi-select/js/jquery.multi-select.js'
                        ]
                    });
                }]
            }
        }).state('publishSeatPlan', {
            url: "/publishSeatPlan",
            controller: 'PublishSeatPlan',
            templateUrl: 'views/semester/publish-seat-plan.html',
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
            .state('viewSeatPlan', {
                url: "/viewSeatPlan",
                controller: 'ViewSeatPlan',
                templateUrl: 'views/semester/view-seat-plan.html',
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
            .state('seatPlanReports', {
                url: "/seatPlanReports",
                controller: 'SeatPlanReports',
                templateUrl: 'views/semester/seat-plan-reports.html',
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
            .state('applicationCCI', {
                url: "/applicationCCI",
                controller: 'ApplicationCCI',
                templateUrl: 'views/semester/application-clearance-carry-improvement.html',
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
            .state('applicationTES', {
                url: "/applicationTES",
                controller: 'studentTES',
                controllerAs: 'vm',
                templateUrl: 'views/teachers-Evaluation-System/student-tes.html',
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
            .state('showSyllabusList', {
                url: "/showSyllabusList",
                controller: "GridSyllabus",
                templateUrl: "views/syllabus/list-syllabus.html",
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/jquery-tablesorter/themes/blue/style-custom.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/jquery-tablesorter/jquery.tablesorter.js'
                            ]
                        });
                    }]
                }
            })
            .state('viewFullSyllabus', {
                url: "/viewFullSyllabus/:syllabusId",
                controller: 'FullSyllabus',
                templateUrl: 'views/syllabus/view-full-syllabus.html',
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
            .state('createStudent', {
                url: "/createStudent",
                controller: 'NewStudent',
                templateUrl: 'views/student/new-student.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/jquery-maskedinput/jquery-maskedinput.js'
                            ]
                        });
                    }]
                }
            })
            .state('createUgCourse', {
                url: "/createUgCourse",
                controller: 'NewCourseUg',
                templateUrl: 'views/course/new-course-ug.html'
            })
            .state('createPgCourse', {
                url: "/createPgCourse",
                controller: 'NewCoursePg',
                templateUrl: 'views/course/new-course-pg.html'
            })

            .state('semesterConfig', {
                url: "/semesterConfig",
                controller: 'SemesterConfig',
                templateUrl: 'views/semester/semester-config.html'
            })

            .state('semesterSettingParameter', {
                url: "/semesterSettingParameter",
                controller: 'SemesterSettingParameter',
                templateUrl: 'views/semester/semester-setting-parameter.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-colorpicker/css/colorpicker.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-daterangepicker/daterangepicker-bs3.css',
                                'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
                                'vendors/bootstrap-timepicker/css/bootstrap-timepicker.min.css',
                                'vendors/bootstrap-clockface/css/clockface.css',
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-daterangepicker/daterangepicker.js',
                                'vendors/moment/moment.js',
                                'vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
                                'vendors/bootstrap-timepicker/js/bootstrap-timepicker.js',
                                'vendors/bootstrap-clockface/js/clockface.js',
                                'vendors/bootstrap-colorpicker/js/bootstrap-colorpicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/jquery-maskedinput/jquery-maskedinput.js',
                                'vendors/charCount.js',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/select2/select2-madmin.css',
                                'vendors/bootstrap-select/bootstrap-select.min.css',
                                'vendors/multi-select/css/multi-select-madmin.css',
                                'vendors/select2/select2.min.js',
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                            ]
                        });
                    }]
                }
            })
            .state('semesterWithdrawAppStd', {
                url: "/semesterWithdrawAppStd",
                controller: 'SemesterWithdrawAppStd',
                templateUrl: 'views/semester/semester-withdraw-app-std.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/ckeditor/ckeditor.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                            ]
                        });
                    }]
                }
            })

            .state('semesterWithdrawApp', {
                url: "/semesterWithdrawApp",
                controller: 'SemesterWithdrawAppEmp',
                templateUrl: 'views/semester/semester-withdraw-app-emp.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/ckeditor/ckeditor.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                            ]
                        });
                    }]
                }
            })
            .state('semesterSyllabusMap', {
                url: "/semesterSyllabusMap",
                controller: 'SemesterSyllabusMap',
                templateUrl: 'views/semester/semester-syllabus-map.html',
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
            .state('changePassword', {
                url: "/changePassword",
                controller: 'ChangePassword',
                templateUrl: 'views/common/change-password.html'
            })
            .state('passwordReport', {
                url: "/passwordReport",
                controller: 'PasswordReport',
                templateUrl: 'views/common/password-report.html'
            })
            .state('passwordReport.singleUserPassword', {
                controller: 'PasswordReport',
                url: "/singleUserPassword",
                templateUrl: "views/user-management/single-user-password.html"
            })
            .state('passwordReport.bulkUserPassword', {
                controller: 'PasswordReport',
                url: "/bulkUserPassword",
                templateUrl: "views/user-management/bulk-user-password.html"
            })
            .state('courseTeacher', {
                url: "/courseTeacher",
                controller: 'CourseTeacher',
                templateUrl: 'views/dept/course-teacher.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: ['vendors/select2/select2-madmin.css',
                                'vendors/bootstrap-select/bootstrap-select.min.css',
                                'vendors/multi-select/css/multi-select-madmin.css',
                                'vendors/select2/select2.min.js',
                                'vendors/bootstrap-select/bootstrap-select.min.js',
                                'vendors/multi-select/js/jquery.multi-select.js']
                        });
                    }]
                }
            })
            .state('examiner', {
                url: "/examiner",
                controller: 'Examiner',
                templateUrl: 'views/dept/examiner.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: ['vendors/select2/select2-madmin.css',
                                'vendors/bootstrap-select/bootstrap-select.min.css',
                                'vendors/multi-select/css/multi-select-madmin.css',
                                'vendors/select2/select2.min.js',
                                'vendors/bootstrap-select/bootstrap-select.min.js',
                                'vendors/multi-select/js/jquery.multi-select.js']
                        });
                    }]
                }
            })
            .state('classRoutine', {
                url: "/classRoutine",
                controller: 'ClassRoutine',
                templateUrl: 'views/routine/class-routine.html',
                controllerAs: 'vm'
            })
            .state('classRoutine.classRoutineChart', {
                url: "/classRoutineChart",
                controller: 'ClassRoutineChartController',
                templateUrl: 'views/routine/class-routine-chart.html',
                controllerAs: 'vm'
            })
            .state('classRoutine.classRoutineChart.classRoutineSlotEditForm', {
                url: "/classRoutineSlotEditForm",
                controller: 'ClassRoutineSlotEditController',
                templateUrl: 'views/routine/routine-slot-edit-form.html',
                controllerAs: 'vm',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-daterangepicker/daterangepicker.js',
                                'vendors/moment/moment.js',
                                'vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
                                'vendors/bootstrap-timepicker/js/bootstrap-timepicker.js'
                            ]
                        });
                    }]
                }
            })
            .state('classRoutineConfig', {
                url: "/classRoutineConfig",
                controller: 'RoutineConfigController',
                templateUrl: 'views/routine/class-routine-config.html',
                controllerAs: 'vm'
            })
            .state('uploadMeritList', {
                url: "/uploadMeritList",
                templateUrl: 'views/admission/merit-list-upload.html',
                controller: 'AdmissionMeritList'
            })
            .state('admissionTotalSeat', {
                url: "/admissionTotalSeat",
                templateUrl: 'views/admission/admission-total-seat-assignment.html',
                controller: 'AdmissionTotalSeatAssignment'
            })
            .state('admissionDepartmentSelection', {
                url: "/admissionDepartmentSelection",
                templateUrl: 'views/admission/admission-department-selection.html',
                controller: 'AdmissionDepartmentSelection',
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
            .state('uploadTaletalkData', {
                url: "/uploadTaletalkData",
                templateUrl: 'views/admission/taletalk-data-upload.html',
                controller: 'AdmissionTaletalkData'
            })
            .state('departmentSelectionDeadline', {
                url: "/departmentSelectionDeadline",
                templateUrl: 'views/admission/department-selection-deadline.html',
                controller: 'DepartmentSelectionDeadlineAssignment'
            })
            .state('admissionStatistics', {
                url: "/admissionStatistics",
                templateUrl: 'views/admission/admission-statistics.html',
                controller: 'AdmissionStatistics'
            })
            .state('admissionStudentId', {
                url: "/admissionStudentId",
                templateUrl: 'views/admission/admission-student-id.html',
                controller: 'AdmissionStudentId'
            })
            .state('admissionMigrationList', {
                url: "/admissionMigrationList",
                templateUrl: 'views/admission/admission-migration-list.html',
                controller: 'AdmissionMigrationList',
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
            .state('certificateVerification', {
                url: "/certificateVerification",
                templateUrl: 'views/admission/certificate-verification.html',
                controller: 'AdmissionCertificateVerification',
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
            .state('studentsRoutine', {
                url: "/studentsRoutine",
                controller: 'StudentsRoutine',
                templateUrl: 'views/routine/students-routine.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: ['vendors/jquery-file-upload/css/jquery.fileupload.css',
                                'vendors/jquery-file-upload/css/jquery.fileupload-ui.css',
                                'vendors/jquery-file-upload/css/blueimp-gallery.min.css']
                        });
                    }]
                }
            })
            .state('studentProfile', {
                url: "/studentProfile",
                controller: "StudentProfile",
                templateUrl: 'views/student/student-profile.html'
            })
            .state('assignRole', {
                url: "/assignRole",
                controller: 'AssignRole',
                templateUrl: 'views/common/assign-role.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/jquery-notify/jquery.notify.js',
                                'vendors/select2/select2-madmin.css',
                                'vendors/bootstrap-select/bootstrap-select.min.css',
                                'vendors/multi-select/css/multi-select-madmin.css'
                            ]
                        });
                    }]
                }
            })
            .state('classRoomInfo', {
                url: "/classRoomInfo",
                controller: 'ClassRoomInfo',
                templateUrl: 'views/common/academic/class-room.html'
            })
            .state('examRoutine', {
                url: "/examRoutine",
                controller: 'ExamRoutine',
                templateUrl: 'views/common/academic/exam-routine.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-datetimepicker-malot/css/bootstrap-datetimepicker.min.css',
                                'vendors/bootstrap-datetimepicker-malot/js/bootstrap-datetimepicker.min.js',
                                'vendors/select2/select2-madmin.css',
                                'vendors/bootstrap-select/bootstrap-select.min.css',
                                'vendors/multi-select/css/multi-select-madmin.css',
                                'vendors/select2/select2.min.js',
                                'vendors/bootstrap-select/bootstrap-select.min.js'
                            ]
                        });
                    }]
                }
            })
            .state('optionalCoursesOffer', {
                url: "/optionalCoursesOffer",
                templateUrl: 'views/dept/optional-course-setting.html',
                controller: 'OptionalCoursesOffer'
            })
            .state('optionalCoursesApplication', {
                url: "/optionalCoursesApplication",
                templateUrl: 'views/student/optional-course.html',
                controller: 'OptionalCoursesApplication'
            })
            .state('semesterEnrollment', {
                url: "/semesterEnrollment",
                templateUrl: 'views/semester/semester-enrollment.html',
                controller: 'SemesterEnrollment'
            })
            .state('flushCache', {
                url: "/flushCache",
                controller: 'FlushCache'
            })
            .state('gradeSheetSelectionTeacher', {
                url: "/gradeSheetSelectionTeacher/:1",
                templateUrl: 'views/grade/grade-sheet-selection.html',
                controller: 'MarksSubmission',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load(['amCharts', 'serial', 'amChartsDirective'], {serie: true});
                    }]
                }
            })
            .state('gradeSheetSelectionHead', {
                url: "/gradeSheetSelectionHead/:1",
                templateUrl: 'views/grade/grade-sheet-selection.html',
                controller: 'MarksSubmission',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load(['amCharts', 'serial', 'amChartsDirective'], {serie: true});
                    }]
                }
            })

            .state('gradeSheetSelectionCoE', {
                url: "/gradeSheetSelectionCoE/:1",
                templateUrl: 'views/grade/grade-sheet-selection.html',
                controller: 'MarksSubmission',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load(['amCharts', 'serial', 'amChartsDirective',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js'], {serie: true});
                    }]
                }
            })
            .state('gradeSheetSelectionVC', {
                url: "/gradeSheetSelectionVC/:1",
                templateUrl: 'views/grade/grade-sheet-selection.html',
                controller: 'MarksSubmission',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load(['amCharts', 'serial', 'amChartsDirective'], {serie: true});
                    }]
                }
            })
            .state('loggerGrid', {
                url: "/loggerGrid",
                controller: 'LoggerGrid',
                templateUrl: 'views/logger/logger-grid.html'
            })
            .state('courseMaterial', {
                url: "/courseMaterial",
                //url: "/courseMaterial/:1/:2",
                controller: 'CourseMaterial',
                controllerAs: 'vm',
                templateUrl: 'views/course-material/course-material.html',
                resolve: {
                    loadMyCtrl: ['$$animateJs', '$ocLazyLoad', function ($$animateJs, $ocLazyLoad) {
                        return $ocLazyLoad.load(['FileManagerApp'])
                    }]

                }
            })
            .state('studentCourseMaterial', {
                url: "/studentCourseMaterial",
                controller: 'StudentCourseMaterial',
                templateUrl: 'views/course-material/student-course-material.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load(['FileManagerApp'])
                    }]
                }
            })
            .state('resultProcessing', {
                url: "/resultProcessing",
                controller: 'ResultProcessing',
                controllerAs: 'vm',
                templateUrl: 'views/result/result-processing.html'
            })
            .state('advisingStudents', {
                url: "/advisingStudents",
                controller: 'AdvisingStudents',
                templateUrl: 'views/dept/advising-students.html'
            })
            .state('classAttendance', {
                url: "/classAttendance",
                controller: 'ClassAttendance',
                templateUrl: 'views/dept/class-attendance.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
                        });
                    }]
                }
            })
            .state('marksSubmissionStat', {
                url: "/marksSubmissionStat",
                controller: 'MarksSubmissionStat',
                templateUrl: 'views/grade/marks-submission-stat.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: ['vendors/jquery-tablesorter/themes/blue/style-custom.css',
                                'vendors/jquery-tablesorter/jquery.tablesorter.js']
                        });
                    }]
                }
            })
            .state('publicHolidays', {
                url: "/publicHolidays",
                controller: 'PublicHolidays',
                controllerAs: 'vm',
                templateUrl: 'views/holidays/public-holidays.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/bootstrap-daterangepicker/daterangepicker.js'
                            ]
                        });
                    }]
                }
            })
            .state('empLeaveApplication', {
                url: "/empLeaveApplication",
                controller: 'employeeLeaveApplicationController',
                controllerAs: 'vm',
                templateUrl: 'views/leave-management/employee-leave-application.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {

                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css',
                                'vendors/bootstrap-datetimepicker/src/js/bootstrap-datetimepicker.js',
                                'vendors/bootstrap-timepicker/css/bootstrap-timepicker.css',
                                'vendors/bootstrap-timepicker/js/bootstrap-timepicker.js'
                            ]
                        });
                    }]
                }
            })
            .state('employeeExamAttendance', {
                url: "/employeeExamAttendance",
                controller: 'EmployeeExamAttendance',
                controllerAs: 'vm',
                templateUrl: 'views/employee-exam-attendance/employee-exam-attendance.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css',
                                'vendors/bootstrap-datetimepicker/src/js/bootstrap-datetimepicker.js',
                                'vendors/bootstrap-timepicker/css/bootstrap-timepicker.css',
                                'vendors/bootstrap-timepicker/js/bootstrap-timepicker.js'
                            ]
                        });
                    }]
                }
            }).state('optCourseOffer', {
            url: "/optCourseOffer",
            controller: 'OptCourseOffer',
            controllerAs: 'vm',
            templateUrl: 'views/optCourse/optCourseOffer/opt-course-offer.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css',
                            'vendors/bootstrap-datetimepicker/src/js/bootstrap-datetimepicker.js',
                            'vendors/bootstrap-timepicker/css/bootstrap-timepicker.css',
                            'vendors/bootstrap-timepicker/js/bootstrap-timepicker.js'
                        ]
                    });
                }]
            }
        }).state('optStudentCourseSelection', {
            url: "/optStudentCourseSelection",
            controller: 'OptStudentCourseSelection',
            controllerAs: 'vm',
            templateUrl: 'views/optCourse/optStudentCourseSelection/opt-student-course-selection.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css',
                            'vendors/bootstrap-datetimepicker/src/js/bootstrap-datetimepicker.js',
                            'vendors/bootstrap-timepicker/css/bootstrap-timepicker.css',
                            'vendors/bootstrap-timepicker/js/bootstrap-timepicker.js'
                        ]
                    });
                }]
            }
        }).state('leaveApplication', {
            url: "/leaveApplication",
            controller: 'LeaveApplicationController',
            controllerAs: 'vm',
            templateUrl: 'views/leave-management/leave-application.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {

                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css',
                            'vendors/bootstrap-datetimepicker/src/js/bootstrap-datetimepicker.js',
                            'vendors/bootstrap-timepicker/css/bootstrap-timepicker.css',
                            'vendors/bootstrap-timepicker/js/bootstrap-timepicker.js'
                        ]
                    });
                }]
            }
        }).state('dailyExamReport.expelledInformation', {
            url: "/expelledInformation",
            controller: 'ExpelledInformation',
            controllerAs: 'vm',
            templateUrl: 'views/expelled-information/add-expelled-information.html',
            params: {
                semesterId: null,
                examType: null,
                examDate: null
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {

                    return $ocLazyLoad.load({
                        files: [

                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('dailyExamReport.dailyExamAttendanceReport', {
            url: "/dailyExamAttendanceReport",
            controller: 'DailyExamAttendanceReport',
            controllerAs: 'vm',
            templateUrl: 'views/daily-examination-report/student-exam-attendant-info.html',
            params: {
                semesterId: null,
                examType: null,
                examDate: null
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {

                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('dailyExamReport', {
            url: "/dailyExamReport",
            controller: 'DailyExamReport',
            controllerAs: 'vm',
            templateUrl: 'views/daily-examination-report/daily-examination-report.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {

                    return $ocLazyLoad.load({
                        files: [

                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('dailyExamReport.questionCorrectionInfo', {
            url: "/questionCorrectionInfo",
            controller: 'QuestionCorrectionInfo',
            controllerAs: 'vm',
            templateUrl: 'views/question-correction-info/question-correction-info.html',
            params: {
                semesterId: null,
                examType: null,
                examDate: null
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {

                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('dailyExamReport.absentLateComingInfo', {
            url: "/absentLateComingInfo",
            controller: 'AbsentLateComingInfo',
            controllerAs: 'vm',
            templateUrl: 'views/absent-late-coming-info/absent-late-coming-info.html',
            params: {
                semesterId: null,
                examType: null,
                examDate: null
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
                            'vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        })
        /*.state('leaveApplication.leaveApplicationManagement', {
           url: "/leaveApplicationManagement",
           controller: 'LeaveApplicationManagement',
           controllerAs: 'vm',
           templateUrl: 'views/leave-management/leave-application-management.html',
           resolve: {
             loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {

               return $ocLazyLoad.load({
                 files: [

                   'vendors/bootstrap-switch/css/bootstrap-switch.css',
                   'vendors/bootstrap-datepicker/css/datepicker.css',
                   'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                   'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                   'vendors/bootstrap-daterangepicker/daterangepicker.js'
                 ]
               });
             }]
           }
         })*/
            .state('leaveApproval', {
                url: "/leaveApproval",
                controller: 'LeaveApplicationApproval',
                controllerAs: 'vm',
                templateUrl: 'views/leave-management/leave-application-approval.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css',
                                'vendors/bootstrap-datetimepicker/src/js/bootstrap-datetimepicker.js',
                                'vendors/bootstrap-timepicker/css/bootstrap-timepicker.css',
                                'vendors/bootstrap-timepicker/js/bootstrap-timepicker.js'
                            ]
                        });
                    }]
                }
            })
            .state('cciApproval', {
                url: "/cciApproval",
                controller: 'CarryApplicationApproval',
                controllerAs: 'vm',
                templateUrl: 'views/cciManagement/cci-application-approval.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/bootstrap-daterangepicker/daterangepicker.js'
                            ]
                        });
                    }]
                }
            }).state('teacherEvaluationSystem', {
            url: "/teacherEvaluationSystem",
            controller: 'AdminVcTes',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/admin-vc-tes.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('teacherEvaluationSystemEng', {
            url: "/teacherEvaluationSystemEng",
            controller: 'DeanEngineeringTes',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/admin-vc-tes.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('teacherEvaluationSystemBbaAndArs', {
            url: "/teacherEvaluationSystemBbaAndArs",
            controller: 'DeanBusinessAndSocialTes',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/admin-vc-tes.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('teacherEvaluationSystemArc', {
            url: "/teacherEvaluationSystemArc",
            controller: 'DeanArchitectureTes',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/admin-vc-tes.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('tesComparison', {
            url: "/tesComparison",
            controller: 'AdminVcTesComparison',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/search-box-evaluation-compare.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('tesComparisonEng', {
            url: "/tesComparisonEng",
            controller: 'DeanEngineeringComparison',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/search-box-evaluation-compare.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('tesComparisonBbaAndArs', {
            url: "/tesComparisonBbaAndArs",
            controller: 'DeanBusinessAndSocialComparison',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/search-box-evaluation-compare.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('questionWiseReport', {
            url: "/questionWiseReport",
            controller: 'IndividualQuestionTesReport',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/individual-question-tes-report.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('questionWiseReportEng', {
            url: "/questionWiseReportEng",
            controller: 'IndividualQuestionTesReport',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/individual-question-tes-report.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('questionWiseReportBbaAndArs', {
            url: "/questionWiseReportBbaAndArs",
            controller: 'DeanBusinessAndSocialIndividualQuestion',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/individual-question-tes-report.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('questionWiseReportArc', {
            url: "/questionWiseReportArc",
            controller: 'DeanArchitectureIndividualQuestion',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/individual-question-tes-report.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('assignQuestions', {
            url: "/assignQuestions",
            controller: 'QuestionsAdmin',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/questions.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js'
                        ]
                    });
                }]
            }
        }).state('tes', {
            url: "/tes",
            controller: 'HeadTES',
            controllerAs: 'vm',
            templateUrl: 'views/teachers-Evaluation-System/head-tes.html',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                            'vendors/bootstrap-daterangepicker/daterangepicker.js',
                            'vendors/select2/select2-madmin.css',
                            'vendors/bootstrap-select/bootstrap-select.min.css',
                            'vendors/multi-select/css/multi-select-madmin.css',
                            'vendors/select2/select2.min.js',
                            'vendors/bootstrap-select/bootstrap-select.min.js',
                            'vendors/multi-select/js/jquery.multi-select.js'
                        ]
                    });
                }]
            }
        })
            .state('userGuide', {
                url: "/userGuide",
                controller: 'UserGuide',
                templateUrl: 'views/common/user-guide.html'
            })
            .state('mailBox', {
                url: "/mailBox",
                templateUrl: 'views/common/mailbox/mailbox.html'
            })
            .state('mailBox.inbox', {
                url: "/inbox",
                controller: 'MailInbox',
                templateUrl: 'views/common/mailbox/mail-inbox.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: ['vendors/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css',
                                'vendors/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js']
                        });
                    }]
                }
            })
            .state('mailBox.composeMail', {
                url: "/composeMail",
                controller: 'MailCompose',
                templateUrl: 'views/common/mailbox/mail-compose.html'
            })
            .state('mailBox.viewMail', {
                url: "/viewMail",
                templateUrl: 'views/common/mailbox/mail-view.html'
            })
            .state('readmission', {
                url: "/readmission",
                controller: 'ReadmissionController',
                controllerAs: 'vm',
                templateUrl: 'views/readmission/readmission.html'
            })
            .state('semesterFee', {
                url: "/semesterFee",
                controller: 'SemesterFeeController',
                controllerAs: 'vm',
                templateUrl: 'views/fee/semesterfee/semester.fee.html'
            })
            .state('certificateFee', {
                url: "/certificateFee",
                controller: 'CertificateFeeController',
                controllerAs: 'vm',
                templateUrl: 'views/fee/certificate/certificate.fee.html'
            })
            .state('certificateStatus', {
                url: "/certificateStatus",
                controller: 'CertificateStatusController',
                controllerAs: 'vm',
                templateUrl: 'views/fee/certificate/certificate.status.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: ['vendors/select2/select2-madmin.css']
                        });
                    }]
                }
            })
            .state('certificateApproval', {
                url: "/certificateApproval",
                controller: 'CertificateApprovalController',
                controllerAs: 'vm',
                templateUrl: 'views/fee/certificate/certificate.approval.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {

                        return $ocLazyLoad.load({
                            files: [

                                'vendors/bootstrap-switch/css/bootstrap-switch.css',
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/bootstrap-daterangepicker/daterangepicker.js',
                                'vendors/select2/select2-madmin.css'
                            ]
                        });
                    }]
                }
            })
            .state('studentDues', {
                url: "/studentDues",
                controller: 'StudentDuesController',
                controllerAs: 'vm',
                templateUrl: 'views/fee/dues/student.dues.html'
            })
            .state('listDues', {
                url: "/listDues",
                controller: 'ListDues',
                controllerAs: 'vm',
                templateUrl: 'views/fee/dues/list.dues.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: ['vendors/select2/select2-madmin.css']
                        });
                    }]
                }
            })
            .state('installmentSetting', {
                url: "/installmentSetting",
                controller: 'InstallmentSettingController',
                controllerAs: 'vm',
                templateUrl: 'views/fee/installment-setting/installment.setting.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js'
                            ]
                        });
                    }]
                }
            })
            .state('paymentHistory', {
                url: "/payment-history",
                controller: 'StudentPaymentHistory',
                controllerAs: 'vm',
                templateUrl: 'views/fee/history/student.payment.history.html'
            })
            .state('payment-status', {
                url: "/payment-status",
                controller: 'PaymentStatusList',
                controllerAs: 'vm',
                templateUrl: 'views/fee/payment-status/payment.status.html',
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
            // .state('studentGradeSheet', {
            //   url: "/studentGradeSheet",
            //   controller: "StudentGradeSheet",
            //   templateUrl: 'views/student/grade-sheet.html'
            // })
            //In database use /dummyController/H or /dummyController/T in the location column
            //https://localhost/ums-web/iums/#/dummyConroller/T
            //https://localhost/ums-web/iums/#/dummyConroller/H
            .state('dummyController', {
                url: "/dummyConroller/:1",
                controller: 'DummyController',
                templateUrl: 'views/dummy/dummy.html'
            })

            .state('twoFATest', {
                url: "/twoFATest",
                controller: 'TwoFATestController',
                controllerAs: 'vm',
                templateUrl: 'views/twofatest/two.fa.test.html'
            })


            .state('employeeProfile', {
                url: "/employeeProfile",
                templateUrl: 'views/ems/profile-management/non-admin/employee-profile.html',
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
                templateUrl: 'views/ems/profile-management/non-admin/personal/personal-information.html',
                controller: 'PersonalInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.academic', {
                url: "/academic",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/academic/academic-information.html',
                controller: 'AcademicInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.publication', {
                url: "/publication",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/publication/publication-information.html',
                controller: 'PublicationInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.training', {
                url: "/training",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/training/training-information.html',
                controller: 'TrainingInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.award', {
                url: "/award",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/award/award-information.html',
                controller: 'AwardInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.experience', {
                url: "/experience",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/experience/experience-information.html',
                controller: 'ExperienceInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.additional', {
                url: "/additional",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/additional/additional-information.html',
                controller: 'AdditionalInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.service', {
                url: "/service",
                params: {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/service/service-information.html',
                controller: 'ServiceInformation',
                controllerAs: 'vm'
            })
            .state('search', {
                url: "/search",
                controller: 'LibrarySearch',
                controllerAs: 'vm',
                templateUrl: 'views/library/library-search.html',
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
            .state('gradesheet', {
                url: "/gradesheet",
                controller: 'StudentGradesheetController',
                controllerAs: 'vm',
                templateUrl: 'views/student/grade-sheet.html'
            })


        // Temporary. Will remove after 1 month

            .state('employeeSearch', {
                url: "/employeeInformation",
                templateUrl: 'views/ems/employee-information-for-aao.html',
                controller: 'EmployeeSearch',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile', {
                url: "/employeeProfile",
                params: {
                    'id' : null
                },
                templateUrl: 'views/ems/profile-management/admin/employee-profile.html',
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
                templateUrl: 'views/ems/profile-management/admin/personal/personal-information.html',
                controller: 'PersonalInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.academic', {
                url: "/academic",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/academic/academic-information.html',
                controller: 'AcademicInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.publication', {
                url: "/publication",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/publication/publication-information.html',
                controller: 'PublicationInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.training', {
                url: "/training",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/training/training-information.html',
                controller: 'TrainingInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.award', {
                url: "/award",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/award/award-information.html',
                controller: 'AwardInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.experience', {
                url: "/experience",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/experience/experience-information.html',
                controller: 'ExperienceInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.additional', {
                url: "/additional",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/additional/additional-information.html',
                controller: 'AdditionalInformation',
                controllerAs: 'vm'
            })

        ////////////////////////////////////////////////////////

    });
}
