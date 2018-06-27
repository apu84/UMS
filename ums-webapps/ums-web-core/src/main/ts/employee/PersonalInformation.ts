module ums {
    class PersonalInformation {
        public static $inject = ['registrarConstants', '$q', 'notify',
            'countryService', 'divisionService', 'districtService', 'thanaService',
            'employeeInformationService', 'areaOfInterestService',
            '$stateParams', 'FileUpload'];

        private entry: {
            general: IGeneralInformationModel,
            contact: IContactInformationModel,
            emergencyContact: IEmergencyContactInformationModel
        };
        private generalReadOnly: boolean = true;
        private contactReadOnly: boolean = true;
        private emergencyContactReadOnly: boolean = true;
        private presentRequired: boolean = false;
        private permanentRequired: boolean = false;
        private disablePresentAddressDropdown: boolean = false;
        private disablePermanentAddressDropdown: boolean = false;
        private checkBoxValue: boolean;
        private genders: IGender[] = [];
        private maritalStatus: ICommon[] = [];
        private religions: ICommon[] = [];
        private nationalities: ICommon[] = [];
        private bloodGroups: ICommon[] = [];
        private publicationTypes: ICommon[] = [];
        private relations: ICommon[] = [];
        private countries: ICommon[] = [];
        private divisions: ICommon[] = [];
        private presentAddressDistricts: ICommon[] = [];
        private permanentAddressDistricts: ICommon[] = [];
        private allDistricts: ICommon[] = [];
        private presentAddressThanas: ICommon[] = [];
        private permanentAddressThanas: ICommon[] = [];
        private allThanas: ICommon[] = [];
        private copyOfGeneralInformation: IGeneralInformationModel = <IGeneralInformationModel> {};
        private copyOfContactInformation: IContactInformationModel = <IContactInformationModel> {};
        private copyOfEmergencyContactInformation: IEmergencyContactInformationModel = <IEmergencyContactInformationModel> {};
        private userId: string = "";
        private stateParams: any;
        private enableEdit: boolean;
        private enableEditButton: boolean = false;
        private test: boolean = true;
        private showLoader: boolean = true;

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private countryService: CountryService,
                    private divisionService: DivisionService,
                    private districtService: DistrictService,
                    private thanaService: ThanaService,
                    private employeeInformationService: EmployeeInformationService,
                    private areaOfInterestService: AreaOfInterestService,
                    private $stateParams: any,
                    private FileUpload: FileUpload) {

            this.entry = {
                general: <IGeneralInformationModel> {},
                contact: <IContactInformationModel> {},
                emergencyContact: <IEmergencyContactInformationModel> {}
            };

            this.stateParams = $stateParams;
            this.enableEditButton = this.stateParams.edit;
            this.genders = this.registrarConstants.genderTypes;
            this.publicationTypes = this.registrarConstants.publicationTypes;
            this.bloodGroups = this.registrarConstants.bloodGroupTypes;
            this.maritalStatus = this.registrarConstants.maritalStatuses;
            this.religions = this.registrarConstants.religionTypes;
            this.relations = this.registrarConstants.relationTypes;
            this.nationalities = this.registrarConstants.nationalityTypes;
            this.userId = this.stateParams.id;
            this.initialization();
        }

        private initialization() {
            this.countryService.getAll().then((countries: any) => {
                this.countries = countries;
                this.divisionService.getAll().then((divisions: any) => {
                    this.divisions = divisions;
                    this.districtService.getAll().then((districts: any) => {
                        this.presentAddressDistricts = districts;
                        this.permanentAddressDistricts = districts;
                        this.allDistricts = districts;
                        this.thanaService.getAll().then((thanas: any) => {
                            this.presentAddressThanas = thanas;
                            this.permanentAddressThanas = thanas;
                            this.allThanas = thanas;
                            this.getPersonalInformation();
                        });
                    });
                });
            });
        }


        private submit(form: string): void {
            if (form === 'general') {
                this.entry.general.employeeId = this.userId;
                this.entry.general.type = "general";
                this.submitGeneralForm();
            }
            else if (form === 'contact') {
                this.entry.contact.employeeId = this.userId;
                this.entry.contact.type = "contact";
                this.submitContactForm();
            }
            else if (form === 'emergencyContact') {
                this.entry.emergencyContact.employeeId = this.userId;
                this.entry.emergencyContact.type = "emergencyContact";
                this.submitEmergencyContactForm();
            }
            else {
                this.notify.error("Submit is not working. Please Contact to IUMS.");
            }
        }


        private submitGeneralForm(): void {
            this.convertToJson('general', this.entry.general)
                .then((json: any) => {
                    this.employeeInformationService.updatePersonalInformation(json)
                        .then((message: any) => {
                            this.getGeneralInformation();
                            this.generalReadOnly = true;
                        });
                });
        }


        private submitContactForm(): void {

            this.convertToJson('contact', this.entry.contact)
                .then((json: any) => {
                    this.employeeInformationService.updatePersonalInformation(json)
                        .then((message: any) => {
                            this.getContactInformation();
                            this.contactReadOnly = true;
                        });
                });

        }


        private submitEmergencyContactForm(): void {
            this.convertToJson('emergencyContact', this.entry.emergencyContact)
                .then((json: any) => {
                    this.employeeInformationService.updatePersonalInformation(json)
                        .then((message: any) => {
                            this.getEmergencyContactInformation();
                            this.emergencyContactReadOnly = true;
                        });
                });

        }


        private getPersonalInformation() {
            this.showLoader = true;
            this.employeeInformationService.getPersonalInformation(this.userId)
                .then((data: any) => {
                    this.initializePersonalObjects('all');
                    this.entry.general = data.general;
                    this.entry.contact = data.contact;
                    this.entry.emergencyContact = data.emergencyContact;
                })
                .then(() => {
                    this.copyOfGeneralInformation = angular.copy(this.entry.general);
                    this.copyOfContactInformation = angular.copy(this.entry.contact);
                    this.copyOfEmergencyContactInformation = angular.copy(this.entry.emergencyContact);
                    this.showLoader = false;
                });
        }

        private getGeneralInformation() {
            this.employeeInformationService.getPersonalInformation(this.userId).then((data: any) => {
                this.initializePersonalObjects('general');
                this.entry.general = data.general;
            }).then(() => {
                this.copyOfGeneralInformation = angular.copy(this.entry.general);
            });
        }

        private getContactInformation() {
            this.employeeInformationService.getPersonalInformation(this.userId).then((data: any) => {
                this.initializePersonalObjects('contact');
                this.entry.contact = data.contact;

            }).then(() => {
                this.copyOfContactInformation = angular.copy(this.entry.contact);
            });
        }

        private getEmergencyContactInformation() {
            this.employeeInformationService.getPersonalInformation(this.userId).then((data: any) => {
                this.initializePersonalObjects('emergencyContact');
                this.entry.emergencyContact = data.emergencyContact;
            }).then(() => {
                this.copyOfEmergencyContactInformation = angular.copy(this.entry.emergencyContact);
            });
        }

        private initializePersonalObjects(type: string): void {
            if (type === 'general') {
                this.entry.general = <IGeneralInformationModel>{};
                this.copyOfGeneralInformation = <IGeneralInformationModel>{};
            }
            else if (type === 'contact') {
                this.entry.contact = <IContactInformationModel> {};
                this.copyOfContactInformation = <IContactInformationModel> {};
            }
            else if (type == 'emergencyContact') {
                this.entry.emergencyContact = <IEmergencyContactInformationModel> {};
                this.copyOfEmergencyContactInformation = <IEmergencyContactInformationModel> {};
            }
            else {
                this.entry.general = <IGeneralInformationModel>{};
                this.entry.contact = <IContactInformationModel> {};
                this.entry.emergencyContact = <IEmergencyContactInformationModel> {};

                this.copyOfGeneralInformation = <IGeneralInformationModel>{};
                this.copyOfContactInformation = <IContactInformationModel> {};
                this.copyOfEmergencyContactInformation = <IEmergencyContactInformationModel> {};
            }
        }

        private setJsonObject(objType: string, obj: any): void {
            if (objType === 'general') {
                this.entry.general = obj;
            }
            else if (objType === 'contact') {
                this.entry.contact = obj;
            }
            else if (objType === 'emergencyContact') {
                this.entry.emergencyContact = obj;
            }
        }

        private changePresentAddressDistrict() {
            this.presentAddressDistricts = [];
            let districtLength = this.allDistricts.length;
            let index = 0;
            for (let i = 0; i < districtLength; i++) {
                if (this.entry.contact.preAddressDivision.id === this.allDistricts[i].foreign_id) {
                    this.presentAddressDistricts[index++] = this.allDistricts[i];
                }
            }
        }

        private changePermanentAddressDistrict() {
            this.permanentAddressDistricts = [];
            let districtLength = this.allDistricts.length;
            let index = 0;
            for (let i = 0; i < districtLength; i++) {
                if (this.entry.contact.perAddressDivision.id === this.allDistricts[i].foreign_id) {
                    this.permanentAddressDistricts[index++] = this.allDistricts[i];
                }
            }
        }

        private changePresentAddressThana() {
            this.presentAddressThanas = [];
            let thanaLength = this.allThanas.length;
            let index = 0;
            for (let i = 0; i < thanaLength; i++) {
                if (this.entry.contact.preAddressDistrict.id === this.allThanas[i].foreign_id) {
                    this.presentAddressThanas[index++] = this.allThanas[i];
                }
            }
        }

        private changePermanentAddressThana() {
            this.permanentAddressThanas = [];
            let thanaLength = this.allThanas.length;
            let index = 0;
            for (let i = 0; i < thanaLength; i++) {
                if (this.entry.contact.perAddressDistrict.id === this.allThanas[i].foreign_id) {
                    this.permanentAddressThanas[index++] = this.allThanas[i];
                }
            }
        }

        private sameAsPresentAddress() {
            if (this.checkBoxValue) {
                this.entry.contact.perAddressLine1 = this.entry.contact.preAddressLine1;
                this.entry.contact.perAddressLine2 = this.entry.contact.preAddressLine2;
                this.entry.contact.perAddressCountry = this.entry.contact.preAddressCountry;
                this.entry.contact.perAddressDivision = this.entry.contact.preAddressDivision;
                this.entry.contact.perAddressDistrict = this.entry.contact.preAddressDistrict;
                this.entry.contact.perAddressThana = this.entry.contact.preAddressThana;
                this.entry.contact.perAddressPostCode = this.entry.contact.preAddressPostCode;
                this.changePermanentAddressFields();
            }
            else {
                this.entry.contact.perAddressLine1 = "";
                this.entry.contact.perAddressLine2 = "";
                this.entry.contact.perAddressCountry = null;
                this.entry.contact.perAddressDivision = null;
                this.entry.contact.perAddressDistrict = null;
                this.entry.contact.perAddressThana = null;
                this.entry.contact.perAddressPostCode = "";
            }
        }

        private changePresentAddressFields() {
            if (this.entry.contact.preAddressCountry.name === "Bangladesh") {
                this.presentRequired = true;
                this.disablePresentAddressDropdown = false;
                this.changePresentAddressDistrict();
                this.changePresentAddressThana();
            }
            else {
                this.presentRequired = false;
                this.disablePresentAddressDropdown = true;
                this.entry.contact.preAddressDivision = null;
                this.entry.contact.preAddressDistrict = null;
                this.entry.contact.preAddressThana = null;
                this.entry.contact.preAddressPostCode = "";
            }
        }

        private changePermanentAddressFields() {
            if (this.entry.contact.perAddressCountry.name === "Bangladesh") {
                this.permanentRequired = true;
                this.disablePermanentAddressDropdown = false;
                this.changePermanentAddressDistrict();
                this.changePermanentAddressThana();
            }
            else {
                this.permanentRequired = false;
                this.disablePermanentAddressDropdown = true;
                this.entry.contact.perAddressDivision = null;
                this.entry.contact.perAddressDistrict = null;
                this.entry.contact.perAddressThana = null;
                this.entry.contact.perAddressPostCode = "";
            }
        }

        private convertToJson(convertThis: string, obj: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = obj;
            defer.resolve(JsonObject);
            return defer.promise;
        }


        private uploadImage() {
            var id = this.userId;
            var photoContent: any = $("#userPhoto").contents();
            var image = photoContent.prevObject[0].files[0];
            this.getFormData(image, id).then((formData) => {
                this.FileUpload.uploadPhoto(formData).then(() => {
                    this.test = false;
                    var that = this;
                    setTimeout(() => {
                        that.test = true;
                    }, 500)
                });
            });
        }

        private getFormData(file, id): ng.IPromise<any> {
            var formData = new FormData();
            formData.append('files', file);
            formData.append('name', file.name);
            formData.append("id", id);
            var defer = this.$q.defer();
            defer.resolve(formData);
            return defer.promise;
        }

        private isObjectEmpty(obj: Object): boolean {
            for (let key in obj) {
                if (obj.hasOwnProperty(key))
                    return false;
            }
            return true;
        }
    }

    UMS.controller("PersonalInformation", PersonalInformation);
}