module ums{
    export class OptStudentCourseSelectionService{
        public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
        constructor(private appConstants: any, private httpClient: HttpClient,
                    private $q:ng.IQService, private notify: Notify,
                    private $sce:ng.ISCEService,private $window:ng.IWindowService) {

        }
        public getOfferedCourses(semesterId:number,programId:number,year:number,semester:number):ng.IPromise<any> {
            var defer = this.$q.defer();
            this.httpClient.get('academic/optOfferedGroup/getOfferedCourses/semesterId/'+semesterId+'/program/'+programId+'/year/'+year+'/semester/'+semester,'application/json',
                (response:any[]) => {
                    defer.resolve(response);
                },
                (response:ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
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
    UMS.service("optStudentCourseSelectionService",OptStudentCourseSelectionService);
}