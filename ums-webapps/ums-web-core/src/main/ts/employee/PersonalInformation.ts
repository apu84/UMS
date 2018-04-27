module ums {
    interface IEmployeeProfile extends ng.IScope {
        submitPersonalForm: Function;
    }

    class PersonalInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify',
            'countryService', 'divisionService', 'districtService', 'thanaService',
            'employeeInformationService', 'areaOfInterestService', 'userService', 'academicDegreeService',
            'cRUDDetectionService', '$stateParams', 'employmentTypeService', 'departmentService', 'designationService', 'FileUpload'];

        private entry: {
            personal: IPersonalInformationModel
        };
        private personal: boolean = false;
        private showPersonalInputDiv: boolean = false;
        private presentRequired: boolean = false;
        private permanentRequired: boolean = false;
        private disablePresentAddressDropdown: boolean = false;
        private disablePermanentAddressDropdown: boolean = false;
        private checkBoxValue: boolean;
        private data: any;
        private genders: IGender[] = [];
        private maritalStatus: ICommon[] = [];
        private religions: ICommon[] = [];
        private nationalities: ICommon[] = [];
        private degreeNames: IAcademicDegreeTypes[] = [];
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
        private previousPersonalInformation: IPersonalInformationModel;
        private userId: string = "";
        private tabs: boolean = false;
        private stateParams: any;
        private isRegistrar: boolean;
        private test: boolean = true;

        constructor(private registrarConstants: any,
                    private $scope: IEmployeeProfile,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private countryService: CountryService,
                    private divisionService: DivisionService,
                    private districtService: DistrictService,
                    private thanaService: ThanaService,
                    private employeeInformationService: EmployeeInformationService,
                    private areaOfInterestService: AreaOfInterestService,
                    private userService: UserService,
                    private academicDegreeService: AcademicDegreeService,
                    private cRUDDetectionService: CRUDDetectionService,
                    private $stateParams: any,
                    private employmentTypeService: EmploymentTypeService,
                    private departmentService: DepartmentService,
                    private designationService: DesignationService,
                    private FileUpload: FileUpload) {

            console.log("In Personal Information ----------");

            $scope.submitPersonalForm = this.submitPersonalForm.bind(this);

            this.entry = {
                personal: <IPersonalInformationModel> {}
            };

            this.data = {
                supOptions: "1",
                borderColor: ""
            };
            this.stateParams = $stateParams;
            this.genders = this.registrarConstants.genderTypes;
            this.publicationTypes = this.registrarConstants.publicationTypes;
            this.bloodGroups = this.registrarConstants.bloodGroupTypes;
            this.maritalStatus = this.registrarConstants.maritalStatuses;
            this.religions = this.registrarConstants.religionTypes;
            this.relations = this.registrarConstants.relationTypes;
            this.nationalities = this.registrarConstants.nationalityTypes;
            this.initialization();
        }

        private initialization() {
            this.userService.fetchCurrentUserInfo().then((user: any) => {
                if(user.roleId == 82 || user.roleId == 81){
                    this.isRegistrar = true;
                }
                else{
                    this.isRegistrar = false;
                }
                if (this.stateParams.id1 == "" || this.stateParams.id1 == null || this.stateParams.id1 == undefined) {
                    console.log("user.employeeId --------- ");
                    console.log(user.employeeId);
                    this.userId = user.employeeId;
                }
                else {
                    this.userId = this.stateParams.id;
                }
                this.countryService.getCountryList().then((countries: any) => {
                    this.countries = countries.entries;
                    this.divisionService.getDivisionList().then((divisions: any) => {
                        this.divisions = divisions.entries;
                        this.districtService.getDistrictList().then((districts: any) => {
                            this.presentAddressDistricts = districts.entries;
                            this.permanentAddressDistricts = districts.entries;
                            this.allDistricts = districts.entries;
                            this.thanaService.getThanaList().then((thanas: any) => {
                                this.presentAddressThanas = thanas.entries;
                                this.permanentAddressThanas = thanas.entries;
                                this.allThanas = thanas.entries;
                                this.academicDegreeService.getAcademicDegreeList().then((degree: any) => {
                                    this.degreeNames = degree;
                                    this.areaOfInterestService.getAll().then((aois: any) => {
                                        this.tabs = true;
                                        this.getPersonalInformation();
                                    });
                                });
                            });
                        });
                    });
                });
            });
        }


        private submitPersonalForm(): void {
            this.entry.personal.employeeId = this.userId;
            if (this.cRUDDetectionService.isObjectEmpty(this.previousPersonalInformation)) {
                this.convertToJson('personal', this.entry.personal)
                    .then((json: any) => {
                        this.employeeInformationService.savePersonalInformation(json)
                            .then((message: any) => {
                                if(message == "Error"){}
                                else {
                                    this.getPersonalInformation();
                                    this.showPersonalInputDiv = false;
                                }
                            });
                    });
            }
            else {
                this.convertToJson('personal', this.entry.personal)
                    .then((json: any) => {
                        this.employeeInformationService.updatePersonalInformation(json)
                            .then((message: any) => {
                                this.getPersonalInformation();
                                this.showPersonalInputDiv = false;
                            });
                    });
            }
        }


        private getPersonalInformation() {
            this.entry.personal = <IPersonalInformationModel>{};
            this.employeeInformationService.getPersonalInformation(this.userId).then((personalInformation: any) => {
                if (personalInformation.length > 0) {
                    this.entry.personal = personalInformation[0];
                }
            }).then(() => {
                this.previousPersonalInformation = angular.copy(this.entry.personal);
            });
        }

        private changePresentAddressDistrict() {
            this.presentAddressDistricts = [];
            let districtLength = this.allDistricts.length;
            let index = 0;
            for (let i = 0; i < districtLength; i++) {
                if (this.entry.personal.preAddressDivision.id === this.allDistricts[i].foreign_id) {
                    this.presentAddressDistricts[index++] = this.allDistricts[i];
                }
            }
        }

        private changePermanentAddressDistrict() {
            this.permanentAddressDistricts = [];
            let districtLength = this.allDistricts.length;
            let index = 0;
            for (let i = 0; i < districtLength; i++) {
                if (this.entry.personal.perAddressDivision.id === this.allDistricts[i].foreign_id) {
                    this.permanentAddressDistricts[index++] = this.allDistricts[i];
                }
            }
        }

        private changePresentAddressThana() {
            this.presentAddressThanas = [];
            let thanaLength = this.allThanas.length;
            let index = 0;
            for (let i = 0; i < thanaLength; i++) {
                if (this.entry.personal.preAddressDistrict.id === this.allThanas[i].foreign_id) {
                    this.presentAddressThanas[index++] = this.allThanas[i];
                }
            }
        }

        private changePermanentAddressThana() {
            this.permanentAddressThanas = [];
            let thanaLength = this.allThanas.length;
            let index = 0;
            for (let i = 0; i < thanaLength; i++) {
                if (this.entry.personal.perAddressDistrict.id === this.allThanas[i].foreign_id) {
                    this.permanentAddressThanas[index++] = this.allThanas[i];
                }
            }
        }

        private sameAsPresentAddress() {
            if(this.checkBoxValue) {
                this.entry.personal.perAddressLine1 = this.entry.personal.preAddressLine1;
                this.entry.personal.perAddressLine2 = this.entry.personal.preAddressLine2;
                this.entry.personal.perAddressCountry = this.entry.personal.preAddressCountry;
                this.entry.personal.perAddressDivision = this.entry.personal.preAddressDivision;
                this.entry.personal.perAddressDistrict = this.entry.personal.preAddressDistrict;
                this.entry.personal.perAddressThana = this.entry.personal.preAddressThana;
                this.entry.personal.perAddressPostCode = this.entry.personal.preAddressPostCode;
                this.changePermanentAddressFields();
            }
            else{
                this.entry.personal.perAddressLine1 = "";
                this.entry.personal.perAddressLine2 = "";
                this.entry.personal.perAddressCountry = null;
                this.entry.personal.perAddressDivision = null;
                this.entry.personal.perAddressDistrict = null;
                this.entry.personal.perAddressThana = null;
                this.entry.personal.perAddressPostCode = "";
            }
        }

        private changePresentAddressFields() {
            if (this.entry.personal.preAddressCountry.name === "Bangladesh") {
                this.presentRequired = true;
                this.disablePresentAddressDropdown = false;
                this.changePresentAddressDistrict();
                this.changePresentAddressThana();
            }
            else {
                this.presentRequired = false;
                this.disablePresentAddressDropdown = true;
                this.entry.personal.preAddressDivision = null;
                this.entry.personal.preAddressDistrict = null;
                this.entry.personal.preAddressThana = null;
                this.entry.personal.preAddressPostCode = "";
            }
        }

        private changePermanentAddressFields() {
            if (this.entry.personal.perAddressCountry.name === "Bangladesh") {
                this.permanentRequired = true;
                this.disablePermanentAddressDropdown = false;
                this.changePermanentAddressDistrict();
                this.changePermanentAddressThana();
            }
            else {
                this.permanentRequired = false;
                this.disablePermanentAddressDropdown = true;
                this.entry.personal.perAddressDivision = null;
                this.entry.personal.perAddressDistrict = null;
                this.entry.personal.perAddressThana = null;
                this.entry.personal.perAddressPostCode = "";
            }
        }

        private convertToJson(convertThis: string, obj: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
            if (convertThis === "personal") {
                item['personal'] = obj;
            }
            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;
            defer.resolve(JsonObject);
            return defer.promise;
        }


        private uploadImage() {
            var id = this.userId;
            var photoContent : any =$("#userPhoto").contents();
            var image = photoContent.prevObject[0].files[0];
            this.getFormData(image, id).then((formData) => {
                this.FileUpload.uploadPhoto(formData).then(() =>{
                    this.test = false;
                    var that = this;
                    setTimeout(()=> {
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
    }

    UMS.controller("PersonalInformation", PersonalInformation);
}