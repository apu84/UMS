module ums{
   export interface IOptCourseList{
        groupId:number;
        groupName:string;
        programId:number;
        year:number;
        semester:number;
        totalApplied:number;
       totalSeats:number;
        isMandatory:boolean;
        courses:IOptCourse[];
        subGrpCourses:IOptOfferedCourse[];
    }
    export interface IOptCourse {
        id: string;
        no: string;
        title: string;
        crHr:any;
        courseType:any;
        year: number;
        semester: number;
        pairCourseId:string;
        statusId:number;
    }
    export interface IOptOfferedCourse {
        subGroupId:number;
        subGroupName:string;
        totalApplied:number;
        totalSeats:number;
        courses:IOptCourse[];
    }
    export class OptCourseOfferService{
        public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
        constructor(private appConstants: any, private httpClient: HttpClient,
                    private $q:ng.IQService, private notify: Notify,
                    private $sce:ng.ISCEService,private $window:ng.IWindowService) {

        }
        public getOptCourses(semesterId:number,programId:number,year:number,semester:number):ng.IPromise<any> {
            var defer = this.$q.defer();
            this.httpClient.get('academic/course/optional/semester-id/'+semesterId+'/program/'+programId+'/year/'+year+'/semester/'+semester,'application/json',
                (response:any[]) => {
                    defer.resolve(response);
                    console.log("Response");
                    console.log(response);
                },
                (response:ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }
        public getOfferEligibleCourses(semesterId:number,examType:number):ng.IPromise<any>{
            var defer = this.$q.defer();
            this.httpClient.get('academic/empExamAttendance/getEmpExamAttendanceList/semesterId/'+semesterId+'/examType/'+examType,HttpClient.MIME_TYPE_JSON,
                (response: any) => {
                    defer.resolve(response.entries);
                });
            return defer.promise;
        }

        public addInfo(programId:number,year:number,semester:number,json:any):ng.IPromise<any>{
            var defer = this.$q.defer();
            console.log("Inside-Service");
            console.log(json);
            this.httpClient.post('academic/optOfferedGroup/addRecords/programId/'+programId+'/year/'+year+'/semester/'+semester,json,'application/json')
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
    UMS.service("optCourseOfferService",OptCourseOfferService);
}