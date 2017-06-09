module ums {
  export class ReadmissionController {
    public static $inject = ['$scope', 'ReadmissionService'];
    public messageText: string;
    public courses: ReadmissionCourse[];
    public alreadyApplied: boolean = false;

    constructor(private $scope: ng.IScope,
                private readmissionService: ReadmissionService) {
      readmissionService.getReadmissionStatus().then((status: string) => {
        switch (status) {
          case readmissionService.responseMap.ALLOWED:
            this.readmissionService.getApplicableCourse().then((courses: ReadmissionCourse[]) => {
              this.courses = courses;
            });
            break;
          case readmissionService.responseMap.APPLIED:
            this.readmissionService.getAppliedCourses().then((courses: ReadmissionCourse[]) => {
              this.courses = courses;
              this.alreadyApplied = !this.alreadyApplied;
            });
            break;
          case readmissionService.responseMap.NOT_ALLOWED:
            this.messageText = 'Not allowed for Readmission';
            break;
          case readmissionService.responseMap.NOT_IN_READMISSION_SLOT:
            this.messageText = 'Not within Readmission time slot';
            break;
        }
      });
    }

    public apply(courses: ReadmissionCourse[]): void {
      this.readmissionService.apply(courses).then((response: string) => {
        switch (response) {
          case this.readmissionService.responseMap.APPLIED:
            this.readmissionService.getAppliedCourses().then((courses: ReadmissionCourse[]) => {
              this.courses = courses;
              this.alreadyApplied = !this.alreadyApplied;
            });
            break;
          case this.readmissionService.responseMap.NOT_ALLOWED:
            this.messageText = 'Not allowed for Readmission';
            break;
          case this.readmissionService.responseMap.NOT_IN_READMISSION_SLOT:
            this.messageText = 'Not within Readmission time slot';
            break;
          case this.readmissionService.responseMap.CONTAINS_INVALID_COURSE:
            this.messageText = 'Application contains invalid course/s';
            break;
          case this.readmissionService.responseMap.NOT_TAKEN_MINIMUM_NO_OF_COURSE:
            this.messageText = 'You haven\'t taken minimum number of course/s' ;
            break;
          case this.readmissionService.responseMap.NOT_TAKEN_MINIMUM_NO_OF_LAST_SEMESTER_FAILED_COURSE:
            this.messageText = 'You have\'t taken minimum taken minimum number of failed courses from last semester';
            break;
          case this.readmissionService.responseMap.REQUIRES_SESSIONAL:
            this.messageText = 'You must take failed sessional course/s';
            break;
          default:
            this.messageText = '';
            break;
        }
      });
    }
  }
  UMS.controller('ReadmissionController', ReadmissionController);
}