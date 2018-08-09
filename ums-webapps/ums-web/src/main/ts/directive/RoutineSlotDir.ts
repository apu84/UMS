module ums{

    interface IRoutineSlotDirScope extends ng.IScope{
      routineList: ClassRoutine[];
      routineSlot: RoutineSlot;
    }

    interface IRoutineRow{
      id: number;
      routineList: ClassRoutine[];
    }

    class RoutineSlotDirController {
        private routineList:ClassRoutine[];
        private routineSlot: RoutineSlot;
        private template:any;
        private colSpan:number;
        private routine:ClassRoutine;
        private routineRows: IRoutineRow[];

        public static $inject = ['$scope','classRoomService', 'courseService', 'appConstants', '$timeout', 'semesterService', '$q','routineConfigService', 'classRoutineService'];

        constructor(private $scope: IRoutineSlotDirScope,
                    private classRoomService: ClassRoomService,
                    private courseService: CourseService,
                    private  appConstants: any,
                    public $timeout: ng.ITimeoutService,
                    private semesterService: SemesterService,
                    private $q: ng.IQService,
                    private routineConfigService: RoutineConfigService,
                    private classRoutineService: ClassRoutineService) {


            this.initialize();
        }

        private initialize(){
            if(this.$scope.routineSlot!=undefined){
                this.routineList = angular.copy(this.$scope.routineSlot.routineList);
                this.routineSlot = angular.copy(this.$scope.routineSlot);
            }

            this.createSlot();
        }

        public getColSpan(routine:ClassRoutine):string{
          let slotStartTime:any = moment(routine.startTime,'hh:mm A');
          let slotEndTime:any = moment(routine.endTime, 'hh:mm A');
          let diff=slotEndTime.diff(slotStartTime,'minutes');
          let colSpan = diff/this.routineConfigService.routineConfig.duration;
          return colSpan.toString();
        }

        private createSlot(){
            if(this.routineList==undefined || this.routineList.length==0)
                this.template='';
            else{
              this.template=`<table class='table table-bordered' style="width: 100%;">`;
              this.template=this.template+`<tr>`;
              let groupStartTime:any = moment(this.routineSlot.startTime,'hh:mm A');
              let groupEndTime: any = moment(this.routineSlot.endTime, 'hh:mm A');
              let iterationLength:number = (groupEndTime.diff(groupStartTime, 'minutes'))/this.routineConfigService.routineConfig.duration;
              let iterationStartTime: string = angular.copy(this.routineSlot.startTime);
              this.routineRows = [];
              while(this.routineList.length>0){
                let routineRow: IRoutineRow = <IRoutineRow>{};
                routineRow.routineList=[];

                for (var i=0; i<iterationLength; i++){
                  if (this.routineList.length==0 && i==(iterationLength))
                    break;


                  if (this.routineList.length==0){
                    let iterationStartTimeObj:any = moment(iterationStartTime, 'hh:mm A');
                    let emptyRoutine:ClassRoutine=<ClassRoutine>{};
                    emptyRoutine.startTime=iterationStartTime;
                    this.template = this.template+`<td></td>`;
                    iterationStartTimeObj = moment(iterationStartTimeObj).add(this.routineConfigService.routineConfig.duration,'m').toDate();
                    iterationStartTime = moment(iterationStartTimeObj).format("hh:mm A");
                    console.log("iteration time"+ iterationStartTime);
                    emptyRoutine.endTime = iterationStartTime;
                    routineRow.routineList.push(emptyRoutine);
                  }else{
                    this.routine = angular.copy(this.routineList.shift());
                    let slotStartTime:any = moment(this.routine.startTime,'hh:mm A');
                    let breakCondition:boolean = false;
                    if (iterationStartTime===this.routine.startTime){
                      routineRow.routineList.push(angular.copy(this.routine));
                      let slotEndTime:any = moment(this.routine.endTime, 'hh:mm A');
                      let diff=slotEndTime.diff(slotStartTime,'minutes');
                      this.colSpan = diff/this.routineConfigService.routineConfig.duration;
                      this.template = this.template+'<td align="center" colspan="'+this.colSpan.toString()+'">';
                      this.template = this.template+this.routine.course.no+" ("+this.routine.section+")<br>"+this.routine.room.roomNo;
                      this.template=this.template+`</td>`;
                      if (this.routine.endTime==this.routineSlot.endTime){
                        iterationStartTime = angular.copy( this.routineSlot.startTime);
                        this.template = this.template+'</tr>';
                        breakCondition = true;
                      }
                      else{
                        /*let iterationStartTimeObj:any = moment(iterationStartTime, 'hh:mm A');
                        iterationStartTimeObj = moment(iterationStartTimeObj).add(this.routineConfigService.routineConfig.duration,'m').toDate();
                        iterationStartTime = moment(iterationStartTimeObj).format("hh:mm A");*/
                        iterationStartTime= this.routine.endTime;
                      }
                      if (breakCondition) break;

                    } else{
                      this.routineList.unshift(this.routine);
                      let iterationStartTimeObj:any = moment(iterationStartTime, 'hh:mm A');
                      let emptyRoutine:ClassRoutine=<ClassRoutine>{};
                      emptyRoutine.startTime=iterationStartTime;
                      this.template = this.template+`<td></td>`;
                      iterationStartTimeObj = moment(iterationStartTimeObj).add(this.routineConfigService.routineConfig.duration,'m').toDate();
                      iterationStartTime = moment(iterationStartTimeObj).format("hh:mm A");
                      emptyRoutine.endTime = iterationStartTime;
                      routineRow.routineList.push(emptyRoutine);

                    }
                  }


                }
                this.routineRows.push(routineRow);
                // this.template = this.template+"</tr>";
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

        public getCourseTeacher(courseId: string):string{
          let courseTeacherStr="(";
          if (this.classRoutineService.courseTeacherMap[courseId]==undefined)
            courseTeacherStr = courseTeacherStr+"TBA";
          else{
            let courseTeacherList:CourseTeacherInterface[] = this.classRoutineService.courseTeacherMap[courseId];
            let courseTeacherInitials:string[] = []
            courseTeacherList.forEach((c:CourseTeacherInterface)=>{
              courseTeacherInitials.push( c.shortName);
            }) ;
            courseTeacherStr = courseTeacherStr+courseTeacherInitials.join(",");
          }
          courseTeacherStr = courseTeacherStr+")";
          return courseTeacherStr;
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