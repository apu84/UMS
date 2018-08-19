module ums{
    export class EmployeeInformationService {
        public static $inject = ['HttpClient', '$q', 'notify'];



        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }

        //PersonalInformationService
/*        public savePersonalInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.personalUrl + "/save", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                })
                .error((data) => {
                    this.notify.error("Error in Saving");
                    defer.resolve("Error");
                });
            return defer.promise;
        }*/




        //AcademicInformationService//



        //publicationInformationService



        //AwardInformationService



        //trainingInformationService



        //ExperienceInformationService


        //AdditionalInformationService




        //serviceInformationService


        public getEmployeeCV(employeeId: string): void {
            let contentType: string = UmsUtil.getFileContentType("pdf");
            let fileName = "CV of " + employeeId;

            this.httpClient.get("employee/report/cv/employeeId/" + employeeId, 'application/pdf', (data: any, etag: string) => {

                    // var file = new Blob([data], {type: 'application/pdf'});
                    // var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
                    // this.$window.open(fileURL);

                    UmsUtil.writeFileContent(data, contentType, fileName);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                }, 'arraybuffer');
        }

        public getEmployeeListPdf(deptList: string[], employeeTypeList: number[], choice: number): void {
            let contentType: string = UmsUtil.getFileContentType("pdf");
            let fileName = "List of Employees";

            this.httpClient.get("academic/employee/report/employeeList/deptList/" + deptList + "/empTypeList/" + employeeTypeList + "/choice/" + choice, 'application/pdf', (data: any, etag: string) => {

                    // var file = new Blob([data], {type: 'application/pdf'});
                    // var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
                    // this.$window.open(fileURL);

                    UmsUtil.writeFileContent(data, contentType, fileName);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                }, 'arraybuffer');
        }
    }

    UMS.service("employeeInformationService", EmployeeInformationService);
}