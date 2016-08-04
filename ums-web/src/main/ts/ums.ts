///<reference path="constants.ts"/>

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

  UMS.constant("appConstants", Constants.Default());

  UMS.config(($stateProvider, $urlRouterProvider) => {
    //
    // For any unmatched url, redirect to /state1

    $urlRouterProvider.when('/passwordReport','/passwordReport/singleUserPassword');
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
                  'vendors/jquery-validate/jquery.validate.min.js',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                  'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                  'vendors/jquery-notify/jquery.notify.js'
                ]
              });
            }]
          }
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
                  'vendors/jquery-validate/jquery.validate.min.js',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                  'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
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
                  'vendors/jquery-validate/jquery.validate.min.js',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                  'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                ]
              });
            }]
          }
        })
        .state('publishSeatPlan', {
          url: "/publishSeatPlan",
          controller: 'PublishSeatPlan',
          templateUrl: 'views/semester/publish-seat-plan.html',
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
        .state('seatPlanRegular', {
          url: "/seatPlanRegular",
          controller: 'SeatPlanRegular',
          templateUrl: 'views/semester/exam-seat-plan-regular.html',
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
        .state('seatPlanCCI', {
          url: "/seatPlanCCI",
          controller: 'SeatPlanCCI',
          templateUrl: 'views/semester/exam-seat-plan-cci.html',
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
          controller:"GridSyllabus",
          templateUrl: "views/syllabus/list-syllabus.html",
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/bootstrap-switch/css/bootstrap-switch.css',
                  'vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/jquery-tablesorter/themes/blue/style-custom.css',
                  'vendors/jquery-validate/jquery.validate.min.js',
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
          controller: 'NewStudent',
          templateUrl: 'views/student/new-student.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/bootstrap-switch/css/bootstrap-switch.css',
                  'vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/jquery-validate/jquery.validate.min.js',
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
          templateUrl: 'views/course/new-course-ug.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/jquery-validate/jquery.validate.min.js'
                ]
              });
            }]
          }
        })
        .state('createPgCourse', {
          url: "/createPgCourse",
          controller: 'NewCoursePg',
          templateUrl: 'views/course/new-course-pg.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/jquery-validate/jquery.validate.min.js'
                ]
              });
            }]
          }
        })
        .state('semesterSettingParameter',{
                   url:"/semesterSettingParameter",
                 controller:'SemesterSettingParameter',
                 templateUrl:'views/semester/semester-setting-parameter.html',
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
                            'vendors/jquery-validate/jquery.validate.min.js',
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
                  'vendors/jquery-validate/jquery.validate.min.js',
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
              'vendors/jquery-validate/jquery.validate.min.js',
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
        })
        .state('passwordReport', {
          url: "/passwordReport",
          controller: 'PasswordReport',
          templateUrl: 'views/common/password-report.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/jquery-validate/jquery.validate.min.js'
                ]
              });
            }]
          }
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
            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
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
            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
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
          controller:'ClassRoutine',
          templateUrl: 'views/dept/class-routine.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: ['vendors/jquery-file-upload/css/jquery.fileupload.css',
                  'vendors/jquery-file-upload/css/jquery.fileupload-ui.css',
                  'vendors/jquery-file-upload/css/blueimp-gallery.min.css']
              });
            }]
          }
        })
        .state('studentsRoutine', {
          url: "/studentsRoutine",
          controller:'StudentsRoutine',
          templateUrl: 'views/routine/students-routine.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
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
          templateUrl: 'views/student/student-profile.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/jquery-validate/jquery.validate.min.js'
                ]
              });
            }]
          }
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
                  'vendors/jquery-validate/jquery.validate.min.js',
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
          controller: 'SemesterEnrollment',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/jquery-validate/jquery.validate.min.js'
                ]
              });
            }]
          }
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
              return $ocLazyLoad.load({
                files: [
                  'vendors/amcharts/amcharts.js',
                    'vendors/amcharts/serial.js'
                ]
              });
            }]
          }
        })
        .state('gradeSheetSelectionHead', {
          url: "/gradeSheetSelectionHead/:1",
          templateUrl: 'views/grade/grade-sheet-selection.html',
          controller: 'MarksSubmission',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/amcharts/amcharts.js',
                  'vendors/amcharts/serial.js'
                ]
              });
            }]
          }
        })
        .state('gradeSheetSelectionCoE', {
          url: "/gradeSheetSelectionCoE/:1",
          templateUrl: 'views/grade/grade-sheet-selection.html',
          controller: 'MarksSubmission',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/amcharts/amcharts.js',
                  'vendors/amcharts/serial.js'
                ]
              });
            }]
          }
        })
        .state('gradeSheetSelectionVC', {
            url: "/gradeSheetSelectionVC/:1",
            templateUrl: 'views/grade/grade-sheet-selection.html',
            controller: 'MarksSubmission',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/amcharts/amcharts.js',
                            'vendors/amcharts/serial.js'
                        ]
                    });
                }]
            }
        })
        .state('loggerGrid', {
          url: "/loggerGrid",
          controller: 'LoggerGrid',
          templateUrl: 'views/logger/logger-grid.html'
        })
        //In database use /dummyController/H or /dummyController/T in the location column
        //https://localhost/ums-web/iums/#/dummyConroller/T
        //https://localhost/ums-web/iums/#/dummyConroller/H
        .state('dummyController', {
          url: "/dummyConroller/:1",
          controller: 'DummyController',
          templateUrl: 'views/dummy/dummy.html'
        })

  });
}
