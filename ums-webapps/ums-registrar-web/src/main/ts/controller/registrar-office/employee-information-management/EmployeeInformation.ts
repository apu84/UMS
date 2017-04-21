module ums{
  interface IEmployeeInformation extends ng.IScope {
    personalTab: boolean;
    academicTab: boolean;
    publicationTab: boolean;
    trainingTab: boolean;
    awardTab: boolean;
    experienceTab: boolean;

    showPersonalInputDiv: boolean;
    showPersonalLabelDiv: boolean;
    showAcademicInputDiv: boolean;
    showAcademicLabelDiv: boolean;
    showPublicationInputDiv: boolean;
    showPublicationLabelDiv: boolean;
    showTrainingInputDiv: boolean;
    showTrainingLabelDiv: boolean;
    showAwardInputDiv: boolean;
    showAwardLabelDiv: boolean;
    showExperienceInputDiv: boolean;
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

    disablePresentAddressDropdown: boolean;
    disablePermanentAddressDropdown: boolean;
    showSup: boolean;


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

    changeNav: Function;
    edit: Function;
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
    disablePresentAddressFields: Function;
    disablePermanentAddressFields: Function;

    fillEmergencyContactAddress: Function;

    getCountry: Function;
    getDivision: Function;
    getDistrictL: Function;
    getThana: Function;
    
    entry: IEntry;
    gender: Array<IGender>;
    maritalStatus: Array<IMaritalStatus>;
    religions: Array<IReligion>;
    nationalities: Array<INationality>;
    degreeNames: Array<IDegreeType>;
    bloodGroups: Array<IBloodGroup>;
    publicationTypes: Array<IPublicationType>;
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

  export interface INationality{
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

  export interface ICountry{
    id: number;
    name: string;
  }

  export interface IDivision{
    id: string;
    name: string;
  }

  export interface IDistrict{
    id: string;
    division_id: string;
    name: string;
  }

  export interface IThana{
    id: string;
    district_id: string;
    name: string;
  }

  export interface IRelation{
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
    public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce', 'countryService', 'divisionService', 'districtService', 'thanaService', 'employeeInformationService'];
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
                private employeeInformationService: EmployeeInformationService) {

      $scope.entry = {
       personal: <IPersonalInformationModel> {},
       academic: new Array<IAcademicInformationModel>(),
       publication: new Array<IPublicationInformationModel>(),
       training: new Array<ITrainingInformationModel>(),
       award: new Array<IAwardInformationModel>(),
       experience: new Array<IExperienceInformationModel>()
       };

      $scope.data = {
        supOptions: ""
      };

      $scope.changeNav = this.changeNav.bind(this);
      $scope.testData = this.testData.bind(this);
      $scope.submitPersonalForm = this.submitPersonalForm.bind(this);
      $scope.submitAcademicForm = this.submitAcademicForm.bind(this);
      $scope.submitPublicationForm = this.submitPublicationForm.bind(this);
      $scope.submitTrainingForm = this.submitTrainingForm.bind(this);
      $scope.submitAwardForm = this.submitAwardForm.bind(this);
      $scope.submitExperienceForm = this.submitExperienceForm.bind(this);
      $scope.edit = this.edit.bind(this);
      $scope.addNewRow = this.addNewRow.bind(this);
      $scope.deleteRow = this.deleteRow.bind(this);
      $scope.sameAsPresentAddress = this.sameAsPresentAddress.bind(this);
      $scope.getCountry = this.getCountry.bind(this);
      $scope.changePresentAddressDistrict = this.changePresentAddressDistrict.bind(this);
      $scope.changePresentAddressThana = this.changePresentAddressThana.bind(this);
      $scope.changePermanentAddressDistrict = this.changePermanentAddressDistrict.bind(this);
      $scope.changePermanentAddressThana = this.changePermanentAddressThana.bind(this);
      $scope.disablePresentAddressFields = this.disablePresentAddressFields.bind(this);
      $scope.disablePermanentAddressFields = this.disablePermanentAddressFields.bind(this);
      $scope.fillEmergencyContactAddress = this.fillEmergencyContactAddress.bind(this);

      this.initializeVariables();
      console.log("i am in EmployeeInformation.ts");
    }

    private initializeVariables() {
      this.$scope.disablePresentAddressDropdown = false;
      this.$scope.disablePermanentAddressDropdown = false;
      this.$scope.showSup = false;

      this.getCountry();
      this.getDivision();
      this.getDistrict();
      this.getThana();

      this.$scope.gender = this.registrarConstants.gender;
      this.$scope.religions = this.registrarConstants.religionTypes;
      this.$scope.nationalities = this.registrarConstants.nationalities;
      this.$scope.bloodGroups = this.registrarConstants.bloodGroups;
      this.$scope.maritalStatus = this.registrarConstants.maritalStatus;
      this.$scope.publicationTypes = this.registrarConstants.publicationTypes;
      this.$scope.degreeNames = this.registrarConstants.degreeTypes;
      this.$scope.relations = this.registrarConstants.relationTypes;

      this.$scope.entry.personal.gender = this.$scope.gender[0];
      this.$scope.entry.personal.religion = this.$scope.religions[0];
      this.$scope.entry.personal.nationality = this.$scope.nationalities[0];
      this.$scope.entry.personal.bloodGroup = this.$scope.bloodGroups[0];
      this.$scope.entry.personal.maritalStatus = this.$scope.maritalStatus[0];
      this.$scope.entry.personal.emergencyContactRelation = this.$scope.relations[0];

      this.addNewRow("academic");
      this.addNewRow("publication");
      this.addNewRow("training");
      this.addNewRow("award");
      this.addNewRow("experience");

      this.addDate();
      this.changeNav("personal");
      this.createMap();
      this.getEmployeeInformation();
    }

    private getEmployeeInformation() {
      this.getPersonalInformation();
      // this.getAcademicInformation();
      // this.getAwardInformation();
      // this.getPublicationInformation();
      // this.getExperienceInformation();
      // this.getTrainingInformation();
    }

    private createMap(){
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

      for(var i = 0; i < this.$scope.degreeNames.length; i++){
        this.$scope.degreeNameMap[this.$scope.degreeNames[i].name] = this.$scope.degreeNames[i];
      }
      for(var i = 0; i < this.$scope.gender.length; i++){
        this.$scope.genderNameMap[this.$scope.gender[i].id] = this.$scope.gender[i];
      }
      for(var i = 0; i < this.$scope.religions.length; i++){
        this.$scope.religionNameMap[this.$scope.religions[i].name] = this.$scope.religions[i];
      }
      for(var i = 0; i < this.$scope.nationalities.length; i++){
        this.$scope.nationalityMap[this.$scope.nationalities[i].name] = this.$scope.nationalities[i];
      }
      for(var i = 0; i < this.$scope.bloodGroups.length; i++){
        this.$scope.bloodGroupMap[this.$scope.bloodGroups[i].name] = this.$scope.bloodGroups[i];
      }
      for(var i = 0; i < this.$scope.maritalStatus.length; i++){
        this.$scope.martialStatusMap[this.$scope.maritalStatus[i].name] = this.$scope.maritalStatus[i];
      }
      for(var i = 0; i < this.$scope.publicationTypes.length; i++){
        this.$scope.publicationTypeMap[this.$scope.publicationTypes[i].name] = this.$scope.publicationTypes[i];
      }
      for(var i = 0; i < this.$scope.relations.length; i++){
        this.$scope.relationMap[this.$scope.relations[i].name] = this.$scope.relations[i];
      }
    }


    private changeNav(navTitle: string){
      this.$scope.personalTab = false;
      this.$scope.academicTab = false;
      this.$scope.publicationTab = false;
      this.$scope.trainingTab = false;
      this.$scope.awardTab = false;
      this.$scope.experienceTab = false;

      if(navTitle === ""){
        this.$scope.personalTab = true;
      }
      else if(navTitle === "personal"){
        this.$scope.personalTab = true;
        this.$scope.showPersonalInputDiv = false;
        this.$scope.showPersonalLabelDiv = true;
        this.$scope.showRequireSign = false;
        this.$scope.showPermanentAddressCheckbox = false;
        this.$scope.showSup = false;
      }
      else if(navTitle === "academic"){
        this.$scope.academicTab = true;
        this.$scope.showAcademicInputDiv = false;
        this.$scope.showAcademicLabelDiv = true;
        this.$scope.showAcademicAddIcon = false;
        this.$scope.showAcademicCrossIcon = false;
        this.$scope.showRequireSign = false;
      }
      else if(navTitle === "publication"){
        this.$scope.publicationTab = true;
        this.$scope.showPublicationInputDiv = false;
        this.$scope.showPublicationLabelDiv = true;
        this.$scope.showPublicationAddIcon = false;
        this.$scope.showPublicationCrossIcon = false;
      }
      else if(navTitle === "training"){
        this.$scope.trainingTab = true;
        this.$scope.showTrainingInputDiv = false;
        this.$scope.showTrainingLabelDiv = true;
        this.$scope.showTrainingAddIcon = false;
        this.$scope.showTrainingCrossIcon = false;
      }
      else if(navTitle === "award"){
        this.$scope.awardTab = true;
        this.$scope.showAwardInputDiv = false;
        this.$scope.showAwardLabelDiv = true;
        this.$scope.showAwardAddIcon = false;
        this.$scope.showAwardCrossIcon = false;
      }
      else if(navTitle === "experience"){
        this.$scope.experienceTab = true;
        this.$scope.showExperienceInputDiv = false;
        this.$scope.showExperienceLabelDiv = true;
        this.$scope.showExperienceAddIcon = false;
        this.$scope.showExperienceCrossIcon = false;
      }
    }

    private testData(){
      console.log("i am in testData()");
      this.$scope.entry.personal = <IPersonalInformationModel>{};
      this.$scope.entry.personal.firstName = "Kawsur";
      this.$scope.entry.personal.lastName = "Mir Md.";
      this.$scope.entry.personal.fatherName = "Mir Abdul Aziz";
      this.$scope.entry.personal.motherName = "Mst Hosne Ara";
      this.$scope.entry.personal.gender = this.$scope.gender[1];
      this.$scope.entry.personal.birthday = "20/10/1995";
      this.$scope.entry.personal.nationality = this.$scope.nationalities[1];
      this.$scope.entry.personal.religion = this.$scope.religions[1];
      this.$scope.entry.personal.maritalStatus = this.$scope.maritalStatus[1];
      this.$scope.entry.personal.spouseName = "";
      this.$scope.entry.personal.nationalIdNo = "19952641478954758";
      this.$scope.entry.personal.spouseNationalIdNo = "";
      this.$scope.entry.personal.bloodGroup = this.$scope.bloodGroups[1];
      this.$scope.entry.personal.website = "www.kawsur.com";
      this.$scope.entry.personal.organizationalEmail = "kawsur.iums@aust.edu";
      this.$scope.entry.personal.personalEmail = "kawsurilu@yahoo.com";
      this.$scope.entry.personal.mobile = "+8801672494863";
      this.$scope.entry.personal.phone = "none";
      this.$scope.entry.personal.presentAddressHouse = "34/1";
      this.$scope.entry.personal.presentAddressRoad = "Kazi Riaz Uddin Road";
      this.$scope.entry.personal.permanentAddressHouse = "None";
      this.$scope.entry.personal.permanentAddressRoad = "";
      this.$scope.entry.personal.emergencyContactName = "None";
      this.$scope.entry.personal.emergencyContactRelation = this.$scope.relations[0];
      this.$scope.entry.personal.emergencyContactPhone = "None";
      this.$scope.entry.personal.emergencyContactAddress = "None";

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

    private submitPersonalForm(){
      console.log("i am in submitPersonalForm()");
      this.convertToJson('personal')
          .then((json: any) => {
            this.employeeInformationService.savePersonalInformation(json)
                .then((message: any) => {
                  console.log("This is message");
                  console.log(message);
                });
          });
      this.changeNav("personal");
    }

    private getPersonalInformation(){
      console.log("i am in getPersonalInformation()");
      this.employeeInformationService.getPersonalInformation().then((personalInformation: any) =>{
        console.log("Employee's Personal Information");
        console.log(personalInformation);
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
        this.$scope.entry.personal.presentAddressPoliceStation = this.$scope.thanaMap[personalInformation[0].presentAddressPoliceStation];
        this.$scope.entry.personal.permanentAddressCountry = this.$scope.countryMap[personalInformation[0].permanentAddressCountry];
        this.$scope.entry.personal.permanentAddressDivision = this.$scope.divisionMap[personalInformation[0].permanentAddressDivision];
        this.$scope.entry.personal.permanentAddressDistrict = this.$scope.districtMap[personalInformation[0].permanentAddressDistrict];
        this.$scope.entry.personal.permanentAddressPoliceStation = this.$scope.thanaMap[personalInformation[0].permanentAddressPoliceStation];
      });
    }

    private submitAcademicForm(){
      console.log("i am in submitAcademicForm()");
      this.convertToJson('academic')
          .then((json: any) => {
            this.employeeInformationService.saveAcademicInformation(json)
                .then((message: any) => {
                  console.log("This is message");
                  console.log(message);
                });
          });
      this.changeNav("academic");
    }

    private getAcademicInformation(){
      console.log("i am in getAcademicInformation()");
      this.employeeInformationService.getAcademicInformation().then((academicInformation: any) =>{
        console.log("Employee's Academic Information");
        console.log(academicInformation);
        for(var i = 0; i < academicInformation.length; i++) {
          this.$scope.entry.academic[i] = academicInformation[i];
          this.$scope.entry.academic[i].academicDegreeName = this.$scope.degreeNameMap[academicInformation[i].academicDegreeName];
        }
      });
    }

    private submitPublicationForm(){
      console.log("i am in submitPublicationForm()");
      this.convertToJson('publication')
          .then((json: any) => {
            this.employeeInformationService.savePublicationInformation(json)
                .then((message: any) => {
                  console.log("This is message");
                  console.log(message);
                });
          });
      this.changeNav("publication");
    }

    private getPublicationInformation(){
      console.log("i am in getPublicationInformation()");
      this.employeeInformationService.getPublicationInformation().then((publicationInformation: any) =>{
        console.log("Employee's Publication Information");
        console.log(publicationInformation);
        console.log("Publication.length");
        console.log(publicationInformation.length);
        for(var i = 0; i < publicationInformation.length; i++)
        {
          this.$scope.entry.publication[i] = publicationInformation[i];
        }
      });
    }

    private submitTrainingForm(){
      console.log("i am in submitTrainingForm()");
      this.convertToJson('training')
          .then((json: any) => {
            this.employeeInformationService.saveTrainingInformation(json)
                .then((message: any) => {
                  console.log("This is message");
                  console.log(message);
                });
          });
      this.changeNav("training");
    }

    private getTrainingInformation(){
      console.log("i am in getTrainingInformation()");
      this.employeeInformationService.getTrainingInformation().then((trainingInformation: any) =>{
        console.log("Employee's Training Information");
        console.log(trainingInformation);
        for(var i = 0; i < trainingInformation.length; i++)
        {
          this.$scope.entry.training[i] = trainingInformation[i];
        }
      });
    }

    private submitAwardForm(){
      console.log("i am in submitAwardForm()");
      this.convertToJson('award')
          .then((json: any) => {
            this.employeeInformationService.saveAwardInformation(json)
                .then((message: any) => {
                  console.log("This is message");
                  console.log(message);
                });
          });
      this.changeNav("award");
    }

    private getAwardInformation(){
      console.log("i am in getAwardInformation()");
      this.employeeInformationService.getAwardInformation().then((awardInformation: any) =>{
        console.log("Employee's award Information");
        console.log(awardInformation);
        for(var i = 0; i < awardInformation.length; i++)
        {
          this.$scope.entry.training[i] = awardInformation[i];
        }

      });
    }

    private submitExperienceForm(){
      console.log("i am in submitExperienceForm()");
      this.convertToJson('experience')
          .then((json: any) => {
            this.employeeInformationService.saveExperienceInformation(json)
                .then((message: any) => {
                  console.log("This is message");
                  console.log(message);
                });
          });
      this.changeNav("experience");
    }

    private getExperienceInformation(){
      console.log("i am in getExperienceInformation()");
      this.employeeInformationService.getExperienceInformation().then((experienceInformation: any) =>{
        console.log("Employee's Experience Information");
        console.log(experienceInformation);
        for(var i = 0; i < experienceInformation.length; i++)
        {
          this.$scope.entry.training[i] = experienceInformation[i];
        }
      });
    }

    private edit(formName: string){
      console.log("i am in edit()");

      if(formName === "personal") {
        this.$scope.showPersonalInputDiv = true;
        this.$scope.showPersonalLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showPermanentAddressCheckbox = true;
        this.$scope.showSup = true;
      }
      else if(formName === "academic") {
        this.$scope.showAcademicInputDiv = true;
        this.$scope.showAcademicLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showAcademicAddIcon = true;
        this.$scope.showAcademicCrossIcon = true;
        this.$scope.showRequireSign = true;
      }
      else if(formName === "publication") {
        this.$scope.showPublicationInputDiv = true;
        this.$scope.showPublicationLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showPublicationAddIcon = true;
        this.$scope.showPublicationCrossIcon = true;
      }
      else if(formName === "training") {
        this.$scope.showTrainingInputDiv = true;
        this.$scope.showTrainingLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showTrainingAddIcon = true;
        this.$scope.showTrainingCrossIcon = true;
      }
      else if(formName === "award") {
        this.$scope.showAwardInputDiv = true;
        this.$scope.showAwardLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showAwardAddIcon = true;
        this.$scope.showAwardCrossIcon = true;
      }
      else if(formName === "experience") {
        this.$scope.showExperienceInputDiv = true;
        this.$scope.showExperienceLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showExperienceAddIcon = true;
        this.$scope.showExperienceCrossIcon = true;
      }
    }

    private addNewRow(divName: string){
      console.log("i am in addNewRow()");
      if(divName === 'academic') {
        var academicEntry: IAcademicInformationModel;
        academicEntry = {employeeId: "", academicDegreeName: this.$scope.degreeNames[0], academicInstitution: "", academicPassingYear: ""};
        this.$scope.entry.academic.push(academicEntry);
      }
      else if(divName === 'publication'){
        var publicationEntry: IPublicationInformationModel;
        publicationEntry = {employeeId: "", publicationTitle: "", publicationType: this.$scope.publicationTypes[0], publicationInterestGenre: "", publicationWebLink: "", publisherName: "", dateOfPublication: ""};
        this.$scope.entry.publication.push(publicationEntry);
      }
      else if(divName === 'training'){
        var trainingEntry: ITrainingInformationModel;
        trainingEntry = {employeeId: "", trainingName: "", trainingInstitution: "", trainingFrom: "", trainingTo: "", trainingDuration: ""};
        this.$scope.entry.training.push(trainingEntry);
      }
      else if(divName === 'award'){
        var awardEntry: IAwardInformationModel;
        awardEntry = {employeeId: "", awardName: "", awardInstitute: "", awardedYear: "", awardShortDescription: ""};
        this.$scope.entry.award.push(awardEntry);
      }
      else if(divName === 'experience'){
        var experienceEntry: IExperienceInformationModel;
        experienceEntry = {employeeId: "", experienceInstitution: "", experienceDesignation: "", experienceFrom: "", experienceTo: "" };
        this.$scope.entry.experience.push(experienceEntry);
      }
    }
    private deleteRow(divName: string, index: number) {
      console.log("i am in deleteRow()");
      if(divName === 'academic') {
        this.$scope.entry.academic.splice(index, 1);
      }
      else if(divName === 'publication'){
        console.log("this.$scope.entry.publication");
        console.log(this.$scope.entry.publication);
        this.$scope.entry.publication.splice(index, 1);
      }
      else if(divName === 'training'){
        this.$scope.entry.training.splice(index, 1);
      }
      else if(divName === 'award'){
        this.$scope.entry.award.splice(index, 1);
      }
      else if(divName === 'experience'){
        this.$scope.entry.experience.splice(index, 1);
      }

    }

    public convertToJson(convertThis: string): ng.IPromise<any>{
      console.log("I am in convertToJSon()");
      var defer = this.$q.defer();
      var JsonObject = {};
      var JsonArray = [];
      var item: any = {};

      if(convertThis === "personal") {
        var personalInformation = <IPersonalInformationModel> {};
        personalInformation = this.$scope.entry.personal;
        item['personal'] = personalInformation;
      }

      else if(convertThis === "academic") {
        var academicInformation = new Array<IAcademicInformationModel>();
        for (var i = 0; i < this.$scope.entry.academic.length; i++) {
          academicInformation = this.$scope.entry.academic;
        }
        item['academic'] = academicInformation;
      }

      else if(convertThis === "publication") {
        var publicationInformation = new Array<IPublicationInformationModel>();
        for (var i = 0; i < this.$scope.entry.publication.length; i++) {
          publicationInformation = this.$scope.entry.publication;
        }
        item['publication'] = publicationInformation;
      }

      else if(convertThis === "training") {
        var trainingInformation = new Array<ITrainingInformationModel>();
        for (var i = 0; i < this.$scope.entry.training.length; i++) {
          trainingInformation = this.$scope.entry.training;
        }
        item['training'] = trainingInformation;
      }

      else if(convertThis === "award") {
        var awardInformation = new Array<IAwardInformationModel>();
        for (var i = 0; i < this.$scope.entry.award.length; i++) {
          awardInformation = this.$scope.entry.award;
        }
        item['award'] = awardInformation;
      }

      else if(convertThis === "experience") {
        var experienceInformation = new Array<IExperienceInformationModel>();
        for (var i = 0; i < this.$scope.entry.experience.length; i++) {
          experienceInformation = this.$scope.entry.experience;
        }
        item['experience'] = experienceInformation;
      }

      JsonArray.push(item);
      JsonObject['entries'] = JsonArray;

      console.log("My Json Data");
      console.log(JsonObject);

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

    private getCountry(){
      console.log("i am in getCountry()");
      this.countryService.getCountryList().then((country: any) => {
        this.$scope.countries = country.entries;
        this.$scope.entry.personal.presentAddressCountry = this.$scope.countries[17];
        this.$scope.entry.personal.permanentAddressCountry = this.$scope.countries[17];
        console.log("Finding Country length");
        console.log(this.$scope.countries.length);
        for(var i = 0; i < this.$scope.countries.length; i++){
          this.$scope.countryMap[this.$scope.countries[i].name] = this.$scope.countries[i];
        }
      });
    }

    private getDivision(){
      console.log("i am in getDivision()");
      this.divisionService.getDivisionList().then((division: any) => {
        this.$scope.divisions = division.entries;
        this.$scope.entry.personal.presentAddressDivision = this.$scope.divisions[0];
        this.$scope.entry.personal.permanentAddressDivision = this.$scope.divisions[0];
        for(var i = 0; i < this.$scope.divisions.length; i++){
          this.$scope.divisionMap[this.$scope.divisions[i].name] = this.$scope.divisions[i];
        }
      });
    }

    private getDistrict(){
      console.log("i am in getDistrict()");
      this.districtService.getDistrictList().then((district: any) => {
        this.$scope.presentAddressDistricts = district.entries;
        this.$scope.permanentAddressDistricts = district.entries;
        this.$scope.allDistricts = district.entries;
        this.$scope.entry.personal.presentAddressDistrict = this.$scope.presentAddressDistricts[17];
        this.$scope.entry.personal.permanentAddressDistrict = this.$scope.permanentAddressDistricts[17];
        for(var i = 0; i < this.$scope.allDistricts.length; i++){
          this.$scope.districtMap[this.$scope.allDistricts[i].name] = this.$scope.allDistricts[i];
        }
      });
    }

    private getThana(){
      console.log("i am in getThana()");
      this.thanaService.getThanaList().then((thana: any) => {
        this.$scope.presentAddressThanas = thana.entries;
        this.$scope.permanentAddressThanas = thana.entries;
        this.$scope.allThanas = thana.entries;
        this.$scope.entry.personal.presentAddressPoliceStation = this.$scope.presentAddressThanas[196];
        this.$scope.entry.personal.permanentAddressPoliceStation = this.$scope.permanentAddressThanas[196];
        for(var i = 0; i < this.$scope.allThanas.length; i++){
          this.$scope.thanaMap[this.$scope.allThanas[i].name] = this.$scope.allThanas[i];
        }
      });
    }

    private changePresentAddressDistrict(){
      this.$scope.presentAddressDistricts = [];
      var districtLength = this.$scope.allDistricts.length;
      var index = 0;
      for(var i = 0; i < districtLength; i++){
        if(this.$scope.entry.personal.presentAddressDivision.id === this.$scope.allDistricts[i].division_id ){
          this.$scope.presentAddressDistricts[index++] = this.$scope.allDistricts[i];
        }
      }
      this.$scope.entry.personal.presentAddressDistrict = this.$scope.presentAddressDistricts[1];
    }

    private changePermanentAddressDistrict(){
      this.$scope.permanentAddressDistricts = [];
      var districtLength = this.$scope.allDistricts.length;
      var index = 0;
      for(var i = 0; i < districtLength; i++){
        if(this.$scope.entry.personal.permanentAddressDivision.id === this.$scope.allDistricts[i].division_id ){
          this.$scope.permanentAddressDistricts[index++] = this.$scope.allDistricts[i];
        }
      }
      this.$scope.entry.personal.permanentAddressDistrict = this.$scope.permanentAddressDistricts[1];
    }

    private changePresentAddressThana(){
      this.$scope.presentAddressThanas = [];
      var thanaLength = this.$scope.allThanas.length;
      var index = 0;
      for(var i = 0; i < thanaLength; i++){
        if(this.$scope.entry.personal.presentAddressDistrict.id === this.$scope.allThanas[i].district_id ){
          this.$scope.presentAddressThanas[index++] = this.$scope.allThanas[i];
        }
      }
      this.$scope.entry.personal.presentAddressPoliceStation = this.$scope.presentAddressThanas[0];
    }

    private changePermanentAddressThana(){
      this.$scope.permanentAddressThanas = [];
      var thanaLength = this.$scope.allThanas.length;
      var index = 0;
      for(var i = 0; i < thanaLength; i++){
        if(this.$scope.entry.personal.permanentAddressDistrict.id === this.$scope.allThanas[i].district_id ){
          this.$scope.permanentAddressThanas[index++] = this.$scope.allThanas[i];
        }
      }
      this.$scope.entry.personal.permanentAddressPoliceStation = this.$scope.permanentAddressThanas[0];
    }

    private sameAsPresentAddress(){
      console.log("I am in sameAsPresentAddress()");
      this.$scope.entry.personal.permanentAddressHouse = this.$scope.entry.personal.presentAddressHouse;
      this.$scope.entry.personal.permanentAddressRoad = this.$scope.entry.personal.presentAddressRoad;
      this.$scope.entry.personal.permanentAddressPoliceStation = this.$scope.entry.personal.presentAddressPoliceStation;
      this.$scope.entry.personal.permanentAddressPostalCode = this.$scope.entry.personal.presentAddressPostalCode;
      this.$scope.entry.personal.permanentAddressDistrict = this.$scope.entry.personal.presentAddressDistrict;
      this.$scope.entry.personal.permanentAddressDivision = this.$scope.entry.personal.presentAddressDivision;
      this.$scope.entry.personal.permanentAddressCountry = this.$scope.entry.personal.presentAddressCountry;
      this.disablePermanentAddressFields();
    }

    private disablePresentAddressFields(){
      if(this.$scope.entry.personal.presentAddressCountry.name === "Bangladesh"){
        this.$scope.disablePresentAddressDropdown = false;
        this.$scope.entry.personal.presentAddressDivision = this.$scope.divisions[0];
        this.changePresentAddressDistrict();
        this.changePresentAddressThana();
      }
      else{
        this.$scope.disablePresentAddressDropdown = true;
        this.$scope.entry.personal.presentAddressDivision = null;
        this.$scope.entry.personal.presentAddressDistrict = null;
        this.$scope.entry.personal.presentAddressPoliceStation = null;
        this.$scope.entry.personal.presentAddressPostalCode = "";
      }
    }

    private disablePermanentAddressFields(){
      if(this.$scope.entry.personal.permanentAddressCountry.name === "Bangladesh"){
        this.$scope.disablePermanentAddressDropdown = false;
        this.changePermanentAddressDistrict();
        this.changePermanentAddressThana();
      }
      else{
        this.$scope.disablePermanentAddressDropdown = true;
        this.$scope.entry.personal.permanentAddressDivision = null;
        this.$scope.entry.personal.permanentAddressDistrict = null;
        this.$scope.entry.personal.permanentAddressPoliceStation = null;
        this.$scope.entry.personal.permanentAddressPostalCode = "";
      }
    }

    private fillEmergencyContactAddress(){
      var presentAddressLine1;
      var presentAddressLine2;
      var presentPostalCode;
      var permanentAddressLine1;
      var permanentAddressLine2;
      var permanentPostalCode;

      if(this.$scope.entry.personal.presentAddressHouse === ""  || this.$scope.entry.personal.presentAddressHouse === undefined){ presentAddressLine1 = ""; }
      else { presentAddressLine1 = this.$scope.entry.personal.presentAddressHouse;}

      if(this.$scope.entry.personal.presentAddressRoad === ""  || this.$scope.entry.personal.presentAddressRoad === undefined){ presentAddressLine2 = ""; }
      else { presentAddressLine2 = this.$scope.entry.personal.presentAddressRoad;}

      if(this.$scope.entry.personal.presentAddressPostalCode === "" || this.$scope.entry.personal.presentAddressPostalCode === undefined){ presentPostalCode = ""; }
      else{ presentPostalCode = this.$scope.entry.personal.presentAddressPostalCode;}

      if(this.$scope.entry.personal.permanentAddressHouse === ""  || this.$scope.entry.personal.permanentAddressHouse === undefined){ permanentAddressLine1 = ""; }
      else { permanentAddressLine1 = this.$scope.entry.personal.presentAddressHouse;}

      if(this.$scope.entry.personal.permanentAddressRoad === ""  || this.$scope.entry.personal.permanentAddressRoad === undefined){ permanentAddressLine2 = ""; }
      else { permanentAddressLine2 = this.$scope.entry.personal.presentAddressRoad;}

      if(this.$scope.entry.personal.permanentAddressPostalCode === "" || this.$scope.entry.personal.permanentAddressPostalCode === undefined){ permanentPostalCode = ""; }
      else{ permanentPostalCode = this.$scope.entry.personal.permanentAddressPostalCode;}

      if(this.$scope.data.supOptions === "1"){
        console.log("this.$scope.data.supOptions");
        console.log(this.$scope.data.supOptions);
        this.$scope.entry.personal.emergencyContactAddress = "";
      }
      else if(this.$scope.data.supOptions === "2")
      {        console.log("this.$scope.data.supOptions");
        console.log(this.$scope.data.supOptions);
        this.$scope.entry.personal.emergencyContactAddress = presentAddressLine1 + " " + presentAddressLine2 + " " +this.$scope.entry.personal.presentAddressPoliceStation.name + " " + this.$scope.entry.personal.presentAddressDistrict.name + " - " + presentPostalCode;
      }
      else if(this.$scope.data.supOptions === "3")
      {        console.log("this.$scope.data.supOptions");
        console.log(this.$scope.data.supOptions);
        this.$scope.entry.personal.emergencyContactAddress = permanentAddressLine1 + " " + permanentAddressLine2 + " " +this.$scope.entry.personal.permanentAddressPoliceStation.name + " " + this.$scope.entry.personal.permanentAddressDistrict.name + " - " + permanentPostalCode;
      }
    }
  }

  UMS.controller("EmployeeInformation",EmployeeInformation);
}