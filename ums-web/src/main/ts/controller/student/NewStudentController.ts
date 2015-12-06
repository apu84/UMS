module ums {
    UMS.controller('NewStudentController', function ($scope, $routeParams){
        $(".form-horizontal").validate({
            errorPlacement: function(error, element){
                error.insertAfter(element);
            }
        });

        $('.datepicker-default').datepicker();

        $("#fileUpload").on('change', function () {

            if (typeof (FileReader) != "undefined") {
                var image_holder = $("#image-holder");
                image_holder.empty();
                var reader = new FileReader();
                reader.onload = function (e) {
                    var targetObject: any = e.target;
                    $("<img />", {
                        "src": targetObject.result,
                        "class": "thumb-image"
                    }).appendTo(image_holder);
                }
                image_holder.show();
                var thisObject: any = $(this)[0];
                reader.readAsDataURL(thisObject.files[0]);
            } else {
                alert("This browser does not support FileReader.");
            }
        });

    });
}
