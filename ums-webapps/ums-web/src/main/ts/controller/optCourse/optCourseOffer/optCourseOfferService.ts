module ums{
   export interface IOptCourseList{
        groupId:number;
        groupName:string;
        courses:IOptCourse[];
        subGrpCourses:IOptOfferedCourse[];
    }
    export interface IOptCourse {
        id: string;
        index: number;
        courseId:string;
        no: string;
        courseNo:string;
        title: string;
        crHr:any;
        courseType:any;
        courseTitle:string;
        year: number;
        semester: number;
        pairCourseId:string;
        statusId:number;
    }
    export interface IOptOfferedCourse {
        subGroupId:number;
        subGroupName:string;
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
    }
    UMS.service("optCourseOfferService",OptCourseOfferService);
}