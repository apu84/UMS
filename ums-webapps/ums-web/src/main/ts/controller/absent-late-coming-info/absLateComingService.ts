module  ums{
    export interface IAbsLateComeInfo{
        employeeId:string;
        employeeName:string;
        semesterId:number;
        examType:number;
        presentType:number;
        presentInfo:string;
        remarks:string;
        invigilatorRoomId:number;
        invigilatorRoomName:string;
        examDate:string;
        arrivalTime:string;
        deptId:string;
        deptName:string;
        employeeType:string;
        apply:boolean;
    }
    export class AbsLateComingService{
        public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
        constructor(private appConstants: any, private httpClient: HttpClient,
                    private $q:ng.IQService, private notify: Notify,
                    private $sce:ng.ISCEService,private $window:ng.IWindowService) {

        }
        public addAbsLateComingInfo(json:any):ng.IPromise<any>{
            var defer = this.$q.defer();
            console.log("Inside-Service")
            console.log(json);
            this.httpClient.post("academic/absLateComing/addRecords",json,'application/json')
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
        public getAbsLateComeInfo(semesterId:number,examType:number):ng.IPromise<any>{
            var defer = this.$q.defer();
            this.httpClient.get('academic/absLateComing/getAbsLateComeInfoList/semesterId/'+semesterId+'/examType/'+examType,HttpClient.MIME_TYPE_JSON,
                (response: any) => {
                    defer.resolve(response.entries);
                });
            return defer.promise;
        }
        public deleteAbsLateComeInfo(json:Array<any>):ng.IPromise<any>{
            var defer = this.$q.defer();
            var that=this;
            console.log("Delete")
            console.log(json);
            this.httpClient.put("academic/absLateComing/deleteRecords",json,'application/json')
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
   UMS.service("absLateComingService",AbsLateComingService)
}