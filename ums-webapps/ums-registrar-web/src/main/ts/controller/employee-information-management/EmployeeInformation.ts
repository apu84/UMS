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
        showFilteredEmployeeList: boolean;
        showEmployeeInformation: boolean;
        showSelectPanel: boolean;
        showTableData: boolean;
        borderColor: string;
        supOptions: string;
        userAreaOfInterests: string;
        customItemPerPage: number;
        degreeNameMap: any;
        genderNameMap: any;
        religionMap: any;
        nationalityMap: any;
        bloodGroupMap: any;
        martialStatusMap: any;
        relationMap: any;
        countryMap: any;
        divisionMap: any;
        districtMap: any;
        thanaMap: any;
        publicationTypeMap: any;
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
        view: Function;
        entry: IEntry;
        genderTypes: Array<IGender>;
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
        userRoleId: number;
        allUser: Array<Employee>;


        changedUserId: string;
        filterData: string;
        searchBy: string;
        changedUserName: string;
        showSearchByUserId: boolean;
        showSearchByUserName: boolean;
        showSearchByDepartment: boolean;
        changedDepartment: IDepartment;
        departments: Array<IDepartment>;
        getSearchFields: Function;
        getEmployeeInformation: Function;
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
    export interface IEntry {
        personal: IPersonalInformationModel;
        academic: Array<IAcademicInformationModel>;
        publication: Array<IPublicationInformationModel>;
        training: Array<ITrainingInformationModel>;
        award: Array<IAwardInformationModel>;
        experience: Array<IExperienceInformationModel>;
        additional: IAdditionalInformationModel;
    }

    class EmployeeInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce','countryService',
            'divisionService', 'districtService', 'thanaService', 'personalInformationService', 'academicInformationService',
            'publicationInformationService', 'trainingInformationService', 'awardInformationService', 'experienceInformationService',
            'areaOfInterestService', 'bloodGroupService', 'nationalityService', 'religionService', 'relationTypeService', 'maritalStatusService', 'areaOfInterestInformationService', 'additionalInformationService', 'userService', 'cRUDDetectionService', 'departmentService', 'employeeService'];

        constructor(private registrarConstants: any, private $scope: IEmployeeInformation, private $q: ng.IQService, private notify: Notify,
                    private $window: ng.IWindowService, private $sce: ng.ISCEService, private countryService: CountryService,
                    private divisionService: DivisionService, private districtService: DistrictService, private thanaService: ThanaService, private personalInformationService: PersonalInformationService,
                    private academicInformationService: AcademicInformationService, private publicationInformationService: PublicationInformationService, private trainingInformationService: TrainingInformationService,
                    private awardInformationService: AwardInformationService, private experienceInformationService: ExperienceInformationService, private areaOfInterestService: AreaOfInterestService, private bloodGroupService: BloodGroupService, private nationalityService: NationalityService,
                    private religionService: ReligionService, private relationTypeService: RelationTypeService, private maritalStatusService: MaritalStatusService,
                    private areaOfInterestInformationService: AreaOfInterestInformationService, private additionalInformationService: AdditionalInformationService, private userService: UserService, private cRUDDetectionService: CRUDDetectionService, private departmentService: DepartmentService,
                    private employeeService: EmployeeService) {

            $scope.entry = {
                personal: <IPersonalInformationModel> {},
                academic: Array<IAcademicInformationModel>(),
                publication: Array<IPublicationInformationModel>(),
                training: Array<ITrainingInformationModel>(),
                award: Array<IAwardInformationModel>(),
                experience: Array<IExperienceInformationModel>(),
                additional: <IAdditionalInformationModel> {}
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
                filterData: ""
            };
            $scope.pagination = {};
            $scope.pagination.currentPage = 1;
            $scope.genderTypes = this.registrarConstants.genderTypes;
            $scope.publicationTypes = this.registrarConstants.publicationTypes;
            $scope.degreeNames = this.registrarConstants.degreeTypes;
            $scope.bloodGroups = this.registrarConstants.bloodGroupTypes;
            $scope.maritalStatus = this.registrarConstants.maritalStatuses;
            $scope.religions = this.registrarConstants.religionTypes;
            $scope.relations = this.registrarConstants.relationTypes;
            $scope.nationalities = this.registrarConstants.nationalityTypes;

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
            $scope.view = this.view.bind(this);

            this.initialization();
            this.addDate();
        }

        private view(user: any): void{
            this.$scope.userId = user.id;
            this.$scope.showFilteredEmployeeList = false;
            this.$scope.showEmployeeInformation = true;
            this.getPreviousFormValues();
            this.setViewModeInitially();
        }

        private getAllUser(): ng.IPromise<any>{
            let defer = this.$q.defer();
            this.employeeService.getAll().then((users: any)=>{
                defer.resolve(users);
            });
            return defer.promise;
        }

        private getDepartment(): void{
            this.$scope.departments = Array<IDepartment>();
            this.departmentService.getAll().then((departments: any)=> {
                this.$scope.departments = departments;
                console.log(this.$scope.departments);
            });
        }

        private getSearchFields(): void{
            console.log(this.$scope.data.searchBy);
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

        private getEmployeeInformation(): void{
            if(this.$scope.data.searchBy == "1"){
                this.$scope.showFilteredEmployeeList = false;
                this.$scope.showEmployeeInformation = true;
                this.$scope.userId = this.$scope.data.changedUserId;
                if(this.$scope.userId == "" || this.$scope.userId == null){
                    this.notify.error("Empty or Null Id");
                }
                else {
                    Utils.expandRightDiv();
                    this.getPreviousFormValues();
                    this.setViewModeInitially();
                }
            } else if (this.$scope.data.searchBy == "2"){
                Utils.expandRightDiv();
                this.$scope.showEmployeeInformation = false;
                this.$scope.showFilteredEmployeeList = true;
                if(this.$scope.data.changedUserName != null || this.$scope.data.changedUserName != ""){
                    this.$scope.showTableData = true;
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


        private initialization() {
            this.getUser().then((user: any) => {
                this.$scope.userId = user.employeeId;
                this.$scope.userRoleId = user.roleId;
                this.getAllUser().then((users: any) => {
                    this.$scope.allUser = users;
                    this.getCountry().then((countries: any) => {
                        this.$scope.countries = countries;
                        this.getDivision().then((divisions: any) => {
                            this.$scope.divisions = divisions;
                            this.getDistrict().then((districts: any) => {
                                this.$scope.presentAddressDistricts = districts;
                                this.$scope.permanentAddressDistricts = districts;
                                this.$scope.allDistricts = districts;
                                this.getThana().then((thanas: any) => {
                                    this.$scope.presentAddressThanas = thanas;
                                    this.$scope.permanentAddressThanas = thanas;
                                    this.$scope.allThanas = thanas;
                                    this.getAreaOfInterest().then((aois: any) => {
                                        this.$scope.arrayOfAreaOfInterest = aois;
                                        this.createMap();
                                        console.log(this.$scope.userId + " outer " + this.$scope.userRoleId);
                                        this.notify.success(this.$scope.userId + " outer " + this.$scope.userRoleId);
                                        if(this.$scope.userRoleId != 82) {
                                            this.$scope.showSelectPanel = false;
                                            this.$scope.showFilteredEmployeeList = false;
                                            this.getPreviousFormValues();
                                            this.$scope.showEmployeeInformation = true;
                                            this.setViewModeInitially();
                                        }
                                        else{
                                            this.$scope.showEmployeeInformation = false;
                                            this.$scope.showSelectPanel = true;
                                            this.$scope.showFilteredEmployeeList = true;
                                        }
                                    });
                                });
                            });
                        });
                    });
                });
            });
        }

        private getUser(): ng.IPromise<any>{
            let defer = this.$q.defer();
            this.userService.fetchCurrentUserInfo().then((user: any) => {
                defer.resolve(user);
            });
            return defer.promise;
        }

        private setViewModeInitially() {
            this.enableViewMode('personal');
            this.enableViewMode('academic');
            this.enableViewMode('publication');
            this.enableViewMode('training');
            this.enableViewMode('award');
            this.enableViewMode('experience');
            this.enableViewMode('additional');
        }

        private getPreviousFormValues() {
            this.getPersonalInformation(this.$scope.userId);
            this.getAcademicInformation(this.$scope.userId);
            this.getAwardInformation(this.$scope.userId);
            this.getPublicationInformation(this.$scope.userId);
            this.getPublicationInformationWithPagination(this.$scope.userId);
            this.getExperienceInformation(this.$scope.userId);
            this.getTrainingInformation(this.$scope.userId);
            //this.getAdditionalInformation(this.$scope.userId);

        }

        private getAreaOfInterest() : ng.IPromise<any>{
            let defer = this.$q.defer();
            this.areaOfInterestService.getAll().then((aoi: any)=>{defer.resolve(aoi);});
            return defer.promise;
        }

        private getCountry(): ng.IPromise<any>{
            let defer = this.$q.defer();
            this.countryService.getCountryList().then((countries: any) => {defer.resolve(countries.entries);});
            return defer.promise;
        }

        private getDivision(): ng.IPromise<any>{
            let defer = this.$q.defer();
            this.divisionService.getDivisionList().then((divisions: any) => { defer.resolve(divisions.entries);});
            return defer.promise;
        }

        private getDistrict(): ng.IPromise<any>{
            let defer = this.$q.defer();
            this.districtService.getDistrictList().then((districts: any) => { defer.resolve(districts.entries);});
            return defer.promise;
        }

        private getThana() : ng.IPromise<any>{
            let defer = this.$q.defer();
            this.thanaService.getThanaList().then((thanas: any) => {defer.resolve(thanas.entries);});
            return defer.promise;
        }

        private createMap() {
            this.$scope.degreeNameMap = {};
            this.$scope.genderNameMap = {};
            this.$scope.publicationTypeMap = {};
            this.$scope.religionMap = {};
            this.$scope.nationalityMap = {};
            this.$scope.bloodGroupMap = {};
            this.$scope.martialStatusMap = {};
            this.$scope.relationMap = {};
            this.$scope.countryMap = {};
            this.$scope.divisionMap = {};
            this.$scope.districtMap = {};
            this.$scope.thanaMap = {};
            this.$scope.aoiMap = {};

            for (let i = 0; i < this.$scope.degreeNames.length; i++) {
                this.$scope.degreeNameMap[this.$scope.degreeNames[i].id] = this.$scope.degreeNames[i];
            }
            for (let i = 0; i < this.$scope.genderTypes.length; i++) {
                this.$scope.genderNameMap[this.$scope.genderTypes[i].id] = this.$scope.genderTypes[i];
            }
            for (let i = 0; i < this.$scope.publicationTypes.length; i++) {
                this.$scope.publicationTypeMap[this.$scope.publicationTypes[i].name] = this.$scope.publicationTypes[i];
            }
            for (let i = 0; i < this.$scope.religions.length; i++) {
                this.$scope.religionMap[this.$scope.religions[i].id] = this.$scope.religions[i];
            }
            for (let i = 0; i < this.$scope.nationalities.length; i++) {
                this.$scope.nationalityMap[this.$scope.nationalities[i].id] = this.$scope.nationalities[i];
            }
            for (let i = 0; i < this.$scope.bloodGroups.length; i++) {
                this.$scope.bloodGroupMap[this.$scope.bloodGroups[i].id] = this.$scope.bloodGroups[i];
            }
            for (let i = 0; i < this.$scope.maritalStatus.length; i++) {
                this.$scope.martialStatusMap[this.$scope.maritalStatus[i].id] = this.$scope.maritalStatus[i];
            }
            for (let i = 0; i < this.$scope.relations.length; i++) {
                this.$scope.relationMap[this.$scope.relations[i].id] = this.$scope.relations[i];
            }
            for (let i = 0; i < this.$scope.countries.length; i++) {
                this.$scope.countryMap[this.$scope.countries[i].id] = this.$scope.countries[i];
            }
            for (let i = 0; i < this.$scope.divisions.length; i++) {
                this.$scope.divisionMap[this.$scope.divisions[i].id] = this.$scope.divisions[i];
            }
            for (let i = 0; i < this.$scope.allDistricts.length; i++) {
                this.$scope.districtMap[this.$scope.allDistricts[i].id] = this.$scope.allDistricts[i];
            }
            for (let i = 0; i < this.$scope.allThanas.length; i++) {
                this.$scope.thanaMap[this.$scope.allThanas[i].id] = this.$scope.allThanas[i];
            }
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
                this.$scope.entry.personal.gender = this.$scope.genderTypes[1];
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
            this.$scope.entry.personal.employeeId = this.$scope.userId;
            if (this.cRUDDetectionService.isObjectEmpty(this.$scope.previousPersonalInformation)) {
                this.convertToJson('personal', this.$scope.entry.personal).then((json: any) => {
                    this.personalInformationService.savePersonalInformation(json).then((message: any) => {
                        this.getPersonalInformation(this.$scope.userId);
                        this.enableViewMode('personal');
                    });
                });
            } else {
                this.convertToJson('personal', this.$scope.entry.personal).then((json: any) => {
                    this.personalInformationService.updatePersonalInformation(json).then((message: any) => {
                        this.getPersonalInformation(this.$scope.userId);
                        this.enableViewMode('personal');
                    });
                });
            }
        }

        private submitAcademicForm(): void {
            let academicObjects = this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousAcademicInformation, this.$scope.entry.academic);
            this.convertToJson('academic', academicObjects).then((json: any) => {
                this.academicInformationService.saveAcademicInformation(json)
                    .then((message: any) => {
                        this.getAcademicInformation(this.$scope.userId);
                        this.enableViewMode('academic');
                    });
            });
        }

        private submitPublicationForm(): void {
            let publicationObjects = this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousPublicationInformation, this.$scope.entry.publication);
            this.convertToJson('publication', publicationObjects)
                .then((json: any) => {
                    this.publicationInformationService.savePublicationInformation(json)
                        .then((message: any) => {
                            this.getPublicationInformation(this.$scope.userId);
                            this.getPublicationInformationWithPagination(this.$scope.userId);
                            this.enableViewMode('publication');
                        });
                });
        }

        private submitTrainingForm(): void {
            let trainingObjects = this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousTrainingInformation, this.$scope.entry.training);
            this.convertToJson('training', trainingObjects)
                .then((json: any) => {
                    this.trainingInformationService.saveTrainingInformation(json)
                        .then((message: any) => {
                            this.getTrainingInformation(this.$scope.userId);
                            this.enableViewMode('training');
                        });
                });
        }

        private submitAwardForm(): void {
            let awardObjects = this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousAwardInformation, this.$scope.entry.award);
            this.convertToJson('award', awardObjects)
                .then((json: any) => {
                    this.awardInformationService.saveAwardInformation(json)
                        .then((message: any) => {
                            this.getAwardInformation(this.$scope.userId);
                            this.enableViewMode('award');
                        });
                });
        }

        private submitExperienceForm(): void {
            let experienceObjects = this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousExperienceInformation, this.$scope.entry.experience);
            this.convertToJson('experience', experienceObjects)
                .then((json: any) => {
                    this.experienceInformationService.saveExperienceInformation(json)
                        .then((message: any) => {
                            this.getExperienceInformation(this.$scope.userId);
                            this.enableViewMode('experience');
                        });
                });
        }

        private submitAdditionalForm(): void {
            this.$scope.entry.additional.employeeId = this.$scope.userId;
            console.log(this.$scope.entry.additional );

            this.convertToJson('additional', this.$scope.entry.additional)
                .then((json: any) => {
                    // this.additionalInformationService.saveAdditionalInformation(json)
                    //     .then((message: any) => {
                    //         this.getAreaOfInterestInformation(this.$scope.userId);
                    //         this.enableViewMode('additional');
                    //     });
                    this.getAreaOfInterestInformation(this.$scope.userId);
                           this.enableViewMode('additional');
                });
        }

        private getPersonalInformation(userId: string) {
            this.$scope.entry.personal = <IPersonalInformationModel>{};
            this.personalInformationService.getPersonalInformation(userId).then((personalInformation: any) => {
                if (personalInformation.length > 0) {
                    this.$scope.entry.personal = personalInformation[0];
                    this.$scope.entry.personal.gender = this.$scope.genderNameMap[personalInformation[0].genderId];
                    this.$scope.entry.personal.religion = this.$scope.religionMap[personalInformation[0].religionId];
                    this.$scope.entry.personal.maritalStatus = this.$scope.martialStatusMap[personalInformation[0].maritalStatusId];
                    this.$scope.entry.personal.gender = this.$scope.genderNameMap[personalInformation[0].gender];
                    this.$scope.entry.personal.nationality = this.$scope.nationalityMap[personalInformation[0].nationalityId];
                    this.$scope.entry.personal.bloodGroup = this.$scope.bloodGroupMap[personalInformation[0].bloodGroupId];
                    this.$scope.entry.personal.emergencyContactRelation = this.$scope.relationMap[personalInformation[0].emergencyContactRelationId];
                    this.$scope.entry.personal.preAddressCountry = this.$scope.countryMap[personalInformation[0].preAddressCountryId];
                    this.$scope.entry.personal.preAddressDivision = this.$scope.divisionMap[personalInformation[0].preAddressDivisionId];
                    this.$scope.entry.personal.preAddressDistrict = this.$scope.districtMap[personalInformation[0].preAddressDistrictId];
                    this.$scope.entry.personal.preAddressThana = this.$scope.thanaMap[personalInformation[0].preAddressThanaId];
                    this.$scope.entry.personal.perAddressCountry = this.$scope.countryMap[personalInformation[0].perAddressCountryId];
                    this.$scope.entry.personal.perAddressDivision = this.$scope.divisionMap[personalInformation[0].perAddressDivisionId];
                    this.$scope.entry.personal.perAddressDistrict = this.$scope.districtMap[personalInformation[0].perAddressDistrictId];
                    this.$scope.entry.personal.perAddressThana = this.$scope.thanaMap[personalInformation[0].perAddressThanaId];
                }
                this.$scope.previousPersonalInformation = angular.copy(this.$scope.entry.personal);
            });
        }

        private getAcademicInformation(userId: string) {
            this.$scope.entry.academic = Array<IAcademicInformationModel>();
            this.academicInformationService.getAcademicInformation(userId).then((academicInformation: any) => {
                for (let i = 0; i < academicInformation.length; i++) {
                    this.$scope.entry.academic[i] = academicInformation[i];
                    this.$scope.entry.academic[i].degree = this.$scope.degreeNameMap[academicInformation[i].degreeId];
                }
                this.$scope.previousAcademicInformation = angular.copy(this.$scope.entry.academic);
            });
        }

        private getPublicationInformation(userId: string) {
            this.$scope.entry.publication = Array<IPublicationInformationModel>();
            this.publicationInformationService.getPublicationInformation(userId).then((publicationInformation: any) => {
                this.$scope.data.totalRecord = publicationInformation.length;
                for (let i = 0; i < publicationInformation.length; i++) {
                    this.$scope.entry.publication[i] = publicationInformation[i];
                    this.$scope.entry.publication[i].publicationType = this.$scope.publicationTypeMap[publicationInformation[i].publicationType];
                }
                this.$scope.previousPublicationInformation = angular.copy(this.$scope.entry.publication);
            });
        }

        private getPublicationInformationWithPagination(userId: string){
            this.$scope.entry.publication = Array<IPublicationInformationModel>();
            this.publicationInformationService.getPublicationInformationViewWithPagination(userId, this.$scope.pagination.currentPage, this.$scope.data.itemPerPage).then((publicationInformationWithPagination: any) => {
                this.$scope.paginatedPublication = publicationInformationWithPagination;
                for (let i = 0; i < publicationInformationWithPagination.length; i++) {
                    this.$scope.paginatedPublication[i] = publicationInformationWithPagination[i];
                    this.$scope.paginatedPublication[i].publicationType = this.$scope.publicationTypeMap[publicationInformationWithPagination[i].publicationType];
                }
            });
        }

        private getTrainingInformation(userId: string) {
            this.$scope.entry.training = Array<ITrainingInformationModel>();
            this.trainingInformationService.getTrainingInformation(userId).then((trainingInformation: any) => {
                for (let i = 0; i < trainingInformation.length; i++) {
                    this.$scope.entry.training[i] = trainingInformation[i];
                }
                this.$scope.previousTrainingInformation = angular.copy(this.$scope.entry.training);
            });
        }

        private getAwardInformation(userId: string) {
            this.$scope.entry.award = Array<IAwardInformationModel>();
            this.awardInformationService.getAwardInformation(userId).then((awardInformation: any) => {
                for (let i = 0; i < awardInformation.length; i++) {
                    this.$scope.entry.award[i] = awardInformation[i];
                }
                this.$scope.previousAwardInformation = angular.copy(this.$scope.entry.award);
            });
        }

        private getExperienceInformation(userId: string) {
            this.$scope.entry.experience = Array<IExperienceInformationModel>();
            this.experienceInformationService.getExperienceInformation(userId).then((experienceInformation: any) => {
                for (let i = 0; i < experienceInformation.length; i++) {
                    this.$scope.entry.experience[i] = experienceInformation[i];
                }
                this.$scope.previousExperienceInformation = angular.copy(this.$scope.entry.experience);
            });
        }

        private getAreaOfInterestInformation(userId: string) {
            this.$scope.entry.additional.areaOfInterestInformation = Array<IAreaOfInterestInformationModel>();
            this.areaOfInterestInformationService.getAreaOfInterestInformation(userId).then((aoiInformation: any) => {
                for (let i = 0; i < aoiInformation.length; i++) {
                    this.$scope.entry.additional.areaOfInterestInformation[i] = aoiInformation[i];
                    this.$scope.entry.additional.areaOfInterestInformation[i] = this.$scope.aoiMap[aoiInformation[i].areaOfInterestId];
                }
            });
        }

        private getAdditionalInformation(userId: string) {
            this.$scope.entry.additional = <IAdditionalInformationModel>{};
            this.$scope.entry.additional.areaOfInterestInformation = Array<IAreaOfInterestInformationModel>();
            this.additionalInformationService.getAdditionalInformation(userId).then((additional: any) => {
                this.$scope.entry.additional = additional;
                this.getAreaOfInterestInformation(userId);
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
            this.getPublicationInformationWithPagination(this.$scope.userId);
        }

        private changeItemPerPage(){
            if(this.$scope.data.customItemPerPage == "" || this.$scope.data.customItemPerPage == null) {}
            else{
                this.$scope.data.itemPerPage = this.$scope.data.customItemPerPage;
                this.getPublicationInformationWithPagination(this.$scope.userId);
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
            }
        }

        private addNewRow(divName: string) {
            if (divName === 'academic') {
                let academicEntry: IAcademicInformationModel;
                academicEntry = {id: null, employeeId: this.$scope.userId, degree: null, degreeId: null, institution: "", passingYear: null, dbAction: ""};
                this.$scope.entry.academic.push(academicEntry);
            } else if (divName === 'publication') {
                let publicationEntry: IPublicationInformationModel;
                publicationEntry = {id: null, employeeId: this.$scope.userId, publicationTitle: "", publicationType: null, publicationInterestGenre: "", publicationWebLink: "", publisherName: "", dateOfPublication: "", publicationISSN: "", publicationIssue: "",
                    publicationVolume: "", publicationJournalName: "", publicationCountry: "", status: "", publicationPages: "", appliedOn: "", actionTakenOn: "", rowNumber: null, dbAction: ""};
                this.$scope.entry.publication.push(publicationEntry);
            } else if (divName === 'training') {
                let trainingEntry: ITrainingInformationModel;
                trainingEntry = {id: null, employeeId: this.$scope.userId, trainingName: "", trainingInstitution: "", trainingFrom: "", trainingTo: "", dbAction: ""};
                this.$scope.entry.training.push(trainingEntry);
            } else if (divName === 'award') {
                let awardEntry: IAwardInformationModel;
                awardEntry = {id: null, employeeId: this.$scope.userId, awardName: "", awardInstitute: "", awardedYear: "", awardShortDescription: "", dbAction: ""};
                this.$scope.entry.award.push(awardEntry);
            } else if (divName === 'experience') {
                let experienceEntry: IExperienceInformationModel;
                experienceEntry = {id: null, employeeId: this.$scope.userId, experienceInstitution: "", experienceDesignation: "", experienceFrom: "", experienceTo: "", dbAction: ""};
                this.$scope.entry.experience.push(experienceEntry);
            }
            this.addDate();
        }

        private deleteRow(divName: string, index: number) {
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
                console.log(obj);
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

        private initializeSelect2(selectBoxId, users, placeHolderText) {
            let data = users;
            $("#" + selectBoxId).select2({
                minimumInputLength: 0,
                query: function (options) {
                    let pageSize = 100;
                    let startIndex = (options.page - 1) * pageSize;
                    let filteredData = data;
                    if (options.term && options.term.length > 0) {
                        if (!options.context) {
                            let term = options.term.toLowerCase();
                            options.context = data.filter(function (metric: any) {
                                console.log(options.context);
                                return ( metric.id.indexOf(term) !== -1 );
                            });
                        }
                        filteredData = options.context;
                    }
                    options.callback({
                        context: filteredData,
                        results: filteredData.slice(startIndex, startIndex + pageSize),
                        more: (startIndex + pageSize) < filteredData.length
                    });
                },
                placeholder: placeHolderText
            });
        }
    }
    UMS.controller("EmployeeInformation", EmployeeInformation);
}