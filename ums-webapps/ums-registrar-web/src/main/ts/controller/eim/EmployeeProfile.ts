module ums {
    interface IEmployeeProfile extends ng.IScope {
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
        deleteAcademicObjects: Array<IAcademicInformationModel>;
        deletedPublicationObjects: Array<IPublicationInformationModel>;
        deletedTrainingObjects: Array<ITrainingInformationModel>;
        deletedAwardObjects: Array<IAwardInformationModel>;
        deletedExperienceObjects: Array<IExperienceInformationModel>;
        userId: string;


        showServiceInputDiv: boolean;
        showServiceLabelDiv: boolean;
        showServiceEditButton: boolean;
        designations: Array<ICommon>;
        employmentTypes: Array<ICommon>;
        serviceIntervals: Array<ICommon>;
        serviceRegularIntervals: Array<ICommon>;
        serviceContractIntervals: Array<ICommon>;
        departments: Array<IDepartment>;
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

    class EmployeeProfile {
        public static $inject = ['registrarConstants', '$scope', '$q',
            'countryService', 'divisionService', 'districtService', 'thanaService',
            'personalInformationService', 'academicInformationService', 'publicationInformationService',
            'trainingInformationService', 'awardInformationService', 'experienceInformationService',
            'areaOfInterestService', 'areaOfInterestInformationService', 'additionalInformationService',
            'userService', 'cRUDDetectionService', 'serviceInformationService', 'serviceInformationDetailService'];

        constructor(private registrarConstants: any,
                    private $scope: IEmployeeProfile,
                    private $q: ng.IQService,
                    private countryService: CountryService,
                    private divisionService: DivisionService,
                    private districtService: DistrictService,
                    private thanaService: ThanaService,
                    private personalInformationService: PersonalInformationService,
                    private academicInformationService: AcademicInformationService,
                    private publicationInformationService: PublicationInformationService,
                    private trainingInformationService: TrainingInformationService,
                    private awardInformationService: AwardInformationService,
                    private experienceInformationService: ExperienceInformationService,
                    private areaOfInterestService: AreaOfInterestService,
                    private areaOfInterestInformationService: AreaOfInterestInformationService,
                    private additionalInformationService: AdditionalInformationService,
                    private userService: UserService,
                    private cRUDDetectionService: CRUDDetectionService,
                    private serviceInformationService: ServiceInformationService,
                    private serviceInformationDetailService: ServiceInformationDetailService) {

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
                customItemPerPage: null
            };
            $scope.showServiceEditButton = false;
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

            this.initialization();
            this.addDate();
        }

        private initialization() {
            this.userService.fetchCurrentUserInfo().then((user: any) => {
                this.$scope.userId = user.employeeId;
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
                                    this.getPreviousFormValues();
                                    this.setViewModeInitially();
                                });
                            });
                        });
                    });
                });
            });
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

        private getPreviousFormValues() {
            this.getPersonalInformation();
            this.getAcademicInformation();
            this.getAwardInformation();
            this.getPublicationInformation();
            this.getPublicationInformationWithPagination();
            this.getExperienceInformation();
            this.getTrainingInformation();
            this.getAdditionalInformation();
            this.getServiceInformation();
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
            }
        }

        private submitPersonalForm(): void {
            this.$scope.entry.personal.employeeId = this.$scope.userId;
            if (this.cRUDDetectionService.isObjectEmpty(this.$scope.previousPersonalInformation)) {
                this.convertToJson('personal', this.$scope.entry.personal).then((json: any) => {
                    this.personalInformationService.savePersonalInformation(json).then((message: any) => {
                        this.getPersonalInformation();
                        this.enableViewMode('personal');
                    });
                });
            }
            else {
                this.convertToJson('personal', this.$scope.entry.personal).then((json: any) => {
                    this.personalInformationService.updatePersonalInformation(json).then((message: any) => {
                        this.getPersonalInformation();
                        this.enableViewMode('personal');
                    });
                });
            }
        }

        private submitAcademicForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousAcademicInformation, angular.copy(this.$scope.entry.academic), this.$scope.deleteAcademicObjects)
                .then((academicObjects:any)=>{
                    this.convertToJson('academic', academicObjects).then((json: any) => {
                        this.academicInformationService.saveAcademicInformation(json)
                            .then((message: any) => {
                                this.getAcademicInformation();
                                this.enableViewMode('academic');
                            });
                    });
                });
        }

        private submitPublicationForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousPublicationInformation, angular.copy(this.$scope.entry.publication), this.$scope.deletedPublicationObjects).then((publicationObjects: any) => {
                this.convertToJson('publication', publicationObjects)
                    .then((json: any) => {
                        this.publicationInformationService.savePublicationInformation(json)
                            .then((message: any) => {
                                this.getPublicationInformation();
                                this.getPublicationInformationWithPagination();
                                this.enableViewMode('publication');
                            });
                    });
            });
        }

        private submitTrainingForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousTrainingInformation, angular.copy(this.$scope.entry.training), this.$scope.deletedTrainingObjects).then((trainingObjects: any)=>{
                this.convertToJson('training', trainingObjects)
                    .then((json: any) => {
                        this.trainingInformationService.saveTrainingInformation(json)
                            .then((message: any) => {
                                this.getTrainingInformation();
                                this.enableViewMode('training');
                            });
                    });
            });
        }

        private submitAwardForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousAwardInformation, angular.copy(this.$scope.entry.award), this.$scope.deletedAwardObjects).then((awardObjects: any)=>{
                this.convertToJson('award', awardObjects)
                    .then((json: any) => {
                        this.awardInformationService.saveAwardInformation(json)
                            .then((message: any) => {
                                this.getAwardInformation();
                                this.enableViewMode('award');
                            });
                    });
            });

        }

        private submitExperienceForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousExperienceInformation, angular.copy(this.$scope.entry.experience), this.$scope.deletedExperienceObjects).then((experienceObjects: any)=>{
                this.convertToJson('experience', experienceObjects)
                    .then((json: any) => {
                        this.experienceInformationService.saveExperienceInformation(json)
                            .then((message: any) => {
                                this.getExperienceInformation();
                                this.enableViewMode('experience');
                            });
                    });
            });
        }


        private submitAdditionalForm(): void {
            this.$scope.entry.additional.employeeId = this.$scope.userId;
            this.convertToJson('additional', this.$scope.entry.additional)
                .then((json: any) => {
                    this.additionalInformationService.saveAdditionalInformation(json)
                        .then((message: any) => {
                            this.getAdditionalInformation();
                            this.enableViewMode('additional');
                        });
                });
        }

        private getPersonalInformation() {
            this.$scope.entry.personal = <IPersonalInformationModel>{};

            this.personalInformationService.getPersonalInformation(this.$scope.userId)
                .then((personalInformation: any) => {
                    if (personalInformation.length > 0) {
                        this.$scope.entry.personal = personalInformation[0];
                    }
                }).then(() => {
                this.$scope.previousPersonalInformation = angular.copy(this.$scope.entry.personal);
            });
        }

        private getAcademicInformation() {
            this.$scope.entry.academic = [];
            this.$scope.previousAcademicInformation = [];
            this.$scope.deleteAcademicObjects = [];

            this.academicInformationService.getAcademicInformation(this.$scope.userId)
                .then((academicInformation: any) => {
                    this.$scope.entry.academic = academicInformation;
                }).then(() => {
                this.$scope.previousAcademicInformation = angular.copy(this.$scope.entry.academic);
            });
        }

        private getPublicationInformation() {
            this.$scope.entry.publication = [];
            this.$scope.previousPublicationInformation = [];
            this.$scope.deletedPublicationObjects = [];

            this.publicationInformationService.getPublicationInformation(this.$scope.userId)
                .then((publicationInformation: any) => {
                    this.$scope.data.totalRecord = publicationInformation.length;
                    this.$scope.entry.publication = publicationInformation;
                }).then(()=> {
                this.$scope.previousPublicationInformation = angular.copy(this.$scope.entry.publication);
            });
        }

        private getPublicationInformationWithPagination(){
            this.$scope.entry.publication = [];
            this.publicationInformationService.getPublicationInformationViewWithPagination(this.$scope.userId, this.$scope.pagination.currentPage, this.$scope.data.itemPerPage).then((publicationInformationWithPagination: any) => {
                this.$scope.paginatedPublication = publicationInformationWithPagination;
            });
        }


        private getTrainingInformation() {
            this.$scope.entry.training = [];
            this.$scope.previousTrainingInformation = [];
            this.$scope.deletedTrainingObjects = [];

            this.trainingInformationService.getTrainingInformation(this.$scope.userId).then((trainingInformation: any) => {
                this.$scope.entry.training = trainingInformation;
            }).then(()=>{
                this.$scope.previousTrainingInformation = angular.copy(this.$scope.entry.training);
            });
        }

        private getAwardInformation() {
            this.$scope.entry.award = [];
            this.$scope.previousAwardInformation = [];
            this.$scope.deletedAwardObjects = [];

            this.awardInformationService.getAwardInformation(this.$scope.userId).then((awardInformation: any) => {
                this.$scope.entry.award = awardInformation;
            }).then(() =>{
                this.$scope.previousAwardInformation = angular.copy(this.$scope.entry.award);
            });
        }

        private getExperienceInformation() {
            this.$scope.entry.experience = [];
            this.$scope.previousExperienceInformation = [];
            this.$scope.deletedExperienceObjects = [];

            this.experienceInformationService.getExperienceInformation(this.$scope.userId).then((experienceInformation: any) => {
                this.$scope.entry.experience = experienceInformation;
            }).then(() => {
                this.$scope.previousExperienceInformation = angular.copy(this.$scope.entry.experience);
            });
        }

        private getAdditionalInformation() {
            this.$scope.entry.additional = null;
            this.additionalInformationService.getAdditionalInformation(this.$scope.userId).then((additional: any) => {
                this.$scope.entry.additional = additional[0];
            });
        }

        private getServiceInformation(): void {
            this.$scope.entry.serviceInfo = [];
            this.serviceInformationService.getServiceInformation(this.$scope.userId).then((services: any) => {
                this.$scope.entry.serviceInfo = services;
            });
        }

        private changePresentAddressDistrict() {
            this.$scope.presentAddressDistricts = [];
            let districtLength = this.$scope.allDistricts.length;
            let index = 0;

            for (let i = 0; i < districtLength; i++) {
                if (this.$scope.entry.personal.preAddressDivision.id === this.$scope.allDistricts[i].foreign_id) {
                    this.$scope.presentAddressDistricts[index++] = this.$scope.allDistricts[i];
                }
            }
        }

        private changePermanentAddressDistrict() {
            this.$scope.permanentAddressDistricts = [];
            let districtLength = this.$scope.allDistricts.length;
            let index = 0;

            for (let i = 0; i < districtLength; i++) {
                if (this.$scope.entry.personal.perAddressDivision.id === this.$scope.allDistricts[i].foreign_id) {
                    this.$scope.permanentAddressDistricts[index++] = this.$scope.allDistricts[i];
                }
            }
        }

        private changePresentAddressThana() {
            this.$scope.presentAddressThanas = [];
            let thanaLength = this.$scope.allThanas.length;
            let index = 0;

            for (let i = 0; i < thanaLength; i++) {
                if (this.$scope.entry.personal.preAddressDistrict.id === this.$scope.allThanas[i].foreign_id) {
                    this.$scope.presentAddressThanas[index++] = this.$scope.allThanas[i];
                }
            }
        }

        private changePermanentAddressThana() {
            this.$scope.permanentAddressThanas = [];
            let thanaLength = this.$scope.allThanas.length;
            let index = 0;

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
            }
            else {
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
            }
            else {
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
            }
            else if (this.$scope.data.supOptions === "2") {
                this.$scope.entry.personal.emergencyContactAddress =
                    this.$scope.entry.personal.preAddressLine1 == null ? "" : this.$scope.entry.personal.preAddressLine1
                    + " " + this.$scope.entry.personal.preAddressLine2 == null ? "" : this.$scope.entry.personal.preAddressLine2
                    + " " + this.$scope.entry.personal.preAddressThana.name == null ? "" : this.$scope.entry.personal.preAddressThana.name
                    + " " + this.$scope.entry.personal.preAddressDistrict.name == null ? "" : this.$scope.entry.personal.preAddressDistrict.name
                    + " - " + this.$scope.entry.personal.preAddressPostCode == null ? "" : this.$scope.entry.personal.preAddressPostCode;
            }
            else if (this.$scope.data.supOptions === "3") {
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
            this.getPublicationInformationWithPagination();
        }

        private changeItemPerPage(){
            if(this.$scope.data.customItemPerPage == "" || this.$scope.data.customItemPerPage == null) {}
            else{
                this.$scope.data.itemPerPage = this.$scope.data.customItemPerPage;
                this.getPublicationInformationWithPagination();
            }
        }
        private enableViewMode(formName: string) {
            if (formName === 'personal') {
                this.$scope.showPersonalInputDiv = false;
                this.$scope.showPersonalLabelDiv = true;
            }
            else if (formName === 'academic') {
                this.$scope.showAcademicInputDiv = false;
                this.$scope.showAcademicLabelDiv = true;
            }
            else if (formName === 'publication') {
                this.$scope.showPublicationInputDiv = false;
                this.$scope.showPublicationLabelDiv = true;
            }
            else if (formName === 'training') {
                this.$scope.showTrainingInputDiv = false;
                this.$scope.showTrainingLabelDiv = true;
            }
            else if (formName === 'award') {
                this.$scope.showAwardInputDiv = false;
                this.$scope.showAwardLabelDiv = true;
            }
            else if (formName === 'experience') {
                this.$scope.showExperienceInputDiv = false;
                this.$scope.showExperienceLabelDiv = true;
            }
            else if(formName === 'additional'){
                this.$scope.showAdditionalInputDiv = false;
                this.$scope.showAdditionalLabelDiv = true;
            }
            else if(formName === 'service'){
                this.$scope.showServiceInputDiv = false;
                this.$scope.showServiceLabelDiv = true;
            }
        }
        private enableEditMode(formName: string) {
            if (formName === "personal") {
                this.$scope.showPersonalLabelDiv = false;
                this.$scope.showPersonalInputDiv = true;
            }
            else if (formName === "academic") {
                this.$scope.showAcademicLabelDiv = false;
                this.$scope.showAcademicInputDiv = true;
            }
            else if (formName === "publication") {
                this.$scope.showPublicationLabelDiv = false;
                this.$scope.showPublicationInputDiv = true;
            }
            else if (formName === "training") {
                this.$scope.showTrainingLabelDiv = false;
                this.$scope.showTrainingInputDiv = true;
            }
            else if (formName === "award") {
                this.$scope.showAwardLabelDiv = false;
                this.$scope.showAwardInputDiv = true;
            }
            else if (formName === "experience") {
                this.$scope.showExperienceLabelDiv = false;
                this.$scope.showExperienceInputDiv = true;
            }
            else if(formName === 'additional'){
                this.$scope.showAdditionalLabelDiv = false;
                this.$scope.showAdditionalInputDiv = true;
            }
        }

        private addNewRow(divName: string) {
            if (divName === 'academic') {
                let academicEntry: IAcademicInformationModel;
                academicEntry = {
                    id: null,
                    employeeId: this.$scope.userId,
                    degree: null,
                    institution: "",
                    passingYear: null,
                    dbAction: "Create"
                };
                this.$scope.entry.academic.push(academicEntry);
            }
            else if (divName === 'publication') {
                let publicationEntry: IPublicationInformationModel;
                publicationEntry = {
                    id: null,
                    employeeId: this.$scope.userId,
                    publicationTitle: "",
                    publicationType: null,
                    publicationInterestGenre: "",
                    publicationWebLink: "",
                    publisherName: "",
                    dateOfPublication: "",
                    publicationISSN: "",
                    publicationIssue: "",
                    publicationVolume: "",
                    publicationJournalName: "",
                    publicationCountry: null,
                    status: "",
                    publicationPages: "",
                    appliedOn: "",
                    actionTakenOn: "",
                    rowNumber: null,
                    dbAction: "Create"
                };
                this.$scope.entry.publication.push(publicationEntry);
            }
            else if (divName === 'training') {
                let trainingEntry: ITrainingInformationModel;
                trainingEntry = {
                    id: null,
                    employeeId: this.$scope.userId,
                    trainingName: "",
                    trainingInstitution: "",
                    trainingFrom: "",
                    trainingTo: "",
                    dbAction: "Create"
                };
                this.$scope.entry.training.push(trainingEntry);
            }
            else if (divName === 'award') {
                let awardEntry: IAwardInformationModel;
                awardEntry = {
                    id: null,
                    employeeId: this.$scope.userId,
                    awardName: "",
                    awardInstitute: "",
                    awardedYear: "",
                    awardShortDescription: "",
                    dbAction: "Create"
                };
                this.$scope.entry.award.push(awardEntry);
            }
            else if (divName === 'experience') {
                let experienceEntry: IExperienceInformationModel;
                experienceEntry = {
                    id: null,
                    employeeId: this.$scope.userId,
                    experienceInstitution: "",
                    experienceDesignation: "",
                    experienceFrom: "",
                    experienceTo: "",
                    dbAction: "Create"
                };
                this.$scope.entry.experience.push(experienceEntry);
            }
            this.addDate();
        }

        private deleteRow(divName: string, index: number) {
            if (divName === 'academic') {
                if(this.$scope.entry.academic[index].id != null){
                    this.$scope.entry.academic[index].dbAction = "Delete";
                    this.$scope.deleteAcademicObjects.push(this.$scope.entry.academic[index]);
                }
                this.$scope.entry.academic.splice(index, 1);
            }
            else if (divName === 'publication') {
                if(this.$scope.entry.publication[index].id != null){
                    this.$scope.entry.publication[index].dbAction = "Delete";
                    this.$scope.deletedPublicationObjects.push(this.$scope.entry.publication[index]);
                }
                this.$scope.entry.publication.splice(index, 1);
            }
            else if (divName === 'training') {
                if(this.$scope.entry.training[index].id != null){
                    this.$scope.entry.training[index].dbAction = "Delete";
                    this.$scope.deletedTrainingObjects.push(this.$scope.entry.training[index]);
                }
                this.$scope.entry.training.splice(index, 1);
            }
            else if (divName === 'award') {
                if(this.$scope.entry.award[index].id != null){
                    this.$scope.entry.award[index].dbAction = "Delete";
                    this.$scope.deletedAwardObjects.push(this.$scope.entry.award[index]);
                }
                this.$scope.entry.award.splice(index, 1);
            }
            else if (divName === 'experience') {
                if(this.$scope.entry.experience[index].id != null){
                    this.$scope.entry.experience[index].dbAction = "Delete";
                    this.$scope.deletedExperienceObjects.push(this.$scope.entry.experience[index]);
                }
                this.$scope.entry.experience.splice(index, 1);
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
            else if (convertThis === "publication"){
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
    UMS.controller("EmployeeProfile", EmployeeProfile);
}