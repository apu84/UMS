module ums {
    UMS.controller('SemesterSyllabusMapController', function ($scope, $routeParams){
        $(".form-horizontal").validate({
            errorPlacement: function(error, element){
                error.insertAfter(element);
            }
        });

    });
}

