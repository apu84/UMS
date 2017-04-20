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

    saveAsPresentAddress: Function;
    changeDistrict: Function;

    getCountry: Function;
    getDivision: Function;
    getDistrictL: Function;
    getThana: Function;

    
    entry: IEntry;
    gender: Array<IGender>;
    maritalStatus: Array<IMaritalStatus>;
    religion: Array<IReligion>;
    nationality: Array<INationality>;
    degreeNames: Array<IDegreeType>;
    bloodGroup: Array<IBloodGroup>;
    typeOfPublication: Array<IPublicationType>;
    countries: Array<ICountry>;
    divisions: Array<IDivision>;
    districts: Array<IDistrict>;
    allDistricts: Array<IDistrict>;
    thanas: Array<IThana>;
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

      $scope.gender = registrarConstants.gender;
      $scope.religion = registrarConstants.religionTypes;
      $scope.nationality = registrarConstants.nationalities;
      $scope.typeOfPublication = registrarConstants.publicationTypes;
      $scope.maritalStatus = registrarConstants.maritalStatus;
      $scope.degreeNames = registrarConstants.degreeTypes;
      $scope.bloodGroup = registrarConstants.bloodGroups;

      $scope.entry = {
       personal: <IPersonalInformationModel> {},
       academic: new Array<IAcademicInformationModel>(),
       publication: new Array<IPublicationInformationModel>(),
       training: new Array<ITrainingInformationModel>(),
       award: new Array<IAwardInformationModel>(),
       experience: new Array<IExperienceInformationModel>()
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
      $scope.saveAsPresentAddress = this.saveAsPresentAddress.bind(this);
      $scope.getCountry = this.getCountry.bind(this);
      $scope.changeDistrict = this.changeDistrict.bind(this);

      this.changeNav("personal");
      this.addNewRow("academic");
      this.addNewRow("publication");
      this.addNewRow("training");
      this.addNewRow("award");
      this.addNewRow("experience");
      this.addDate();
      this.getCountry();
      this.getDivision();
      this.getDistrict();
      this.getThana();
      this.getPersonalInformation();
      // this.getAcademicInformation();
      // this.getAwardInformation();
      // this.getPublicationInformation();
      // this.getExperienceInformation();
      // this.getTrainingInformation();
      console.log("i am in EmployeeInformation.ts");

    }


    private changeNav(navTitle: string){
      this.$scope.personalTab = false;
      this.$scope.academicTab = false;
      this.$scope.publicationTab = false;
      this.$scope.trainingTab = false;
      this.$scope.awardTab = false;
      this.$scope.experienceTab = false;

      if(navTitle == ""){
        this.$scope.personalTab = true;
      }
      else if(navTitle == "personal"){
        this.$scope.personalTab = true;
        this.$scope.showPersonalInputDiv = false;
        this.$scope.showPersonalLabelDiv = true;
        this.$scope.showRequireSign = false;
        this.$scope.showPermanentAddressCheckbox = false;
      }
      else if(navTitle == "academic"){
        this.$scope.academicTab = true;
        this.$scope.showAcademicInputDiv = false;
        this.$scope.showAcademicLabelDiv = true;
        this.$scope.showAcademicAddIcon = false;
        this.$scope.showAcademicCrossIcon = false;
      }
      else if(navTitle == "publication"){
        this.$scope.publicationTab = true;
        this.$scope.showPublicationInputDiv = false;
        this.$scope.showPublicationLabelDiv = true;
        this.$scope.showPublicationAddIcon = false;
        this.$scope.showPublicationCrossIcon = false;
      }
      else if(navTitle == "training"){
        this.$scope.trainingTab = true;
        this.$scope.showTrainingInputDiv = false;
        this.$scope.showTrainingLabelDiv = true;
        this.$scope.showTrainingAddIcon = false;
        this.$scope.showTrainingCrossIcon = false;
      }
      else if(navTitle == "award"){
        this.$scope.awardTab = true;
        this.$scope.showAwardInputDiv = false;
        this.$scope.showAwardLabelDiv = true;
        this.$scope.showAwardAddIcon = false;
        this.$scope.showAwardCrossIcon = false;
      }
      else if(navTitle == "experience"){
        this.$scope.experienceTab = true;
        this.$scope.showExperienceInputDiv = false;
        this.$scope.showExperienceLabelDiv = true;
        this.$scope.showExperienceAddIcon = false;
        this.$scope.showExperienceCrossIcon = false;
      }
    }

    private testData(){
      console.log("i am in testData()");
      this.$scope.entry.personal.firstName = "Kawsur";
      this.$scope.entry.personal.lastName = "Mir Md.";
      this.$scope.entry.personal.fatherName = "Mir Abdul Aziz";
      this.$scope.entry.personal.motherName = "Mst Hosne Ara";
      this.$scope.entry.personal.gender = this.$scope.gender[1];
      this.$scope.entry.personal.birthday = "20/10/1995";
      this.$scope.entry.personal.nationality = this.$scope.nationality[1];
      this.$scope.entry.personal.religion = this.$scope.religion[1];
      this.$scope.entry.personal.maritalStatus = this.$scope.maritalStatus[1];
      this.$scope.entry.personal.spouseName = "";
      this.$scope.entry.personal.nationalIdNo = 19952641478954758;
      this.$scope.entry.personal.spouseNationalIdNo = 0;
      this.$scope.entry.personal.bloodGroup = this.$scope.bloodGroup[1];
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
      this.$scope.entry.personal.emergencyContactRelation = "None";
      this.$scope.entry.personal.emergencyContactPhone = "None";
      this.$scope.entry.personal.emergencyContactAddress = "None";

      this.$scope.entry.academic[0].academicDegreeName.name = "Bachelor";
      this.$scope.entry.academic[0].academicInstitution = "American International University-Bangladesh";
      this.$scope.entry.academic[0].academicPassingYear = "2016";

      this.$scope.entry.publication[0].publicationTitle = "N/A";
      this.$scope.entry.publication[0].publicationInterestGenre = "N/A";
      this.$scope.entry.publication[0].publisherName = "N/A";
      this.$scope.entry.publication[0].dateOfPublication = "11/11/3010";
      this.$scope.entry.publication[0].publicationType = this.$scope.typeOfPublication[1];
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
      this.employeeInformationService.getPersonalInformation("33333").then((personalInformation: any) =>{
        console.log("Employee's Personal Information");
        console.log(personalInformation);
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
      this.employeeInformationService.getAcademicInformation("33333").then((academicInformation: any) =>{
        console.log("Employee's Academic Information");
        console.log(academicInformation);
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
      this.employeeInformationService.getPublicationInformation("33333").then((publicationInformation: any) =>{
        console.log("Employee's Publication Information");
        console.log(publicationInformation);
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
      this.employeeInformationService.getTrainingInformation("33333").then((trainingInformation: any) =>{
        console.log("Employee's Training Information");
        console.log(trainingInformation);
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
      this.employeeInformationService.getAwardInformation("33333").then((awardInformation: any) =>{
        console.log("Employee's award Information");
        console.log(awardInformation);
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
      this.employeeInformationService.getExperienceInformation("33333").then((experienceInformation: any) =>{
        console.log("Employee's Experience Information");
        console.log(experienceInformation);
      });
    }

    private edit(formName: string){
      console.log("i am in edit()");

      if(formName == "personal") {
        this.$scope.showPersonalInputDiv = true;
        this.$scope.showPersonalLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showPermanentAddressCheckbox = true;
      }
      else if(formName == "academic") {
        this.$scope.showAcademicInputDiv = true;
        this.$scope.showAcademicLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showAcademicAddIcon = true;
        this.$scope.showAcademicCrossIcon = true;
      }
      else if(formName == "publication") {
        this.$scope.showPublicationInputDiv = true;
        this.$scope.showPublicationLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showPublicationAddIcon = true;
        this.$scope.showPublicationCrossIcon = true;
      }
      else if(formName == "training") {
        this.$scope.showTrainingInputDiv = true;
        this.$scope.showTrainingLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showTrainingAddIcon = true;
        this.$scope.showTrainingCrossIcon = true;
      }
      else if(formName == "award") {
        this.$scope.showAwardInputDiv = true;
        this.$scope.showAwardLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showAwardAddIcon = true;
        this.$scope.showAwardCrossIcon = true;
      }
      else if(formName == "experience") {
        this.$scope.showExperienceInputDiv = true;
        this.$scope.showExperienceLabelDiv = false;
        this.$scope.showRequireSign = true;
        this.$scope.showExperienceAddIcon = true;
        this.$scope.showExperienceCrossIcon = true;
      }
    }

    private addNewRow(divName: string){
      console.log("i am in addNewRow()");
      if(divName == 'academic') {
        var academicEntry: IAcademicInformationModel;
        academicEntry = {employeeId: "", academicDegreeName: this.$scope.degreeNames[1], academicInstitution: "", academicPassingYear: ""};
        this.$scope.entry.academic.push(academicEntry);
      }
      else if(divName == 'publication'){
        var publicationEntry: IPublicationInformationModel;
        publicationEntry = {employeeId: "", publicationTitle: "", publicationType: this.$scope.typeOfPublication[1], publicationInterestGenre: "", publicationWebLink: "", publisherName: "", dateOfPublication: ""};
        this.$scope.entry.publication.push(publicationEntry);
      }
      else if(divName == 'training'){
        var trainingEntry: ITrainingInformationModel;
        trainingEntry = {employeeId: "", trainingName: "", trainingInstitution: "", trainingFrom: "", trainingTo: "", trainingDuration: ""};
        this.$scope.entry.training.push(trainingEntry);
      }
      else if(divName == 'award'){
        var awarEntry: IAwardInformationModel;
        awarEntry = {employeeId: "", awardName: "", awardInstitute: "", awardedYear: "", awardShortDescription: ""};
        this.$scope.entry.award.push(awarEntry);
      }
      else if(divName == 'experience'){
        var enperienceEntry: IExperienceInformationModel;
        enperienceEntry = {employeeId: "", experienceInstitution: "", experienceDesignation: "", experienceFrom: "", experienceTo: "" };
        this.$scope.entry.experience.push(enperienceEntry);
      }
    }
    private deleteRow(divName: string, index: number) {
      console.log("i am in deleteRow()");
      if(divName == 'academic') {
        this.$scope.entry.academic.splice(index, 1);
      }
      else if(divName == 'publication'){
        console.log("this.$scope.entry.publication");
        console.log(this.$scope.entry.publication);
        this.$scope.entry.publication.splice(index, 1);
      }
      else if(divName == 'training'){
        this.$scope.entry.training.splice(index, 1);
      }
      else if(divName == 'award'){
        this.$scope.entry.award.splice(index, 1);
      }
      else if(divName == 'experience'){
        this.$scope.entry.experience.splice(index, 1);
      }

    }

    private saveAsPresentAddress(){
      console.log("I am in saveAsPresentAddress()");
      this.$scope.entry.personal.permanentAddressHouse = this.$scope.entry.personal.presentAddressHouse;
      this.$scope.entry.personal.permanentAddressRoad = this.$scope.entry.personal.presentAddressRoad;
      this.$scope.entry.personal.permanentAddressPoliceStation = this.$scope.entry.personal.presentAddressPoliceStation;
      this.$scope.entry.personal.permanentAddressPostalCode = this.$scope.entry.personal.presentAddressPostalCode;
      this.$scope.entry.personal.permanentAddressDistrict = this.$scope.entry.personal.presentAddressDistrict;
      this.$scope.entry.personal.permanentAddressDivision = this.$scope.entry.personal.presentAddressDivision;
      this.$scope.entry.personal.permanentAddressCountry = this.$scope.entry.personal.presentAddressCountry;
    }

    public convertToJson(convertThis: string): ng.IPromise<any>{
      console.log("I am in convertToJSon()");
      var defer = this.$q.defer();
      var JsonObject = {};
      var JsonArray = [];
      var item: any = {};

      if(convertThis == "personal") {
        var personalInformation = <IPersonalInformationModel> {};
        personalInformation = this.$scope.entry.personal;
        item['personal'] = personalInformation;
      }

      else if(convertThis == "academic") {
        var academicInformation = new Array<IAcademicInformationModel>();
        for (var i = 0; i < this.$scope.entry.academic.length; i++) {
          academicInformation = this.$scope.entry.academic;
        }
        item['academic'] = academicInformation;
      }

      else if(convertThis == "publication") {
        var publicationInformation = new Array<IPublicationInformationModel>();
        for (var i = 0; i < this.$scope.entry.publication.length; i++) {
          publicationInformation = this.$scope.entry.publication;
        }
        item['publication'] = publicationInformation;
      }

      else if(convertThis == "training") {
        var trainingInformation = new Array<ITrainingInformationModel>();
        for (var i = 0; i < this.$scope.entry.training.length; i++) {
          trainingInformation = this.$scope.entry.training;
        }
        item['training'] = trainingInformation;
      }

      else if(convertThis == "award") {
        var awardInformation = new Array<IAwardInformationModel>();
        for (var i = 0; i < this.$scope.entry.award.length; i++) {
          awardInformation = this.$scope.entry.award;
        }
        item['award'] = awardInformation;
      }

      else if(convertThis == "experience") {
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
      });
    }

    private getDivision(){
      console.log("i am in getDivision()");
      this.divisionService.getDivisionList().then((division: any) => {
        this.$scope.divisions = division.entries;
      });
    }

    private getDistrict(){
      console.log("i am in getDistrict()");
      this.districtService.getDistrictList().then((district: any) => {
        this.$scope.allDistricts = district.entries;
        //this.$scope.districts = district.entries;
      });
    }

    private getThana(){
      console.log("i am in getThana()");
      this.thanaService.getThanaList().then((thana: any) => {
        this.$scope.thanas = thana.entries;
      });
    }

    private changeDistrict(){
      var districtLength = this.$scope.allDistricts.length;
      var index = 0;
      console.log("i am in changeDistrict()");
      console.log(districtLength);
      for(var i = 0; i < districtLength; i++){
        if(this.$scope.entry.personal.presentAddressDivision.id == this.$scope.allDistricts[i].division_id ){
          this.$scope.districts[index++] = this.$scope.allDistricts[i];
        }
      }
    }
  }


  UMS.controller("EmployeeInformation",EmployeeInformation);
}