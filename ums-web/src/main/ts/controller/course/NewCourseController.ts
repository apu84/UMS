module ums {
    UMS.controller('NewCourseController', function ($scope, $routeParams){
        $(".form-horizontal").validate({
            errorPlacement: function(error, element){
                error.insertAfter(element);
            }
        });
    });
}

