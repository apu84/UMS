///<reference path="constants.ts"/>

module ums {
  export var UMS = angular.module('UMS', [
    'ngRoute', 'ui.bootstrap', 'ui.router', 'oc.lazyLoad', 'LocalStorageModule', 'ngCookies'
  ]);

  UMS.constant("Constants", Constants.Default);

  UMS.config(($stateProvider, $urlRouterProvider) => {
    //
    // For any unmatched url, redirect to /state1
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
                  'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
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
                  'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                ]
              });
            }]
          }
        })
        .state('createCourse', {
          url: "/createCourse",
          controller: 'NewCourse',
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
  });
}
