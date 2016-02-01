///<reference path="constants.ts"/>

module ums {
  export var UMS = angular.module('UMS', [
    'ngRoute', 'ui.bootstrap', 'ui.router', 'oc.lazyLoad', 'LocalStorageModule', 'ngCookies'
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
        .state('classRoutine', {
          url: "/classRoutine",
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
        .state('studentProfile', {
          url: "/studentProfile",
          templateUrl: 'views/student/student-profile.html'
        })

  });
}
