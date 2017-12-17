module ums{
    export class EmployeeInformationService {
        public static $inject = ['HttpClient', '$q', 'notify'];

        private personalUrl: string = "employee/personal";
        private academicUrl: string = "employee/academic";
        private publicationUrl: string = "employee/publication";
        private trainingUrl: string = "employee/training";
        private awardUrl: string = "employee/award";
        private experienceUrl: string = "employee/experience";
        private additionalUrl: string = "employee/additional";
        private serviceUrl: string = "employee/service";

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }

        //PersonalInformationService
        public savePersonalInformation(json: any): ng.IPromise<any> {
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
        }

        public updatePersonalInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.put(this.personalUrl + "/update", json, HttpClient.MIME_TYPE_JSON)
                .success(() => {
                    this.notify.success("Update Successful");
                    defer.resolve();
                })
                .error((data) => {
                    this.notify.error("Error in Updating");
                });
            return defer.promise;
        }

        public getPersonalInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.personalUrl + "/get/employeeId/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in fetching Personal Information");
                });
            return defer.promise;
        }


        //AcademicInformationService//
        public saveAcademicInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.academicUrl + "/save", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                })
                .error((data) => {
                    this.notify.error("Error in Saving");
                    defer.resolve("Error");
                });
            return defer.promise;
        }

        public getAcademicInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.academicUrl + "/get/employeeId/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Academic Information");
                });
            return defer.promise;
        }


        //publicationInformationService
        public savePublicationInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.publicationUrl + "/save", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                })
                .error((data) => {
                    this.notify.error("Error in Saving");
                    defer.resolve("Error");
                });
            return defer.promise;
        }

        public getPublicationInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.publicationUrl + "/get/employeeId/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Publication Information");
                });
            return defer.promise;
        }

        public getPublicationInformationWithPagination(employeeId: string, status: string, pageNumber: number, itemPerPage: number): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.publicationUrl + "/get/employeeId/" + employeeId + "/publicationStatus/" + status + "/pageNumber/" + pageNumber + "/ipp/" + itemPerPage, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Publication Information");
                });
            return defer.promise;
        }

        public getPublicationInformationViewWithPagination(employeeId: string, pageNumber: number, itemPerPage: number): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.publicationUrl + "/get/employeeId/" + employeeId + "/pageNumber/" + pageNumber + "/ipp/" + itemPerPage, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Publication Information");
                });
            return defer.promise;
        }

        public getSpecificTeacherPublicationInformation(employeeId: string, status: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.publicationUrl + "/get/" + employeeId + "/" + status, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }

        public fetchRecords(employeeId: string, status: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.publicationUrl + "/get/" + employeeId + "/" + status, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Publication Information");
                });
            return defer.promise;
        }


        //AwardInformationService
        public saveAwardInformation(json: any): ng.IPromise<any> {
            let url = this.awardUrl + "/save";
            let defer = this.$q.defer();
            this.httpClient.post(url, json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                })
                .error((data) => {
                    this.notify.error("Error in saving");
                    defer.resolve("Error");
                });
            return defer.promise;
        }

        public getAwardInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.awardUrl + "/get/employeeId/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Award Information");
                });
            return defer.promise;
        }


        //trainingInformationService
        public saveTrainingInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.trainingUrl + "/save", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                })
                .error((data) => {
                    this.notify.error("Error in Saving");
                    defer.resolve("Error");
                });
            return defer.promise;
        }

        public getTrainingInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.trainingUrl + "/get/employeeId/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Training Information");
                });
            return defer.promise;
        }


        //ExperienceInformationService
        public saveExperienceInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.experienceUrl + "/save", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                })
                .error((data) => {
                    this.notify.error("Error in Saving");
                    defer.resolve("Error");
                });
            return defer.promise;
        }

        public getExperienceInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.experienceUrl + "/get/employeeId/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Experience Information");
                });
            return defer.promise;
        }


        //AdditionalInformationService
        public saveAdditionalInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.additionalUrl + "/save", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                })
                .error((data) => {
                    this.notify.error("Error in Saving");
                    defer.resolve("Error");
                });
            return defer.promise;
        }

        public getAdditionalInformation(employeeId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.additionalUrl + "/get/employeeId/" + employeeId, HttpClient.MIME_TYPE_JSON,
                (json: any, etag: string) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Additional Information");
                });
            return defer.promise;
        }


        //serviceInformationService
        public saveServiceInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.serviceUrl + "/save", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                })
                .error((data) => {
                    this.notify.error("Error in Saving");
                    defer.resolve("Error");
                });
            return defer.promise;
        }

        public getServiceInformation(userId: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.serviceUrl + "/get/employeeId/" + userId, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in Fetching Service Information");
                });
            return defer.promise;
        }

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
    }

    UMS.service("employeeInformationService", EmployeeInformationService);
}