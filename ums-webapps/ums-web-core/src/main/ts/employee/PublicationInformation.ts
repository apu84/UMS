module ums {

    class PublicationInformation {

        public static $inject = [
            'registrarConstants',
            '$q',
            'notify',
            'countryService',
            'employeeInformationService',
            '$stateParams'];


        private publication: IPublicationInformationModel[] = [];
        private publicationTypes: ICommon[] = [];
        private countries: ICommon[] = [];
        readonly userId: string = "";
        private stateParams: any;
        private enableEdit: boolean[] = [false];
        private enableEditButton: boolean = false;

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private countryService: CountryService,
                    private employeeInformationService: EmployeeInformationService,
                    private $stateParams: any) {

            this.publication = [];
            this.stateParams = $stateParams;
            this.publicationTypes = this.registrarConstants.publicationTypes;
            this.userId = this.stateParams.id;
            this.enableEditButton = this.stateParams.edit;
            this.countryService.getAll().then((countries: any) => {
                this.countries = countries;
                this.get();
            });
        }

        public submit(index: number): void {
            this.convertToJson(this.publication[index]).then((json: any) => {
                if (!this.publication[index].id) {
                    this.create(json, index);
                }
                else {
                    this.update(json, index);
                }
            });
        }


        private create(json: any, index: number) {
            this.employeeInformationService.savePublicationInformation(json).then((data: any) => {
                this.publication[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }

        private update(json: any, index: number) {
            this.employeeInformationService.updatePublicationInformation(json).then((data: any) => {
                this.publication[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }

        private get(): void {
            this.publication = [];
            this.employeeInformationService.getPublicationInformation(this.userId).then((academicInformation: any) => {
                if (academicInformation) {
                    this.publication = academicInformation;
                }
                else {
                    this.publication = [];
                }
            });
        }

        public delete(index: number): void {
            if (this.publication[index].id) {
                this.employeeInformationService.deletePublicationInformation(this.publication[index].id).then((data: any) => {
                    this.publication.splice(index, 1);
                });
            }
            else {
                this.publication.splice(index, 1);
            }
        }

        public activeEditButton(index: number, canEdit: boolean): void{
            this.enableEdit[index] = canEdit;
        }

        public addNew(): void {
            let publicationEntry: IPublicationInformationModel;
            publicationEntry = {
                id: "",
                employeeId: this.userId,
                publicationTitle: "",
                publicationType: null,
                publicationInterestGenre: "",
                publicationWebLink: "",
                publisherName: "",
                dateOfPublication: null,
                publicationISSN: "",
                publicationIssue: "",
                publicationVolume: "",
                publicationJournalName: "",
                publicationCountry: null,
                status: "0",
                publicationPages: "",
                appliedOn: "",
                actionTakenOn: "",
                rowNumber: null
            };
            this.publication.push(publicationEntry);
        }

        private convertToJson(object: IPublicationInformationModel): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = object;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("PublicationInformation", PublicationInformation);
}