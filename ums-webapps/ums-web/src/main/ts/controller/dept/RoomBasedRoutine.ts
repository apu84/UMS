/**
 * Created by Munna on 26-Oct-16.
 */

module ums{
  interface IRoomBasedRoutine extends ng.IScope{

  }

  class RoomBasedRoutine{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','classRoutineService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IRoomBasedRoutine,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService,private classRoutineService:ClassRoutineService) {

    }
  }

  UMS.controller("RoomBasedRoutine", RoomBasedRoutine);
}