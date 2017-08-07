module ums {
    interface IEmployeeInformation extends ng.IScope {
        showPersonalInputDiv: boolean;
        showAcademicInputDiv: boolean;
        showPublicationInputDiv: boolean;
        showTrainingInputDiv: boolean;
        showAwardInputDiv: boolean;
        showExperienceInputDiv: boolean;
        showPersonalLabelDiv: boolean;
        showAcademicLabelDiv: boolean;
        showPublicationLabelDiv: boolean;
        showTrainingLabelDiv: boolean;
        showAwardLabelDiv: boolean;
        showExperienceLabelDiv: boolean;
        showAdditionalInputDiv: boolean;
        showAdditionalLabelDiv: boolean;
        required: boolean;
        disablePresentAddressDropdown: boolean;
        disablePermanentAddressDropdown: boolean;
        borderColor: string;
        supOptions: string;
        customItemPerPage: number;
        aoiMap: any;
        data: any;
        pagination: any;
        enableEditMode: Function;
        enableViewMode: Function;
        addNewRow: Function;
        deleteRow: Function;
        testData: Function;
        submitPersonalForm: Function;
        submitAcademicForm: Function;
        submitPublicationForm: Function;
        submitTrainingForm: Function;
        submitAwardForm: Function;
        submitExperienceForm: Function;
        submitAdditionalForm: Function;
        sameAsPresentAddress: Function;
        changePresentAddressDistrict: Function;
        changePresentAddressThana: Function;
        changePermanentAddressDistrict: Function;
        changePermanentAddressThana: Function;
        changePresentAddressFields: Function;
        changePermanentAddressFields: Function;
        fillEmergencyContactAddress: Function;
        pageChanged: Function;
        changeItemPerPage: Function;
        entry: IEntry;
        genders: Array<IGender>;
        maritalStatus: Array<ICommon>;
        religions: Array<ICommon>;
        nationalities: Array<ICommon>;
        degreeNames: Array<ICommon>;
        bloodGroups: Array<ICommon>;
        publicationTypes: Array<ICommon>;
        relations: Array<ICommon>;
        countries: Array<ICommon>;
        divisions: Array<ICommon>;
        presentAddressDistricts: Array<ICommon>;
        permanentAddressDistricts: Array<ICommon>;
        allDistricts: Array<ICommon>;
        presentAddressThanas: Array<ICommon>;
        permanentAddressThanas: Array<ICommon>;
        allThanas: Array<ICommon>;
        arrayOfAreaOfInterest: Array<ICommon>;
        paginatedPublication: Array<IPublicationInformationModel>;
        previousPersonalInformation: IPersonalInformationModel;
        previousAcademicInformation: Array<IAcademicInformationModel>;
        previousPublicationInformation: Array<IPublicationInformationModel>;
        previousTrainingInformation: Array<ITrainingInformationModel>;
        previousAwardInformation: Array<IAwardInformationModel>;
        previousExperienceInformation: Array<IExperienceInformationModel>;
        userId: string;

        filterData: string;
        searchBy: string;
        changedUserName: string;
        showSearchByUserId: boolean;
        showSearchByUserName: boolean;
        showSearchByDepartment: boolean;
        showServiceInputDiv: boolean;
        showServiceLabelDiv: boolean;
        showServiceEditButton: boolean;
        showFilteredEmployeeList: boolean;
        showEmployeeInformation: boolean;
        showSelectPanel: boolean;
        showTableData: boolean;
        changedDepartment: IDepartment;
        allUser: Array<Employee>;
        designations: Array<ICommon>;
        employmentTypes: Array<ICommon>;
        serviceIntervals: Array<ICommon>;
        serviceRegularIntervals: Array<ICommon>;
        serviceContractIntervals: Array<ICommon>;
        departments: Array<IDepartment>;
        previousServiceInformation: Array<IServiceInformationModel>;
        getSearchFields: Function;
        getEmployeeInformation: Function;
        addNewServiceRow: Function;
        addNewServiceDetailsRow: Function;
        submitServiceForm: Function;
        view: Function;

        test: string;
    }
    export interface IGender {
        id: string;
        name: string;
    }
    export interface ICommon{
        id: number;
        name: string;
        foreign_id: number;
    }
    export interface IDepartment{
        id: string;
        shortName: string;
        longName: string;
        type: string;
    }
    export interface IEntry {
        personal: IPersonalInformationModel;
        academic: Array<IAcademicInformationModel>;
        publication: Array<IPublicationInformationModel>;
        training: Array<ITrainingInformationModel>;
        award: Array<IAwardInformationModel>;
        experience: Array<IExperienceInformationModel>;
        additional: IAdditionalInformationModel;
        serviceInfo: Array<IServiceInformationModel>;
    }

    class EmployeeInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce', '$filter', 'countryService',
            'divisionService', 'districtService', 'thanaService', 'personalInformationService', 'academicInformationService',
            'publicationInformationService', 'trainingInformationService', 'awardInformationService', 'experienceInformationService',
            'areaOfInterestService', 'areaOfInterestInformationService', 'additionalInformationService', 'userService', 'cRUDDetectionService', 'serviceInformationService', 'serviceInformationDetailService', 'employmentTypeService',
            'departmentService', 'designationService', 'employeeService'];

        constructor(private registrarConstants: any, private $scope: IEmployeeInformation, private $q: ng.IQService, private notify: Notify,
                    private $window: ng.IWindowService, private $sce: ng.ISCEService, private $filter: ng.IFilterService, private countryService: CountryService,
                    private divisionService: DivisionService, private districtService: DistrictService, private thanaService: ThanaService, private personalInformationService: PersonalInformationService,
                    private academicInformationService: AcademicInformationService, private publicationInformationService: PublicationInformationService, private trainingInformationService: TrainingInformationService,
                    private awardInformationService: AwardInformationService, private experienceInformationService: ExperienceInformationService, private areaOfInterestService: AreaOfInterestService,
                    private areaOfInterestInformationService: AreaOfInterestInformationService, private additionalInformationService: AdditionalInformationService, private userService: UserService, private cRUDDetectionService: CRUDDetectionService,
                    private serviceInformationService: ServiceInformationService, private serviceInformationDetailService: ServiceInformationDetailService, private employmentTypeService: EmploymentTypeService,
                    private departmentService: DepartmentService, private designationService: DesignationService, private employeeService: EmployeeService) {

            $scope.entry = {
                personal: <IPersonalInformationModel> {},
                academic: Array<IAcademicInformationModel>(),
                publication: Array<IPublicationInformationModel>(),
                training: Array<ITrainingInformationModel>(),
                award: Array<IAwardInformationModel>(),
                experience: Array<IExperienceInformationModel>(),
                additional: <IAdditionalInformationModel> {},
                serviceInfo: Array<IServiceInformationModel>()
            };
            $scope.data = {
                supOptions: "1",
                borderColor: "",
                itemPerPage: 2,
                totalRecord: 0,
                customItemPerPage: null,
                changedUserId: "",
                changedUserName: "",
                searchBy: "",
                changedDepartment: null,
                filterData: "",
                userId: "",
                test: ""
            };
            $scope.pagination = {};
            $scope.pagination.currentPage = 1;
            $scope.genders = this.registrarConstants.genderTypes;
            $scope.publicationTypes = this.registrarConstants.publicationTypes;
            $scope.degreeNames = this.registrarConstants.degreeTypes;
            $scope.bloodGroups = this.registrarConstants.bloodGroupTypes;
            $scope.maritalStatus = this.registrarConstants.maritalStatuses;
            $scope.religions = this.registrarConstants.religionTypes;
            $scope.relations = this.registrarConstants.relationTypes;
            $scope.nationalities = this.registrarConstants.nationalityTypes;
            $scope.serviceIntervals = registrarConstants.servicePeriods;

            $scope.testData = this.testData.bind(this);
            $scope.submitPersonalForm = this.submitPersonalForm.bind(this);
            $scope.submitAcademicForm = this.submitAcademicForm.bind(this);
            $scope.submitPublicationForm = this.submitPublicationForm.bind(this);
            $scope.submitTrainingForm = this.submitTrainingForm.bind(this);
            $scope.submitAwardForm = this.submitAwardForm.bind(this);
            $scope.submitExperienceForm = this.submitExperienceForm.bind(this);
            $scope.submitAdditionalForm = this.submitAdditionalForm.bind(this);
            $scope.enableEditMode = this.enableEditMode.bind(this);
            $scope.enableViewMode = this.enableViewMode.bind(this);
            $scope.addNewRow = this.addNewRow.bind(this);
            $scope.deleteRow = this.deleteRow.bind(this);
            $scope.sameAsPresentAddress = this.sameAsPresentAddress.bind(this);
            $scope.changePresentAddressDistrict = this.changePresentAddressDistrict.bind(this);
            $scope.changePresentAddressThana = this.changePresentAddressThana.bind(this);
            $scope.changePermanentAddressDistrict = this.changePermanentAddressDistrict.bind(this);
            $scope.changePermanentAddressThana = this.changePermanentAddressThana.bind(this);
            $scope.changePresentAddressFields = this.changePresentAddressFields.bind(this);
            $scope.changePermanentAddressFields = this.changePermanentAddressFields.bind(this);
            $scope.fillEmergencyContactAddress = this.fillEmergencyContactAddress.bind(this);
            $scope.pageChanged = this.pageChanged.bind(this);
            $scope.changeItemPerPage = this.changeItemPerPage.bind(this);
            $scope.getSearchFields = this.getSearchFields.bind(this);
            $scope.getEmployeeInformation = this.getEmployeeInformation.bind(this);
            $scope.addNewServiceRow = this.addNewServiceRow.bind(this);
            $scope.addNewServiceDetailsRow = this.addNewServiceDetailsRow.bind(this);
            $scope.view = this.view.bind(this);
            $scope.submitServiceForm = this.submitServiceForm.bind(this);

            this.initialization();
            this.addDate();
        }

        private initialization() {
            this.employeeService.getAll().then((users: any) => {
                this.$scope.allUser = users;
                this.countryService.getCountryList().then((countries: any) => {
                    this.$scope.countries = countries.entries;
                    this.divisionService.getDivisionList().then((divisions: any) => {
                        this.$scope.divisions = divisions.entries;
                        this.districtService.getDistrictList().then((districts: any) => {
                            this.$scope.presentAddressDistricts = districts.entries;
                            this.$scope.permanentAddressDistricts = districts.entries;
                            this.$scope.allDistricts = districts.entries;
                            this.thanaService.getThanaList().then((thanas: any) => {
                                this.$scope.presentAddressThanas = thanas.entries;
                                this.$scope.permanentAddressThanas = thanas.entries;
                                this.$scope.allThanas = thanas.entries;
                                this.areaOfInterestService.getAll().then((aois: any) => {
                                    this.$scope.arrayOfAreaOfInterest = aois;
                                    this.departmentService.getAll().then((departments: any) => {
                                        this.$scope.departments = departments;
                                        this.designationService.getAll().then((designations: any) => {
                                            this.$scope.designations = designations;
                                            this.employmentTypeService.getAll().then((employmentTypes: any) => {
                                                this.$scope.employmentTypes = employmentTypes;
                                                this.createMap();
                                                this.getServiceIntervals();
                                                this.$scope.showEmployeeInformation = false;
                                                this.$scope.showSelectPanel = true;
                                                this.$scope.showFilteredEmployeeList = true;

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

        private view(user: any): void{
            console.log(user);
            this.$scope.data.userId = user.id;
            console.log(this.$scope.data.userId);
            this.$scope.showFilteredEmployeeList = false;
            this.$scope.showEmployeeInformation = true;
            this.getPreviousFormValues(user.employeeId);
            this.setViewModeInitially();
            Utils.expandRightDiv();
        }

        private getSearchFields(): void{
            if(this.$scope.data.searchBy == "1"){
                this.$scope.showSearchByUserName = false;
                this.$scope.showSearchByDepartment = false;
                this.$scope.showFilteredEmployeeList = false;
                this.$scope.showSearchByUserId = true;
                this.$scope.showEmployeeInformation = true;
                this.setViewModeInitially();
            } else if(this.$scope.data.searchBy == "2"){
                this.$scope.showSearchByUserId = false;
                this.$scope.showSearchByDepartment = false;
                this.$scope.showEmployeeInformation = false;
                this.$scope.showSearchByUserName = true;
                this.$scope.showFilteredEmployeeList = true;
            } else if(this.$scope.data.searchBy == "3"){
                this.$scope.showSearchByUserId = false;
                this.$scope.showSearchByUserName = false;
                this.$scope.showEmployeeInformation = false;
                this.$scope.showFilteredEmployeeList = true;
                this.$scope.showSearchByDepartment = true;
            }
        }

        private getServiceIntervals(): void {
            this.$scope.serviceRegularIntervals = Array<ICommon>();
            this.$scope.serviceContractIntervals = Array<ICommon>();
            this.$scope.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[0]);
            this.$scope.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[1]);
            this.$scope.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[2]);
            this.$scope.serviceContractIntervals.push(this.registrarConstants.servicePeriods[3]);
        }

        private getEmployeeInformation(): void{
            if(this.$scope.data.searchBy == "1"){
                console.log(this.$scope.data.changedUserId);
                if(this.findUser() == true) {
                    this.$scope.data.userId = this.$scope.data.changedUserId;
                    this.$scope.showFilteredEmployeeList = false;
                    this.$scope.showEmployeeInformation = true;
                    if (this.$scope.data.userId == "" || this.$scope.data.userId == null) {
                        this.notify.error("Empty or Null Id");
                    }
                    else {
                        Utils.expandRightDiv();
                        this.getPreviousFormValues(this.$scope.data.userId);
                        this.setViewModeInitially();
                    }
                }
                else{
                    this.notify.error("No User Found");
                }
            } else if (this.$scope.data.searchBy == "2"){
                this.$scope.showEmployeeInformation = false;
                this.$scope.showFilteredEmployeeList = true;
                if(this.$scope.data.changedUserName != null || this.$scope.data.changedUserName != ""){
                    this.$scope.showTableData = true;
                    Utils.expandRightDiv();
                }
                else{
                    this.$scope.showTableData = false;
                }
                this.$scope.data.filterData = this.$scope.data.changedUserName;
            } else if (this.$scope.data.searchBy == "3"){
                Utils.expandRightDiv();
                this.$scope.showEmployeeInformation = false;
                this.$scope.showFilteredEmployeeList = true;
            }
        }

        private findUser(): boolean{
            let length = this.$scope.allUser.length;
            for(let i = 0; i < length; i++){
                if(this.$scope.allUser[i].id == this.$scope.data.changedUserId){
                    return true;
                }
            }
            return false;
        }

        private setViewModeInitially() {
            this.enableViewMode('personal');
            this.enableViewMode('academic');
            this.enableViewMode('publication');
            this.enableViewMode('training');
            this.enableViewMode('award');
            this.enableViewMode('experience');
            this.enableViewMode('additional');
            this.enableViewMode('service');
        }

        private getPreviousFormValues(userId: string) {
            this.getPersonalInformation(userId);
            this.getAcademicInformation(userId);
            this.getAwardInformation(userId);
            this.getPublicationInformation(userId);
            this.getPublicationInformationWithPagination(userId);
            this.getExperienceInformation(userId);
            this.getTrainingInformation(userId);
            this.getAdditionalInformation(userId);
            this.getServiceInformation(userId);
        }

        private createMap() {
            this.$scope.aoiMap = {};

            for (let i = 0; i < this.$scope.arrayOfAreaOfInterest.length; i++) {
                this.$scope.aoiMap[this.$scope.arrayOfAreaOfInterest[i].id] = this.$scope.arrayOfAreaOfInterest[i];
            }
        }

        private testData(formName: string): void {
            if (formName == "personal") {
                this.$scope.entry.personal.firstName = "Kawsur";
                this.$scope.entry.personal.lastName = "Mir Md.";
                this.$scope.entry.personal.fatherName = "Mir Abdul Aziz";
                this.$scope.entry.personal.motherName = "Mst Hosne Ara";
                this.$scope.entry.personal.gender = this.$scope.genders[1];
                this.$scope.entry.personal.dateOfBirth = "20/10/1995";
                this.$scope.entry.personal.nationality = this.$scope.nationalities[1];
                this.$scope.entry.personal.religion = this.$scope.religions[1];
                this.$scope.entry.personal.maritalStatus = this.$scope.maritalStatus[1];
                this.$scope.entry.personal.spouseName = "";
                this.$scope.entry.personal.nidNo = "19952641478954758";
                this.$scope.entry.personal.spouseNidNo = "";
                this.$scope.entry.personal.bloodGroup = this.$scope.bloodGroups[1];
                this.$scope.entry.personal.website = "https://www.kawsur.com";
                this.$scope.entry.personal.organizationalEmail = "kawsur.iums@aust.edu";
                this.$scope.entry.personal.personalEmail = "kawsurilu@yahoo.com";
                this.$scope.entry.personal.mobile = "+8801672494863";
                this.$scope.entry.personal.phone = "none";
                this.$scope.entry.personal.emergencyContactName = "None";
                this.$scope.entry.personal.emergencyContactRelation = this.$scope.relations[0];
                this.$scope.entry.personal.emergencyContactPhone = "01898889851";
            } else if (formName == "academic") {
                this.$scope.entry.academic[0].degree.name = "Bachelor";
                this.$scope.entry.academic[0].institution = "American International University-Bangladesh";
                this.$scope.entry.academic[0].passingYear = 2011;
            } else if(formName == "publication") {
                this.$scope.entry.publication[0].publicationTitle = "N/A";
                this.$scope.entry.publication[0].publicationInterestGenre = "N/A";
                this.$scope.entry.publication[0].publisherName = "N/A";
                this.$scope.entry.publication[0].dateOfPublication = "11/11/3010";
                this.$scope.entry.publication[0].publicationType = this.$scope.publicationTypes[1];
                this.$scope.entry.publication[0].publicationWebLink = "N/A";
            } else if(formName == "training") {
                this.$scope.entry.training[0].trainingInstitution = "ABC";
                this.$scope.entry.training[0].trainingName = "XYZ";
                this.$scope.entry.training[0].trainingFrom = "2016";
                this.$scope.entry.training[0].trainingTo = "2015";
            } else if(formName == "award") {
                this.$scope.entry.award[0].awardName = "My Award";
                this.$scope.entry.award[0].awardInstitute = "Really !";
                this.$scope.entry.award[0].awardedYear = "1990";
                this.$scope.entry.award[0].awardShortDescription = "Hello! This is My Award, Don't Ask Description :@";
            } else if(formName == "experience") {
                this.$scope.entry.experience[0].experienceInstitution = "My Award";
                this.$scope.entry.experience[0].experienceDesignation = "Really !";
                this.$scope.entry.experience[0].experienceFrom = "6";
                this.$scope.entry.experience[0].experienceTo = "2010";
            } else if(formName == "additional"){

            }
        }

        private submitPersonalForm(): void {
            this.$scope.entry.personal.employeeId = this.$scope.data.userId;
            if (this.cRUDDetectionService.isObjectEmpty(this.$scope.previousPersonalInformation)) {
                this.convertToJson('personal', this.$scope.entry.personal).then((json: any) => {
                    this.personalInformationService.savePersonalInformation(json).then((message: any) => {
                        this.getPersonalInformation(this.$scope.data.userId);
                        this.enableViewMode('personal');
                    });
                });
            } else {
                this.convertToJson('personal', this.$scope.entry.personal).then((json: any) => {
                    this.personalInformationService.updatePersonalInformation(json).then((message: any) => {
                        this.getPersonalInformation(this.$scope.data.userId);
                        this.enableViewMode('personal');
                    });
                });
            }
        }

        private submitAcademicForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousAcademicInformation, angular.copy(this.$scope.entry.academic)).then((academicObjects:any)=>{
                this.convertToJson('ascademic', academicObjects).then((json: any) => {
                    this.academicInformationService.saveAcademicInformation(json)
                        .then((message: any) => {
                            this.getAcademicInformation(this.$scope.data.userId);
                            this.enableViewMode('academic');
                        });
                });
            });
        }

        private submitPublicationForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousPublicationInformation, angular.copy(this.$scope.entry.publication)).then((publicationObjects: any) => {
                this.convertToJson('publication', publicationObjects)
                    .then((json: any) => {
                        this.publicationInformationService.savePublicationInformation(json)
                            .then((message: any) => {
                                this.getPublicationInformation(this.$scope.data.userId);
                                this.getPublicationInformationWithPagination(this.$scope.data.userId);
                                this.enableViewMode('publication');
                            });
                    });
            });
        }

        private submitTrainingForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousTrainingInformation, angular.copy(this.$scope.entry.training)).then((trainingObjects: any)=>{
                this.convertToJson('training', trainingObjects)
                    .then((json: any) => {
                        console.log(json);
                        this.trainingInformationService.saveTrainingInformation(json)
                            .then((message: any) => {
                                this.getTrainingInformation(this.$scope.data.userId);
                                this.enableViewMode('training');
                            });
                    });
            });
        }

        private submitAwardForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousAwardInformation, angular.copy(this.$scope.entry.award)).then((awardObjects: any)=>{
                this.convertToJson('award', awardObjects)
                    .then((json: any) => {
                        this.awardInformationService.saveAwardInformation(json)
                            .then((message: any) => {
                                this.getAwardInformation(this.$scope.data.userId);
                                this.enableViewMode('award');
                            });
                    });
            });

        }

        private submitExperienceForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousExperienceInformation, angular.copy(this.$scope.entry.experience)).then((experienceObjects: any)=>{
                this.convertToJson('experience', experienceObjects)
                    .then((json: any) => {
                        this.experienceInformationService.saveExperienceInformation(json)
                            .then((message: any) => {
                                this.getExperienceInformation(this.$scope.data.userId);
                                this.enableViewMode('experience');
                            });
                    });
            });
        }

        private submitAdditionalForm(): void {
            this.$scope.entry.additional.employeeId = this.$scope.data.userId;
            this.convertToJson('additional', this.$scope.entry.additional)
                .then((json: any) => {
                    this.additionalInformationService.saveAdditionalInformation(json)
                        .then((message: any) => {
                            this.getAdditionalInformation(this.$scope.data.userId);
                            this.enableViewMode('additional');
                        });
                });
        }

        private submitServiceForm(): void {
            let countEmptyResignDate = 0;
            for (let i = 0; i < this.$scope.entry.serviceInfo.length; i++) {
                if (this.$scope.entry.serviceInfo[i].resignDate == "" || this.$scope.entry.serviceInfo[i].resignDate == null) {
                    countEmptyResignDate++;
                }
            }
            if (countEmptyResignDate > 1) {
                this.notify.error("You can empty only one resign date");
            } else {
                this.cRUDDetectionService.ObjectDetectionForServiceObjects(this.$scope.previousServiceInformation, angular.copy(this.$scope.entry.serviceInfo)).then((serviceObjects) => {
                    this.convertToJson('service', serviceObjects).then((json: any) => {
                        console.log(json);
                        this.serviceInformationService.saveServiceInformation(json).then((message: any) => {
                            this.getServiceInformation(this.$scope.data.userId);
                            this.enableViewMode("service");
                        });
                    });
                });
            }
        }

        private getPersonalInformation(userId: string) {
            this.$scope.entry.personal = null;
            this.personalInformationService.getPersonalInformation(userId).then((personalInformation: any) => {
                if (personalInformation.length > 0) {
                    this.$scope.entry.personal = personalInformation[0];
                }
            }).then(() => {
                this.$scope.previousPersonalInformation = angular.copy(this.$scope.entry.personal);
            });
        }

        private getAcademicInformation(userId: string) {
            this.$scope.entry.academic = [];
            this.academicInformationService.getAcademicInformation(userId).then((academicInformation: any) => {
                for (let i = 0; i < academicInformation.length; i++) {
                    this.$scope.entry.academic[i] = academicInformation[i];
                }
            }).then(() => {
                this.$scope.previousAcademicInformation = angular.copy(this.$scope.entry.academic);
            });
        }

        private getPublicationInformation(userId: string) {
            this.$scope.entry.publication = [];
            this.publicationInformationService.getPublicationInformation(userId).then((publicationInformation: any) => {
                this.$scope.data.totalRecord = publicationInformation.length;
                for (let i = 0; i < publicationInformation.length; i++) {
                    this.$scope.entry.publication[i] = publicationInformation[i];
                }
            }).then(()=> {
                this.$scope.previousPublicationInformation = angular.copy(this.$scope.entry.publication);
            });
        }

        private getPublicationInformationWithPagination(userId: string){
            this.$scope.entry.publication = [];
            this.publicationInformationService.getPublicationInformationViewWithPagination(userId, this.$scope.pagination.currentPage, this.$scope.data.itemPerPage).then((publicationInformationWithPagination: any) => {
                this.$scope.paginatedPublication = publicationInformationWithPagination;
                for (let i = 0; i < publicationInformationWithPagination.length; i++) {
                    this.$scope.paginatedPublication[i] = publicationInformationWithPagination[i];
                }
            });
        }


        private getTrainingInformation(userId: string) {
            this.$scope.entry.training = [];
            this.trainingInformationService.getTrainingInformation(userId).then((trainingInformation: any) => {
                for (let i = 0; i < trainingInformation.length; i++) {
                    this.$scope.entry.training[i] = trainingInformation[i];
                }
            }).then(()=>{
                this.$scope.previousTrainingInformation = angular.copy(this.$scope.entry.training);
            });
        }

        private getAwardInformation(userId: string) {
            this.$scope.entry.award = [];
            this.awardInformationService.getAwardInformation(userId).then((awardInformation: any) => {
                for (let i = 0; i < awardInformation.length; i++) {
                    this.$scope.entry.award[i] = awardInformation[i];
                }
            }).then(() =>{
                this.$scope.previousAwardInformation = angular.copy(this.$scope.entry.award);
            });
        }

        private getExperienceInformation(userId: string) {
            this.$scope.entry.experience = [];
            this.experienceInformationService.getExperienceInformation(userId).then((experienceInformation: any) => {
                for (let i = 0; i < experienceInformation.length; i++) {
                    this.$scope.entry.experience[i] = experienceInformation[i];
                }
            }).then(() => {
                this.$scope.previousExperienceInformation = angular.copy(this.$scope.entry.experience);
            });
        }

        private getAdditionalInformation(userId: string) { console.log('.....................');
            this.$scope.entry.additional = <IAdditionalInformationModel>{};
            this.$scope.entry.additional.areaOfInterestInformation = [];
            this.additionalInformationService.getAdditionalInformation(this.$scope.data.userId).then((additional: any) => {
                this.$scope.entry.additional = additional[0];
                if(additional[0].areaOfInterestInformation) {
                    for (let i = 0; i < additional[0].areaOfInterestInformation.length; i++) {
                        this.$scope.entry.additional.areaOfInterestInformation[i] = additional[0].areaOfInterestInformation[i].id;
                        // $('#aoi').val(this.$scope.entry.additional.areaOfInterestInformation[i].id+'').trigger('change');
                    }
                    console.log('>>',this.$scope.entry.additional.areaOfInterestInformation);

                }
            });
        }

        private getServiceInformation(userId: string): void{
            console.log(this.$scope.data.userId);
            this.$scope.entry.serviceInfo = [];
            this.serviceInformationService.getServiceInformation(this.$scope.data.userId).then((services: any) =>{
                for(let i = 0; i < services.length; i++){
                    this.$scope.entry.serviceInfo[i] = services[i];
                    for(let j = 0; j < services[i].intervalDetails.length; j++) {
                        this.$scope.entry.serviceInfo[i].intervalDetails[j] = services[i].intervalDetails[j];
                    }
                }
            }).then(()=>{
                this.$scope.previousServiceInformation = angular.copy(this.$scope.entry.serviceInfo);
            });
        }

        private changePresentAddressDistrict() {
            this.$scope.presentAddressDistricts = Array<ICommon>();
            let districtLength = this.$scope.allDistricts.length; let index = 0;
            for (let i = 0; i < districtLength; i++) {
                if (this.$scope.entry.personal.preAddressDivision.id === this.$scope.allDistricts[i].foreign_id) {
                    this.$scope.presentAddressDistricts[index++] = this.$scope.allDistricts[i];
                }
            }
        }

        private changePermanentAddressDistrict() {
            this.$scope.permanentAddressDistricts = Array<ICommon>();
            let districtLength = this.$scope.allDistricts.length; let index = 0;
            for (let i = 0; i < districtLength; i++) {
                if (this.$scope.entry.personal.perAddressDivision.id === this.$scope.allDistricts[i].foreign_id) {
                    this.$scope.permanentAddressDistricts[index++] = this.$scope.allDistricts[i];
                }
            }
        }

        private changePresentAddressThana() {
            this.$scope.presentAddressThanas = Array<ICommon>();
            let thanaLength = this.$scope.allThanas.length; let index = 0;
            for (let i = 0; i < thanaLength; i++) {
                if (this.$scope.entry.personal.preAddressDistrict.id === this.$scope.allThanas[i].foreign_id) {
                    this.$scope.presentAddressThanas[index++] = this.$scope.allThanas[i];
                }
            }
        }

        private changePermanentAddressThana() {
            this.$scope.permanentAddressThanas = Array<ICommon>();
            let thanaLength = this.$scope.allThanas.length; let index = 0;
            for (let i = 0; i < thanaLength; i++) {
                if (this.$scope.entry.personal.perAddressDistrict.id === this.$scope.allThanas[i].foreign_id) {
                    this.$scope.permanentAddressThanas[index++] = this.$scope.allThanas[i];
                }
            }
        }

        private sameAsPresentAddress() {
            this.$scope.entry.personal.perAddressLine1 = this.$scope.entry.personal.preAddressLine1;
            this.$scope.entry.personal.perAddressLine2 = this.$scope.entry.personal.preAddressLine2;
            this.$scope.entry.personal.perAddressCountry = this.$scope.entry.personal.preAddressCountry;
            this.$scope.entry.personal.perAddressDivision = this.$scope.entry.personal.preAddressDivision;
            this.$scope.entry.personal.perAddressDistrict = this.$scope.entry.personal.preAddressDistrict;
            this.$scope.entry.personal.perAddressThana = this.$scope.entry.personal.preAddressThana;
            this.$scope.entry.personal.perAddressPostCode = this.$scope.entry.personal.preAddressPostCode;
            this.changePermanentAddressFields();
        }

        private changePresentAddressFields() {
            if (this.$scope.entry.personal.preAddressCountry.name === "Bangladesh") {
                this.$scope.required = true;
                this.$scope.disablePresentAddressDropdown = false;
                this.changePresentAddressDistrict();
                this.changePresentAddressThana();
            } else {
                this.$scope.required = false;
                this.$scope.disablePresentAddressDropdown = true;
                this.$scope.entry.personal.preAddressDivision = null;
                this.$scope.entry.personal.preAddressDistrict = null;
                this.$scope.entry.personal.preAddressThana = null;
                this.$scope.entry.personal.preAddressPostCode = null;
            }
        }

        private changePermanentAddressFields() {
            if (this.$scope.entry.personal.perAddressCountry.name === "Bangladesh") {
                this.$scope.disablePermanentAddressDropdown = false;
                this.changePermanentAddressDistrict();
                this.changePermanentAddressThana();
            } else {
                this.$scope.disablePermanentAddressDropdown = true;
                this.$scope.entry.personal.perAddressDivision = null;
                this.$scope.entry.personal.perAddressDistrict = null;
                this.$scope.entry.personal.perAddressThana = null;
                this.$scope.entry.personal.perAddressPostCode = null;
            }
        }

        private fillEmergencyContactAddress() {
            if (this.$scope.data.supOptions === "1") {
                this.$scope.entry.personal.emergencyContactAddress = "";
            } else if (this.$scope.data.supOptions === "2") {
                this.$scope.entry.personal.emergencyContactAddress =
                    this.$scope.entry.personal.preAddressLine1 == null ? "" : this.$scope.entry.personal.preAddressLine1
                    + " " + this.$scope.entry.personal.preAddressLine2 == null ? "" : this.$scope.entry.personal.preAddressLine2
                    + " " + this.$scope.entry.personal.preAddressThana.name == null ? "" : this.$scope.entry.personal.preAddressThana.name
                    + " " + this.$scope.entry.personal.preAddressDistrict.name == null ? "" : this.$scope.entry.personal.preAddressDistrict.name
                    + " - " + this.$scope.entry.personal.preAddressPostCode == null ? "" : this.$scope.entry.personal.preAddressPostCode;
            } else if (this.$scope.data.supOptions === "3") {
                this.$scope.entry.personal.emergencyContactAddress =
                    this.$scope.entry.personal.perAddressLine1 == null ? "" : this.$scope.entry.personal.perAddressLine1
                    + " " + this.$scope.entry.personal.perAddressLine2 == null ? "" : this.$scope.entry.personal.perAddressLine2
                    + " " + this.$scope.entry.personal.perAddressThana.name == null ? "" : this.$scope.entry.personal.perAddressThana.name
                    + " " + this.$scope.entry.personal.perAddressDistrict.name == null ? "" : this.$scope.entry.personal.perAddressDistrict.name
                    + " - " + this.$scope.entry.personal.perAddressPostCode == null ? "" : this.$scope.entry.personal.perAddressPostCode;
            }
        }

        private pageChanged(pageNumber: number){
            this.$scope.pagination.currentPage = pageNumber;
            this.getPublicationInformationWithPagination(this.$scope.data.userId);
        }

        private changeItemPerPage(){
            if(this.$scope.data.customItemPerPage == "" || this.$scope.data.customItemPerPage == null) {}
            else{
                this.$scope.data.itemPerPage = this.$scope.data.customItemPerPage;
                this.getPublicationInformationWithPagination(this.$scope.data.userId);
            }
        }
        private enableViewMode(formName: string) {
            if (formName === 'personal') {
                this.$scope.showPersonalInputDiv = false;
                this.$scope.showPersonalLabelDiv = true;
            } else if (formName === 'academic') {
                this.$scope.showAcademicInputDiv = false;
                this.$scope.showAcademicLabelDiv = true;
            } else if (formName === 'publication') {
                this.$scope.showPublicationInputDiv = false;
                this.$scope.showPublicationLabelDiv = true;
            } else if (formName === 'training') {
                this.$scope.showTrainingInputDiv = false;
                this.$scope.showTrainingLabelDiv = true;
            } else if (formName === 'award') {
                this.$scope.showAwardInputDiv = false;
                this.$scope.showAwardLabelDiv = true;
            } else if (formName === 'experience') {
                this.$scope.showExperienceInputDiv = false;
                this.$scope.showExperienceLabelDiv = true;
            } else if(formName === 'additional'){
                this.$scope.showAdditionalInputDiv = false;
                this.$scope.showAdditionalLabelDiv = true;
            } else if(formName === 'service'){
                this.$scope.showServiceInputDiv = false;
                this.$scope.showServiceLabelDiv = true;
                this.$scope.showServiceEditButton = true;
            }
        }
        private enableEditMode(formName: string) {
            if (formName === "personal") {
                this.$scope.showPersonalLabelDiv = false;
                this.$scope.showPersonalInputDiv = true;
            } else if (formName === "academic") {
                this.$scope.showAcademicLabelDiv = false;
                this.$scope.showAcademicInputDiv = true;
            } else if (formName === "publication") {
                this.$scope.showPublicationLabelDiv = false;
                this.$scope.showPublicationInputDiv = true;
            } else if (formName === "training") {
                this.$scope.showTrainingLabelDiv = false;
                this.$scope.showTrainingInputDiv = true;
            } else if (formName === "award") {
                this.$scope.showAwardLabelDiv = false;
                this.$scope.showAwardInputDiv = true;
            } else if (formName === "experience") {
                this.$scope.showExperienceLabelDiv = false;
                this.$scope.showExperienceInputDiv = true;
            } else if(formName === 'additional'){
                this.$scope.showAdditionalLabelDiv = false;
                this.$scope.showAdditionalInputDiv = true;
            } else if(formName === 'service'){
                this.$scope.showServiceLabelDiv = false;
                this.$scope.showServiceEditButton = false;
                this.$scope.showServiceInputDiv = true;
            }
        }

        private addNewServiceRow(parameter: string, index?: number): void {
            if(parameter == "serviceInfo") {
                if(this.$scope.entry.serviceInfo.length == 0){
                    this.addNewRow("service");
                } else {
                    if(this.$scope.entry.serviceInfo[this.$scope.entry.serviceInfo.length - 1].resignDate == ""){
                        this.notify.error("Please fill up the resign date first");
                    } else {
                        this.addNewRow("service");
                    }
                }
            } else if(parameter == "serviceDetails") {
                if(this.$scope.entry.serviceInfo[index].intervalDetails.length == 0) {
                    this.addNewServiceDetailsRow(index);
                } else{
                    if(this.$scope.entry.serviceInfo[index].intervalDetails[this.$scope.entry.serviceInfo[index].intervalDetails.length - 1].endDate == ""){
                        this.notify.error("Please fill up the end date first");
                    } else{
                        this.addNewServiceDetailsRow(index);
                    }
                }
            }
            this.addDate();
        }

        private addNewServiceDetailsRow(index: number) {
            let serviceDetailsEntry: IServiceDetailsModel;
            serviceDetailsEntry = {id: null, interval: null, startDate: "", endDate: "", serviceId: null, dbAction: "Create"};
            this.$scope.entry.serviceInfo[index].intervalDetails.push(serviceDetailsEntry);
            this.addDate();
        }

        private addNewRow(divName: string) {
            if (divName === 'academic') {
                let academicEntry: IAcademicInformationModel;
                academicEntry = {id: null, employeeId: this.$scope.data.userId, degree: null, institution: "", passingYear: null, dbAction: "Create"};
                this.$scope.entry.academic.push(academicEntry);
            } else if (divName === 'publication') {
                let publicationEntry: IPublicationInformationModel;
                publicationEntry = {id: null, employeeId: this.$scope.data.userId, publicationTitle: "", publicationType: null, publicationInterestGenre: "", publicationWebLink: "", publisherName: "", dateOfPublication: "", publicationISSN: "", publicationIssue: "",
                    publicationVolume: "", publicationJournalName: "", publicationCountry: null, status: "", publicationPages: "", appliedOn: "", actionTakenOn: "", rowNumber: null, dbAction: "Create"};
                this.$scope.entry.publication.push(publicationEntry);
            } else if (divName === 'training') {
                let trainingEntry: ITrainingInformationModel;
                trainingEntry = {id: null, employeeId: this.$scope.data.userId, trainingName: "", trainingInstitution: "", trainingFrom: "", trainingTo: "", dbAction: "Create"};
                this.$scope.entry.training.push(trainingEntry);
            } else if (divName === 'award') {
                let awardEntry: IAwardInformationModel;
                awardEntry = {id: null, employeeId: this.$scope.data.userId, awardName: "", awardInstitute: "", awardedYear: "", awardShortDescription: "", dbAction: "Create"};
                this.$scope.entry.award.push(awardEntry);
            } else if (divName === 'experience') {
                let experienceEntry: IExperienceInformationModel;
                experienceEntry = {id: null, employeeId: this.$scope.data.userId, experienceInstitution: "", experienceDesignation: "", experienceFrom: "", experienceTo: "", dbAction: "Create"};
                this.$scope.entry.experience.push(experienceEntry);
            } else if(divName = 'service'){
                let serviceEntry: IServiceInformationModel;
                serviceEntry = {id: null, employeeId: this.$scope.data.userId, department: null, designation: null, employmentType: null,
                    joiningDate: "", resignDate: "", dbAction: "Create", intervalDetails: Array<IServiceDetailsModel>()};
                this.$scope.entry.serviceInfo.push(serviceEntry);
            }
            this.addDate();
        }

        private deleteRow(divName: string, index: number, parentIndex?: number) {
            if (divName === 'academic') {
                this.$scope.entry.academic.splice(index, 1);
            } else if (divName === 'publication') {
                this.$scope.entry.publication.splice(index, 1);
            } else if (divName === 'training') {
                this.$scope.entry.training.splice(index, 1);
            } else if (divName === 'award') {
                this.$scope.entry.award.splice(index, 1);
            } else if (divName === 'experience') {
                this.$scope.entry.experience.splice(index, 1);
            } else if(divName == "serviceInfo") {
                this.$scope.entry.serviceInfo.splice(index, 1);
            } else if(divName == "serviceDetails"){
                this.$scope.entry.serviceInfo[parentIndex].intervalDetails.splice(index, 1);
            }
        }

        private convertToJson(convertThis: string, obj: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {}; let JsonArray = []; let item: any = {};
            if (convertThis === "personal") {
                item['personal'] = obj;
            } else if (convertThis === "academic") {
                item['academic'] = obj;
            } else if (convertThis === "publication"){
                item['publication'] = obj;
            } else if (convertThis === "training") {
                item['training'] = obj;
            } else if (convertThis === "award") {
                item['award'] = obj;
            } else if (convertThis === "experience") {
                item['experience'] = obj;
            } else if (convertThis === "additional") {
                item['additional'] = obj;
            } else if(convertThis == "service"){
                item['service'] = obj;
            }
            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;
            defer.resolve(JsonObject);
            return defer.promise;
        }

        private addDate(): void {
            setTimeout(function () {
                $('.datepicker-default').datepicker();
                $('.datepicker-default').on('change', function () {
                    $('.datepicker').hide();
                });
            }, 100);
            setTimeout(function () {
                $('.modified-datepicker').datepicker({
                    // startView: 1,
                    // minViewMode: 1
                });
                $('.modified-datepicker').on('change', function () {
                    $('.datepicker').hide();
                });
            }, 100);
            setTimeout(function () {
                $('.custom-datepicker').datepicker({
                    // startView: 2,
                    // minViewMode: 2

                });
                $('.custom-datepicker').on('change', function () {
                    $('.datepicker').hide();
                });
            }, 100);
        }
    }
    UMS.controller("EmployeeInformation", EmployeeInformation);
}