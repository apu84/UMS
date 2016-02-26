///<reference path="../../../service/HttpClient.ts"/>
///<reference path="../../../lib/jquery.notific8.d.ts"/>
///<reference path="../../../lib/jquery.notify.d.ts"/>
///<reference path="../../../lib/jquery.jqGrid.d.ts"/>
module ums {

  interface IExamRoutineScope extends ng.IScope {
    addNewDateTime: Function;
    removeDateTime: Function;
    addNewProgram:Function;
    removeProgram:Function;
    addNewCourse:Function;
    removeCourse:Function;
    setCourseTitle:Function;
    routine:any;
    data:any;
    rowId:any;
  }
  interface IPrograms {
    index:number;
    programId:string;
    courses : Array<ICourse>;
  }
  interface ICourse {
    index:number;
    courseId: string;
    courseNumber:string;
    courseTitle: string;
    year: number;
    semester: number;
  }

  export class ExamRoutine {
    public static $inject = ['appConstants', 'HttpClient', '$scope'];
    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IExamRoutineScope) {

      $scope.data = {
        examTimeOptions:appConstants.examTime,
        ugPrograms:appConstants.ugPrograms,
        courses:this.getCourses('abc')
      };
      console.log("===============");
      console.log($scope.data.courses);

      $scope.routine = {
        date_times: [{
          index: 0,
          programs: Array<IPrograms>()

        }]
      };


      $scope.addNewDateTime = this.addNewDateTime.bind(this);
      $scope.removeDateTime = this.removeDateTime.bind(this);
      $scope.addNewProgram = this.addNewProgram.bind(this);
      $scope.removeProgram = this.removeProgram.bind(this);

      $scope.addNewCourse = this.addNewCourse.bind(this);
      $scope.removeCourse = this.removeCourse.bind(this);
      $scope.setCourseTitle=this.setCourseTitle.bind(this);

    }
    private addNewDateTime():void {
      var index = this.$scope.routine.date_times.length + 1;
      var item = this.getNewDateTimeRow(index);
      this.$scope.routine.date_times.splice(0, 0, item);

      $('.datepicker-default').datepicker();
      $('.datepicker-default').on('change', function(){
        $('.datepicker').hide();
      });

    }
    private removeDateTime(index:number):void {
      var date_time_Arr = eval( this.$scope.routine.date_times );
      var targetIndex = this.findIndex(date_time_Arr,index);
      this.$scope.routine.date_times.splice( targetIndex, 1 );
    }

    private addNewProgram(index:number):void {
      var date_time_Arr = eval( this.$scope.routine.date_times );
      var targetIndex = this.findIndex(date_time_Arr,index);
      var index:number = this.$scope.routine.date_times[targetIndex].programs.length + 1;
      var item = this.getNewProgramRow(index);
      this.$scope.routine.date_times[targetIndex].programs.splice(0, 0, item);
    }


    private removeProgram(date_time_index:number,program_index:number):void {
      var date_time_Arr = eval( this.$scope.routine.date_times );
      var dateTimeTargetIndex = this.findIndex(date_time_Arr,date_time_index);
      var program_Arr = eval( this.$scope.routine.date_times[dateTimeTargetIndex].programs );
      var programTargetIndex = this.findIndex(program_Arr,program_index);
      this.$scope.routine.date_times[dateTimeTargetIndex].programs.splice( programTargetIndex, 1 );
    }

    private addNewCourse(date_time_index:number,program_index:number):void {
      var date_time_Arr = eval( this.$scope.routine.date_times );
      var dateTimeTargetIndex = this.findIndex(date_time_Arr,date_time_index);

      var program_Arr = eval( this.$scope.routine.date_times[dateTimeTargetIndex].programs );
      var programTargetIndex = this.findIndex(program_Arr,program_index);

      var index = this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses.length + 1;
      var item = this.getNewCourseRow(index);
      this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses.splice(0, 0, item);

      setTimeout(function(){ $('.select2-size').select2({
        placeholder: "Select an option",
        allowClear: true
      });}, 50);

    }

    private removeCourse(date_time_index:number,program_index:number,course_index:number):void {
      var date_time_Arr = eval( this.$scope.routine.date_times );
      var dateTimeTargetIndex = this.findIndex(date_time_Arr,date_time_index);

      var program_Arr = eval( this.$scope.routine.date_times[dateTimeTargetIndex].programs );
      var programTargetIndex = this.findIndex(program_Arr,program_index);

      var course_Arr = eval( this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses );
      var courseTargetIndex = this.findIndex(course_Arr,course_index);
      this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses.splice( courseTargetIndex, 1 );
    }


private findIndex(source_arr: Array<any>, target_index: number): number {
      var targetIndex = -1;
      for( var i = 0; i < source_arr.length; i++ ) {
        if( source_arr[i].index == target_index ) {
          targetIndex = i;
          break;
        }
      }
      return targetIndex;
      }

    private getNewDateTimeRow(index:number){
      var item = {
        index: index - 1,
        programs: Array<IPrograms>()
      };
      return item;
    }
    private getNewProgramRow(index:number){
      var item = {
        index:index-1,
        courses: Array<ICourse>()
      };
      return item;
    }
    private getNewCourseRow(index:number){
      var item = {
        index:index-1,
        course: ''
      };
      return item;
    }

    private setCourseTitle(course:ICourse):void{
      alert(course.courseId+"=="+course.courseNumber);
    }
    private getCourses(semesterId:string){
      var courses=[{courseId:1,courseNumber:'CSE 1234',courseTitle:'ABC Course',year:1,semester:1},{courseId:2,courseNumber:'CSE 6666',courseTitle:'BBCDE Course',year:2,semester:2}]
      return courses;
    }
  }
  UMS.controller('ExamRoutine', ExamRoutine);
}

