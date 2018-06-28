module ums{

    export enum DayType{
        SATURDAY=1,
        SUNDAY=2,
        MONDAY=3,
        TUESDAY=4,
        WEDNESDAY=5,
        THURSDAY=6,
        FRIDAY=7
    }

    export interface RoutineConfig{
        id: string;
      programType: number;
        semester: Semester;
        semesterId: number;
        dayFrom: DayType;
        dayTo: DayType;
        startTime: string;
        endTime: string;
        duration: number;
    }

    export class RoutineConfigService{
        private routineUrl:string='academic/routine-config';
      public routineConfig: RoutineConfig;
        public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
        constructor(private appConstants: any, private httpClient: HttpClient,
                    private $q:ng.IQService, private notify: Notify,
                    private $sce:ng.ISCEService,private $window:ng.IWindowService) {

        }

        public saveOrUpdate(routineConfig: RoutineConfig): ng.IPromise<RoutineConfig>{
            let defer:ng.IDeferred<RoutineConfig> = this.$q.defer();
            this.httpClient.put(this.routineUrl, routineConfig, HttpClient.MIME_TYPE_JSON)
                .success((response: any) => {
                  this.notify.success("Saved Successfully")
                  defer.resolve(response)
                });
            return defer.promise;
        }

      public getBySemesterAndProgramType(semesterId: number, programType: number): ng.IPromise<RoutineConfig> {
            let defer: ng.IDeferred<RoutineConfig> = this.$q.defer();
        this.httpClient.get(this.routineUrl + "/semester/" + semesterId + "/programType/" + programType, HttpClient.MIME_TYPE_JSON,
            (response: any) => {
              console.log("response");
              console.log(response);
              defer.resolve(response)
            });
            return defer.promise;
        }
    }

    UMS.service("routineConfigService", RoutineConfigService);
}