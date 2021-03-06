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


        private calculateDifference(form: ng.IFormController, index: number, FromDate: string, ToDate: string): void {
            if(Utils.validateDateInput(FromDate, ToDate, 1)) {
                this.experience[index].experienceDuration = Utils.getDateDiffInDays(FromDate, ToDate, 1);
                this.experience[index].experienceDurationString = Utils.getFormattedDateDiff(FromDate, ToDate, 1);
                form.$invalid = false;
            }
            else{
                if(this.experience[index].experienceTo) {
                    this.notify.error("From date is greater than to date");
                }
                form.$invalid = true;
            }
        }

    }

    UMS.controller("ExperienceInformation", ExperienceInformation);
}