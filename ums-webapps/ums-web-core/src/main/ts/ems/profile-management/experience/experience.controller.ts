module ums {

    class ExperienceInformation {

        public static $inject = ['registrarConstants',
            '$q',
            'notify',
            'experienceService',
            '$stateParams'
        ];

        private experience: IExperienceInformationModel[] = []
        private experienceTypes: ICommon[] = [];
        readonly userId: string = "";
        private stateParams: any;
        private enableEdit: boolean[] = [false];
        private enableEditButton: boolean = false;
        private showLoader: boolean = false;

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private experienceService: ExperienceService,
                    private $stateParams: any) {

            this.experience = [];
            this.stateParams = $stateParams;
            this.experienceTypes = registrarConstants.experienceCategories;
            this.userId = this.stateParams.id;
            this.enableEditButton = this.stateParams.edit;
            this.get();
        }

        public submit(index: number): void {
            this.convertToJson(this.experience[index]).then((json: any) => {
                if (!this.experience[index].id) {
                    this.create(json, index);
                }
                else {
                    this.update(json, index);
                }
            });
        }

        private create(json: any, index: number) {
            this.experienceService.saveExperienceInformation(json).then((data: any) => {
                this.experience[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }

        private update(json: any, index: number) {
            this.experienceService.updateExperienceInformation(json).then((data: any) => {
                this.experience[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }

        private get(): void {
            this.showLoader = true;
            this.experience = [];
            this.experienceService.getExperienceInformation(this.userId).then((experienceInformation: any) => {
                if (experienceInformation) {
                    this.experience = experienceInformation;
                }
                else {
                    this.experience = [];
                }
                this.showLoader = false;
            });
        }

        private addNew() {
            let experienceEntry: IExperienceInformationModel;
            experienceEntry = {
                id: "",
                employeeId: this.userId,
                experienceInstitution: "",
                experienceDesignation: "",
                experienceFrom: "",
                experienceTo: "",
                experienceDuration: null,
                experienceDurationString: "",
                experienceCategory: null
            };
            this.experience.push(experienceEntry);
        }

        public delete(index: number): void {
            if (this.experience[index].id) {
                this.experienceService.deleteExperienceInformation(this.experience[index].id).then((data: any) => {
                    this.experience.splice(index, 1);
                });
            }
            else {
                this.experience.splice(index, 1);
            }
        }

        public activeEditButton(index: number, canEdit: boolean): void {
            this.enableEdit[index] = canEdit;
        }

        private convertToJson(object: IExperienceInformationModel): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = object;
            defer.resolve(JsonObject);
            return defer.promise;
        }

        private calculateDifference(tabName: string, index: number, FromDate: string, ToDate: string): void {
            if (FromDate != "" && ToDate != "") {
                let formattedFromDate: string = moment(FromDate, "DD/MM/YYYY").format("MM/DD/YYYY");
                let fromDate: Date = new Date(formattedFromDate);
                let formattedToDate: string = moment(ToDate, "DD/MM/YYYY").format("MM/DD/YYYY");
                let toDate: Date = new Date(formattedToDate);
                if (fromDate > toDate) {
                    this.notify.error("From date is greater than to date");
                } else {
                    let diffDateString = "";
                    let year: number = 0;
                    let month: number = 0;
                    let day: number = 0;
                    let timeDiff: number = (toDate.getTime() - fromDate.getTime());
                    let diffDays: number = Math.ceil(timeDiff / (1000 * 3600 * 24));

                    if (diffDays >= 365) {
                        year = Math.floor(diffDays / 365);
                        month = Math.floor((diffDays % 365) / 30);
                        day = Math.floor((diffDays % 365) % 30);
                        diffDateString = year + " year(s) " + month + " month(s) " + day + " day(s)";
                    }
                    else if (diffDays >= 30) {
                        month = Math.floor((diffDays / 30));
                        day = Math.floor(diffDays % 30);
                        diffDateString = month + " month(s) " + day + " day(s)";
                    }
                    else {
                        diffDateString = diffDays + " day(s)";
                    }
                    if (tabName == "experience") {
                        this.experience[index].experienceDuration = diffDays;
                        this.experience[index].experienceDurationString = diffDateString;
                    }
                }
            }
        }
    }

    UMS.controller("ExperienceInformation", ExperienceInformation);
}