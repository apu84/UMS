module ums {
    UMS.controller('NewSyllabusController', function ($scope, $routeParams){
        $(".form-horizontal").validate({
            errorPlacement: function(error, element){
                error.insertAfter(element);
            }
        });
    });
}

