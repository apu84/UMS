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
        changeAreaOfInterest: Function;
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
        selectedAreaOfInterest: Array<ICommon>;
        paginatedPublication: Array<IPublicationInformationModel>;
        previousPersonalInformation: IPersonalInformationModel;
        previousAcademicInformation: Array<IAcademicInformationModel>;
        previousPublicationInformation: Array<IPublicationInformationModel>;
        previousTrainingInformation: Array<ITrainingInformationModel>;
        previousAwardInformation: Array<IAwardInformationModel>;
        previousExperienceInformation: Array<IExperienceInformationModel>;
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
    }

    class EmployeeInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce','countryService',
            'divisionService', 'districtService', 'thanaService', 'personalInformationService', 'academicInformationService',
            'publicationInformationService', 'trainingInformationService', 'awardInformationService', 'experienceInformationService',
            'areaOfInterestService', 'bloodGroupService', 'nationalityService', 'religionService', 'relationTypeService', 'maritalStatusService', 'areaOfInterestInformationService'];

        constructor(private registrarConstants: any,
                    private $scope: IEmployeeInformation,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService,
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
                    private bloodGroupService: BloodGroupService,
                    private nationalityService: NationalityService,
                    private religionService: ReligionService,
                    private relationTypeService: RelationTypeService,
                    private maritalStatusService: MaritalStatusService,
                    private areaOfInterestInformationService: AreaOfInterestInformationService) {

            $scope.entry = {
                personal: <IPersonalInformationModel> {},
                academic: Array<IAcademicInformationModel>(),
                publication: Array<IPublicationInformationModel>(),
                training: Array<ITrainingInformationModel>(),
                award: Array<IAwardInformationModel>(),
                experience: Array<IExperienceInformationModel>()
            };
            $scope.data = {
                supOptions: "1",
                borderColor: "",
                itemPerPage: 2,
                totalRecord: 0,
                customItemPerPage: null
            };
            $scope.pagination = {};
            $scope.pagination.currentPage = 1;
            $scope.bloodGroups = Array<ICommon>();
            $scope.religions = Array<ICommon>();
            $scope.nationalities = Array<ICommon>();
            $scope.maritalStatus = Array<ICommon>();
            $scope.relations = Array<ICommon>();
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
            $scope.changeAreaOfInterest = this.changeAreaOfInterest.bind(this);

            this.addDate();
            this.getInitialParameters();
            this.setViewModeInitially();
            this.getPreviousFormValues();

            console.log("Hello I am updated 1");
        }

        private getInitialParameters() {
            this.getCountry();
            this.getDivision();
            this.getDistrict();
            this.getThana();
            this.getAreaOfInterest();
            this.createMap();
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
            this.getPersonalInformation();
            this.getAcademicInformation();
            this.getAwardInformation();
            this.getPublicationInformation();
            this.getPublicationInformationWithPagination();
            this.getExperienceInformation();
            this.getTrainingInformation();

        }

        private changeAreaOfInterest(){
            this.$scope.data.userAreaOfInterests += this.$scope.data.selectedAreaOfInterest[this.$scope.data.selectedAreaOfInterest.length - 1].name + ", ";
        }

        private getAreaOfInterest() : any{
            this.areaOfInterestService.getAll().then((aoi: any)=>{
               this.$scope.arrayOfAreaOfInterest = aoi;
                return aoi;
            });
        }

        private getCountry(): void{
            this.$scope.countries = Array<ICommon>();
            this.countryService.getCountryList().then((country: any) => {
                this.$scope.countries = country.entries;
            });
        }

        private getDivision(): void{
            this.$scope.divisions = Array<ICommon>();
            this.divisionService.getDivisionList().then((division: any) => {
                this.$scope.divisions = division.entries;
            });
        }

        private getDistrict(): void{
            this.$scope.presentAddressDistricts = Array<ICommon>(); this.$scope.permanentAddressDistricts = Array<ICommon>(); this.$scope.allDistricts = Array<ICommon>();
            this.districtService.getDistrictList().then((district: any) => {
                this.$scope.presentAddressDistricts = district.entries;
                this.$scope.permanentAddressDistricts = district.entries;
                this.$scope.allDistricts = district.entries;
            });
        }

        private getThana() : void{
            this.$scope.presentAddressThanas = Array<ICommon>(); this.$scope.permanentAddressThanas = Array<ICommon>(); this.$scope.allThanas = Array<ICommon>();
            this.thanaService.getThanaList().then((thana: any) => {
                this.$scope.presentAddressThanas = thana.entries;
                this.$scope.permanentAddressThanas = thana.entries;
                this.$scope.allThanas = thana.entries;
            });
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

        private submitPersonalForm(): void{

            if (this.isEmpty(this.$scope.previousPersonalInformation)) {
                // Save operation will go here.
                this.convertToJson('personal', this.$scope.entry.personal)
                    .then((json: any) => {
                        this.personalInformationService.savePersonalInformation(json)
                            .then((message: any) => {
                                this.getPersonalInformation();
                                this.enableViewMode('personal');
                            });
                    });
            }
            else {
                // if (this.objectEqualityTest("personal", this.$scope.previousPersonalInformation, this.$scope.entry.personal)) {
                //     this.notify.info("No Changes Detected");
                // }
                // else {
                    this.convertToJson('personal', this.$scope.entry.personal)
                        .then((json: any) => {
                            this.personalInformationService.savePersonalInformation(json)
                                .then((message: any) => {
                                    this.getPersonalInformation();
                                    this.enableViewMode('personal');
                                });
                        });
                // }
            }
        }

        private isEmpty(obj: Object): boolean {
            for (let key in obj) {
                if (obj.hasOwnProperty(key))
                    return false;
            }
            return true;
        }

        private submitAcademicForm(): void {
            let academicObjects = this.ObjectDetectionForCRUDOperation("academic", this.$scope.previousAcademicInformation, this.$scope.entry.academic);
            this.convertToJson('academic', academicObjects)
                .then((json: any) => {
                    this.academicInformationService.saveAcademicInformation(json)
                        .then((message: any) => {
                            this.getAcademicInformation();
                            this.enableViewMode('academic');
                        });
                });
            // this.convertToJson('academic', this.$scope.entry.academic)
            //     .then((json: any) => {
            //         this.academicInformationService.saveAcademicInformation(json)
            //             .then((message: any) => {
            //                 this.getAcademicInformation();
            //                 this.enableViewMode('academic');
            //             });
            //     });
        }

        private ObjectDetectionForCRUDOperation(objectType: string, baseArrayOfObjects: any, comparingArrayOfObjects: any): void {
            let copyOfComparingArrayOfObjects: any;
            copyOfComparingArrayOfObjects = angular.copy(comparingArrayOfObjects);
            let baseArrayOfObjectsLength: number = baseArrayOfObjects.length;
            let comparingArrayOfObjectsLength: number = copyOfComparingArrayOfObjects.length;
            let flag: number = 0;

            for (let i = 0; i < baseArrayOfObjectsLength; i++) {
                for (let j = 0; j < comparingArrayOfObjectsLength; j++) {
                    if (baseArrayOfObjects[i].id == copyOfComparingArrayOfObjects[j].id) {
                        if (this.objectEqualityTest(objectType, baseArrayOfObjects[i], copyOfComparingArrayOfObjects[i]) == true) {
                            copyOfComparingArrayOfObjects[i].dbAction = "No Change";
                        }
                        else {
                            copyOfComparingArrayOfObjects[i].dbAction = "Update";
                        }
                        flag = 1;
                        break;
                    }
                    else {
                        flag = 0;
                    }
                }
                if (flag == 0) {
                    flag = 0;
                    copyOfComparingArrayOfObjects.push(baseArrayOfObjects[i]);
                }
            }
            for (let i = 0; i < comparingArrayOfObjectsLength; i++) {
                if (copyOfComparingArrayOfObjects[i].id == null) {
                    copyOfComparingArrayOfObjects[i].dbAction = "Create";
                }
            }
            return copyOfComparingArrayOfObjects;
        }

        private objectEqualityTest(objType: string, baseObj: any, comparingObj: any): boolean{
            // if(objType == "personal"){
            //     if(baseObj.firstName == comparingObj.firstName && baseObj.lastName == comparingObj.lastName && baseObj.fatherName == comparingObj.fatherName && baseObj.motherName == comparingObj.motherName
            //     && baseObj.gender.name == comparingObj.gender.name && baseObj.dateOfBirth == comparingObj.dateOfBirth && baseObj.nationality.name == comparingObj.nationality.name && baseObj.religion.name == comparingObj.religion.name
            //     && baseObj.maritalStatus.name == comparingObj.maritalStatus.name && baseObj.spouseName == comparingObj.spouseName && baseObj.nidNo == comparingObj.nidNo && baseObj.bloodGroup.name == comparingObj.bloodGroup.name
            //     && baseObj.spouseNidNo == comparingObj.spouseNidNo && baseObj.website == comparingObj.website && baseObj.organizationalEmail == comparingObj.organizationalEmail
            //     && baseObj.personalEmail == comparingObj.personalEmail && baseObj.mobile == comparingObj.mobile && baseObj.phone == comparingObj.phone && baseObj.presentAddressHouse == comparingObj.presentAddressHouse
            //     && baseObj.presentAddressRoad == comparingObj.presentAddressRoad && baseObj.presentAddressThana.name == comparingObj.presentAddressThana.name && baseObj.presentAddressPostOfficeNo == comparingObj.presentAddressPostOfficeNo && baseObj.presentAddressDistrict.name == comparingObj.presentAddressDistrict.name
            //         && baseObj.presentAddressDivision.name == comparingObj.presentAddressDivision.name && baseObj.presentAddressCountry.name == comparingObj.presentAddressCountry.name && baseObj.permanentAddressHouse == comparingObj.permanentAddressHouse && baseObj.permanentAddressRoad == comparingObj.permanentAddressRoad &&
            //         baseObj.permanentAddressThana.name == comparingObj.permanentAddressThana.name && baseObj.permanentAddressPostOfficeNo == comparingObj.permanentAddressPostOfficeNo && baseObj.permanentAddressDistrict.name == comparingObj.permanentAddressDistrict.name && baseObj.permanentAddressDivision.name == comparingObj.permanentAddressDivision.name &&
            //         baseObj.permanentAddressCountry.name == comparingObj.permanentAddressCountry.name && baseObj.emergencyContactName == comparingObj.emergencyContactName && baseObj.emergencyContactRelation.name == comparingObj.emergencyContactRelation.name && baseObj.emergencyContactPhone == comparingObj.emergencyContactPhone &&
            //         baseObj.emergencyContactAddress == comparingObj.emergencyContactAddress){
            //         return true;
            //     }
            //     else{
            //         return false;
            //     }
            // }
            if(objType == "academic") {
                if (baseObj.degree.name == comparingObj.degree.name && baseObj.institution == comparingObj.institution
                    && baseObj.passingYear == comparingObj.passingYear) {
                    return true;
                }
                else {
                    return false;
                }
            }
            if(objType == "publication"){
                if(baseObj.publicationType.name == comparingObj.publicationType.name && baseObj.publicationISSN == comparingObj.publicationISSN && baseObj.publicationTitle == comparingObj.publicationTitle
                && baseObj.publicationVolume == comparingObj.publicationVolume && baseObj.dateOfPublication == comparingObj.dateOfPublication && baseObj.publicationIssue == comparingObj.publicationIssue &&
                baseObj.publicationJournalName == comparingObj.publicationJournalName && baseObj.publicationPages == comparingObj.publicationPages && baseObj.publicationWebLink == comparingObj.publicationWebLink
                && baseObj.publicationInterestGenre == comparingObj.publicationInterestGenre && baseObj.publicationCountry == comparingObj.publicationCountry && baseObj.publisherName == comparingObj.publisherName){
                    return true;
                }
                else{
                    return false;
                }
            }
            if(objType == "training"){
                if(baseObj.trainingName == comparingObj.trainingName && baseObj.trainingInstitution == comparingObj.trainingInstitution && baseObj.trainingFrom == comparingObj.trainingFrom
                && baseObj.trainingTo == comparingObj.trainingTo){
                    return true;
                }
                else{
                    return false;
                }
            }
            if(objType == "award"){
                if(baseObj.awardName == comparingObj.awardName && baseObj.awardedYear == comparingObj.awardedYear && baseObj.awardInstitute == comparingObj.awardInstitute
                && baseObj.awardShortDescription == comparingObj.awardShortDescription){
                    return true;
                }
                else{
                    return false;
                }
            }
            if(objType == "experience"){
                if(baseObj.experienceInstitution == comparingObj.experienceInstitution && baseObj.experienceDesignation == comparingObj.experienceDesignation && baseObj.experienceFrom == comparingObj.experienceFrom
                && baseObj.experienceTo == comparingObj.experienceTo){
                    return true;
                }
                else{
                    return false;
                }
            }
        }

        private submitPublicationForm(): void {
            let publicationObjects = this.ObjectDetectionForCRUDOperation("publication", this.$scope.previousPublicationInformation, this.$scope.entry.publication);
            this.convertToJson('publication', publicationObjects)
                .then((json: any) => {
                    this.publicationInformationService.savePublicationInformation(json)
                        .then((message: any) => {
                        this.getPublicationInformation();
                        this.getPublicationInformationWithPagination();
                        this.enableViewMode('publication');
                        });
                });
        }

        private submitTrainingForm(): void{
            let trainingObjects = this.ObjectDetectionForCRUDOperation("training", this.$scope.previousTrainingInformation, this.$scope.entry.training);
            this.convertToJson('training', trainingObjects)
                .then((json: any) => {
                    this.trainingInformationService.saveTrainingInformation(json)
                        .then((message: any) => {
                        this.getTrainingInformation();
                        this.enableViewMode('training');
                        });
                });
        }

        private submitAwardForm(): void {
            let awardObjects = this.ObjectDetectionForCRUDOperation("award", this.$scope.previousAwardInformation, this.$scope.entry.award);
            this.convertToJson('award', awardObjects)
                .then((json: any) => {
                    this.awardInformationService.saveAwardInformation(json)
                        .then((message: any) => {
                        this.getAwardInformation();
                        this.enableViewMode('award');
                        });
                });

        }

        private submitExperienceForm(): void{
            let experienceObjects = this.ObjectDetectionForCRUDOperation("experience", this.$scope.previousExperienceInformation, this.$scope.entry.experience);
            this.convertToJson('experience', experienceObjects)
                .then((json: any) => {
                    this.experienceInformationService.saveExperienceInformation(json)
                        .then((message: any) => {
                        this.getExperienceInformation();
                        this.enableViewMode('experience');
                        });
                });
        }

        private submitAdditionalForm(): void{

        }

        private getPersonalInformation() {
            this.personalInformationService.getPersonalInformation().then((personalInformation: any) => {
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

        private getAcademicInformation() {
            this.academicInformationService.getAcademicInformation().then((academicInformation: any) => {
                console.log(academicInformation);
                this.$scope.entry.academic = Array<IAcademicInformationModel>();
                for (let i = 0; i < academicInformation.length; i++) {
                    this.$scope.entry.academic[i] = academicInformation[i];
                    this.$scope.entry.academic[i].degree = this.$scope.degreeNameMap[academicInformation[i].degreeId];
                }
                this.$scope.previousAcademicInformation = angular.copy(this.$scope.entry.academic);
            });
        }

        private getPublicationInformation() {
            this.$scope.entry.publication = Array<IPublicationInformationModel>();
            this.$scope.paginatedPublication = Array<IPublicationInformationModel>();
            this.publicationInformationService.getPublicationInformation().then((publicationInformation: any) => {
                this.$scope.data.totalRecord = publicationInformation.length;
                for (let i = 0; i < publicationInformation.length; i++) {
                    this.$scope.entry.publication[i] = publicationInformation[i];
                    this.$scope.entry.publication[i].publicationType = this.$scope.publicationTypeMap[publicationInformation[i].publicationType];
                }
                this.$scope.previousPublicationInformation = angular.copy(this.$scope.entry.publication);
            });
        }

        private getPublicationInformationWithPagination(){
            this.publicationInformationService.getPublicationInformationViewWithPagination(this.$scope.pagination.currentPage, this.$scope.data.itemPerPage).then((publicationInformationWithPagination: any) => {
                this.$scope.paginatedPublication = publicationInformationWithPagination;
                for (let i = 0; i < publicationInformationWithPagination.length; i++) {
                    this.$scope.paginatedPublication[i] = publicationInformationWithPagination[i];
                    this.$scope.paginatedPublication[i].publicationType = this.$scope.publicationTypeMap[publicationInformationWithPagination[i].publicationType];
                }
            });
        }

        private getTrainingInformation() {
            this.$scope.entry.training = Array<ITrainingInformationModel>();
            this.trainingInformationService.getTrainingInformation().then((trainingInformation: any) => {
                for (let i = 0; i < trainingInformation.length; i++) {
                    this.$scope.entry.training[i] = trainingInformation[i];
                }
                this.$scope.previousTrainingInformation = angular.copy(this.$scope.entry.training);
            });
        }

        private getAwardInformation() {
            this.$scope.entry.award = Array<IAwardInformationModel>();
            this.awardInformationService.getAwardInformation().then((awardInformation: any) => {
                for (let i = 0; i < awardInformation.length; i++) {
                    this.$scope.entry.award[i] = awardInformation[i];
                }
                this.$scope.previousAwardInformation = angular.copy(this.$scope.entry.award);
            });
        }

        private getExperienceInformation() {
            this.$scope.entry.experience = Array<IExperienceInformationModel>();
            this.experienceInformationService.getExperienceInformation().then((experienceInformation: any) => {
                for (let i = 0; i < experienceInformation.length; i++) {
                    this.$scope.entry.experience[i] = experienceInformation[i];
                }
                this.$scope.previousExperienceInformation = angular.copy(this.$scope.entry.experience);
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
                academicEntry = {id: null, employeeId: "", degree: null, degreeId: null, institution: "", passingYear: null, dbAction: ""};
                this.$scope.entry.academic.push(academicEntry);
            } else if (divName === 'publication') {
                let publicationEntry: IPublicationInformationModel;
                publicationEntry = {id: null, employeeId: "", publicationTitle: "", publicationType: null, publicationInterestGenre: "", publicationWebLink: "", publisherName: "", dateOfPublication: "", publicationISSN: "", publicationIssue: "",
                    publicationVolume: "", publicationJournalName: "", publicationCountry: "", status: "", publicationPages: "", appliedOn: "", actionTakenOn: "", rowNumber: null, dbAction: ""};
                this.$scope.entry.publication.push(publicationEntry);
            } else if (divName === 'training') {
                let trainingEntry: ITrainingInformationModel;
                trainingEntry = {id: null, employeeId: "", trainingName: "", trainingInstitution: "", trainingFrom: "", trainingTo: "", dbAction: ""};
                this.$scope.entry.training.push(trainingEntry);
            } else if (divName === 'award') {
                let awardEntry: IAwardInformationModel;
                awardEntry = {id: null, employeeId: "", awardName: "", awardInstitute: "", awardedYear: "", awardShortDescription: "", dbAction: ""};
                this.$scope.entry.award.push(awardEntry);
            } else if (divName === 'experience') {
                let experienceEntry: IExperienceInformationModel;
                experienceEntry = {id: null, employeeId: "", experienceInstitution: "", experienceDesignation: "", experienceFrom: "", experienceTo: "", dbAction: ""};
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
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
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