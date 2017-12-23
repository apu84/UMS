(function ($) {
  $.fn.Data = function () {
    return {};
  };
  var $this: any = $.fn.Data;

  $.fn.Data.pages = {
    '/index': {title: 'Dashboard', 'breadcrumb': ['Dashboard']},
    '/userHome': {title: 'User Home', 'breadcrumb': ['User Home']},
    '/profile': {title: 'Profile', 'breadcrumb': ['Profile']}
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