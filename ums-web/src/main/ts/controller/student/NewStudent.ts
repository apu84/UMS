///<reference path="../../lib/jquery-maskedinput.d.ts"/>
///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../service/FileUpload.ts"/>
///<reference path="../../model/NewStudentModel.ts"/>
module ums {
  interface NewStudentScope extends ng.IScope {
    newStudentModel: NewStudentModel;
    submit: Function;
  }
  export class NewStudent {
    public static $inject = ['appConstants', '$scope', 'HttpClient', 'FileUpload', 'BaseUri'];

    constructor(private appConstants:any, private $scope:NewStudentScope, private httpClient:HttpClient,
                private fileUpload:FileUpload, private baseUri:BaseUri) {

      $scope.newStudentModel = new NewStudentModel(appConstants, httpClient);
      $scope.submit = this.submit.bind(this);

      //$('.datepicker-default').datepicker();
      $("#birthDate").mask("99/99/9999");
      $("#fileUpload").on('change', function () {

        if (typeof (FileReader) != "undefined") {
          var image_holder = $("#image-holder");
          image_holder.empty();
          var reader = new FileReader();
          reader.onload = function (e) {
            var targetObject:any = e.target;
            $("<img />", {
              "src": targetObject.result,
              "class": "thumb-image",
              "height":"140px",
              "width":"120px"
            }).appendTo(image_holder);
          };
          image_holder.show();
          var thisObject:any = $(this)[0];
          reader.readAsDataURL(thisObject.files[0]);
        } else {
          console.error("This browser does not support FileReader.");
        }
      });
    }

    private submit():void {
      console.debug("upload file....%o", this.$scope.newStudentModel.picture);
      var fileReader = new FileReader();
      console.debug("upload file....%o", fileReader);
      fileReader.onload = () => {
        console.debug("onload.....");
        var dataURL = fileReader.result;
        console.debug("data....%o", dataURL);
        this.$scope.newStudentModel.imageData = dataURL; console.debug("%o", this.$scope.newStudentModel);
        this.httpClient.post(this.baseUri.toAbsolute('academic/student'), this.$scope.newStudentModel, this.appConstants.mimeTypeJson)
            .success((data, status, headers) => {
              console.debug("Student created, resource location : " + headers('location'));
            }).error((data) => {
              console.error(data);
            });

        //this.fileUpload.uploadFile(dataURL, $scope.fileUpload.type, $scope.fileUpload.name, this.baseUri.toAbsolute('academic/student/upload'));
      };
      fileReader.readAsDataURL(this.$scope.newStudentModel.picture);
    }
  }
  UMS.controller('NewStudent', NewStudent);
}
