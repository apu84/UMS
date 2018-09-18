module ums{
    export class EmployeeSearchService {
        public static $inject = ['HttpClient', '$q', 'notify'];

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }

        public getEmployeeCV(employeeId: string): void {
            let contentType: string = UmsUtil.getFileContentType("pdf");
            let fileName = "CV of " + employeeId;

            this.httpClient.get("employee/report/cv/employeeId/" + employeeId, 'application/pdf', (data: any, etag: string) => {

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

                    UmsUtil.writeFileContent(data, contentType, fileName);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                }, 'arraybuffer');
        }
    }

    UMS.service("employeeSearchService", EmployeeSearchService);
}