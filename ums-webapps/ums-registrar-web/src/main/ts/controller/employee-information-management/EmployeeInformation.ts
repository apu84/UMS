module ums {
    interface IEmployeeInformation extends ng.IScope {
        personalTab: boolean;
        academicTab: boolean;
        publicationTab: boolean;
        trainingTab: boolean;
        awardTab: boolean;
        experienceTab: boolean;

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

        showAcademicAddIcon: boolean;
        showPublicationAddIcon: boolean;
        showTrainingAddIcon: boolean;
        showAwardAddIcon: boolean;
        showExperienceAddIcon: boolean;

        showAcademicCrossIcon: boolean;
        showPublicationCrossIcon: boolean;
        showTrainingCrossIcon: boolean;
        showAwardCrossIcon: boolean;
        showExperienceCrossIcon: boolean;

        showRequireSign: boolean;
        showPermanentAddressCheckbox: boolean;
        required: boolean;

        disablePresentAddressDropdown: boolean;
        disablePermanentAddressDropdown: boolean;
        showSup: boolean;
        showPublicationISSNDiv: boolean;

        disablePersonalSubmitButton: boolean;
        disableAcademicSubmitButton: boolean;
        disablePublicationSubmitButton: boolean;
        disableTrainingSubmitButton: boolean;
        disableAwardSubmitButton: boolean;
        disableExperienceSubmitButton: boolean;

        showPersonalEditButton: boolean;
        showAcademicEditButton: boolean;
        showPublicationEditButton: boolean;
        showTrainingEditButton: boolean;
        showAwardEditButton: boolean;
        showExperienceEditButton: boolean;

        showPersonalCancelButton: boolean;
        showAcademicCancelButton: boolean;
        showPublicationCancelButton: boolean;
        showTrainingCancelButton: boolean;
        showAwardCancelButton: boolean;
        showExperienceCancelButton: boolean;

        borderColor: string;
        supOptions: string;

        degreeNameMap: any;
        genderNameMap: any;
        religionNameMap: any;
        nationalityMap: any;
        bloodGroupMap: any;
        martialStatusMap: any;
        publicationTypeMap: any;
        relationMap: any;
        countryMap: any;
        divisionMap: any;
        districtMap: any;
        thanaMap: any;
        data: any;
        pagination: any;
        //$$childTail: any;

        changeNav: Function;
        enableEditMode: Function;
        enableViewMode: Function;
        addNewRow: Function;
        deleteRow: Function;
        testData: Function;
        getPersonalInformation: Function;
        getAcademicInformation: Function;
        getPublicationInformation: Function;
        getTrainingInformation: Function;
        getAwardInformation: Function;
        getExperienceInformation: Function;
        submitPersonalForm: Function;
        submitAcademicForm: Function;
        submitPublicationForm: Function;
        submitTrainingForm: Function;
        submitAwardForm: Function;
        submitExperienceForm: Function;
        sameAsPresentAddress: Function;
        changePresentAddressDistrict: Function;
        changePresentAddressThana: Function;
        changePermanentAddressDistrict: Function;
        changePermanentAddressThana: Function;
        changePresentAddressFields: Function;
        changePermanentAddressFields: Function;
        fillEmergencyContactAddress: Function;
        getCountry: Function;
        getDivision: Function;
        getDistrictL: Function;
        getThana: Function;
        pageChanged: Function;

        entry: IEntry;
        gender: Array<IGender>;
        maritalStatus: Array<IMaritalStatus>;
        religions: Array<IReligion>;
        nationalities: Array<INationality>;
        degreeNames: Array<IDegreeType>;
        bloodGroups: Array<IBloodGroup>;
        publicationTypes: Array<IPublicationType>;
        waitingForApprovalPublications: Array<IPublicationInformationModel>;
        relations: Array<IRelation>;
        countries: Array<ICountry>;
        divisions: Array<IDivision>;
        presentAddressDistricts: Array<IDistrict>;
        permanentAddressDistricts: Array<IDistrict>;
        allDistricts: Array<IDistrict>;
        presentAddressThanas: Array<IThana>;
        permanentAddressThanas: Array<IThana>;
        allThanas: Array<IThana>;
    }

    export interface IGender {
        id: string;
        name: string;
    }

    export interface IMaritalStatus {
        id: number;
        name: string;
    }

    export interface IDegreeType {
        id: number;
        name: string;
    }

    export interface IPublicationType {
        id: number;
        name: string;
    }

    export interface INationality {
        id: number;
        name: string;
    }

    export interface IReligion {
        id: number;
        name: string;
    }

    export interface IBloodGroup {
        id: number;
        name: string;
    }

    export interface ICountry {
        id: number;
        name: string;
    }

    export interface IDivision {
        id: string;
        name: string;
    }

    export interface IDistrict {
        id: string;
        division_id: string;
        name: string;
    }

    export interface IThana {
        id: string;
        district_id: string;
        name: string;
    }

    export interface IRelation {
        id: number;
        name: string;
    }

    export interface IEntry {
        personal: IPersonalInformationModel;
        academic: Array<IAcademicInformationModel>;
        publication: Array<IPublicationInformationModel>;
        training: Array<ITrainingInformationModel>;
        award: Array<IAwardInformationModel>;
        experience: Array<IExperienceInformationModel>;
    }

    class EmployeeInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce','employeeService','countryService', 'divisionService', 'districtService', 'thanaService', 'personalInformationService', 'academicInformationService', 'publicationInformationService', 'trainingInformationService', 'awardInformationService', 'experienceInformationService'];

        constructor(private registrarConstants: any,
                    private $scope: IEmployeeInformation,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService,
                    private employeeService: EmployeeService,
                    private countryService: CountryService,
                    private divisionService: DivisionService,
                    private districtService: DistrictService,
                    private thanaService: ThanaService,
                    private personalInformationService: PersonalInformationService,
                    private academicInformationService: AcademicInformationService,
                    private publicationInformationService: PublicationInformationService,
                    private trainingInformationService: TrainingInformationService,
                    private awardInformationService: AwardInformationService,
                    private experienceInformationService: ExperienceInformationService) {

            $scope.entry = {
                personal: <IPersonalInformationModel> {},
                academic: Array<IAcademicInformationModel>(),
                publication: Array<IPublicationInformationModel>(),
                training: Array<ITrainingInformationModel>(),
                award: Array<IAwardInformationModel>(),
                experience: Array<IExperienceInformationModel>()
            };

            $scope.data = {
                supOptions: "",
                borderColor: "",
                itemPerPage: 2,
                totalRecord: 0
            };

            $scope.pagination = {};
            $scope.pagination.currentPage = 1;

            $scope.waitingForApprovalPublications = Array<IPublicationInformationModel>();

            $scope.gender = this.registrarConstants.gender;
            $scope.religions = this.registrarConstants.religionTypes;
            $scope.nationalities = this.registrarConstants.nationalities;
            $scope.bloodGroups = this.registrarConstants.bloodGroups;
            $scope.maritalStatus = this.registrarConstants.maritalStatuses;
            $scope.publicationTypes = this.registrarConstants.publicationTypes;
            $scope.degreeNames = this.registrarConstants.degreeTypes;
            $scope.relations = this.registrarConstants.relationTypes;

            // $scope.changeNav = this.changeNav.bind(this);
            $scope.testData = this.testData.bind(this);
            $scope.submitPersonalForm = this.submitPersonalForm.bind(this);
            $scope.submitAcademicForm = this.submitAcademicForm.bind(this);
            $scope.submitPublicationForm = this.submitPublicationForm.bind(this);
            $scope.submitTrainingForm = this.submitTrainingForm.bind(this);
            $scope.submitAwardForm = this.submitAwardForm.bind(this);
            $scope.submitExperienceForm = this.submitExperienceForm.bind(this);
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

            this.initializeVariables();
        }

        private initializeVariables() {
            //this.getLoggedUserId();

            this.getCountry();
            this.getDivision();
            this.getDistrict();
            this.getThana();

            this.enableViewMode('personal');
            this.enableViewMode('academic');
            this.enableViewMode('publication');
            this.enableViewMode('training');
            this.enableViewMode('award');
            this.enableViewMode('experience');

            this.addNewRow("academic");
            this.addNewRow("publication");
            this.addNewRow("training");
            this.addNewRow("award");
            this.addNewRow("experience");

            this.addDate();
            this.createMap();
            // this.changeNav('personal');

            this.getPersonalInformation();
            // this.getAcademicInformation();
            // this.getAwardInformation();
            // this.getPublicationInformation();
            // this.getExperienceInformation();
            // this.getTrainingInformation();
        }

        private getLoggedUserId(){
            this.employeeService.getLoggedEmployeeInfo().then((user: any) => {
            });

        }

        private enableViewMode(formName: string) {
            if (formName === 'personal') {
                this.$scope.showPersonalInputDiv = false;
                this.$scope.showRequireSign = false;
                this.$scope.showPermanentAddressCheckbox = false;
                this.$scope.showSup = false;
                this.$scope.showPersonalCancelButton = false;
                this.$scope.disablePresentAddressDropdown = false;
                this.$scope.disablePermanentAddressDropdown = false;
                this.$scope.showPersonalLabelDiv = true;
                this.$scope.showPersonalEditButton = true;
                this.$scope.disablePersonalSubmitButton = true;
            }
            else if (formName === 'academic') {
                this.$scope.showAcademicInputDiv = false;
                this.$scope.showAcademicAddIcon = false;
                this.$scope.showAcademicCrossIcon = false;
                this.$scope.showRequireSign = false;
                this.$scope.showAcademicCancelButton = false;
                this.$scope.showAcademicLabelDiv = true;
                this.$scope.showAcademicEditButton = true;
                this.$scope.disableAcademicSubmitButton = true;
            }
            else if (formName === 'publication') {
                this.$scope.showPublicationInputDiv = false;
                this.$scope.showPublicationAddIcon = false;
                this.$scope.showPublicationCrossIcon = false;
                this.$scope.showPublicationCancelButton = false;
                this.$scope.showPublicationLabelDiv = true;
                this.$scope.showPublicationEditButton = true;
                this.$scope.disablePublicationSubmitButton = true;
                this.$scope.showPublicationISSNDiv = true;
            }
            else if (formName === 'training') {
                this.$scope.showTrainingInputDiv = false;
                this.$scope.showTrainingAddIcon = false;
                this.$scope.showTrainingCrossIcon = false;
                this.$scope.showTrainingCancelButton = false;
                this.$scope.showTrainingLabelDiv = true;
                this.$scope.showTrainingEditButton = true;
                this.$scope.disableTrainingSubmitButton = true;
            }
            else if (formName === 'award') {
                this.$scope.showAwardInputDiv = false;
                this.$scope.showAwardAddIcon = false;
                this.$scope.showAwardCrossIcon = false;
                this.$scope.showAwardCancelButton = false;
                this.$scope.showAwardLabelDiv = true;
                this.$scope.showAwardEditButton = true;
                this.$scope.disableAwardSubmitButton = true;
            }
            else if (formName === 'experience') {
                this.$scope.showExperienceInputDiv = false;
                this.$scope.showExperienceAddIcon = false;
                this.$scope.showExperienceCrossIcon = false;
                this.$scope.showExperienceCancelButton = false;
                this.$scope.showExperienceLabelDiv = true;
                this.$scope.showExperienceEditButton = true;
                this.$scope.disableExperienceSubmitButton = true;
            }
        }

        private createMap() {
            this.$scope.degreeNameMap = {};
            this.$scope.genderNameMap = {};
            this.$scope.religionNameMap = {};
            this.$scope.nationalityMap = {};
            this.$scope.bloodGroupMap = {};
            this.$scope.martialStatusMap = {};
            this.$scope.publicationTypeMap = {};
            this.$scope.relationMap = {};
            this.$scope.countryMap = {};
            this.$scope.divisionMap = {};
            this.$scope.districtMap = {};
            this.$scope.thanaMap = {};

            for (let i = 0; i < this.$scope.degreeNames.length; i++) {
                this.$scope.degreeNameMap[this.$scope.degreeNames[i].name] = this.$scope.degreeNames[i];
            }
            for (let i = 0; i < this.$scope.gender.length; i++) {
                this.$scope.genderNameMap[this.$scope.gender[i].id] = this.$scope.gender[i];
            }
            for (let i = 0; i < this.$scope.religions.length; i++) {
                this.$scope.religionNameMap[this.$scope.religions[i].name] = this.$scope.religions[i];
            }
            for (let i = 0; i < this.$scope.nationalities.length; i++) {
                this.$scope.nationalityMap[this.$scope.nationalities[i].name] = this.$scope.nationalities[i];
            }
            for (let i = 0; i < this.$scope.bloodGroups.length; i++) {
                this.$scope.bloodGroupMap[this.$scope.bloodGroups[i].name] = this.$scope.bloodGroups[i];
            }
            for (let i = 0; i < this.$scope.maritalStatus.length; i++) {
                this.$scope.martialStatusMap[this.$scope.maritalStatus[i].name] = this.$scope.maritalStatus[i];
            }
            for (let i = 0; i < this.$scope.publicationTypes.length; i++) {
                this.$scope.publicationTypeMap[this.$scope.publicationTypes[i].name] = this.$scope.publicationTypes[i];
            }
            for (let i = 0; i < this.$scope.relations.length; i++) {
                this.$scope.relationMap[this.$scope.relations[i].name] = this.$scope.relations[i];
            }
        }


        // private changeNav(navTitle: string) {
        //     this.$scope.personalTab = false;
        //     this.$scope.academicTab = false;
        //     this.$scope.publicationTab = false;
        //     this.$scope.trainingTab = false;
        //     this.$scope.awardTab = false;
        //     this.$scope.experienceTab = false;
        //
        //     if (navTitle === "") {
        //         this.$scope.personalTab = true;
        //     }
        //     else if (navTitle === "personal") {
        //         this.$scope.personalTab = true;
        //     }
        //     else if (navTitle === "academic") {
        //         this.$scope.academicTab = true;
        //
        //     }
        //     else if (navTitle === "publication") {
        //         this.$scope.publicationTab = true;
        //     }
        //     else if (navTitle === "training") {
        //         this.$scope.trainingTab = true;
        //     }
        //     else if (navTitle === "award") {
        //         this.$scope.awardTab = true;
        //     }
        //     else if (navTitle === "experience") {
        //         this.$scope.experienceTab = true;
        //     }
        // }

        private testData() {
            this.$scope.entry.personal.firstName = "Kawsur";
            this.$scope.entry.personal.lastName = "Mir Md.";
            this.$scope.entry.personal.fatherName = "Mir Abdul Aziz";
            this.$scope.entry.personal.motherName = "Mst Hosne Ara";
            this.$scope.entry.personal.gender = this.$scope.gender[1];
            this.$scope.entry.personal.dateOfBirth = "20/10/1995";
            this.$scope.entry.personal.nationality = this.$scope.nationalities[1];
            this.$scope.entry.personal.religion = this.$scope.religions[1];
            this.$scope.entry.personal.maritalStatus = this.$scope.maritalStatus[1];
            this.$scope.entry.personal.spouseName = "";
            this.$scope.entry.personal.nationalIdNo = "19952641478954758";
            this.$scope.entry.personal.spouseNationalIdNo = "";
            this.$scope.entry.personal.bloodGroup = this.$scope.bloodGroups[1];
            this.$scope.entry.personal.personalWebsite = "https://www.kawsur.com";
            this.$scope.entry.personal.organizationalEmail = "kawsur.iums@aust.edu";
            this.$scope.entry.personal.personalEmail = "kawsurilu@yahoo.com";
            this.$scope.entry.personal.mobile = "+8801672494863";
            this.$scope.entry.personal.phone = "none";
            this.$scope.entry.personal.emergencyContactName = "None";
            this.$scope.entry.personal.emergencyContactRelation = this.$scope.relations[0];
            this.$scope.entry.personal.emergencyContactPhone = "01898889851";

            this.$scope.entry.academic[0].academicDegreeName.name = "Bachelor";
            this.$scope.entry.academic[0].academicInstitution = "American International University-Bangladesh";
            this.$scope.entry.academic[0].academicPassingYear = "";

            this.$scope.entry.publication[0].publicationTitle = "N/A";
            this.$scope.entry.publication[0].publicationInterestGenre = "N/A";
            this.$scope.entry.publication[0].publisherName = "N/A";
            this.$scope.entry.publication[0].dateOfPublication = "11/11/3010";
            this.$scope.entry.publication[0].publicationType = this.$scope.publicationTypes[1];
            this.$scope.entry.publication[0].publicationWebLink = "N/A";

            this.$scope.entry.training[0].trainingInstitution = "ABC";
            this.$scope.entry.training[0].trainingName = "XYZ";
            this.$scope.entry.training[0].trainingFrom = "2016";
            this.$scope.entry.training[0].trainingTo = "2015";
            this.$scope.entry.training[0].trainingDuration = (+this.$scope.entry.training[0].trainingTo - +this.$scope.entry.training[0].trainingFrom).toString();

            this.$scope.entry.award[0].awardName = "My Award";
            this.$scope.entry.award[0].awardInstitute = "Really !";
            this.$scope.entry.award[0].awardedYear = "1990";
            this.$scope.entry.award[0].awardShortDescription = "Hello! This is My Award, Don't Ask Description :@";

            this.$scope.entry.experience[0].experienceInstitution = "My Award";
            this.$scope.entry.experience[0].experienceDesignation = "Really !";
            this.$scope.entry.experience[0].experienceFrom = "6";
            this.$scope.entry.experience[0].experienceTo = "2010";
        }

        private submitPersonalForm() {
            this.convertToJson('personal')
                .then((json: any) => {

                if(this.isEmpty(this.$scope.entry.personal)){
                    console.log("object empty");
                    // Save operation will go here.
                }
                else{
                    console.log("Not Empty");

                    if(json.entries === this.$scope.entry.personal){
                        console.log("objects are equal");
                    }
                    else{
                        console.log("objects are not equal");
                    }

                }
                    // this.personalInformationService.savePersonalInformation(json)
                    //     .then((message: any) => {
                    //
                    //     });
                });
            this.enableViewMode('personal');
        }

        private isEmpty(obj: Object): boolean {
        for(var key in obj) {
            if(obj.hasOwnProperty(key))
                return false;
        }
        return true;
    }

        private updatePersonalForm() {
            this.convertToJson('personal')
                .then((json: any) => {
                    this.personalInformationService.updatePersonalInformation(json)
                        .then((message: any) => {
                        });
                });
        }

        private submitAcademicForm() {

            this.convertToJson('academic')
                .then((json: any) => {
                    this.academicInformationService.saveAcademicInformation(json)
                        .then((message: any) => {

                        });
                });
            this.enableViewMode('academic');
        }

        private submitPublicationForm() {

            this.convertToJson('publication')
                .then((json: any) => {
                    this.publicationInformationService.savePublicationInformation(json)
                        .then((message: any) => {

                        });
                });
            this.enableViewMode('academic');
        }

        private submitTrainingForm() {
            this.convertToJson('training')
                .then((json: any) => {
                    this.trainingInformationService.saveTrainingInformation(json)
                        .then((message: any) => {
                        });
                });
            this.enableViewMode('training');
        }

        private submitAwardForm() {
            this.convertToJson('award')
                .then((json: any) => {
                    this.awardInformationService.saveAwardInformation(json)
                        .then((message: any) => {
                        });
                });
            this.enableViewMode('award');
        }

        private submitExperienceForm() {
            this.convertToJson('experience')
                .then((json: any) => {
                    this.experienceInformationService.saveExperienceInformation(json)
                        .then((message: any) => {

                        });
                });
            this.enableViewMode('experience');
        }


        private getPersonalInformation() {
            this.personalInformationService.getPersonalInformation().then((personalInformation: any) => {
                this.setSavedValuesOfPersonalForm(personalInformation);
            });
        }

        private setSavedValuesOfPersonalForm(personalInformation) {
            if (personalInformation.length > 0) {
                this.$scope.entry.personal = personalInformation[0];
                this.$scope.entry.personal.maritalStatus = this.$scope.martialStatusMap[personalInformation[0].maritalStatus];
                this.$scope.entry.personal.gender = this.$scope.genderNameMap[personalInformation[0].gender];
                this.$scope.entry.personal.religion = this.$scope.religionNameMap[personalInformation[0].religion];
                this.$scope.entry.personal.nationality = this.$scope.nationalityMap[personalInformation[0].nationality];
                this.$scope.entry.personal.bloodGroup = this.$scope.bloodGroupMap[personalInformation[0].bloodGroup];
                this.$scope.entry.personal.emergencyContactRelation = this.$scope.relationMap[personalInformation[0].emergencyContactRelation];
                this.$scope.entry.personal.presentAddressCountry = this.$scope.countryMap[personalInformation[0].presentAddressCountry];
                this.$scope.entry.personal.presentAddressDivision = this.$scope.divisionMap[personalInformation[0].presentAddressDivision];
                this.$scope.entry.personal.presentAddressDistrict = this.$scope.districtMap[personalInformation[0].presentAddressDistrict];
                this.$scope.entry.personal.presentAddressThana = this.$scope.thanaMap[personalInformation[0].presentAddressThana];
                this.$scope.entry.personal.permanentAddressCountry = this.$scope.countryMap[personalInformation[0].permanentAddressCountry];
                this.$scope.entry.personal.permanentAddressDivision = this.$scope.divisionMap[personalInformation[0].permanentAddressDivision];
                this.$scope.entry.personal.permanentAddressDistrict = this.$scope.districtMap[personalInformation[0].permanentAddressDistrict];
                this.$scope.entry.personal.permanentAddressThana = this.$scope.thanaMap[personalInformation[0].permanentAddressThana];
            }
        }

        private getAcademicInformation() {
            this.academicInformationService.getAcademicInformation().then((academicInformation: any) => {
                this.setSavedValuesOfAcademicForm(academicInformation);
            });
        }

        private setSavedValuesOfAcademicForm(academicInformation: any) {
            for (let i = 0; i < academicInformation.length; i++) {
                this.$scope.entry.academic[i] = academicInformation[i];
                this.$scope.entry.academic[i].academicDegreeName = this.$scope.degreeNameMap[academicInformation[i].academicDegreeName];
            }
        }

        private getPublicationInformation() {
            this.publicationInformationService.getPublicationInformation().then((publicationInformation: any) => {
                this.$scope.data.totalRecord = publicationInformation.length;
                this.setSavedValuesOfPublicationForm(publicationInformation);
            });
        }

        private setSavedValuesOfPublicationForm(publicationInformation: any) {
            for (let i = 0; i < publicationInformation.length; i++) {
                if(publicationInformation[i].status === "0") {
                    this.$scope.borderColor = "red";
                    this.$scope.entry.publication[i] = publicationInformation[i];
                    this.$scope.entry.publication[i].publicationType = this.$scope.publicationTypeMap[publicationInformation[i].publicationType];
                }
                else{
                    this.$scope.borderColor = "black";
                    this.$scope.entry.publication[i] = publicationInformation[i];
                    this.$scope.entry.publication[i].publicationType = this.$scope.publicationTypeMap[publicationInformation[i].publicationType];
                }
            }
        }

        private getTrainingInformation() {
            this.trainingInformationService.getTrainingInformation().then((trainingInformation: any) => {
                this.setSavedValuesOfTrainingForm(trainingInformation);
            });
        }

        private setSavedValuesOfTrainingForm(trainingInformation: any) {
            for (let i = 0; i < trainingInformation.length; i++) {
                this.$scope.entry.training[i] = trainingInformation[i];
            }
        }

        private getAwardInformation() {
            this.awardInformationService.getAwardInformation().then((awardInformation: any) => {
                this.setSavedValuesOfAwardForm(awardInformation);
            });
        }

        private setSavedValuesOfAwardForm(awardInformation: any) {
            for (let i = 0; i < awardInformation.length; i++) {
                this.$scope.entry.award[i] = awardInformation[i];
            }
        }

        private getExperienceInformation() {
            this.experienceInformationService.getExperienceInformation().then((experienceInformation: any) => {
                this.setSavedValuesOfExperienceForm(experienceInformation);
            });
        }

        private setSavedValuesOfExperienceForm(experienceInformation: any) {
            for (let i = 0; i < experienceInformation.length; i++) {
                this.$scope.entry.experience[i] = experienceInformation[i];
            }
        }

        private enableEditMode(formName: string) {
            if (formName === "personal") {
                this.$scope.showPersonalInputDiv = true;
                this.$scope.showPersonalLabelDiv = false;
                this.$scope.showRequireSign = true;
                this.$scope.showPermanentAddressCheckbox = true;
                this.$scope.showSup = true;
                this.$scope.disablePersonalSubmitButton = false;
                this.$scope.showPersonalCancelButton = true;
                this.$scope.showPersonalEditButton = false;
                this.$scope.required = false;
            }
            else if (formName === "academic") {
                this.$scope.showAcademicInputDiv = true;
                this.$scope.showAcademicLabelDiv = false;
                this.$scope.showRequireSign = true;
                this.$scope.showAcademicAddIcon = true;
                this.$scope.showAcademicCrossIcon = true;
                this.$scope.showRequireSign = true;
                this.$scope.disableAcademicSubmitButton = false;
                this.$scope.showAcademicCancelButton = true;
                this.$scope.showAcademicEditButton = false;
            }
            else if (formName === "publication") {
                this.$scope.showPublicationInputDiv = true;
                this.$scope.showPublicationLabelDiv = false;
                this.$scope.showRequireSign = true;
                this.$scope.showPublicationAddIcon = true;
                this.$scope.showPublicationCrossIcon = true;
                this.$scope.disablePublicationSubmitButton = false;
                this.$scope.showPublicationCancelButton = true;
                this.$scope.showPublicationEditButton = false;
            }
            else if (formName === "training") {
                this.$scope.showTrainingInputDiv = true;
                this.$scope.showTrainingLabelDiv = false;
                this.$scope.showRequireSign = true;
                this.$scope.showTrainingAddIcon = true;
                this.$scope.showTrainingCrossIcon = true;
                this.$scope.disableTrainingSubmitButton = false;
                this.$scope.showTrainingCancelButton = true;
                this.$scope.showTrainingEditButton = false;
            }
            else if (formName === "award") {
                this.$scope.showAwardInputDiv = true;
                this.$scope.showAwardLabelDiv = false;
                this.$scope.showRequireSign = true;
                this.$scope.showAwardAddIcon = true;
                this.$scope.showAwardCrossIcon = true;
                this.$scope.disableAwardSubmitButton = false;
                this.$scope.showAwardCancelButton = true;
                this.$scope.showAwardEditButton = false;
            }
            else if (formName === "experience") {
                this.$scope.showExperienceInputDiv = true;
                this.$scope.showExperienceLabelDiv = false;
                this.$scope.showRequireSign = true;
                this.$scope.showExperienceAddIcon = true;
                this.$scope.showExperienceCrossIcon = true;
                this.$scope.disableExperienceSubmitButton = false;
                this.$scope.showExperienceCancelButton = true;
                this.$scope.showExperienceEditButton = false;
            }
        }

        private addNewRow(divName: string) {
            if (divName === 'academic') {
                let academicEntry: IAcademicInformationModel;
                academicEntry = {
                    id: null,
                    employeeId: "",
                    academicDegreeName: null,
                    academicInstitution: "",
                    academicPassingYear: ""
                };
                this.$scope.entry.academic.push(academicEntry);
            }
            else if (divName === 'publication') {
                let publicationEntry: IPublicationInformationModel;
                publicationEntry = {
                    id: null,
                    employeeId: "",
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
                    publicationCountry: "",
                    status: "",
                    publicationPages: "",
                    appliedOn: "",
                    actionTakenOn: ""
                };
                this.$scope.entry.publication.push(publicationEntry);
            }
            else if (divName === 'training') {
                let trainingEntry: ITrainingInformationModel;
                trainingEntry = {
                    id: null,
                    employeeId: "",
                    trainingName: "",
                    trainingInstitution: "",
                    trainingFrom: "",
                    trainingTo: "",
                    trainingDuration: ""
                };
                this.$scope.entry.training.push(trainingEntry);
            }
            else if (divName === 'award') {
                let awardEntry: IAwardInformationModel;
                awardEntry = {
                    id: null,
                    employeeId: "",
                    awardName: "",
                    awardInstitute: "",
                    awardedYear: "",
                    awardShortDescription: ""
                };
                this.$scope.entry.award.push(awardEntry);
            }
            else if (divName === 'experience') {
                let experienceEntry: IExperienceInformationModel;
                experienceEntry = {
                    id: null,
                    employeeId: "",
                    experienceInstitution: "",
                    experienceDesignation: "",
                    experienceFrom: "",
                    experienceTo: ""
                };
                this.$scope.entry.experience.push(experienceEntry);
            }
        }

        private deleteRow(divName: string, index: number) {
            if (divName === 'academic') {
                this.$scope.entry.academic.splice(index, 1);
            }
            else if (divName === 'publication') {
                this.$scope.entry.publication.splice(index, 1);
            }
            else if (divName === 'training') {
                this.$scope.entry.training.splice(index, 1);
            }
            else if (divName === 'award') {
                this.$scope.entry.award.splice(index, 1);
            }
            else if (divName === 'experience') {
                this.$scope.entry.experience.splice(index, 1);
            }
        }

        private convertToJson(convertThis: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};

            if (convertThis === "personal") {
                let personalInformation = <IPersonalInformationModel> {};
                personalInformation = this.$scope.entry.personal;
                item['personal'] = personalInformation;
            }

            else if (convertThis === "academic") {
                let academicInformation = Array<IAcademicInformationModel>();
                for (let i = 0; i < this.$scope.entry.academic.length; i++) {
                    academicInformation = this.$scope.entry.academic;
                }
                item['academic'] = academicInformation;
            }

            else if (convertThis === "publication") {
                let publicationInformation = Array<IPublicationInformationModel>();
                for (let i = 0; i < this.$scope.entry.publication.length; i++) {
                    this.$scope.entry.publication[i].status = '0';
                    publicationInformation = this.$scope.entry.publication;
                }
                item['publication'] = publicationInformation;
            }

            else if (convertThis === "training") {
                let trainingInformation = Array<ITrainingInformationModel>();
                for (let i = 0; i < this.$scope.entry.training.length; i++) {
                    trainingInformation = this.$scope.entry.training;
                }
                item['training'] = trainingInformation;
            }

            else if (convertThis === "award") {
                let awardInformation = Array<IAwardInformationModel>();
                for (let i = 0; i < this.$scope.entry.award.length; i++) {
                    awardInformation = this.$scope.entry.award;
                }
                item['award'] = awardInformation;
            }

            else if (convertThis === "experience") {
                let experienceInformation = Array<IExperienceInformationModel>();
                for (let i = 0; i < this.$scope.entry.experience.length; i++) {
                    experienceInformation = this.$scope.entry.experience;
                }
                item['experience'] = experienceInformation;
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

        private getCountry() {
            this.countryService.getCountryList().then((country: any) => {
                this.$scope.countries = country.entries;
                for (let i = 0; i < this.$scope.countries.length; i++) {
                    this.$scope.countryMap[this.$scope.countries[i].name] = this.$scope.countries[i];
                }
            });
        }

        private getDivision() {
            this.divisionService.getDivisionList().then((division: any) => {
                this.$scope.divisions = division.entries;
                for (let i = 0; i < this.$scope.divisions.length; i++) {
                    this.$scope.divisionMap[this.$scope.divisions[i].name] = this.$scope.divisions[i];
                }
            });
        }

        private getDistrict() {
            this.districtService.getDistrictList().then((district: any) => {
                this.$scope.presentAddressDistricts = district.entries;
                this.$scope.permanentAddressDistricts = district.entries;
                this.$scope.allDistricts = district.entries;
                for (let i = 0; i < this.$scope.allDistricts.length; i++) {
                    this.$scope.districtMap[this.$scope.allDistricts[i].name] = this.$scope.allDistricts[i];
                }
            });
        }

        private getThana() {
            this.thanaService.getThanaList().then((thana: any) => {
                this.$scope.presentAddressThanas = thana.entries;
                this.$scope.permanentAddressThanas = thana.entries;
                this.$scope.allThanas = thana.entries;
                for (let i = 0; i < this.$scope.allThanas.length; i++) {
                    this.$scope.thanaMap[this.$scope.allThanas[i].name] = this.$scope.allThanas[i];
                }
            });
        }

        private changePresentAddressDistrict() {
            this.$scope.presentAddressDistricts = [];
            let districtLength = this.$scope.allDistricts.length;
            let index = 0;
            for (let i = 0; i < districtLength; i++) {
                if (this.$scope.entry.personal.presentAddressDivision.id === this.$scope.allDistricts[i].division_id) {
                    this.$scope.presentAddressDistricts[index++] = this.$scope.allDistricts[i];
                }
            }
            this.$scope.entry.personal.presentAddressDistrict = this.$scope.presentAddressDistricts[1];
        }

        private changePermanentAddressDistrict() {
            this.$scope.permanentAddressDistricts = [];
            let districtLength = this.$scope.allDistricts.length;
            let index = 0;
            for (let i = 0; i < districtLength; i++) {
                if (this.$scope.entry.personal.permanentAddressDivision.id === this.$scope.allDistricts[i].division_id) {
                    this.$scope.permanentAddressDistricts[index++] = this.$scope.allDistricts[i];
                }
            }
            this.$scope.entry.personal.permanentAddressDistrict = this.$scope.permanentAddressDistricts[1];
        }

        private changePresentAddressThana() {
            this.$scope.presentAddressThanas = [];
            let thanaLength = this.$scope.allThanas.length;
            let index = 0;
            for (let i = 0; i < thanaLength; i++) {
                if (this.$scope.entry.personal.presentAddressDistrict.id === this.$scope.allThanas[i].district_id) {
                    this.$scope.presentAddressThanas[index++] = this.$scope.allThanas[i];
                }
            }
            this.$scope.entry.personal.presentAddressThana = this.$scope.presentAddressThanas[0];
        }

        private changePermanentAddressThana() {
            this.$scope.permanentAddressThanas = [];
            let thanaLength = this.$scope.allThanas.length;
            let index = 0;
            for (let i = 0; i < thanaLength; i++) {
                if (this.$scope.entry.personal.permanentAddressDistrict.id === this.$scope.allThanas[i].district_id) {
                    this.$scope.permanentAddressThanas[index++] = this.$scope.allThanas[i];
                }
            }
            this.$scope.entry.personal.permanentAddressThana = this.$scope.permanentAddressThanas[0];
        }

        private sameAsPresentAddress() {
            this.$scope.entry.personal.permanentAddressHouse = this.$scope.entry.personal.presentAddressHouse;
            this.$scope.entry.personal.permanentAddressRoad = this.$scope.entry.personal.presentAddressRoad;
            this.$scope.entry.personal.permanentAddressCountry = this.$scope.entry.personal.presentAddressCountry;
            if(this.$scope.entry.personal.presentAddressCountry.name === "Bangladesh"){
                this.changePermanentAddressFields();
            }
            else {
                this.$scope.entry.personal.permanentAddressDivision = this.$scope.entry.personal.presentAddressDivision;
                this.$scope.entry.personal.permanentAddressPostOfficeNo = this.$scope.entry.personal.presentAddressPostOfficeNo;
                this.$scope.entry.personal.permanentAddressDistrict = this.$scope.entry.personal.presentAddressDistrict;
                this.$scope.entry.personal.permanentAddressThana = this.$scope.entry.personal.presentAddressThana;
            }
        }

        private changePresentAddressFields() {
            if (this.$scope.entry.personal.presentAddressCountry.name === "Bangladesh") {
                this.$scope.required = true;
                this.$scope.disablePresentAddressDropdown = false;
                this.$scope.entry.personal.presentAddressDivision = this.$scope.divisions[0];
                this.changePresentAddressDistrict();
                this.changePresentAddressThana();
            }
            else {
                this.$scope.required = false;
                this.$scope.disablePresentAddressDropdown = true;
                this.$scope.entry.personal.presentAddressDivision = null;
                this.$scope.entry.personal.presentAddressDistrict = null;
                this.$scope.entry.personal.presentAddressThana = null;
                this.$scope.entry.personal.presentAddressPostOfficeNo = "";
            }
        }

        private changePermanentAddressFields() {
            if (this.$scope.entry.personal.permanentAddressCountry.name === "Bangladesh") {
                this.$scope.disablePermanentAddressDropdown = false;
                this.changePermanentAddressDistrict();
                this.changePermanentAddressThana();
            }
            else {
                this.$scope.disablePermanentAddressDropdown = true;
                this.$scope.entry.personal.permanentAddressDivision = null;
                this.$scope.entry.personal.permanentAddressDistrict = null;
                this.$scope.entry.personal.permanentAddressThana = null;
                this.$scope.entry.personal.permanentAddressPostOfficeNo = "";
            }
        }

        private fillEmergencyContactAddress() {
            let presentAddressLine1;
            let presentAddressLine2;
            let presentPostalCode;
            let permanentAddressLine1;
            let permanentAddressLine2;
            let permanentPostalCode;

            if (this.$scope.entry.personal.presentAddressHouse === "" || this.$scope.entry.personal.presentAddressHouse === undefined) {
                presentAddressLine1 = "";
            }
            else {
                presentAddressLine1 = this.$scope.entry.personal.presentAddressHouse;
            }

            if (this.$scope.entry.personal.presentAddressRoad === "" || this.$scope.entry.personal.presentAddressRoad === undefined) {
                presentAddressLine2 = "";
            }
            else {
                presentAddressLine2 = this.$scope.entry.personal.presentAddressRoad;
            }

            if (this.$scope.entry.personal.presentAddressPostOfficeNo === "" || this.$scope.entry.personal.presentAddressPostOfficeNo === undefined) {
                presentPostalCode = "";
            }
            else {
                presentPostalCode = this.$scope.entry.personal.presentAddressPostOfficeNo;
            }

            if (this.$scope.entry.personal.permanentAddressHouse === "" || this.$scope.entry.personal.permanentAddressHouse === undefined) {
                permanentAddressLine1 = "";
            }
            else {
                permanentAddressLine1 = this.$scope.entry.personal.presentAddressHouse;
            }

            if (this.$scope.entry.personal.permanentAddressRoad === "" || this.$scope.entry.personal.permanentAddressRoad === undefined) {
                permanentAddressLine2 = "";
            }
            else {
                permanentAddressLine2 = this.$scope.entry.personal.presentAddressRoad;
            }

            if (this.$scope.entry.personal.permanentAddressPostOfficeNo === "" || this.$scope.entry.personal.permanentAddressPostOfficeNo === undefined) {
                permanentPostalCode = "";
            }
            else {
                permanentPostalCode = this.$scope.entry.personal.permanentAddressPostOfficeNo;
            }

            if (this.$scope.data.supOptions === "1") {
                this.$scope.entry.personal.emergencyContactAddress = "";
            }
            else if (this.$scope.data.supOptions === "2") {
                this.$scope.entry.personal.emergencyContactAddress = presentAddressLine1 + " " + presentAddressLine2 + " " + this.$scope.entry.personal.presentAddressThana.name + " " + this.$scope.entry.personal.presentAddressDistrict.name + " - " + presentPostalCode;
            }
            else if (this.$scope.data.supOptions === "3") {
                this.$scope.entry.personal.emergencyContactAddress = permanentAddressLine1 + " " + permanentAddressLine2 + " " + this.$scope.entry.personal.permanentAddressThana.name + " " + this.$scope.entry.personal.permanentAddressDistrict.name + " - " + permanentPostalCode;
            }
        }

        private pageChanged(pageNumber: number){
            //this.getPublicationInformation(pageNumber);
        }
    }

    UMS.controller("EmployeeInformation", EmployeeInformation);
}