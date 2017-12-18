(function ($) {
  $.fn.Data = function () {
    return {};
  };
  var $this: any = $.fn.Data;

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
    '/singleUserPassword': {title: 'User Management', 'breadcrumb': ['User Management', 'Single']},
    '/bulkUserPassword': {title: 'User Management ', 'breadcrumb': ['User Management', 'Bulk']},
    '/courseTeacher': {title: 'Course Teacher Assign ', 'breadcrumb': ['Course Teacher']},
    '/classRoutine': {title: 'Class Routine Upload ', 'breadcrumb': ['Class Routine']},
    '/studentProfile': {title: 'Student Profile ', 'breadcrumb': ['Student Profile']},
    '/classRoomInfo': {title: 'Class Room Information ', 'breadcrumb': ['Class Room Information']},
    '/examRoutine': {title: 'Exam Routine ', 'breadcrumb': ['Exam Routine']},
    '/optionalCoursesApplication': {title: 'Optional Courses ', 'breadcrumb': ['Optional Course Application']},
    '/optionalCoursesOffer': {title: 'Optional Courses ', 'breadcrumb': ['Optional Course Settings']},
    '/gradeSheetSelectionTeacher/T': {
      title: 'Grade Sheet View/Selection',
      'breadcrumb': ['Grade Sheet View/Selection'],
      'navId': '2029'
    },
    '/applicationCCI': {
      title: 'Appliction for Clearance/Improvement/Carry',
      'breadcrumb': ['Application for Clearance/Improvement/Carry']
    },
    '/examSeatPlan': {title: 'Exam Seat Plan ', 'breadcrumb': ['Seat Plan ']},
    '/seatPlanRegular': {title: 'Exam Seat Plan - Regular ', 'breadcrumb': ['Seat Plan', 'Regular']},
    '/seatPlanCCI': {title: 'Exam Seat Plan - CCI ', 'breadcrumb': ['Seat Plan', 'CCI']},
    '/publishSeatPlan': {title: 'Publish Seat Plan', 'breadcrumb': ['Seat Plan', 'Publish']},
    '/examiner': {title: 'Assign Preparer-Scrutinizer', 'breadcrumb': ['Preparer-Scrutinizer']},
    '/courseMaterial': {title: 'Course Materials', 'breadcrumb': ['Course Materials']},
    '/studentCourseMaterial': {title: 'Course Materials', 'breadcrumb': ['Course Materials']},
    '/gradeSubmissionDeadLine': {title: 'Grade Submission Deadline', 'breadcrumb': ['Grade Submission Deadline']},
    '/studentAdviser': {title: 'Students Adviser', 'breadcrumb': ['Students Adviser']},
    '/advisingStudents': {title: 'Advising Students', 'breadcrumb': ['Advising Students']},
    '/resultProcessing': {title: 'Result Processing', 'breadcrumb': ['Result Processing']},
    '/classAttendance': {title: 'Attendance Sheet', 'breadcrumb': ['Attendance Sheet'], 'navId': '1111'},
    '/roomBasedRoutine': {title: 'Room-wise Routine', 'breadcrumb': ['Routine', 'Room-wise']},
    '/marksSubmissionStat': {title: 'Marks Submission Statistics', 'breadcrumb': ['Marks Submission Stat.']},
    '/uploadMeritList': {title: 'Upload Admission Merit List', 'breadcrumb': ['Admission Merit List']},
    '/userGuide': {title: 'User Guide', 'breadcrumb': ['User Guide']},
    '/inbox': {title: 'MailBox - Inbox', 'breadcrumb': ['MailBox', 'Inbox']},
    '/composeMail': {title: 'MailBox - Compose Mail', 'breadcrumb': ['MailBox', 'Compose']},
    '/viewMail': {title: 'MailBox - View Mail', 'breadcrumb': ['MailBox', 'View']},
    '/uploadTaletalkData': {title: 'Upload Taletalk Data', 'breadcrumb': ['Admission Taletalk Student List']},
    '/certificateVerification': {
      title: 'Certificate Verification',
      'breadcrumb': ['Admission Certificate Verification']
    },
    '/admissionTotalSeat': {title: 'Assign program-wise total seat', 'breadcrumb': ['Admission Total Seat Assignment']},
    '/admissionStudentId': {title: 'Admitted Student Id', 'breadcrumb': ['Admitted Student Information']},
    '/leaveApplication': {title: 'Leave Application', 'breadcrumb': ['Leave Application ']},
    '/leaveApproval': {title: 'Leave Application Approval', 'breadcrumb': ['Leave Application Approval']},
    '/publicHolidays': {title: 'Public Holidays', 'breadcrumb': ['Public Holidays  ']},

      '/profile': {title: 'Profile', 'breadcrumb': ['Profile']}
  };


  $.fn.Data.get = function (id) {
    if (id && $this.pages[id]) {
      return $this.pages[id];
    }
  };
})(jQuery);
