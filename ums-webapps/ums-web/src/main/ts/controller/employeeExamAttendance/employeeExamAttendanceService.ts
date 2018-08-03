module ums{
    export interface IEmployeeExamAttendance{
        id:number;
        semesterId:string
        examType:number;
        roomInCharge:number;
        invigilatorRoomId:number;
        invigilatorRoomName:string;
        employeeId:string;
        employee:Employee;
        employeeType:number;
        designationId:number;
        deptId:string;
        department:IDepartment;
        invigilatorDate:string;
        reserveDate:string;
        invigilatorDateForUpdate:string;
        reserveDateForUpdate:string;
        departmentShortName:string;

    }
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
                    defer.resolve('success')
                })
                .error((data)=>{
                    console.log(data);
                    this.notify.error("Problem in saving data");
                    defer.resolve('failure');
                });
            return defer.promise;
        }
        public updateEmpExamAttendanceInfo(json:any):ng.IPromise<any>{
            var defer = this.$q.defer();
            console.log("Inside-Service-add-info")
            console.log(json);
            this.httpClient.post("academic/empExamAttendance/updateRecords",json,'application/json')
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
                    defer.resolve('success')
                })
                .error((data)=>{
                    console.log(data);
                    this.notify.error("Problem in saving data");
                    defer.resolve('failure');
                });
            return defer.promise;

        }
        public getMemorandumReport(pSemesterId:number,pExamType:number){
            var defer = this.$q.defer();
            let contentType: string = UmsUtil.getFileContentType("pdf");
            let fileName="MEMORANDUM";
            this.httpClient.get('/ums-webservice-academic/academic/empExamAttendance/getMemorandumReport/semesterId/'+pSemesterId+'/examType/'+pExamType, 'application/pdf',
                (data: any, etag: string) => {
                    console.log("pdf");
                    UmsUtil.writeFileContent(data, contentType, fileName);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                }, 'arraybuffer');
            return defer.promise;
        }
        public getStaffAttendantReport(pSemesterId:number,pExamType:number,pExamDate:string){
            console.log("in");
            var defer = this.$q.defer();
            let contentType: string = UmsUtil.getFileContentType("pdf");
            let fileName="Staff Attendant Report";
            this.httpClient.get('/ums-webservice-academic/academic/empExamAttendance/getStaffAttendantReport/semesterId/'+pSemesterId+'/examType/'+pExamType+'/examDate/'+pExamDate, 'application/pdf',
                (data: any, etag: string) => {
                    console.log("pdf");
                    UmsUtil.writeFileContent(data, contentType, fileName);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                }, 'arraybuffer');
            return defer.promise;
        }
        public getEmployeeAttendantReport(pSemesterId:number,pExamType:number,pExamDate:string,pDeptId:string){
            console.log("in");
            var defer = this.$q.defer();
            let contentType: string = UmsUtil.getFileContentType("pdf");
            let fileName="Employee Attendant Report";
            this.httpClient.get('/ums-webservice-academic/academic/empExamAttendance/getEmployeeAttendantReport/semesterId/'+pSemesterId+'/examType/'+pExamType+'/examDate/'+pExamDate+'/deptId/'+pDeptId, 'application/pdf',
                (data: any, etag: string) => {
                    console.log("pdf");
                    UmsUtil.writeFileContent(data, contentType, fileName);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                }, 'arraybuffer');
            return defer.promise;
        }
        public getReserveEmployeeAttendantReport(pSemesterId:number,pExamType:number,pExamDate:string,pDeptId:string){
            console.log("in");
            var defer = this.$q.defer();
            let contentType: string = UmsUtil.getFileContentType("pdf");
            let fileName="Reserve Attendant Report";
            this.httpClient.get('/ums-webservice-academic/academic/empExamAttendance/getReserveEmployeeAttendantReport/semesterId/'+pSemesterId+'/examType/'+pExamType+'/examDate/'+pExamDate+'/deptId/'+pDeptId, 'application/pdf',
                (data: any, etag: string) => {
                    console.log("pdf");
                    UmsUtil.writeFileContent(data, contentType, fileName);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                }, 'arraybuffer');
            return defer.promise;
        }

    }
    UMS.service("employeeExamAttendanceService",EmployeeExamAttendanceService);
}