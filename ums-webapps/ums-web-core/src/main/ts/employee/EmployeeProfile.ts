module ums {
    interface IEmployeeProfile extends ng.IScope {
        submitPersonalForm: Function;
        submitAcademicForm: Function;
        submitPublicationForm: Function;
        submitTrainingForm: Function;
        submitAwardForm: Function;
        submitExperienceForm: Function;
        submitAdditionalForm: Function;
        submitServiceForm: Function;
    }

    class EmployeeProfile {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify',
            'countryService', 'divisionService', 'districtService', 'thanaService',
            'employeeInformationService', 'areaOfInterestService', 'userService', 'academicDegreeService',
            'cRUDDetectionService', '$stateParams', 'employmentTypeService', 'departmentService', 'designationService', 'FileUpload'];

        private entry: {
            personal: IPersonalInformationModel,
            academic: IAcademicInformationModel[],
            publication: IPublicationInformationModel[],
            training: ITrainingInformationModel[],
            award: IAwardInformationModel[],
            experience: IExperienceInformationModel[],
            additional: IAdditionalInformationModel,
            serviceInfo: IServiceInformationModel[]
        };
        private personal: boolean = false;
        private academic: boolean = false;
        private publication: boolean = false;
        private training: boolean = false;
        private award: boolean = false;
        private experience: boolean = false;
        private additional: boolean = false;
        private service: boolean = false;
        private showPersonalInputDiv: boolean = false;
        private showAcademicInputDiv: boolean = false;
        private showPublicationInputDiv: boolean = false;
        private showTrainingInputDiv: boolean = false;
        private showAwardInputDiv: boolean = false;
        private showExperienceInputDiv: boolean = false;
        private showAdditionalInputDiv: boolean = false;
        private showServiceInputDiv: boolean = false;
        private presentRequired: boolean = false;
        private permanentRequired: boolean = false;
        private disablePresentAddressDropdown: boolean = false;
        private disablePermanentAddressDropdown: boolean = false;
        private showServiceEditButton: boolean = false;
        private showAcademicEditButton: boolean = false;
        private showExperienceEditButton: boolean = false;
        private checkBoxValue: boolean;
        private data: any;
        private itemPerPage: number = 2;
        private currentPage: number = 1;
        private totalRecord: number = 0;
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
        private arrayOfAreaOfInterest: ICommon[] = [];
        private trainingTypes: ICommon[] = [];
        private experienceTypes: ICommon[] = [];
        private paginatedPublication: IPublicationInformationModel[];
        private previousPersonalInformation: IPersonalInformationModel;
        private previousAcademicInformation: IAcademicInformationModel[];
        private previousPublicationInformation: IPublicationInformationModel[];
        private previousTrainingInformation: ITrainingInformationModel[];
        private previousAwardInformation: IAwardInformationModel[];
        private previousExperienceInformation: IExperienceInformationModel[];
        private previousServiceInformation: IServiceInformationModel[];
        private academicDeletedObjects: IAcademicInformationModel[];
        private publicationDeletedObjects: IPublicationInformationModel[];
        private trainingDeletedObjects: ITrainingInformationModel[];
        private awardDeletedObjects: IAwardInformationModel[];
        private experienceDeletedObjects: IExperienceInformationModel[];
        private serviceDeletedObjects: IServiceInformationModel[];
        private serviceDetailDeletedObjects: IServiceDetailsModel[];
        private userId: string = "";
        private tabs: boolean = false;
        private stateParams: any;
        private designations: ICommon[] = [];
        private employmentTypes: ICommon[] = [];
        private serviceIntervals: ICommon[] = [];
        private serviceRegularIntervals: ICommon[] = [];
        private serviceContractIntervals: ICommon[] = [];
        private departments: IDepartment[] = [];
        private isRegistrar: boolean;

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

            $scope.submitPersonalForm = this.submitPersonalForm.bind(this);
            $scope.submitAcademicForm = this.submitAcademicForm.bind(this);
            $scope.submitPublicationForm = this.submitPublicationForm.bind(this);
            $scope.submitTrainingForm = this.submitTrainingForm.bind(this);
            $scope.submitAwardForm = this.submitAwardForm.bind(this);
            $scope.submitExperienceForm = this.submitExperienceForm.bind(this);
            $scope.submitAdditionalForm = this.submitAdditionalForm.bind(this);
            $scope.submitServiceForm = this.submitServiceForm.bind(this);

            this.entry = {
                personal: <IPersonalInformationModel> {},
                academic: Array<IAcademicInformationModel>(),
                publication: Array<IPublicationInformationModel>(),
                training: Array<ITrainingInformationModel>(),
                award: Array<IAwardInformationModel>(),
                experience: Array<IExperienceInformationModel>(),
                additional: <IAdditionalInformationModel> {},
                serviceInfo: Array<IServiceInformationModel>()
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
            this.serviceIntervals = registrarConstants.servicePeriods;
            this.trainingTypes = registrarConstants.trainingCategories;
            this.experienceTypes = registrarConstants.experienceCategories;
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
                if (this.stateParams.id == "" || this.stateParams.id == null || this.stateParams.id == undefined) {
                    this.userId = user.employeeId;
                    this.showServiceEditButton = false;
                    this.showAcademicEditButton = false;
                    this.showExperienceEditButton = false;
                }
                else {
                    this.userId = this.stateParams.id;
                    this.showServiceEditButton = true;
                    this.showAcademicEditButton = true;
                    this.showExperienceEditButton = true;
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
                                        this.arrayOfAreaOfInterest = aois;
                                        this.tabs = true;
                                        this.showTab("personal");
                                        this.departmentService.getAll().then((departments: any) => {
                                            this.departments = departments;
                                            this.designationService.getAll().then((designations: any) => {
                                                this.designations = designations;
                                                this.employmentTypeService.getAll().then((employmentTypes: any) => {
                                                    this.employmentTypes = employmentTypes;
                                                    this.getServiceIntervals();
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        }

        private getServiceIntervals(): void {
            this.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[0]);
            this.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[1]);
            this.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[2]);
            this.serviceContractIntervals.push(this.registrarConstants.servicePeriods[3]);
        }

        private showTab(formName: string) {
            if (formName === "personal") {
                this.getPersonalInformation();
                this.academic = false;
                this.publication = false;
                this.training = false;
                this.award = false;
                this.experience = false;
                this.additional = false;
                this.service = false;
                this.personal = true;
            }
            else if (formName === "academic") {
                this.getAcademicInformation();
                this.personal = false;
                this.publication = false;
                this.training = false;
                this.award = false;
                this.experience = false;
                this.additional = false;
                this.service = false;
                this.academic = true;
            }
            else if (formName === "publication") {
                this.getPublicationInformation();
                this.getPublicationInformationWithPagination();
                this.personal = false;
                this.academic = false;
                this.training = false;
                this.award = false;
                this.experience = false;
                this.additional = false;
                this.service = false;
                this.publication = true;
            }
            else if (formName === "training") {
                this.getTrainingInformation();
                this.personal = false;
                this.academic = false;
                this.publication = false;
                this.award = false;
                this.experience = false;
                this.additional = false;
                this.service = false;
                this.training = true;
            }
            else if (formName === "award") {
                this.getAwardInformation();
                this.personal = false;
                this.academic = false;
                this.publication = false;
                this.training = false;
                this.experience = false;
                this.additional = false;
                this.service = false;
                this.award = true;
            }
            else if (formName === "experience") {
                this.getExperienceInformation();
                this.personal = false;
                this.academic = false;
                this.publication = false;
                this.training = false;
                this.award = false;
                this.additional = false;
                this.service = false;
                this.experience = true;
            }
            else if (formName === 'additional') {
                this.getAdditionalInformation();
                this.personal = false;
                this.academic = false;
                this.publication = false;
                this.training = false;
                this.award = false;
                this.experience = false;
                this.service = false;
                this.additional = true;
            }
            else if (formName === 'service') {
                this.getServiceInformation();
                this.personal = false;
                this.academic = false;
                this.publication = false;
                this.training = false;
                this.award = false;
                this.experience = false;
                this.additional = false;
                this.service = true;
            }
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

        private submitAcademicForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.previousAcademicInformation, angular.copy(this.entry.academic), this.academicDeletedObjects)
                .then((academicObjects: any) => {
                    this.convertToJson('academic', academicObjects)
                        .then((json: any) => {
                            this.employeeInformationService.saveAcademicInformation(json)
                                .then((message: any) => {
                                    if(message == "Error"){}
                                    else {
                                        this.getAcademicInformation();
                                        this.showAcademicInputDiv = false;
                                    }
                                });
                        });
                });
        }

        private submitPublicationForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.previousPublicationInformation, angular.copy(this.entry.publication), this.publicationDeletedObjects)
                .then((publicationObjects: any) => {
                    this.convertToJson('publication', publicationObjects)
                        .then((json: any) => {
                            this.employeeInformationService.savePublicationInformation(json)
                                .then((message: any) => {
                                    if(message == "Error") {}
                                    else{
                                        this.getPublicationInformation();
                                        this.getPublicationInformationWithPagination();
                                        this.showPublicationInputDiv = false;
                                    }
                                });
                        });
                });
        }

        private submitTrainingForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.previousTrainingInformation, angular.copy(this.entry.training), this.trainingDeletedObjects)
                .then((trainingObjects: any) => {
                    this.convertToJson('training', trainingObjects)
                        .then((json: any) => {
                            this.employeeInformationService.saveTrainingInformation(json)
                                .then((message: any) => {
                                    if(message == "Error"){}
                                    else {
                                        this.getTrainingInformation();
                                        this.showTrainingInputDiv = false;
                                    }
                                });
                        });
                });
        }

        private submitAwardForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.previousAwardInformation, angular.copy(this.entry.award), this.awardDeletedObjects)
                .then((awardObjects: any) => {
                    this.convertToJson('award', awardObjects)
                        .then((json: any) => {
                            this.employeeInformationService.saveAwardInformation(json)
                                .then((message: any) => {
                                    if(message == "Error"){}
                                    else {
                                        this.getAwardInformation();
                                        this.showAwardInputDiv = false;
                                    }
                                });
                        });
                });

        }

        private submitExperienceForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.previousExperienceInformation, angular.copy(this.entry.experience), this.experienceDeletedObjects)
                .then((experienceObjects: any) => {
                    this.convertToJson('experience', experienceObjects)
                        .then((json: any) => {
                            this.employeeInformationService.saveExperienceInformation(json)
                                .then((message: any) => {
                                    if(message == "Error"){}
                                    else {
                                        this.getExperienceInformation();
                                        this.showExperienceInputDiv = false;
                                    }
                                });
                        });
                });
        }

        private submitAdditionalForm(): void {
            this.entry.additional.employeeId = this.userId;
            this.convertToJson('additional', this.entry.additional)
                .then((json: any) => {
                    this.employeeInformationService.saveAdditionalInformation(json)
                        .then((message: any) => {
                            if(message == "Error"){}
                            else {
                                this.getAdditionalInformation();
                                this.showAdditionalInputDiv = false;
                            }
                        });
                });
        }

        private submitServiceForm(): void {
            let countEmptyResignDate = 0;
            for (let i = 0; i < this.entry.serviceInfo.length; i++) {
                if (this.entry.serviceInfo[i].resignDate == "" || this.entry.serviceInfo[i].resignDate == null) {
                    countEmptyResignDate++;
                }
            }
            if (countEmptyResignDate > 1) {
                this.notify.error("You can empty only one resign date");
            }
            else {
                this.cRUDDetectionService.ObjectDetectionForServiceObjects(this.previousServiceInformation, angular.copy(this.entry.serviceInfo), this.serviceDeletedObjects, this.serviceDetailDeletedObjects)
                    .then((serviceObjects) => {
                        this.convertToJson('service', serviceObjects).then((json: any) => {
                            this.employeeInformationService.saveServiceInformation(json).then((message: any) => {
                                if(message == "Error"){}
                                else {
                                    this.getServiceInformation();
                                    this.showServiceInputDiv = false;
                                }
                            });
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

        private getAcademicInformation() {
            this.entry.academic = [];
            this.academicDeletedObjects = [];
            this.employeeInformationService.getAcademicInformation(this.userId).then((academicInformation: any) => {
                this.entry.academic = academicInformation;
            }).then(() => {
                this.previousAcademicInformation = angular.copy(this.entry.academic);
            });
        }

        private getPublicationInformation() {
            this.entry.publication = [];
            this.publicationDeletedObjects = [];
            this.employeeInformationService.getPublicationInformation(this.userId).then((publicationInformation: any) => {
                this.totalRecord = publicationInformation.length;
                this.entry.publication = publicationInformation;
            }).then(() => {
                this.previousPublicationInformation = angular.copy(this.entry.publication);
            });
        }

        private getPublicationInformationWithPagination() {
            this.paginatedPublication = [];
            this.employeeInformationService.getPublicationInformationViewWithPagination(this.userId, this.currentPage, this.itemPerPage).then((publicationInformationWithPagination: any) => {
                this.paginatedPublication = publicationInformationWithPagination;
            });
        }

        private getTrainingInformation() {
            this.entry.training = [];
            this.trainingDeletedObjects = [];
            this.employeeInformationService.getTrainingInformation(this.userId).then((trainingInformation: any) => {
                this.entry.training = trainingInformation;
            }).then(() => {
                this.previousTrainingInformation = angular.copy(this.entry.training);
            });
        }

        private getAwardInformation() {
            this.entry.award = [];
            this.awardDeletedObjects = [];
            this.employeeInformationService.getAwardInformation(this.userId).then((awardInformation: any) => {
                this.entry.award = awardInformation;
            }).then(() => {
                this.previousAwardInformation = angular.copy(this.entry.award);
            });
        }

        private getExperienceInformation() {
            this.entry.experience = [];
            this.experienceDeletedObjects = [];
            this.employeeInformationService.getExperienceInformation(this.userId).then((experienceInformation: any) => {
                this.entry.experience = experienceInformation;
            }).then(() => {
                this.previousExperienceInformation = angular.copy(this.entry.experience);
            });
        }

        private getAdditionalInformation() {
            this.entry.additional = <IAdditionalInformationModel>{};
            this.employeeInformationService.getAdditionalInformation(this.userId).then((additional: any) => {
                this.entry.additional = additional[0];
                var intArray = new Array();
                for (var i = 0; i < additional[0].areaOfInterestInformation.length; i++) {
                    for (var j = 0; j < this.arrayOfAreaOfInterest.length; j++) {
                        if (additional[0].areaOfInterestInformation[i].id == this.arrayOfAreaOfInterest[j].id)
                            intArray.push(this.arrayOfAreaOfInterest[j].id);
                    }

                }
                $("#iAoi").val(intArray);
                $("#iAoi").trigger("change");
                //this.entry.additional.areaOfInterestInformation = intArray;

            });
        }

        private getServiceInformation(): void {
            this.entry.serviceInfo = [];
            this.serviceDeletedObjects = [];
            this.serviceDetailDeletedObjects = [];
            this.employeeInformationService.getServiceInformation(this.userId).then((services: any) => {
                this.entry.serviceInfo = services;
            }).then(() => {
                this.previousServiceInformation = angular.copy(this.entry.serviceInfo);
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

        private pageChanged(pageNumber: number) {
            this.currentPage = pageNumber;
            this.getPublicationInformationWithPagination();
        }

        private changeItemPerPage() {
            if (this.data.customItemPerPage == "" || this.data.customItemPerPage == null) {
            }
            else {
                this.data.itemPerPage = this.data.customItemPerPage;
                this.getPublicationInformationWithPagination();
            }
        }

        private addNewServiceRow(parameter: string, index?: number): void {
            if (parameter == "serviceInfo") {
                if (this.entry.serviceInfo.length == 0) {
                    this.addNewRow("service");
                }
                else {
                    if (this.entry.serviceInfo[this.entry.serviceInfo.length - 1].resignDate == "") {
                        this.notify.error("Please fill up the resign date first");
                    }
                    else {
                        this.addNewRow("service");
                    }
                }
            }
            else if (parameter == "serviceDetails") {
                if (this.entry.serviceInfo[index].intervalDetails.length == 0) {
                    this.addNewServiceDetailsRow(index);
                }
                else {
                    if (this.entry.serviceInfo[index].intervalDetails[this.entry.serviceInfo[index].intervalDetails.length - 1].endDate == "") {
                        this.notify.error("Please fill up the end date first");
                    }
                    else {
                        this.addNewServiceDetailsRow(index);
                    }
                }
            }
        }

        private addNewServiceDetailsRow(index: number) {
            let serviceDetailsEntry: IServiceDetailsModel;
            serviceDetailsEntry = {
                id: "",
                interval: null,
                startDate: "",
                endDate: "",
                comment: "",
                serviceId: null,
                dbAction: "Create"
            };
            this.entry.serviceInfo[index].intervalDetails.push(serviceDetailsEntry);
        }

        private addNewRow(divName: string) {
            if (divName === 'academic') {
                let academicEntry: IAcademicInformationModel;
                academicEntry = {
                    id: "",
                    employeeId: this.userId,
                    degree: null,
                    institution: "",
                    passingYear: null,
                    result: "",
                    dbAction: "Create"
                };
                this.entry.academic.push(academicEntry);
            }
            else if (divName === 'publication') {
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
                    status: "9",
                    publicationPages: "",
                    appliedOn: "",
                    actionTakenOn: "",
                    rowNumber: null,
                    dbAction: "Create"
                };
                this.entry.publication.push(publicationEntry);
            }
            else if (divName === 'training') {
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
                    trainingCategory: null,
                    dbAction: "Create"
                };
                this.entry.training.push(trainingEntry);
            }
            else if (divName === 'award') {
                let awardEntry: IAwardInformationModel;
                awardEntry = {
                    id: "",
                    employeeId: this.userId,
                    awardName: "",
                    awardInstitute: "",
                    awardedYear: null,
                    awardShortDescription: "",
                    dbAction: "Create"
                };
                this.entry.award.push(awardEntry);
            }
            else if (divName === 'experience') {
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
                    experienceCategory: null,
                    dbAction: "Create"
                };
                this.entry.experience.push(experienceEntry);
            }
            else if (divName = 'service') {
                let serviceEntry: IServiceInformationModel;
                serviceEntry = {
                    id: "",
                    employeeId: this.userId,
                    department: null,
                    designation: null,
                    employmentType: null,
                    joiningDate: "",
                    resignDate: "",
                    dbAction: "Create",
                    intervalDetails: Array<IServiceDetailsModel>()
                };
                this.entry.serviceInfo.push(serviceEntry);
            }
        }

        private deleteRow(divName: string, index: number, parentIndex?: number) {
            if (divName === 'academic') {
                if (this.entry.academic[index].id != "") {
                    this.entry.academic[index].dbAction = "Delete";
                    this.academicDeletedObjects.push(this.entry.academic[index]);
                }
                this.entry.academic.splice(index, 1);
            }
            else if (divName === 'publication') {
                if (this.entry.publication[index].id != "") {
                    this.entry.publication[index].dbAction = "Delete";
                    this.publicationDeletedObjects.push(this.entry.publication[index]);
                }
                this.entry.publication.splice(index, 1);
            }
            else if (divName === 'training') {
                if (this.entry.training[index].id != "") {
                    this.entry.training[index].dbAction = "Delete";
                    this.trainingDeletedObjects.push(this.entry.training[index]);
                }
                this.entry.training.splice(index, 1);
            }
            else if (divName === 'award') {
                if (this.entry.award[index].id != "") {
                    this.entry.award[index].dbAction = "Delete";
                    this.awardDeletedObjects.push(this.entry.award[index]);
                }
                this.entry.award.splice(index, 1);
            }
            else if (divName === 'experience') {
                if (this.entry.experience[index].id != "") {
                    this.entry.experience[index].dbAction = "Delete";
                    this.experienceDeletedObjects.push(this.entry.experience[index]);
                }
                this.entry.experience.splice(index, 1);
            }
            else if (divName == "serviceInfo") {
                if (this.entry.serviceInfo[index].id != "") {
                    this.entry.serviceInfo[index].dbAction = "Delete";
                    this.serviceDeletedObjects.push(this.entry.serviceInfo[index]);
                }
                this.entry.serviceInfo.splice(index, 1);
            }
            else if (divName == "serviceDetails") {
                if (this.entry.serviceInfo[parentIndex].intervalDetails[index].id != "") {
                    this.entry.serviceInfo[parentIndex].intervalDetails[index].dbAction = "Delete";
                    this.serviceDetailDeletedObjects.push(this.entry.serviceInfo[parentIndex].intervalDetails[index]);
                }
                this.entry.serviceInfo[parentIndex].intervalDetails.splice(index, 1);
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
            else if (convertThis === "academic") {
                item['academic'] = obj;
            }
            else if (convertThis === "publication") {
                item['publication'] = obj;
            }
            else if (convertThis === "training") {
                item['training'] = obj;
            }
            else if (convertThis === "award") {
                item['award'] = obj;
            }
            else if (convertThis === "experience") {
                item['experience'] = obj;
            }
            else if (convertThis === "additional") {
                item['additional'] = obj;
            }
            else if (convertThis === "service") {
                item['service'] = obj;
            }
            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;
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

                    if (tabName == "training") {
                        this.entry.training[index].trainingDuration = diffDays;
                        this.entry.training[index].trainingDurationString = diffDateString;
                    }
                    else if (tabName == "experience") {
                        this.entry.experience[index].experienceDuration = diffDays;
                        this.entry.experience[index].experienceDurationString = diffDateString;
                    }
                }
            }
        }

        private uploadImage() {
            var id = this.userId;
            var image = $("#userPhoto").contents().prevObject[0].files[0];
            this.getFormData(image, id).then((formData) => {
                this.FileUpload.uploadPhoto(formData);
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

    UMS.controller("EmployeeProfile", EmployeeProfile);
}