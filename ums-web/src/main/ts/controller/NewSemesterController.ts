module ums {
    UMS.controller('NewSemesterController', function ($scope, $routeParams){
        $(".form-validate").validate({
            errorPlacement: function(error, element){
                error.insertAfter(element);
            }
        });
    });
}

