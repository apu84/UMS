module ums {

    class AdditionalInformation {
        public static $inject = [
            'registrarConstants',
            '$q',
            'notify',
            'additionalService',
            '$stateParams'];


        private additional: IAdditionalInformationModel = null;
        private aoi: IAreaOfInterestInformationModel[] = [];
        readonly userId: string = "";
        private enableEdit: boolean = false;
        private enableAoiEdit: boolean = false;
        private enableEditButton: boolean = false;
        private stateParams: any;
        private showLoader: boolean = false;

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private additionalService: AdditionalService,
                    private $stateParams: any) {

            this.additional = <IAdditionalInformationModel>{};
            this.aoi = [];
            this.stateParams = $stateParams;
            this.userId = this.stateParams.id;
            this.enableEditButton = this.stateParams.edit;
            this.get('additional');
            this.get('aoi');
        }

        public get(type: string): void {
            this.showLoader = true;
            if (type === 'additional') {
                this.additionalService.getAdditionalInformation(this.userId).then((additionalInformation: any) => {
                    if (additionalInformation) {
                        this.additional = additionalInformation;
                    }
                    else {
                        this.additional = <IAdditionalInformationModel>{};
                    }
                    this.showLoader = false;
                });
            }
            else if (type === 'aoi') {
                this.additionalService.getAoiInformation(this.userId).then((aoiInformation: any) => {
                    if (aoiInformation) {
                        this.aoi = aoiInformation;
                    }
                    else {
                        this.aoi = [];
                    }
                    this.showLoader = false;
                });
            }
        }


        private submit(type: string): void {
            if (type === 'additional') {
                this.additional.employeeId = this.stateParams.id;
                console.log(this.additional);
                this.convertToJson(this.additional).then((json: any) => {
                    this.additionalService.saveAdditionalInformation(json).then((data: any) => {
                        this.enableEdit = false;
                    }).catch((reason: any) => {
                        this.enableEdit = true;
                    });
                });
            }
            else if(type === 'aoi') {
                this.convertToJson(this.aoi).then((json: any) => {
                    this.additionalService.saveAoiInformation(json).then((data: any) => {
                        this.enableAoiEdit = false;
                    }).catch((reason: any) => {
                        this.enableAoiEdit = true;
                    });
                });
            }
        }

        public activeEditButton(canEdit: boolean, type: string): void {
            if(type === 'additional'){
                this.enableEdit = canEdit;
            }
            else if(type === 'aoi'){
                this.enableAoiEdit = canEdit;
            }
        }

        public delete(index: number): void{
            this.aoi.splice(index, 1);
        }

        public addNew(): void{
            let aoiEntry: IAreaOfInterestInformationModel;
            aoiEntry = {
                employeeId: this.userId,
                areaOfInterest: ""
            };
            this.aoi.push(aoiEntry);
        }

        private convertToJson(object: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = object;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("AdditionalInformation", AdditionalInformation);
}