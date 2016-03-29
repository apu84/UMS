module ums {
  export interface StudentProfileScope extends ng.IScope {
    student: Student;
    editProfileMode: boolean;
    etag: string;
  }
  export class StudentProfile {
    public static $inject = ['$scope', 'appConstants', 'HttpClient'];

    constructor(private $scope:any,
                private appConstants:any,
                private httpClient:HttpClient) {

      $scope.editProfileMode = false;
      $scope.saveProfile = this.saveProfile.bind(this);

      httpClient.get('academic/student/profile/', HttpClient.MIME_TYPE_JSON,
          (response:Student, etag:string) => {
            $scope.student = response;
            $scope.etag = etag;
          });
    }

    private saveProfile():void {
      this.httpClient.put('academic/student/profile/', this.$scope.student, HttpClient.MIME_TYPE_JSON, this.$scope.etag)
          .success((data) => {
            console.debug(data);
          }).error((data) => {
      });
    }
  }

  UMS.controller('StudentProfile', StudentProfile);
}
