///<reference path="../../../model/master_data/ClassRoom.ts"/>
///<reference path="../../../service/HttpClient.ts"/>
///<reference path="../../../lib/jquery.notific8.d.ts"/>
///<reference path="../../../lib/jquery.notify.d.ts"/>
///<reference path="../../../lib/jquery.jqGrid.d.ts"/>
module ums {

  interface IExamRoutineScope extends ng.IScope {
    addNewRow: Function;
    removeDateTime: Function;
    addNewProgram:Function;
    removeProgram:Function;
    addNewCourse:Function;
    removeCourse:Function;
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
    title: string;
    year: number;
    semester: number;
  }
  export class ExamRoutine {
    public static $inject = ['appConstants', 'HttpClient', '$scope'];
    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IExamRoutineScope) {

      $scope.routine = {
        date_times: [{
          index:0,
          programs: Array<IPrograms>
        }]
      };

      $scope.addNewRow = function() {

        var index = $scope.routine.date_times.length + 1;
        var item = {
          index: index - 1,
          programs: Array<any>
        };
        $scope.routine.date_times.splice(0, 0, item);

      }
      $scope.removeDateTime = function(index){
        var targetIndex = -1;
        var date_time_Arr = eval( $scope.routine.date_times );
        for( var i = 0; i < date_time_Arr.length; i++ ) {
          if( date_time_Arr[i].index == index ) {
            targetIndex = i;
            break;
          }
        }
        if( targetIndex == -1 ) {
          alert( "Something gone wrong" );
        }
        $scope.routine.date_times.splice( targetIndex, 1 );
      };

      $scope.addNewProgram = function(index){
        var targetIndex = -1;
        var date_time_Arr = eval( $scope.routine.date_times );
        for( var i = 0; i < date_time_Arr.length; i++ ) {
          if( date_time_Arr[i].index == index ) {
            targetIndex = i;
            break;
          }
        }

        var index = $scope.routine.date_times[targetIndex].programs.length + 1;
        var item = {
          index:index-1,
          courses: Array<ICourse>
        };
        $scope.routine.date_times[targetIndex].programs.splice(0, 0, item);

      };

        $scope.removeProgram = function(date_time_index,program_index){
          var dateTimeTargetIndex = -1;
          var date_time_Arr = eval( $scope.routine.date_times );
          for( var i = 0; i < date_time_Arr.length; i++ ) {
            if( date_time_Arr[i].index == date_time_index ) {
              dateTimeTargetIndex = i;
              break;
            }
          }
          console.log(dateTimeTargetIndex);
          var programTargetIndex = -1;
          var program_Arr = eval( $scope.routine.date_times[dateTimeTargetIndex].programs );
          for( var i = 0; i < program_Arr.length; i++ ) {
            if( program_Arr[i].index == program_index ) {
              programTargetIndex = i;
              break;
            }
          }

          if( programTargetIndex == -1 ) {
            alert( "Something gone wrong" );
          }
          $scope.routine.date_times[dateTimeTargetIndex].programs.splice( programTargetIndex, 1 );
        };

      $scope.addNewCourse = function(date_time_index,program_index){

        var dateTimeTargetIndex = -1;
        var date_time_Arr = eval( $scope.routine.date_times );
        for( var i = 0; i < date_time_Arr.length; i++ ) {
          if( date_time_Arr[i].index == date_time_index ) {
            dateTimeTargetIndex = i;
            break;
          }
        }
console.log(dateTimeTargetIndex);
        var programTargetIndex = -1;
        var program_Arr = eval( $scope.routine.date_times[dateTimeTargetIndex].programs );
        for( var i = 0; i < program_Arr.length; i++ ) {
          if( program_Arr[i].index == program_index ) {
            programTargetIndex = i;
            break;
          }
        }
        console.log(programTargetIndex);
        var index = $scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses.length + 1;
        var item = {
          index:index-1,
          course: ''
        };
        $scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses.splice(0, 0, item);


      };


      $scope.removeCourse = function(date_time_index,program_index,course_index){
        var dateTimeTargetIndex = -1;
        var date_time_Arr = eval( $scope.routine.date_times );
        for( var i = 0; i < date_time_Arr.length; i++ ) {
          if( date_time_Arr[i].index == date_time_index ) {
            dateTimeTargetIndex = i;
            break;
          }
        }
        console.log(dateTimeTargetIndex);
        var programTargetIndex = -1;
        var program_Arr = eval( $scope.routine.date_times[dateTimeTargetIndex].programs );
        for( var i = 0; i < program_Arr.length; i++ ) {
          if( program_Arr[i].index == program_index ) {
            programTargetIndex = i;
            break;
          }
        }

        var courseTargetIndex = -1;
        var course_Arr = eval( $scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses );
        for( var i = 0; i < program_Arr.length; i++ ) {
          if( course_Arr[i].index == course_index ) {
            courseTargetIndex = i;
            break;
          }
        }

        if( courseTargetIndex == -1 ) {
          alert( "Something gone wrong" );
        }
        $scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses.splice( courseTargetIndex, 1 );
      };
    }
  }
  UMS.controller('ExamRoutine', ExamRoutine);
}

