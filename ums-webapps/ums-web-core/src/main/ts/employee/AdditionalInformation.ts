module ums {

    class AdditionalInformation {
        public static $inject = [
            'registrarConstants',
            '$q',
            'notify',
            'employeeInformationService',
            'areaOfInterestService',
            '$stateParams'];


        private additional: IAdditionalInformationModel = null;
        private aoi: IAreaOfInterestInformationModel = null;
        private arrayOfAreaOfInterest: ICommon[] = [];
        readonly userId: string = "";
        private enableEdit: boolean[] = [false];
        private enableEditButton: boolean = false;
        private stateParams: any;

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private employeeInformationService: EmployeeInformationService,
                    private areaOfInterestService: AreaOfInterestService,
                    private $stateParams: any) {

            this.additional = <IAdditionalInformationModel>{};
            this.aoi = <IAreaOfInterestInformationModel>{};
            this.stateParams = $stateParams;
            this.userId = this.stateParams.id;
            this.enableEditButton = this.stateParams.edit;
            this.areaOfInterestService.getAll().then((aois: any) => {
                this.arrayOfAreaOfInterest = aois;
                this.get('additional');
                this.get('aoi');
            });
        }

        public get(type: string): void {
            if (type === 'additional') {
                this.employeeInformationService.getAdditionalInformation(this.userId).then((additionalInformation: any) => {
                    console.log(additionalInformation);
                    if (additionalInformation) {
                        this.additional = additionalInformation;
                    }
                    else {
                        this.additional = <IAdditionalInformationModel>{};
                    }
                });
            }
            else if (type === 'aoi') {
                this.employeeInformationService.getAoiInformation(this.userId).then((aoiInformation: any) => {
                    if (aoiInformation) {
                        console.log(aoiInformation);
                        this.aoi = aoiInformation;
                    }
                    else {
                        this.aoi = <IAreaOfInterestInformationModel>{};
                    }
                });
            }
        }


        private submit(type: string): void {
            if(type === 'additional') {
                this.convertToJson(this.additional).then((json: any) => {
                    this.employeeInformationService.saveAdditionalInformation(json).then((data: any) => {
                        this.enableEdit[type] = false;
                    }).catch((reason: any) => {
                        this.enableEdit[type] = true;
                    });
                });
            }
            else{
                this.convertToJson(this.aoi).then((json: any) => {
                    this.employeeInformationService.saveAoiInformation(json).then((data: any) => {
                        this.enableEdit[type] = false;
                    }).catch((reason: any) => {
                        this.enableEdit[type] = true;
                    });
                });
            }
        }

        public activeEditButton(canEdit: boolean, type: string): void {
            this.enableEdit[type] = canEdit;
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