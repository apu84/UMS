/// <reference path='model/SemesterEnrollmentModel.ts'/>
module ums {
  export class SemesterEnrollment {
    public static $inject = ['$scope', 'appConstants', 'HttpClient'];

    constructor(private $scope: any,
                private appConstants: any,
                private httpClient: HttpClient) {

      $scope.semesterEnrollmentModel = new SemesterEnrollmentModel(appConstants, httpClient);
      $scope.submit = this.submit.bind(this);

      $scope.$watch(
          () => {
            return $scope.semesterEnrollmentModel.semesterId;
          },
          (newValue, oldValue) => {
            console.debug("Change detected: %o", $scope.semesterEnrollmentModel);
            if (newValue !== oldValue) {

              this.httpClient.get('academic/studentEnrollment/enrollment-type/'
                  + $scope.semesterEnrollmentModel.enrollmentType
                  + '/program/' + $scope.semesterEnrollmentModel.programSelector.programId
                  + '/semester/' + $scope.semesterEnrollmentModel.semesterId + '',
                  HttpClient.MIME_TYPE_JSON,
                  (json: any, etag: string) => {
                    var enrollmentStatus: Array<SemesterEnrollmentStatus> = json.entries;
                    this.$scope.semesterEnrollmentModel.status = enrollmentStatus;
                  },
                  (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                  });


            }
          }
      );
    }

    private submit(): void {
      console.debug("Submit.....");
      this.httpClient.post('academic/studentEnrollment/enroll/'
      + this.$scope.semesterEnrollmentModel.enrollmentType
      + "/program/"
      + this.$scope.semesterEnrollmentModel.programSelector.programId
      + "/semester/"
      + this.$scope.semesterEnrollmentModel.semesterId, {}, 'application/json')
          .success((data) => {
            console.debug(data);
          }).error((data) => {
          });
    }
  }
  UMS.controller("SemesterEnrollment", SemesterEnrollment)
}