module ums{

    interface IRoutineSlotDirScope extends ng.IScope{
      routineList: ClassRoutine[];
      routineSlot: RoutineSlot;
    }

    class RoutineSlotDirController {
        private routineList:ClassRoutine[];
        private routineSlot: RoutineSlot;
        private template:string;

        public static $inject = ['$scope','classRoomService', 'courseService', 'appConstants', '$timeout', 'semesterService', '$q','routineConfigService'];

        constructor(private $scope: IRoutineSlotDirScope,
                    private classRoomService: ClassRoomService,
                    private courseService: CourseService,
                    private  appConstants: any,
                    public $timeout: ng.ITimeoutService,
                    private semesterService: SemesterService,
                    private $q: ng.IQService,
                    private routineConfigService: RoutineConfigService) {

            if($scope.routineSlot!=undefined)
             this.routineList = $scope.routineSlot.routineList;
            this.routineSlot = $scope.routineSlot;
            this.createSlot()

            console.log("Hello, Form routine slot directive");
        }

        private createSlot(){
            if(this.routineList==undefined || this.routineList.length==0)
                this.template='';
            else{
                this.template=`<table class='table table-bordered'>`
                while(this.routineList.length>0){
                    let routine: ClassRoutine = this.routineList.pop();
                    let slotStartTime:any = moment(routine.startTime,'hh:mm A');
                    let slotEndTime:any = moment(routine.endTime, 'hh:mm A');
                    let diff=slotEndTime.diff(slotStartTime,'minutes');
                    let colSpan = diff/this.routineConfigService.routineConfig.duration;
                    this.template=this.template+`<tr>`;
                    this.template = this.template+`<td align="center" colspan='`+colSpan+`'>`;
                    this.template = this.template+routine.course.no+" ("+routine.section+")<br>"+routine.room.roomNo;
                    this.template=this.template+`</td>`;
                    this.template=this.template+`</tr>`;

                }

                /*this.template=
                    `
                <table class="table table-bordered">
                     <tr ng-repeat="routine in vm.routineList">
                        <td align="center">
                            {{routine.course.no}}({{routine.section}})<br>
                            {{routine.room.roomNo}}<br>
                            {{vm.getCourseTeacherByCourseAndSection(routine.courseId, routine.section)}}
                        </td>
                    </tr>
                </table>
                `;*/
            }

        }

    }

    class RoutineSlotDir implements ng.IDirective{
        constructor(){

        }

        public restrict: string='EA';
        public scope={
            routineSlot:'=routineSlot'
        };

        public controller = RoutineSlotDirController;
        public controllerAs = "vm";

        public link = (scope:any, element:any, attributes:any)=>{

        }

        public templateUrl:string="./views/directive/routine-slot-dir.html";

    }

    UMS.directive("routineSlotDir", [()=>{
        return new RoutineSlotDir();
    }]);

}