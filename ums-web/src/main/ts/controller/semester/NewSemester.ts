module ums {
  export class NewSemester {
    constructor() {
      setTimeout(function () {
        $('.make-switch').bootstrapSwitch();
        $('#TheCheckBox').bootstrapSwitch();
      }, 50);

      $('.datepicker-default').datepicker();
    }
  }
  UMS.controller('NewSemester', NewSemester);
}

