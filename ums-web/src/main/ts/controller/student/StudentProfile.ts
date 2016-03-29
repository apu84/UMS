module ums {
  export interface StudentProfileScope extends ng.IScope {
    student: Student;
    editProfileMode: boolean;
    etag: string;
    toggleEditProfile: Function;
    cancelEditProfile: Function;
  }
  export class StudentProfile {
    public static $inject = ['$scope', 'appConstants', 'HttpClient'];

    constructor(private $scope:any,
                private appConstants:any,
                private httpClient:HttpClient) {

      $scope.editProfileMode = false;
      $scope.saveProfile = this.saveProfile.bind(this);
      $scope.toggleEditProfile = this.toggleEditProfile.bind(this);
      $scope.cancelEditProfile = this.cancelEditProfile.bind(this);
      this.getProfileInfo();
    }

    private getProfileInfo(): void {
      this.httpClient.get('academic/student/profile', HttpClient.MIME_TYPE_JSON,
          (response: Student, etag: string) => {
            this.$scope.student = response;
            this.$scope.etag = etag;
          });
    }

    private toggleEditProfile(): void {
      this.$scope.editProfileMode = !this.$scope.editProfileMode;
    }

    private cancelEditProfile(): void {
      this.getProfileInfo();
      this.toggleEditProfile();
    }

    private saveProfile():void {
      this.httpClient.put('academic/student/profile', this.$scope.student, HttpClient.MIME_TYPE_JSON, this.$scope.etag)
          .success((data) => {
            this.toggleEditProfile();
            this.getProfileInfo();
          }).error((error) => {
            console.error(error);
      });
    }
  }

  UMS.controller('StudentProfile', StudentProfile);
}
