/// <reference path='model/SemesterEnrollmentModel.ts'/>
module ums {
  export interface SemesterEnrollmentScope extends ng.IScope {
    semesterEnrollmentModel: SemesterEnrollmentModel;
    submit: Function;
    showStatus: boolean;
  }
  export class SemesterEnrollment {
    public static $inject = ['$scope', 'appConstants', 'HttpClient'];

    constructor(private $scope: SemesterEnrollmentScope,
                private appConstants: any,
                private httpClient: HttpClient) {

      $scope.semesterEnrollmentModel = new SemesterEnrollmentModel(appConstants, httpClient);
      $scope.submit = this.submit.bind(this);

      $scope.$watchGroup(['semesterEnrollmentModel.semesterId',
            'semesterEnrollmentModel.enrollmentType',
            'semesterEnrollmentModel.programSelector.programId'],
          (newValue, oldValue) => {
            console.debug("Change detected: %o", $scope.semesterEnrollmentModel);

            if (newValue !== oldValue) {
/*
              if ($scope.semesterEnrollmentModel.enrollmentType != ''
                  && $scope.semesterEnrollmentModel.programSelector.programId
                  && $scope.semesterEnrollmentModel.semesterId) {

                this.httpClient.get('academic/studentEnrollment'
                    + '/program/' + $scope.semesterEnrollmentModel.programSelector.programId
                    + '/semester/' + $scope.semesterEnrollmentModel.semesterId + '',
                    HttpClient.MIME_TYPE_JSON,
                    (json: any, etag: string) => {
                      var enrollmentStatus: Array<SemesterEnrollmentStatus> = json.entries;
                      this.$scope.semesterEnrollmentModel.status = enrollmentStatus;
                      $scope.showStatus = true;
                    },
                    (response: ng.IHttpPromiseCallbackArg<any>) => {
                      console.error(response);
                    });
              }
              */
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