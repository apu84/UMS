/// <reference path='model/SemesterEnrollmentModel.ts'/>
module ums {
  export interface SemesterEnrollmentScope extends ng.IScope {
    semesterEnrollmentModel: SemesterEnrollmentModel;
    submit: Function;
    showStatus: boolean;
    numberWithSuffix: (n: number) => string;
  }
  export class SemesterEnrollment {
    public static $inject = ['$scope', 'appConstants', 'HttpClient', '$q', 'notify'];

    constructor(private $scope: SemesterEnrollmentScope,
                private appConstants: any,
                private httpClient: HttpClient,
                private $q: ng.IQService,
                private notify: Notify) {

      $scope.semesterEnrollmentModel = new SemesterEnrollmentModel(appConstants, httpClient);
      $scope.submit = this.submit.bind(this);

      $scope.$watchGroup(['semesterEnrollmentModel.semesterId',
            'semesterEnrollmentModel.enrollmentType',
            'semesterEnrollmentModel.programSelector.programId'],
          (newValue, oldValue) => {

            if (newValue !== oldValue) {

              if ($scope.semesterEnrollmentModel.enrollmentType
                  && $scope.semesterEnrollmentModel.programSelector.programId
                  && $scope.semesterEnrollmentModel.semesterId) {

                this.enrollmentStatus($scope.semesterEnrollmentModel.programSelector.programId,
                    $scope.semesterEnrollmentModel.semesterId)
                    .then((enrollmentStatus: Array<SemesterEnrollmentStatus>) => {
                      this.$scope.semesterEnrollmentModel.status = enrollmentStatus;
                      this.$scope.showStatus = true;
                    });
              }
            }
          }
      );

      $scope.numberWithSuffix = (n: number) => {
        return UmsUtil.getNumberWithSuffix(n);
      };
    }

    private enrollmentStatus(programId: string, semesterId: string): ng.IPromise<Array<SemesterEnrollmentStatus>> {
      var defer: ng.IDeferred = this.$q.defer();
      this.httpClient.get('academic/studentEnrollment'
          + '/program/' + programId
          + '/semester/' + semesterId + '',
          HttpClient.MIME_TYPE_JSON,
          (json: any, etag: string) => {
            var enrollmentStatus: Array<SemesterEnrollmentStatus> = json.entries;
            defer.resolve(enrollmentStatus);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private submit(): void {
      this.httpClient.post('academic/studentEnrollment/enroll/'
      + this.$scope.semesterEnrollmentModel.enrollmentType
      + "/program/"
      + this.$scope.semesterEnrollmentModel.programSelector.programId
      + "/semester/"
      + this.$scope.semesterEnrollmentModel.semesterId, {'status': this.$scope.semesterEnrollmentModel.status}, 'application/json')
          .success((data:NotifyMessage) => {
            this.notify.show(data);
            this.enrollmentStatus(this.$scope.semesterEnrollmentModel.programSelector.programId,
                this.$scope.semesterEnrollmentModel.semesterId).then((enrollmentStatus: Array<SemesterEnrollmentStatus>) => {
                  this.$scope.semesterEnrollmentModel.status = enrollmentStatus;
                  this.$scope.showStatus = true;
                });

          }).error((data) => {
            console.error(data);
          });
    }
  }
  UMS.controller("SemesterEnrollment", SemesterEnrollment)
}