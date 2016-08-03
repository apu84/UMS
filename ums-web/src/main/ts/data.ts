(function ($) {
  $.fn.Data = function () {
    return {};
  };
  var $this:any = $.fn.Data;

  $.fn.Data.pages = {
    '/index': {title: 'Dashboard', 'breadcrumb': ['Dashboard']},
    '/userHome': {title: 'User Home', 'breadcrumb': ['User Home']},
    '/createSemester': {title: 'Create New Semester', 'breadcrumb': ['Semester']},
    '/showSemesterList': {title: 'Semester List', 'breadcrumb': ['Semester List']},
    '/createSyllabus': {title: 'Create New Syllabus', 'breadcrumb': ['Syllabus']},
    '/showSyllabusList': {title: 'Syllabus List', 'breadcrumb': ['Syllabus List']},
    '/viewFullSyllabus': {title: 'Syllabus', 'breadcrumb': ['Syllabus']},
    '/createStudent': {title: 'Create New Student', 'breadcrumb': ['Student']},
    '/changePassword': {title: 'Change Password', 'breadcrumb': ['Password']},
    '/createUgCourse': {title: 'Create UG Course', 'breadcrumb': ['Course']},
    '/createPgCourse': {title: 'Create PG Course', 'breadcrumb': ['Course']},
    '/semesterSyllabusMap': {title: 'Semester Syllabus Map', 'breadcrumb': ['Semester-Syllabus Map']},
    '/singleUserPassword': {title: 'User Management', 'breadcrumb': ['User Management','Single']},
    '/bulkUserPassword': {title: 'User Management ', 'breadcrumb': ['User Management','Bulk']},
    '/courseTeacher': {title: 'Course Teacher Assign ', 'breadcrumb': ['Course Teacher']},
    '/classRoutine': {title: 'Class Routine Upload ', 'breadcrumb': ['Class Routine']},
    '/studentProfile': {title: 'Student Profile ', 'breadcrumb': ['Student Profile']},
    '/classRoomInfo': {title: 'Class Room Information ', 'breadcrumb': ['Class Room Information']},
    '/examRoutine': {title: 'Exam Routine ', 'breadcrumb': ['Exam Routine']},
    '/optionalCoursesApplication': {title: 'Optional Courses ', 'breadcrumb': ['Optional Course Application']},
    '/optionalCoursesOffer': {title: 'Optional Courses ', 'breadcrumb': ['Optional Course Settings']},
    '/gradeSheetSelection': {title: 'Grade Sheet Selection ', 'breadcrumb': ['Grade Sheet Selection']},
    '/applicationCCI':{title:'Appliction for Clearance/Improvement/Carry','breadcrumb':['Application for Clearance/Improvement/Carry']},
    '/examSeatPlan': {title: 'Exam Seat Plan ', 'breadcrumb': ['Seat Plan ']},
    '/seatPlanRegular': {title: 'Exam Seat Plan - Regular ', 'breadcrumb': ['Seat Plan','Regular']},
    '/seatPlanCCI': {title: 'Exam Seat Plan - CCI ', 'breadcrumb': ['Seat Plan','CCI']}
  };

  $.fn.Data.get = function (id) {
    if (id && $this.pages[id]) {
      return $this.pages[id];
    }
  };

  $.fn.Data.checkbox = function () {
    if ($('#demo-checkbox-radio').length <= 0) {
/*      $('input[type="checkbox"]:not(".switch")').iCheck({
        checkboxClass: 'icheckbox_minimal-grey',
        increaseArea: '20%' // optional
      });
      $('input[type="radio"]:not(".switch")').iCheck({
        radioClass: 'iradio_minimal-grey',
        increaseArea: '20%' // optional
      });*/
    }
  };
})(jQuery);