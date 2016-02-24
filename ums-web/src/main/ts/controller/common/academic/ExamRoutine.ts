///<reference path="../../../model/master_data/ClassRoom.ts"/>
///<reference path="../../../service/HttpClient.ts"/>
///<reference path="../../../lib/jquery.notific8.d.ts"/>
///<reference path="../../../lib/jquery.notify.d.ts"/>
///<reference path="../../../lib/jquery.jqGrid.d.ts"/>
module ums {

  interface IExamRoutineScope extends ng.IScope {
    addNewRow: Function;
    routine:any;
    data:any;
    rowId:any;
  }

  export class ExamRoutine {
    public static $inject = ['appConstants', 'HttpClient', '$scope'];

    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IExamRoutineScope) {

      $scope.routine = {
        rows: [{
          date_time: '--',
          others: ''
        }]
      };

      $scope.addNewRow = function() {

        var c = $scope.routine.rows.length + 1;
        var item = {
          date_time: '',
          others: ''
        };
        $scope.routine.rows.splice(0, 0, item);
        /*
        $scope.routine.rows.push({
          date_time: '',
          others: ''
        });
        */
      }

    }
  }
  UMS.controller('ExamRoutine', ExamRoutine);
}

