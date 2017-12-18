(function ($) {
  $.fn.Data = function () {
    return {};
  };
  var $this:any = $.fn.Data;

  $.fn.Data.pages = {
      '/index': {title: 'Dashboard', 'breadcrumb': ['Dashboard']},
      '/userHome': {title: 'User Home', 'breadcrumb': ['User Home']},
      '/profile': {title: 'Profile', 'breadcrumb': ['Profile']},
      '/employeeInformation': {title: 'Employee Information', 'breadcrumb': ['Employee Information']},
      '/profile/:id': {title: 'Employee Information', 'breadcrumb': ['Employee Information']},
      '/employeePublication': {title: 'Publication Approval', 'breadcrumb': ['Publication Approval']},
      '/meetingSchedule': {title: 'Meeting Management', 'breadcrumb': ['Meeting Schedule & Invite Members']},
      '/agendaAndResolution': {title: 'Meeting Management', 'breadcrumb': ['Prepare Agenda & Resolution']},
      '/meetingSearch': {title: 'Meeting Management', 'breadcrumb': ['Search']}

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