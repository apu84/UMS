///<reference path="../../../service/HttpClient.ts"/>
///<reference path="../../../lib/jquery.notific8.d.ts"/>
///<reference path="../../../lib/jquery.notify.d.ts"/>
///<reference path="../../../lib/jquery.jqGrid.d.ts"/>
module ums {

  interface IExamRoutineScope extends ng.IScope {
    addNewDateTime: Function;
    removeDateTime: Function;
    addNewProgram: Function;
    removeProgram: Function;
    addNewCourse: Function;
    removeCourse: Function;
    programSelectionChanged: Function;
    courseSelectionChanged:Function;
    routine: any;
    data: any;
    rowId: any;
  }
  interface IDateTime{
    index: number;
    examDate: string;
    examTime: string;
    programs: Array<IProgram>;
  }
  interface IProgram {
    index:number;
    programId: number;
    courses : Array<ICourse>;
    courseArr: Array<ICourse>;
  }
  interface ICourse {
    index: number;
    id: string;
    no: string;
    title: string;
    year: number;
    semester: number;
  }

  export class ExamRoutine {
    public static $inject = ['appConstants', 'HttpClient', '$scope','$q'];
    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IExamRoutineScope,private $q:ng.IQService) {

      $scope.data = {
        examTimeOptions:appConstants.examTime,
        ugPrograms:appConstants.ugPrograms
      };
      $scope.routine = {
        date_times: Array<IDateTime>()
      };


      $scope.addNewDateTime = this.addNewDateTime.bind(this);
      $scope.removeDateTime = this.removeDateTime.bind(this);
      $scope.addNewProgram = this.addNewProgram.bind(this);
      $scope.removeProgram = this.removeProgram.bind(this);

      $scope.addNewCourse = this.addNewCourse.bind(this);
      $scope.removeCourse = this.removeCourse.bind(this);

      $scope.programSelectionChanged = this.programSelectionChanged.bind(this);
      $scope.courseSelectionChanged = this.courseSelectionChanged.bind(this);


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

    private addNewCourse(date_time_index:number,program_index:number,program_id:number):void {
     // console.log(date_time_index+"--"+program_index+"---");

      var date_time_Arr = eval( this.$scope.routine.date_times );
      var dateTimeTargetIndex = this.findIndex(date_time_Arr,date_time_index);

      var program_Arr = eval( this.$scope.routine.date_times[dateTimeTargetIndex].programs );
      var programTargetIndex = this.findIndex(program_Arr,program_index);

      var index = this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses.length + 1;
      var courseRow=this.getNewCourseRow(index);

      this.getCourseArr(program_id).then((courseArr: Array<ICourse>)=>{
        this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courseArr=courseArr;
        this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses.splice(0, 0, courseRow);

        console.log('#'+program_index+'select'+courseRow.index);
        setTimeout(function(){ $('#'+program_index+'select'+courseRow.index).select2({
          placeholder: "Select an option",
          allowClear: true
        });}, 50);
      });

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

    private programSelectionChanged(program_obj_row:IProgram,exam_program_id:number):void {
      console.log(exam_program_id);
      console.log(program_obj_row);
      for (var ind in program_obj_row.courses)
      {
        var course:ICourse=program_obj_row.courses[ind];
        course.year=null;
        course.semester=null;
        course.title=null;
      }
      program_obj_row.courseArr=null;

      this.getCourseArr(exam_program_id).then((courseArr: Array<ICourse>)=>{
        program_obj_row.courseArr=courseArr;
        for (var ind in program_obj_row.courses) {
          $("#" + program_obj_row.index + "select"+ind).select2("destroy").select2();
        }
      });



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
      var dateTimeRow:IDateTime ={
        index: index-1,
        examDate: '',
        examTime: '',
        programs: Array<IProgram>()
      }
      return dateTimeRow;
    }
    private getNewProgramRow(index:number){
      var programRow:IProgram={
        index:index-1,
        programId: null,
        courses: Array<ICourse>(),
        courseArr: Array<ICourse>()
      }
      return programRow;
    }
    private getNewCourseRow(index:number){
      var courseRow:ICourse={
        index: index-1,
        id: '',
        no: '',
        title: '',
        year: null,
        semester: null
      }
      return courseRow;
    }
    private getCourseArr(program_id:number):ng.IPromise<any>{
      var defer = this.$q.defer();
      var courseArr:Array<any>;
      this.httpClient.get('academic/course/semester/11012015/program/'+program_id, 'application/json',
          (json:any, etag:string) => {
            courseArr = json.entries;
            defer.resolve(courseArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;
    }
    private courseSelectionChanged(course_row:ICourse,selected_course:ICourse){
      course_row.year=selected_course.year;
      course_row.semester=selected_course.semester;
      course_row.title=selected_course.title;
    }

  }
  UMS.controller('ExamRoutine', ExamRoutine);
}

