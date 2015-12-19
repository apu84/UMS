module ums {
    UMS.controller('ChangePassword', function (appConstant,$scope, $routeParams){
       console.log(appConstant.gender);
        $(".form-horizontal").validate({
            errorPlacement: function(error, element){
                error.insertAfter(element);
            }
        });
        $('#newPassword').bind("cut copy paste",function(e) {
            e.preventDefault();
        });
        $('#confirmNewPassword').bind("cut copy paste",function(e) {
            e.preventDefault();
        });

    });
}

