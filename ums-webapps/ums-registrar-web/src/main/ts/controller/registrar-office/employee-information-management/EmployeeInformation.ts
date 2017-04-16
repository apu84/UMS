module ums{
  interface IEmployeeInformation extends ng.IScope {

    personalTab: boolean;
    academicTab: boolean;
    publicationTab: boolean;
    trainingTab: boolean;
    awardTab: boolean;
    experienceTab: boolean;

    showInputDiv: boolean;
    showLabelDiv: boolean;
    hideRequireSpan: boolean;
    required: boolean;
    //showPermanentAddressCheckbox: boolean;

    changeNav: Function;
    submit: Function;
    cancel: Function;
    addNewRow: Function;
    deleteRow: Function;
    testData: Function;
    saveAsPresentAddress: Function;
    getDesignation: Function;
    getEmploymentType: Function;
    getDeptOffice: Function;

    entry: IEntry;
    employeeGender: Array<IEmpGender>;
    employeeMaritalStatus: Array<IEmpMaritalStatus>;
    employeeReligion: Array<IReligion>;
    employeeNationality: Array<INationality>;
    degreeNames: Array<IEmpDegree>;
    typeOfPublication: Array<IPublicationType>;

  }

  interface IEntry {
    personal: IPersonalInformationModel;
    academic: Array<IAcademicInformationModel>;
    publication: Array<IPublicationInformationModel>;
    training: Array<ITrainingInformationModel>;
    award: Array<IAwardInformationModel>;
    experience: Array<IExperienceInformationModel>;
  }

  class EmployeeInformation {

    public static $inject = ['appConstants', '$scope', '$q', 'notify', '$window', '$sce', 'employeeInformationService'];

    constructor(private appConstants: any,
                private $scope: IEmployeeInformation,
                private $q: ng.IQService,
                private notify: Notify,
                private $window: ng.IWindowService,
                private $sce: ng.ISCEService,
                private employeeInformationService: EmployeeInformationService) {


      $scope.employeeGender = appConstants.gender;
      // $scope.employeeMaritalStatus = appConstanst.maritalStatus;
      // $scope.degreeNames = appConstants.degreeName;

      $scope.employeeReligion = [
        {id: 0, name: "Select Religion"},
        {id: 1, name: "ISLAM"}
      ];

      $scope.employeeNationality = [
        {id: 0, name: "Select Nationality"},
        {id: 1, name: "Bangladeshi"}
      ];

      $scope.typeOfPublication = [
        {id: 0, name: "Select Publication type"},
        {id: 1, name: "Conference"},
        {id: 2, name: "Journal"},
        {id: 3, name: "Proceedings"},
        {id: 4, name: "Presentation"}
      ];

      $scope.employeeMaritalStatus = [
        {id: 0, name: "Select Marital Status"},
        {id: 1, name: "Single"},
        {id: 2, name: "Married"},
        {id: 3, name: "Divorced"},
        {id: 4, name: "Widowed"}
      ];

      $scope.degreeNames = [
        {id: 1, name: "SSC"},
        {id: 2, name: "HSC"},
        {id: 3, name: "Bachelor"},
        {id: 4, name: "Master's"},
        {id: 5, name: "PhD"}
      ];

      $scope.entry = {
        personal: <IPersonalInformationModel> {},
        academic: new Array<IAcademicInformationModel>(),
        publication: new Array<IPublicationInformationModel>(),
        training: new Array<ITrainingInformationModel>(),
        award: new Array<IAwardInformationModel>(),
        experience: new Array<IExperienceInformationModel>()
      };

      this.addNewRow("academic");
      this.addNewRow("publication");
      this.addNewRow("training");
      this.addNewRow("award");
      this.addNewRow("experience");

      $scope.personalTab = true;
      $scope.showInputDiv = true;
      $scope.showLabelDiv = false;
      $scope.hideRequireSpan = false;
      $scope.required = true;
      //$scope.showPermanentAddressCheckbox = true;

      $scope.changeNav = this.changeNav.bind(this);
      $scope.testData = this.testData.bind(this);
      $scope.submit = this.submit.bind(this);
      $scope.cancel = this.cancel.bind(this);
      $scope.addNewRow = this.addNewRow.bind(this);
      $scope.deleteRow = this.deleteRow.bind(this);
      $scope.saveAsPresentAddress = this.saveAsPresentAddress.bind(this);
      $scope.getDesignation = this.getDesignation.bind(this);
      $scope.getEmploymentType = this.getEmploymentType.bind(this);
      $scope.getDeptOffice = this.getDeptOffice.bind(this);

      this.addDate();
      this.getDesignation();
      console.log("i am in EmployeeInformation.ts 11");
    }


    private changeNav(navTitle: number){

      this.$scope.personalTab = false;
      this.$scope.academicTab = false;
      this.$scope.publicationTab = false;
      this.$scope.trainingTab = false;
      this.$scope.awardTab = false;
      this.$scope.experienceTab = false;

      if(navTitle == null){

      }
      else if(navTitle == 1){
        this.$scope.personalTab = true;
      }
      else if(navTitle == 2){
        this.$scope.academicTab = true;
      }
      else if(navTitle == 3){
        this.$scope.publicationTab = true;
      }
      else if(navTitle == 4){
        this.$scope.trainingTab = true;
      }
      else if(navTitle == 5){
        this.$scope.awardTab = true;
      }
      else if(navTitle == 6){
        this.$scope.experienceTab = true;
      }
    }

    private testData(){
      console.log("i am in testData()");
      this.$scope.entry.personal.employeeId = 11;
      this.$scope.entry.personal.firstName = "Kawsur";
      this.$scope.entry.personal.lastName = "Mir Md.";
      this.$scope.entry.personal.fatherName = "Mir Abdul Aziz";
      this.$scope.entry.personal.motherName = "Mst Hosne Ara";
      this.$scope.entry.personal.gender = this.$scope.employeeGender[1];
      this.$scope.entry.personal.birthday = "20/10/1995";
      this.$scope.entry.personal.nationality = this.$scope.employeeNationality[1];
      this.$scope.entry.personal.religion = this.$scope.employeeReligion[1];
      this.$scope.entry.personal.maritalStatus = this.$scope.employeeMaritalStatus[1];
      this.$scope.entry.personal.spouseName = "";
      this.$scope.entry.personal.nationalIdNo = 19952641478954758;
      this.$scope.entry.personal.spouseNationalIdNo = 0;
      this.$scope.entry.personal.bloodGroup = "B+";
      this.$scope.entry.personal.website = "www.kawsur.com";
      this.$scope.entry.personal.organizationalEmail = "kawsur.iums@aust.edu";
      this.$scope.entry.personal.personalEmail = "kawsurilu@yahoo.com";
      this.$scope.entry.personal.mobile = "+8801672494863";
      this.$scope.entry.personal.phone = "none";
      this.$scope.entry.personal.presentAddressHouse = "34/1";
      this.$scope.entry.personal.presentAddressRoad = "Kazi Riaz Uddin Road";
      this.$scope.entry.personal.presentAddressPoliceStation = "Lalgagh";
      this.$scope.entry.personal.presentAddressPostalCode = "1211";
      this.$scope.entry.personal.presentAddressDistrict = "Dhaka";
      this.$scope.entry.personal.presentAddressDivision = "Dhaka";
      this.$scope.entry.personal.presentAddressCountry = "Bangladesh";
      this.$scope.entry.personal.permanentAddressHouse = "None";
      this.$scope.entry.personal.permanentAddressRoad = "";
      this.$scope.entry.personal.permanentAddressPoliceStation = "";
      this.$scope.entry.personal.permanentAddressPostalCode = "";
      this.$scope.entry.personal.permanentAddressDistrict = "";
      this.$scope.entry.personal.permanentAddressDivision = "None";
      this.$scope.entry.personal.permanentAddressCountry = "None";
      this.$scope.entry.personal.emergencyContactName = "None";
      this.$scope.entry.personal.emergencyContactRelation = "None";
      this.$scope.entry.personal.emergencyContactPhone = "None";
      this.$scope.entry.personal.emergencyContactAddress = "None";

      this.$scope.entry.academic[0].employeeId = 111;
      this.$scope.entry.academic[0].academicDegreeName.name = "Bachelor";
      this.$scope.entry.academic[0].academicInstitution = "American International University-Bangladesh";
      this.$scope.entry.academic[0].academicPassingYear = "2016";

      this.$scope.entry.publication[0].employeeId = 111;
      this.$scope.entry.publication[0].publicationTitle = "N/A";
      this.$scope.entry.publication[0].publicationInterestGenre = "N/A";
      this.$scope.entry.publication[0].authorsName = "N/A";
      this.$scope.entry.publication[0].publisherName = "N/A";
      this.$scope.entry.publication[0].dateOfPublication = "11/11/3010";
      this.$scope.entry.publication[0].publicationType = this.$scope.typeOfPublication[1];
      this.$scope.entry.publication[0].publicationWebLink = "N/A";

      this.$scope.entry.training[0].employeeId = 111;
      this.$scope.entry.training[0].trainingInstitution = "ABC";
      this.$scope.entry.training[0].trainingName = "XYZ";
      this.$scope.entry.training[0].trainingFrom = "2016";
      this.$scope.entry.training[0].trainingTo = "2015";
      this.$scope.entry.training[0].trainingDuration = (+this.$scope.entry.training[0].trainingTo - +this.$scope.entry.training[0].trainingFrom).toString();


      this.$scope.entry.award[0].employeeId = 111;
      this.$scope.entry.award[0].awardName = "My Award";
      this.$scope.entry.award[0].awardInstitute = "Really !";
      this.$scope.entry.award[0].awardedYear = "1990";
      this.$scope.entry.award[0].awardShortDescription = "Hello! This is My Award, Don't Ask Description :@";

      this.$scope.entry.experience[0].employeeId = 111;
      this.$scope.entry.experience[0].experienceInstitution = "My Award";
      this.$scope.entry.experience[0].experienceDesignation = "Really !";
      this.$scope.entry.experience[0].experienceFrom = "6";
      this.$scope.entry.experience[0].experienceTo = "2010";
    }


    private submit(){
      console.log("i am in submit()");
      this.convertToJson()
          .then((json: any) => {
            this.employeeInformationService.saveEmployeeInformation(json)
                .then((message: any) => {
                  console.log("This is message");
                  console.log(message);
                });
          });
      this.$scope.showInputDiv = false;
      this.$scope.showLabelDiv = true;
      this.$scope.required = true;
      //this.$scope.showPermanentAddressCheckbox = false;

    }

    private cancel(){
      console.log("i am in cancel()");
      this.$scope.entry.personal.firstName = "";
      this.$scope.entry.personal.lastName = "";
      this.$scope.entry.personal.fatherName = "";
      this.$scope.entry.personal.motherName = "";
      this.$scope.entry.personal.gender = this.$scope.employeeGender[0];
      this.$scope.entry.personal.birthday = "";
      this.$scope.entry.personal.nationality = this.$scope.employeeNationality[0];
      this.$scope.entry.personal.maritalStatus = this.$scope.employeeMaritalStatus[0];
      this.$scope.entry.personal.spouseName = "";
      this.$scope.entry.personal.nationalIdNo = null;
      this.$scope.entry.personal.spouseNationalIdNo = null;
      this.$scope.entry.personal.bloodGroup = "";
      this.$scope.entry.personal.website = "";
      this.$scope.entry.personal.organizationalEmail = "";
      this.$scope.entry.personal.personalEmail = "";
      this.$scope.entry.personal.mobile = "";
      this.$scope.entry.personal.phone = "";
      this.$scope.entry.personal.presentAddressHouse = "";
      this.$scope.entry.personal.presentAddressRoad = "";
      this.$scope.entry.personal.presentAddressPoliceStation = "";
      this.$scope.entry.personal.presentAddressPostalCode = "";
      this.$scope.entry.personal.presentAddressDistrict = "";
      this.$scope.entry.personal.presentAddressDivision = "";
      this.$scope.entry.personal.presentAddressCountry = "";
      this.$scope.entry.personal.permanentAddressHouse = "";
      this.$scope.entry.personal.permanentAddressRoad = "";
      this.$scope.entry.personal.permanentAddressPoliceStation = "";
      this.$scope.entry.personal.permanentAddressPostalCode = "";
      this.$scope.entry.personal.permanentAddressDistrict = "";
      this.$scope.entry.personal.permanentAddressDivision = "";
      this.$scope.entry.personal.permanentAddressCountry = "";
      this.$scope.entry.personal.emergencyContactName = "";
      this.$scope.entry.personal.emergencyContactRelation = "";
      this.$scope.entry.personal.emergencyContactPhone = "";
      this.$scope.entry.personal.emergencyContactAddress = "";

      this.$scope.entry.academic[0].academicDegreeName.name = "";
      this.$scope.entry.academic[0].academicInstitution = "";
      this.$scope.entry.academic[0].academicPassingYear = "";

      this.$scope.entry.publication[0].publicationTitle = "";
      this.$scope.entry.publication[0].publicationInterestGenre = "";
      this.$scope.entry.publication[0].authorsName = "";
      this.$scope.entry.publication[0].publisherName = "";
      this.$scope.entry.publication[0].dateOfPublication = "";
      this.$scope.entry.publication[0].publicationType = this.$scope.typeOfPublication[0];
      this.$scope.entry.publication[0].publicationWebLink = "";

      this.$scope.entry.training[0].trainingInstitution = "";
      this.$scope.entry.training[0].trainingName = "";
      this.$scope.entry.training[0].trainingFrom = "";
      this.$scope.entry.training[0].trainingTo = "";
      this.$scope.entry.training[0].trainingDuration = "";

      this.$scope.entry.award[0].awardName = "";
      this.$scope.entry.award[0].awardInstitute = "";
      this.$scope.entry.award[0].awardedYear = "";
      this.$scope.entry.award[0].awardShortDescription = "";

      this.$scope.entry.experience[0].experienceInstitution = "";
      this.$scope.entry.experience[0].experienceDesignation = "";
      this.$scope.entry.experience[0].experienceFrom = "";
      this.$scope.entry.experience[0].experienceTo = "";

      this.$scope.showInputDiv = true;
      this.$scope.showLabelDiv = false;
      this.$scope.hideRequireSpan = false;
    }

    private addNewRow(divName: string){
      console.log("i am in addNewRow()");
      if(divName == 'academic') {
        var academicEntry: IAcademicInformationModel;
        academicEntry = {employeeId: 1, academicDegreeName: this.$scope.degreeNames[0], academicInstitution: "", academicPassingYear: ""};
        this.$scope.entry.academic.push(academicEntry);
      }
      else if(divName == 'publication'){
        var publicationEntry: IPublicationInformationModel;
        publicationEntry = {employeeId: 1, publicationTitle: "", publicationType: this.$scope.typeOfPublication[0], publicationInterestGenre: "", authorsName: "", publicationWebLink: "", publisherName: "", dateOfPublication: ""};
        this.$scope.entry.publication.push(publicationEntry);
      }
      else if(divName == 'training'){
        var trainingEntry: ITrainingInformationModel;
        trainingEntry = {employeeId: 1, trainingName: "", trainingInstitution: "", trainingFrom: "", trainingTo: "", trainingDuration: ""};
        this.$scope.entry.training.push(trainingEntry);
      }
      else if(divName == 'award'){
        var awarEntry: IAwardInformationModel;
        awarEntry = {employeeId: 1, awardName: "", awardInstitute: "", awardedYear: "", awardShortDescription: ""};
        this.$scope.entry.award.push(awarEntry);
      }
      else if(divName == 'experience'){
        var enperienceEntry: IExperienceInformationModel;
        enperienceEntry = {employeeId: 1, experienceInstitution: "", experienceDesignation: "", experienceFrom: "", experienceTo: "" };
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

    public convertToJson(): ng.IPromise<any>{
      console.log("I am in convertToJSon()");
      var defer = this.$q.defer();
      var JsonObject = {};
      var JsonArray = [];
      var item: any = {};

      var personalInformation = <IPersonalInformationModel> {} ;
      personalInformation  = this.$scope.entry.personal;
      item['personal'] = personalInformation;

      var academicInformation = new Array<IAcademicInformationModel>();
      for(var i = 0; i < this.$scope.entry.academic.length; i++) {
        academicInformation = this.$scope.entry.academic;
      }
      item['academic'] = academicInformation;

      var publicationInformation = new Array<IPublicationInformationModel>();
      for(var i = 0; i < this.$scope.entry.publication.length; i++){
        publicationInformation = this.$scope.entry.publication;
      }
      item['publication'] = publicationInformation;

      var trainingInformation = new Array<ITrainingInformationModel>();
      for(var i = 0; i < this.$scope.entry.training.length; i++){
        trainingInformation = this.$scope.entry.training;
      }
      item['training'] = trainingInformation;

      var awardInformation = new Array<IAwardInformationModel>();
      for(var i = 0; i < this.$scope.entry.award.length; i++){
        awardInformation = this.$scope.entry.award;
      }
      item['award'] = awardInformation;

      var experienceInformation = new Array<IExperienceInformationModel>();
      for(var i = 0; i < this.$scope.entry.experience.length; i++){
        experienceInformation = this.$scope.entry.experience;
      }
      item['experience'] = experienceInformation;

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

    private getDesignation(): void {
      // this.semesterService.fetchSemesters(Number(this.$scope.programType.id), 5, Utils.SEMESTER_FETCH_WITH_NEWLY_CREATED).then((semesters: any) => {
      //   this.$scope.semesters = semesters;
      //   for (var i = 0; i < semesters.length; i++) {
      //     if (semesters[i].status == 2) {
      //       this.$scope.semester = semesters[i];
      //       break;
      //     }
      //   }
      // });
    }
    private getEmploymentType(): void {
      // this.semesterService.fetchSemesters(Number(this.$scope.programType.id), 5, Utils.SEMESTER_FETCH_WITH_NEWLY_CREATED).then((semesters: any) => {
      //   this.$scope.semesters = semesters;
      //   for (var i = 0; i < semesters.length; i++) {
      //     if (semesters[i].status == 2) {
      //       this.$scope.semester = semesters[i];
      //       break;
      //     }
      //   }
      // });
    }
    private getDeptOffice(): void {
      // this.semesterService.fetchSemesters(Number(this.$scope.programType.id), 5, Utils.SEMESTER_FETCH_WITH_NEWLY_CREATED).then((semesters: any) => {
      //   this.$scope.semesters = semesters;
      //   for (var i = 0; i < semesters.length; i++) {
      //     if (semesters[i].status == 2) {
      //       this.$scope.semester = semesters[i];
      //       break;
      //     }
      //   }
      // });
    }
  }

  UMS.controller("EmployeeInformation",EmployeeInformation);
}