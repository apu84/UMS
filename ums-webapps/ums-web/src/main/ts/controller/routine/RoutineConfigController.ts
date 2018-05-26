module ums{

    interface IStateParams{
        semester:Semester;
        user: User;
        department:any;
        program:Program;
    }

    export class RoutineConfigController{

        private weekDays:IParameter[];
        private selectedDayFrom:IParameter;
        private selectedDayTo:IParameter;
        private startTime: string;
        private endTime: string;
        private duration:number;
        private stateParams:IStateParams;

        public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window','routineConfigService','$stateParams'];
        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q:ng.IQService,
                    private notify: Notify,
                    private $sce:ng.ISCEService,
                    private $window:ng.IWindowService,
                    private routineConfigService: RoutineConfigService,
                    private $stateParams:any) {
            this.stateParams = $stateParams;
            this.weekDays = this.appConstants.weekDays;
        }
    }

    UMS.controller("RoutineConfigCongroller", RoutineConfigController);
}