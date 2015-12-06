module ums {
    UMS.controller('NewSemesterController', function ($scope, $routeParams){
        $(".form-horizontal").validate({
            errorPlacement: function(error, element){
                error.insertAfter(element);
            }
        });
        setTimeout(function(){
            $('.make-switch').bootstrapSwitch();
            $('#TheCheckBox').bootstrapSwitch();
        }, 50);


        $('.datepicker-default').datepicker();
    });
}

