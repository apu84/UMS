module ums {
  interface IOptCourseApplication extends ng.IScope {
    optional:any;
    saveOrSubmitApplication:Function;
    applicationSelectionChange:Function;
  }

  interface IOptCourse {
    id: string;
    index: number;
    courseId:string;
    no: string;
    courseNo:string;
    title: string;
    courseTitle:string;
    year: number;
    semester: number;
    pairCourseId:string;
    application:boolean;
    approved:boolean;
    bgColor:string;
    statusId:number;
    statusLabel:string;
    newStatusId:number;
    applicationTypeId:number;
    applicationTypeLabel:String;
    newStatusLabel:string;
    studentApplied:number;
  }


  var map = new Map();
  map.set("application", "academic/optional/application/student");
  map.set("application_save_submit", "academic/optional/application/student/{STATUS-ID}");


  export class OptionalCoursesApplication {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q'];

    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IOptCourseApplication, private $q:ng.IQService) {
      $scope.optional = {
        callForApplicationCourses: Array<IOptCourse>(),
        appliedCourses: Array<IOptCourse>() , //Approved courses among student applied
        courses:Array<IOptCourse>(),
        applicationStatus: ''
      };

      this.getApplication(map.get("application")).then((json:any)=> {
        this.$scope.optional.applicationStatus=json.application_status;
        this.$scope.optional.callForApplicationCourses=json.callForApplication_courses;
        this.$scope.optional.appliedCourses=json.applied_courses;

        this.arrangeCourses();
        $scope.saveOrSubmitApplication=this.saveOrSubmitApplication.bind(this);
        $scope.applicationSelectionChange=this.applicationSelectionChange.bind(this);
      });


    }

    private arrangeCourses(){
      var flag:boolean=false;
      for(var ind1 in this.$scope.optional.callForApplicationCourses){
        var course1 :IOptCourse=this.$scope.optional.callForApplicationCourses[ind1];
        flag=false;
        for(var ind2 in this.$scope.optional.appliedCourses){
          var course2 :IOptCourse=this.$scope.optional.appliedCourses[ind2];
              if(course1.id==course2.courseId) {
               var course:IOptCourse=course1;
                course.statusId=course2.statusId;
                course.statusLabel=course2.statusLabel;
                course.studentApplied=1;
                this.$scope.optional.courses.push(course);
                flag=true;
                break;
              }
        }
        if(flag==false){
          var course:IOptCourse=course1;
          course.statusId=-1;
          course.statusLabel="";
          this.$scope.optional.courses.push(course);
        }
      }

    }
    private getApplication(url:string):ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            defer.resolve(json);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;

    }

    private saveOrSubmitApplication(statusId:number){
      var courseList:Array<IOptCourse>=new Array<IOptCourse>();
      for(var ind in this.$scope.optional.courses){
        var course:IOptCourse=this.$scope.optional.courses[ind];
        if(course.studentApplied==1)
          courseList.push(course);
      }
      var  url=map.get("application_save_submit");
      url=url.replace("{STATUS-ID}",statusId);
      var complete_json={};
      complete_json["courseList"] = courseList;

      this.httpClient.put(url, complete_json, 'application/json')
          .success(() => {
            $.notific8("Successfully Saved");
            if(statusId==1)
            this.$scope.optional.applicationStatus="Submitted";
            else
              this.$scope.optional.applicationStatus="Saved";

          }).error((data) => {
          });

    }

private applicationSelectionChange(course:IOptCourse){
  if(course.pairCourseId!=""){
    var pairCourseIndex=Utils.findIndex(this.$scope.optional.courses,course.pairCourseId);
    this.$scope.optional.courses[pairCourseIndex].studentApplied =course.studentApplied;
  }
}
  }
  UMS.controller('OptionalCoursesApplication', OptionalCoursesApplication);
}

