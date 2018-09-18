module ums {

    class AwardInformation {

        public static $inject = [
            'registrarConstants',
            '$q',
            'notify',
            'awardService',
            '$stateParams'];

        private award: IAwardInformationModel[] = [];
        readonly userId: string = "";
        private stateParams: any;
        private enableEdit: boolean[] = [false];
        private enableEditButton: boolean = false;
        private showLoader: boolean = false;

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private awardService: AwardService,
                    private $stateParams: any) {


            this.award = [];
            this.stateParams = $stateParams;
            this.userId = this.stateParams.id;
            this.enableEditButton = this.stateParams.edit;
            this.get();
        }

        public submit(index: number): void {
            this.convertToJson(this.award[index]).then((json: any) => {
                if (!this.award[index].id) {
                    this.create(json, index);
                }
                else {
                    this.update(json, index);
                }
            });
        }


        private create(json: any, index: number) {
            this.awardService.saveAwardInformation(json).then((data: any) => {
                this.award[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }

        private update(json: any, index: number) {
            this.awardService.updateAwardInformation(json).then((data: any) => {
                this.award[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }

        private get() {
            this.showLoader = true;
            this.award = [];
            this.awardService.getAwardInformation(this.userId).then((awardInformation: any) => {
                if (awardInformation) {
                    this.award = awardInformation;
                }
                else {
                    this.award = [];
                }

                this.showLoader = false;
            });
        }


        private addNew() {
            let awardEntry: IAwardInformationModel;
            awardEntry = {
                id: "",
                employeeId: this.userId,
                awardName: "",
                awardInstitute: "",
                awardedYear: null,
                awardShortDescription: ""
            };
            this.award.push(awardEntry);
        }

        private delete(index: number) {
            if (this.award[index].id) {
                this.awardService.deleteAwardInformation(this.award[index].id).then((data: any) => {
                    this.award.splice(index, 1);
                });
            }
            else {
                this.award.splice(index, 1);
            }
        }

        public activeEditButton(index: number, canEdit: boolean): void {
            this.enableEdit[index] = canEdit;
        }


        private convertToJson(object: IAwardInformationModel): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = object;
            defer.resolve(JsonObject);
            return defer.promise;
        }

    }

    UMS.controller("AwardInformation", AwardInformation);
}