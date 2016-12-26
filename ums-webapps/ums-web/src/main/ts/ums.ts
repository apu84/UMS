module ums {
  export var FILEMANAGER_CONFIG: any = {};

  UMS.config(['BaseUriProvider', (baseUriProvider: BaseUriProvider)  => {
    baseUriProvider.setServicePath('/ums-webservice-academic/');
  }]);

  angular.module('FileManagerApp').config(
      ['fileManagerConfigProvider', (config) => {
        var defaults = config.$get();
        FILEMANAGER_CONFIG = config;
        config.set(
            {
              appName: 'Course Materials',
              tplPath: 'views/file-manager',
              pickCallback: function (item) {
                var msg = 'Picked %s "%s" for external use'
                    .replace('%s', item.type)
                    .replace('%s', item.fullPath());
                window.alert(msg);
              },

              allowedActions: angular.extend(
                  defaults.allowedActions, {
                    createFolder: true,
                    pickFiles: true,
                    pickFolders: true
                  })
            });
      }]);


  angular.module('ngHandsontableApp', ['ngHandsontable']);

  UMS.constant("appConstants", Constants.Default());


  UMS.filter('$split', function() {
    return function(input) {
      return input.split(',');
    }
  });
  /* Filter used in course-teacher.html, it returns the total size of the keys in a json object */
  UMS.filter('numKeys', function() {
    return function(json) {
      var keys = Object.keys(json)
      return keys.length;
    }
  });

  UMS.filter('nth', function() {
    return function(serial) {
      switch(serial){
        case 1: return serial+"st";
        case 2: return serial+"st";
        case 3: return serial+"rd";
        case 4: return serial+"th";
        case 5: return serial+"th";
      }
      return serial;
    }
  });

  UMS.config(($stateProvider, $urlRouterProvider, $locationProvider) => {
    //
    // For any unmatched url, redirect to /state1
    //$locationProvider.html5Mode(true);
    $urlRouterProvider.when('/passwordReport','/passwordReport/singleUserPassword');
    $urlRouterProvider.when('/mailBox','/mailBox/inbox');
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
          controller:"SemesterInfo",
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
        .state('teachersRoutine', {
          url: "/teachersRoutine",
          controller: 'TeachersRoutine',
          templateUrl: 'views/dept/teachers-routine.html',
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
        .state('uploadMeritList', {
          url: "/uploadMeritList",
          templateUrl: 'views/admission/merit-list-upload.html',
          controller: 'AdmissionMeritList'
        })
        .state('uploadTaletalkData', {
          url: "/uploadTaletalkData",
          templateUrl: 'views/admission/taletalk-data-upload.html',
          controller: 'AdmissionTaletalkData'
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
        .state('courseMaterial', {
          url: "/courseMaterial",
          //url: "/courseMaterial/:1/:2",
          controller: 'CourseMaterial',
          templateUrl: 'views/course-material/course-material.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
              });
            }]
          }
        })
        .state('studentCourseMaterial', {
          url: "/studentCourseMaterial",
          controller: 'StudentCourseMaterial',
          templateUrl: 'views/course-material/student-course-material.html',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
              });
            }]
          }
        })
        .state('resultProcessing', {
          url: "/resultProcessing",
          controller: 'ResultProcessing',
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
            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
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
