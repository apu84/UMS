module ums{
    export class OptCourseOfferService{
        public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
        constructor(private appConstants: any, private httpClient: HttpClient,
                    private $q:ng.IQService, private notify: Notify,
                    private $sce:ng.ISCEService,private $window:ng.IWindowService) {

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