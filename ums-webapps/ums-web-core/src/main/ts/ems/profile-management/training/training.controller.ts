module ums {
    class TrainingInformation {

        public static $inject = [
            'registrarConstants',
            '$q',
            'notify',
            'trainingService',
            '$stateParams'];

        private training: ITrainingInformationModel[] = [];
        private trainingTypes: ICommon[] = [];
        private userId: string = "";
        private stateParams: any;
        private enableEdit: boolean[] = [false];
        private enableEditButton: boolean = false;
        private showLoader: boolean = false;

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private trainingService: TrainingService,
                    private $stateParams: any) {


            this.training = [];
            this.trainingTypes = registrarConstants.trainingCategories;
            this.stateParams = $stateParams;
            this.userId = this.stateParams.id;
            this.enableEditButton = this.stateParams.edit;
            this.get();
        }

        public submit(index: number): void {
            this.convertToJson(this.training[index]).then((json: any) => {
                if (!this.training[index].id) {
                    this.create(json, index);
                }
                else {
                    this.update(json, index);
                }
            });
        }

        private create(json: any, index: number) {
            this.trainingService.saveTrainingInformation(json).then((data: any) => {
                this.training[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }

        private update(json: any, index: number) {
            this.trainingService.updateTrainingInformation(json).then((data: any) => {
                this.training[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }


        private get(): void {
            this.showLoader = true;
            this.training = [];
            this.trainingService.getTrainingInformation(this.userId).then((trainingData: any) => {
                if (trainingData) {
                    this.training = trainingData;
                }
                else {
                    this.training = [];
                }
                this.showLoader = false;
            });
        }

        private addNew(): void {
            let trainingEntry: ITrainingInformationModel;
            trainingEntry = {
                id: "",
                employeeId: this.userId,
                trainingName: "",
                trainingInstitution: "",
                trainingFrom: "",
                trainingTo: "",
                trainingDuration: null,
                trainingDurationString: "",
                trainingCategory: null
            };
            this.training.push(trainingEntry);
        }

        public delete(index: number): void {
            if (this.training[index].id) {
                this.trainingService.deleteTrainingInformation(this.training[index].id).then((data: any) => {
                    this.training.splice(index, 1);
                });
            }
            else {
                this.training.splice(index, 1);
            }
        }

        public activeEditButton(index: number, canEdit: boolean): void{
            this.enableEdit[index] = canEdit;
        }

        private convertToJson(object: ITrainingInformationModel): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = object;
            defer.resolve(JsonObject);
            return defer.promise;
        }

        private calculateDifference(form: ng.IFormController, index: number, FromDate: string, ToDate: string): void {
            if(Utils.validateDateInput(FromDate, ToDate, 1)) {
                this.training[index].trainingDuration = Utils.getDateDiffInDays(FromDate, ToDate, 1);
                this.training[index].trainingDurationString = Utils.getFormattedDateDiff(FromDate, ToDate, 1);
                form.$invalid = false;
            }
            else{
                this.notify.error("From date is greater than to date");
                form.$invalid = true;
            }
        }
    }

    UMS.controller("TrainingInformation", TrainingInformation);
}