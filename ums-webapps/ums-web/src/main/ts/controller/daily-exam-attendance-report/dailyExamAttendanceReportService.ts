module ums{

    export interface IExamAttendantYearSemesterWiseData{
        year:number;
        semester:number;
        totalAttendantStudentNumber:number;
        programId:number;
        courseId:string;
        absentStudent:number;
        presentStudent:number;
    }

    export interface  IStudentsExamAttendantData{
        programName: string;
        examAttendantYearSemesterWiseDataList: IExamAttendantYearSemesterWiseData[];
    }

    export  class DailyExamAttendanceReportService{
        public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
        constructor(private appConstants: any, private httpClient: HttpClient,
                    private $q:ng.IQService, private notify: Notify,
                    private $sce:ng.ISCEService,private $window:ng.IWindowService) {

        }
        public getExamAttendantInfo(semesterId:number,examDate:string,examType:number):ng.IPromise<IStudentsExamAttendantData[]>{
            var defer:ng.IDeferred<IStudentsExamAttendantData[]> = this.$q.defer();
            this.httpClient.get('academic/studentsExamAttendantInfo/getExamAttendantInfo/semesterId/'+semesterId+'/examDate/'+examDate+'/examType/'+examType,'application/json',
                (response: IStudentsExamAttendantData[]) => {
                    defer.resolve(response);
                });
            return defer.promise;

        }
        public addStudentAttendantInfo(json:any):ng.IPromise<any>{
            var defer = this.$q.defer();
            var that=this;
            console.log("Inside-Service")
            console.log(json);
            this.httpClient.post("academic/studentsExamAttendantInfo/addStudentAttendantRecords",json,'application/json')
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
    UMS.service("DailyExamAttendanceReportService",DailyExamAttendanceReportService);
}