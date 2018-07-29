module ums{
    export class EmployeeExamAttendanceService{
        public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
        constructor(private appConstants: any, private httpClient: HttpClient,
                    private $q:ng.IQService, private notify: Notify,
                    private $sce:ng.ISCEService,private $window:ng.IWindowService) {

        }
        public addEmpExamAttendanceInfo(json:any):ng.IPromise<any>{
            var defer = this.$q.defer();
            console.log("Inside-Service-add-info")
            console.log(json);
            this.httpClient.post("academic/empExamAttendance/addRecords",json,'application/json')
                .success(()=>{
                    this.notify.success("Successfully Saved");
                    defer.resolve('success')
                })
                .error((data)=>{
                    console.log(data);
                    this.notify.error("Problem in saving data");
                    defer.resolve('failure');
                });
            return defer.promise;
        }
        public getEmpExamAttendanceInfo(semesterId:number,examType:number):ng.IPromise<any>{
            var defer = this.$q.defer();
            this.httpClient.get('academic/empExamAttendance/getEmpExamAttendanceList/semesterId/'+semesterId+'/examType/'+examType,HttpClient.MIME_TYPE_JSON,
                (response: any) => {
                    defer.resolve(response.entries);
                });
            return defer.promise;
        }
        public deleteEmpExamAttendanceInfo(json:Array<any>):ng.IPromise<any>{
            var defer = this.$q.defer();
            var that=this;
            console.log("Delete")
            console.log(json);
            this.httpClient.put("academic/empExamAttendance/deleteRecords",json,'application/json')
                .success(()=>{
                    this.notify.success("Successfully Saved");
                    defer.resolve('success')
                })
                .error((data)=>{
                    console.log(data);
                    this.notify.error("Problem in saving data");
                    defer.resolve('failure');
                });
            return defer.promise;

        }

    }
    UMS.service("employeeExamAttendanceService",EmployeeExamAttendanceService);
}