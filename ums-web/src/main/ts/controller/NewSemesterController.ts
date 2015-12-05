/// <reference path="../../webapp/iums/vendors/jquery-validate/jquery.validate.min.js" />
module ums {
    UMS.controller('NewSemesterController', function ($scope, $routeParams){
        $(".form-validate") .validate({
            errorPlacement: function(error, element){
                error.insertAfter(element);
            }
        });
    });
}

