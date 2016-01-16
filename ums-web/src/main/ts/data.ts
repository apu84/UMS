(function ($) {
  $.fn.Data = function () {
    return {};
  };
  var $this:any = $.fn.Data;

  $.fn.Data.pages = {
    '/index': {title: 'Dashboard', 'breadcrumb': ['Dashboard']},
    '/userHome': {title: 'User Home', 'breadcrumb': ['User Home']},
    '/createSemester': {title: 'Create New Semester', 'breadcrumb': ['Semester']},
    '/createSyllabus': {title: 'Create New Syllabus', 'breadcrumb': ['Syllabus']},
    '/showSyllabusList': {title: 'Syllabus List', 'breadcrumb': ['Syllabus List']},
    '/viewFullSyllabus': {title: 'Syllabus', 'breadcrumb': ['Syllabus']},
    '/createStudent': {title: 'Create New Student', 'breadcrumb': ['Student']},
    '/changePassword': {title: 'Change Password', 'breadcrumb': ['Password']},
    '/createUgCourse': {title: 'Create UG Course', 'breadcrumb': ['Course']},
    '/createPgCourse': {title: 'Create PG Course', 'breadcrumb': ['Course']},
    '/semesterSyllabusMap': {title: 'Semester Syllabus Map', 'breadcrumb': ['Semester-Syllabus Map']},
    '/singleUserPassword': {title: 'User Management', 'breadcrumb': ['User Management','Single']},
    '/bulkUserPassword': {title: 'User Management ', 'breadcrumb': ['User Management','Bulk']}
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