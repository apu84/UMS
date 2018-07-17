module  ums{
    export interface IQuestionCorrectionInfo{
        programId:number;
        programName:string;
        year:number;
        semester:number;
        courseId:string;
        courseNo:string;
        courseTitle:string;
        employeeName:string;
        examDate:string;
        examType:number;
        apply:boolean;
    }
    export class QuestionCorrectionInfoService{
        public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
        constructor(private appConstants: any, private httpClient: HttpClient,
                    private $q:ng.IQService, private notify: Notify,
                    private $sce:ng.ISCEService,private $window:ng.IWindowService) {

        }
        public addQuestionCorrectionInfo(json:any):ng.IPromise<any>{
            var defer = this.$q.defer();
            console.log("Inside-Service")
            console.log(json);
            this.httpClient.post("academic/questionCorrectionInfo/addRecords",json,'application/json')
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
        public getQuestionCorrectionInfo(semesterId:number,examType:number):ng.IPromise<any>{
            var defer = this.$q.defer();
            this.httpClient.get('academic/questionCorrectionInfo/getQuestionCorrectionInfoList/semesterId/'+semesterId+'/examType/'+examType,HttpClient.MIME_TYPE_JSON,
                (response: any) => {
                    defer.resolve(response.entries);
                });
            return defer.promise;
        }
        public getCourses(programId:number,year:number,semester:number):ng.IPromise<any>{
            var defer = this.$q.defer();
            this.httpClient.get('academic/questionCorrectionInfo/getCourses/programId/'+programId+'/year/'+year+'/semester/'+semester,HttpClient.MIME_TYPE_JSON,
                (response: any) => {
                    defer.resolve(response.entries);
                });
            return defer.promise;
        }
        public getTeachers(courseId:number):ng.IPromise<any>{
            var defer = this.$q.defer();
            this.httpClient.get('academic/questionCorrectionInfo/getTeachers/courseId/'+courseId,HttpClient.MIME_TYPE_JSON,
                (response: any) => {
                    defer.resolve(response.entries);
                });
            return defer.promise;
        }
        public deleteQuestionCorrectionInfo(json:Array<any>):ng.IPromise<any>{
            var defer = this.$q.defer();
            var that=this;
            console.log("Delete")
            console.log(json);
            this.httpClient.put("academic/questionCorrectionInfo/deleteRecords",json,'application/json')
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
    UMS.service("questionCorrectionInfoService",QuestionCorrectionInfoService)
}