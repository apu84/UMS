module ums{
  interface IEmployeeInformation extends ng.IScope {
    personal: boolean;
    academic: boolean;
    job: boolean;
    publication: boolean;
    training: boolean;
    award: boolean;
    experience: boolean;

    //testData
    name: string;
    fatherName: string;
    motherName: string;
    gender: string;
    birthday: string;
    maritalStatus: string;
    spouseName: string;
    nationalIdNo: number;
    bloodGroup: string;
    spouseNationalIdNo: number;
    email: string;
    phone: string;
    presentAddress: string;
    permanentAddress: string;

    employeeId: number;
    employeeDesignation: string;
    employeeEmploymentType: number;
    employeeDepartment : number;
    employeeJoiningDate: string;
    employeeJobPermanentDate: string;
    employeeExtensionNumber: number;
    employeeShortName: string;

    sscSelected: string;
    sscInstitution: string;
    sscGPA: string;
    sscPassingYear: string;
    hscSelected: string;
    hscInstitution: string;
    hscGPA: string;
    hscPassingYear: string;
    bachelorSelected: string;
    bachelorInstitution: string;
    bachelorCGPA: string;
    bachelorPassingYear: string;
    mastersSelected: string;
    mastersInstitution: string;
    mastersCGPA: string;
    mastersPassingYear: string;
    phdSelected: string;
    phdInstitution: string;
    phdCGPA: string;
    phdPassingYear: string;

    publicationTitle: string;
    publicationType: string;
    publicationYear: string;
    publicationPlace: string;
    webReference: string;

    trainingName: string;
    trainingInstitution: string;
    trainingDuration: string;
    trainingAccreditedYear: string;

    awardName: string;
    purpose: string;
    yearOfRecognition: string;
    description: string;

    experienceInstitution: string;
    experienceDesignation: string;
    experienceFromMonth: string;
    experienceFromYear: string;
    experienceToMonth: string;
    experienceToYear: string;


    showInputNameDiv: boolean;
    showLabelNameDiv: boolean;
    showInputFatherNameDiv: boolean;
    showLabelFatherNameDiv: boolean;
    showInputMotherNameDiv: boolean;
    showLabelMotherNameDiv: boolean;
    showInputGenderDiv: boolean;
    showLabelGenderDiv: boolean;
    showInputBirthdayDiv: boolean;
    showLabelBirthdayDiv: boolean;
    showInputMaritalStatusDiv: boolean;
    showLabelMaritalStatusDiv: boolean;
    showInputSpouseNameDiv: boolean;
    showLabelSpouseNameDiv: boolean;
    showInputNationalIdNoDiv: boolean;
    showLabelNationalIdNoDiv: boolean;
    showSpouseInputNationalIdNoDiv: boolean;
    showLabelSpouseNationalIdNoDiv: boolean;
    showInputBloodGroupDiv: boolean;
    showLabelBloodGroupDiv: boolean;
    showInputEmailDiv: boolean;
    showLabelEmailDiv: boolean;
    showInputPhoneDiv: boolean;
    showLabelPhoneDiv: boolean;
    showInputPresentAddressDiv: boolean;
    showLabelPresentAddressDiv: boolean;
    showInputPermanentAddressDiv: boolean;
    showLabelPermanentAddressDiv: boolean;
    showInputEmployeeIdDiv: boolean;
    showLabelEmployeeIdDiv: boolean;
    showInputEmployeeDesignationDiv: boolean;
    showLabelEmployeeDesignationDiv: boolean;
    showInputEmploymentTypeDiv: boolean;
    showLabelEmploymentTypeDiv: boolean;
    showSpouseInputEmployeeDepartmentDiv: boolean;
    showLabelEmployeeDepartmentDiv: boolean;
    showInputEmployeeJoiningDateDiv: boolean;
    showLabelEmployeeJoiningDateDiv: boolean;
    showInputEmployeeJobPermanentDateDiv: boolean;
    showLabelEmployeeJobPermanentDateDiv: boolean;
    showInputEmployeeExtensionDiv: boolean;
    showLabelEmployeeExtensionDiv: boolean;
    showSpouseInputEmployeeShortNameDiv: boolean;
    showLabelEmployeeShortNameDiv: boolean;
    showInputSSCSelectedDiv: boolean;
    showLabelSSCSelectedDiv: boolean;
    showInputSSCInstitutionDiv: boolean;
    showLabelSSCInstitutionDiv: boolean;
    showInputSSCGpaDiv: boolean;
    showLabelSSCGpaDiv: boolean;
    showInputSSCPassingYearDiv: boolean;
    showLabelSSCPassingYearDiv: boolean;
    showInputHSCSelectedDiv: boolean;
    showLabelHSCSelectedDiv: boolean;
    showInputHSCInstitutionDiv: boolean;
    showLabelHSCInstitutionDiv: boolean;
    showInputHSCGpaDiv: boolean;
    showLabelHSCGpaDiv: boolean;
    showInputHSCPassingYearDiv: boolean;
    showLabelHSCPassingYearDiv: boolean;
    showInputBachelorSelectedDiv: boolean;
    showLabelBachelorSelectedDiv: boolean;
    showInputBachelorInstitutionDiv: boolean;
    showLabelBachelorInstitutionDiv: boolean;
    showInputBachelorCGpaDiv: boolean;
    showLabelBachelorCGpaDiv: boolean;
    showInputBachelorPassingYearDiv: boolean;
    showLabelBachelorPassingYearDiv: boolean;
    showInputMastersSelectedDiv: boolean;
    showLabelMastersSelectedDiv: boolean;
    showInputMastersInstitutionDiv: boolean;
    showLabelMastersInstitutionDiv: boolean;
    showInputMastersCGpaDiv: boolean;
    showLabelMastersCGpaDiv: boolean;
    showInputMastersPassingYearDiv: boolean;
    showLabelMastersPassingYearDiv: boolean;
    showInputPhdSelectedDiv: boolean;
    showLabelPhdSelectedDiv: boolean;
    showInputPhdInstitutionDiv: boolean;
    showLabelPhdInstitutionDiv: boolean;
    showInputPhdCGpaDiv: boolean;
    showLabelPhdCGpaDiv: boolean;
    showInputPhdPassingYearDiv: boolean;
    showLabelPhdPassingYearDiv: boolean;
    showInputPublicationTitleDiv: boolean;
    showLabelPublicationTitleDiv: boolean;
    showInputPublicationTypeDiv: boolean;
    showLabelPublicationTypeDiv: boolean;
    showInputPublicationYearDiv: boolean;
    showLabelPublicationYearDiv: boolean;
    showInputPublicationPlaceDiv: boolean;
    showLabelPublicationPlaceDiv: boolean;
    showInputWebReferenceDiv: boolean;
    showLabelWebReferenceDiv: boolean;
    showInputTrainingNameDiv: boolean;
    showLabelTrainingNameDiv: boolean;
    showInputTrainingInstitutionDiv: boolean;
    showLabelTrainingInstitutionDiv: boolean;
    showInputTrainingDurationDiv: boolean;
    showLabelTrainingDurationDiv: boolean;
    showInputTrainingAccreditedYearDiv: boolean;
    showLabelTrainingAccreditedYearDiv: boolean;
    showInputAwardNameDiv: boolean;
    showLabelAwardNameDiv: boolean;
    showInputPurposeDiv: boolean;
    showLabelPurposeDiv: boolean;
    showInputYearOfRecognitionDiv: boolean;
    showLabelYearOfRecognitionDiv: boolean;
    showInputDescriptionDiv: boolean;
    showLabelDescriptionDiv: boolean;
    showInputExperienceInstitutionDiv: boolean;
    showLabelExperienceInstitutionDiv: boolean;
    showInputExperienceDesignationDiv: boolean;
    showLabelExperienceDesignationDiv: boolean;
    showInputExperienceFromMonthDiv: boolean;
    showLabelExperienceFromMonthDiv: boolean;
    showInputExperienceFromYearDiv: boolean;
    showLabelExperienceFromYearDiv: boolean;
    showInputExperienceToMonthDiv: boolean;
    showLabelExperienceToMonthDiv: boolean;
    showInputExperienceToYearDiv: boolean;
    showLabelExperienceToYearDiv: boolean;

    degreeNames: Array<IDegreeName>;
    entry: IEntry;

    changeNav: Function;
    submit: Function;
    cancel: Function;
    addNewRow: Function;
    deleteRow: Function;
    testData: Function;
  }

  interface IDegreeName{
    id: number;
    name: string;
  }
  interface IAcademicEntry{
    academicDegreeName: IDegreeName;
    academicInstitution: string;
    academicCgpa: string;
    academicPassingYear: string;
  }
  interface IPublicationEntry{
    publicationTitle: string;
    publicationType: string;
    publicationYear: string;
    publicationPlace: string;
    publicationWebReference: string;
  }
  interface ITrainingEntry{
    trainingName: string;
    trainingInstitution: string;
    trainingDuration: string;
    trainingYear: string;
  }
  interface IAwardEntry{
    awardName: string;
    purposeOfTheAward: string;
    yearOfRecognition: string;
    shortDescription: string;
  }
  interface IExperienceEntry{
    experienceInstitution: string;
    experienceDesignation: string;
    experienceFromMonth: string;
    experienceFromYear: string;
    experienceToMonth: string;
    experienceToYear: string;
  }
  interface IEntry {
    academic: Array<IAcademicEntry>;
    publication: Array<IPublicationEntry>;
    training: Array<ITrainingEntry>;
    award: Array<IAwardEntry>;
    experience: Array<IExperienceEntry>;
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


      $scope.degreeNames = [
        {id: 1, name: "SSC"},
        {id: 2, name: "HSC"},
        {id: 3, name: "Bachelor"},
        {id: 4, name: "Master's"},
        {id: 5, name: "PhD"}
      ];

      $scope.entry = {
        academic: new Array<IAcademicEntry>(),
        publication: new Array<IPublicationEntry>(),
        training: new Array<ITrainingEntry>(),
        award: new Array<IAwardEntry>(),
        experience: new Array<IExperienceEntry>()
      };

      this.addNewRow("academic");
      this.addNewRow("publication");
      this.addNewRow("training");
      this.addNewRow("award");
      this.addNewRow("experience");

      $scope.personal = true;
      $scope.showInputNameDiv = true;
      $scope.showLabelNameDiv = false;
      $scope.showInputFatherNameDiv = true;
      $scope.showLabelFatherNameDiv = false;
      $scope.showInputMotherNameDiv = true;
      $scope.showLabelMotherNameDiv = false;
      $scope.showInputGenderDiv = true;
      $scope.showLabelGenderDiv = false;
      $scope.showInputBirthdayDiv = true;
      $scope.showLabelBirthdayDiv = false;
      $scope.showInputMaritalStatusDiv = true;
      $scope.showLabelMaritalStatusDiv = false;
      $scope.showInputSpouseNameDiv = true;
      $scope.showLabelSpouseNameDiv = false;
      $scope.showInputNationalIdNoDiv = true;
      $scope.showLabelNationalIdNoDiv = false;
      $scope.showSpouseInputNationalIdNoDiv = true;
      $scope.showLabelSpouseNationalIdNoDiv = false;
      $scope.showInputBloodGroupDiv = true;
      $scope.showLabelBloodGroupDiv = false;
      $scope.showInputEmailDiv = true;
      $scope.showLabelEmailDiv = false;
      $scope.showInputPhoneDiv = true;
      $scope.showLabelPhoneDiv = false;
      $scope.showInputPresentAddressDiv = true;
      $scope.showLabelPresentAddressDiv = false;
      $scope.showInputPermanentAddressDiv = true;
      $scope.showLabelPermanentAddressDiv = false;
      $scope.showInputEmployeeIdDiv = true;
      $scope.showLabelEmployeeIdDiv = false;
      $scope.showInputEmployeeDesignationDiv = true;
      $scope.showLabelEmployeeDesignationDiv = false;
      $scope.showInputEmploymentTypeDiv = true;
      $scope.showLabelEmploymentTypeDiv = false;
      $scope.showSpouseInputEmployeeDepartmentDiv = true;
      $scope.showLabelEmployeeDepartmentDiv = false;
      $scope.showInputEmployeeJoiningDateDiv = true;
      $scope.showLabelEmployeeJoiningDateDiv = false;
      $scope.showInputEmployeeJobPermanentDateDiv = true;
      $scope.showLabelEmployeeJobPermanentDateDiv = false;
      $scope.showInputEmployeeExtensionDiv = true;
      $scope.showLabelEmployeeExtensionDiv = false;
      $scope.showSpouseInputEmployeeShortNameDiv = true;
      $scope.showLabelEmployeeShortNameDiv = false;
      $scope.showInputSSCSelectedDiv = true;
      $scope.showLabelSSCSelectedDiv = false;
      $scope.showInputSSCInstitutionDiv = true;
      $scope.showLabelSSCInstitutionDiv = false;
      $scope.showInputSSCGpaDiv = true;
      $scope.showLabelSSCGpaDiv = false;
      $scope.showInputSSCPassingYearDiv = true;
      $scope. showLabelSSCPassingYearDiv = false;
      $scope.showInputHSCSelectedDiv = true;
      $scope.showLabelHSCSelectedDiv = false;
      $scope.showInputHSCInstitutionDiv = true;
      $scope.showLabelHSCInstitutionDiv = false;
      $scope.showInputHSCGpaDiv = true;
      $scope.showLabelHSCGpaDiv = false;
      $scope.showInputHSCPassingYearDiv = true;
      $scope.showLabelHSCPassingYearDiv = false;
      $scope.showInputBachelorSelectedDiv = true;
      $scope.showLabelBachelorSelectedDiv = false;
      $scope.showInputBachelorInstitutionDiv = true;
      $scope.showLabelBachelorInstitutionDiv = false;
      $scope.showInputBachelorCGpaDiv = true;
      $scope.showLabelBachelorCGpaDiv = false;
      $scope.showInputBachelorPassingYearDiv = true;
      $scope.showLabelBachelorPassingYearDiv = false;
      $scope.showInputMastersSelectedDiv = true;
      $scope.showLabelMastersSelectedDiv = false;
      $scope.showInputMastersInstitutionDiv = true;
      $scope.showLabelMastersInstitutionDiv = false;
      $scope.showInputMastersCGpaDiv = true;
      $scope.showLabelMastersCGpaDiv = false;
      $scope.showInputMastersPassingYearDiv = true;
      $scope.showLabelMastersPassingYearDiv = false;
      $scope.showInputPhdSelectedDiv = true;
      $scope.showLabelPhdSelectedDiv = false;
      $scope.showInputPhdInstitutionDiv = true;
      $scope.showLabelPhdInstitutionDiv = false;
      $scope.showInputPhdCGpaDiv = true;
      $scope.showLabelPhdCGpaDiv = false;
      $scope.showInputPhdPassingYearDiv = true;
      $scope.showLabelPhdPassingYearDiv = false;
      $scope.showInputPublicationTitleDiv = true;
      $scope.showLabelPublicationTitleDiv = false;
      $scope.showInputPublicationTypeDiv = true;
      $scope.showLabelPublicationTypeDiv = false;
      $scope.showInputPublicationYearDiv = true;
      $scope.showLabelPublicationYearDiv = false;
      $scope.showInputPublicationPlaceDiv = true;
      $scope.showLabelPublicationPlaceDiv = false;
      $scope.showInputWebReferenceDiv = true;
      $scope.showLabelWebReferenceDiv = false;
      $scope.showInputTrainingNameDiv = true;
      $scope.showLabelTrainingNameDiv = false;
      $scope.showInputTrainingInstitutionDiv = true;
      $scope.showLabelTrainingInstitutionDiv = false;
      $scope.showInputTrainingDurationDiv = true;
      $scope.showLabelTrainingDurationDiv = false;
      $scope.showInputTrainingAccreditedYearDiv = true;
      $scope.showLabelTrainingAccreditedYearDiv = false;
      $scope.showInputAwardNameDiv = true;
      $scope.showLabelAwardNameDiv = false;
      $scope.showInputPurposeDiv = true;
      $scope.showLabelPurposeDiv = false;
      $scope.showInputYearOfRecognitionDiv = true;
      $scope.showLabelYearOfRecognitionDiv = false;
      $scope.showInputDescriptionDiv = true;
      $scope.showLabelDescriptionDiv = false;
      $scope.showInputExperienceInstitutionDiv = true;
      $scope.showLabelExperienceInstitutionDiv = false;
      $scope.showInputExperienceDesignationDiv = true;
      $scope.showLabelExperienceDesignationDiv = false;
      $scope.showInputExperienceFromMonthDiv = true;
      $scope.showLabelExperienceFromMonthDiv = false;
      $scope.showInputExperienceFromYearDiv = true;
      $scope.showLabelExperienceFromYearDiv = false;
      $scope.showInputExperienceToMonthDiv = true;
      $scope.showLabelExperienceToMonthDiv = false;
      $scope.showInputExperienceToYearDiv = true;
      $scope.showLabelExperienceToYearDiv = false;


      $scope.changeNav = this.changeNav.bind(this);
      $scope.testData = this.testData.bind(this);
      $scope.submit = this.submit.bind(this);
      $scope.cancel = this.cancel.bind(this);
      $scope.addNewRow = this.addNewRow.bind(this);
      $scope.deleteRow = this.deleteRow.bind(this);

      this.addDate();

      console.log("i am in EmployeeInformation.ts 1");
    }


    private changeNav(navTitle: number){

      this.$scope.personal = false;
      this.$scope.academic = false;
      this.$scope.job = false;
      this.$scope.publication = false;
      this.$scope.training = false;
      this.$scope.award = false;
      this.$scope.experience = false;

      if(navTitle == null){

      }
      else if(navTitle == 1){
        this.$scope.personal = true;
      }
      else if(navTitle == 2){
        this.$scope.job = true;
      }
      else if(navTitle == 3){
        this.$scope.academic = true;
      }
      else if(navTitle == 4){
        this.$scope.publication = true;
      }
      else if(navTitle == 5){
        this.$scope.training = true;
      }
      else if(navTitle == 6){
        this.$scope.award = true;
      }
      else if(navTitle == 7){
        this.$scope.experience = true;
      }
    }


    // Used For Test Data
    private testData(){
      console.log("i am in testData()");
      this.$scope.name = "Cooldude ilu";
      this.$scope.fatherName = "Mir Abdul Aziz";
      this.$scope.motherName = "Mst Hosne Ara";
      this.$scope.gender = "Male";
      this.$scope.birthday = "20/10/1995";
      this.$scope.maritalStatus = "Single";
      this.$scope.spouseName = "";
      this.$scope.nationalIdNo = 19952641478954758;
      this.$scope.spouseNationalIdNo = null;
      this.$scope.bloodGroup = "B+";
      this.$scope.email = "kawsur.iums@aust.edu";
      this.$scope.phone = "+8801672494863";
      this.$scope.presentAddress = "34/1 K R Road Posta Lalbagh Dhaka-1211, Bangladesh";
      this.$scope.permanentAddress = "Don't Know";

      this.$scope.employeeId = 1;
      this.$scope.employeeDesignation = "Programmer";
      this.$scope.employeeEmploymentType = 1;
      this.$scope.employeeDepartment = 1;
      this.$scope.employeeJoiningDate = "01/11/2017";
      this.$scope.employeeJobPermanentDate = "01/11/2017";
      this.$scope.employeeExtensionNumber = 724;
      this.$scope.employeeShortName = "mmk";

      this.$scope.entry.academic[0].academicDegreeName.name = "Bachelor";
      this.$scope.entry.academic[0].academicInstitution = "American International University-Bangladesh";
      this.$scope.entry.academic[0].academicCgpa = "3.79";
      this.$scope.entry.academic[0].academicPassingYear = "2016";

      this.$scope.entry.publication[0].publicationTitle = "ABC";
      this.$scope.entry.publication[0].publicationType = "XYZ";
      this.$scope.entry.publication[0].publicationPlace = "Unknown";
      this.$scope.entry.publication[0].publicationYear = "2015";
      this.$scope.entry.publication[0].publicationWebReference = "ABC.COM";

      this.$scope.entry.training[0].trainingInstitution = "ABC";
      this.$scope.entry.training[0].trainingName = "XYZ";
      this.$scope.entry.training[0].trainingDuration = "Unknown";
      this.$scope.entry.training[0].trainingYear = "2015";

      this.$scope.entry.award[0].awardName = "My Award";
      this.$scope.entry.award[0].purposeOfTheAward = "Really !";
      this.$scope.entry.award[0].yearOfRecognition = "1990";
      this.$scope.entry.award[0].shortDescription = "Hello! This is My Award, Don't Ask Description :@";

      this.$scope.entry.experience[0].experienceInstitution = "My Award";
      this.$scope.entry.experience[0].experienceDesignation = "Really !";
      this.$scope.entry.experience[0].experienceFromMonth = "6";
      this.$scope.entry.experience[0].experienceFromYear = "2010";
      this.$scope.entry.experience[0].experienceToMonth = "7";
      this.$scope.entry.experience[0].experienceToYear = "2009";
    }


    private submit(){
      console.log("i am in submit()");
      this.$scope.showInputNameDiv = false;
      this.$scope.showLabelNameDiv = true;
      this.$scope.showInputFatherNameDiv = false;
      this.$scope.showLabelFatherNameDiv = true;
      this.$scope.showInputMotherNameDiv = false;
      this.$scope.showLabelMotherNameDiv = true;
      this.$scope.showInputGenderDiv = false;
      this.$scope.showLabelGenderDiv = true;
      this.$scope.showInputBirthdayDiv = false;
      this.$scope.showLabelBirthdayDiv = true;
      this.$scope.showInputMaritalStatusDiv = false;
      this.$scope.showLabelMaritalStatusDiv = true;
      this.$scope.showInputSpouseNameDiv = false;
      this.$scope.showLabelSpouseNameDiv = true;
      this.$scope.showInputNationalIdNoDiv = false;
      this.$scope.showLabelNationalIdNoDiv = true;
      this.$scope.showSpouseInputNationalIdNoDiv = false;
      this.$scope.showLabelSpouseNationalIdNoDiv = true;
      this.$scope.showInputBloodGroupDiv = false;
      this.$scope.showLabelBloodGroupDiv = true;
      this.$scope.showInputEmailDiv = false;
      this.$scope.showLabelEmailDiv = true;
      this.$scope.showInputPhoneDiv = false;
      this.$scope.showLabelPhoneDiv = true;
      this.$scope.showInputPresentAddressDiv = false;
      this.$scope.showLabelPresentAddressDiv = true;
      this.$scope.showInputPermanentAddressDiv = false;
      this.$scope.showLabelPermanentAddressDiv = true;
      this.$scope.showInputEmployeeIdDiv = false;
      this.$scope.showLabelEmployeeIdDiv = true;
      this.$scope.showInputEmployeeDesignationDiv = false;
      this.$scope.showLabelEmployeeDesignationDiv = true;
      this.$scope.showInputEmploymentTypeDiv = false;
      this.$scope.showLabelEmploymentTypeDiv = true;
      this.$scope.showSpouseInputEmployeeDepartmentDiv = false;
      this.$scope.showLabelEmployeeDepartmentDiv = true;
      this.$scope.showInputEmployeeJoiningDateDiv = false;
      this.$scope.showLabelEmployeeJoiningDateDiv = true;
      this.$scope.showInputEmployeeJobPermanentDateDiv = false;
      this.$scope.showLabelEmployeeJobPermanentDateDiv = true;
      this.$scope.showInputEmployeeExtensionDiv = false;
      this.$scope.showLabelEmployeeExtensionDiv = true;
      this.$scope.showSpouseInputEmployeeShortNameDiv = false;
      this.$scope.showLabelEmployeeShortNameDiv = true;
      this.$scope.showInputSSCSelectedDiv = false;
      this.$scope.showLabelSSCSelectedDiv = true;
      this.$scope.showInputSSCInstitutionDiv = false;
      this.$scope.showLabelSSCInstitutionDiv = true;
      this.$scope.showInputSSCGpaDiv = false;
      this.$scope.showLabelSSCGpaDiv = true;
      this.$scope.showInputSSCPassingYearDiv = false;
      this.$scope.showLabelSSCPassingYearDiv = true;
      this.$scope.showInputHSCSelectedDiv = false;
      this.$scope.showLabelHSCSelectedDiv = true;
      this.$scope.showInputHSCInstitutionDiv = false;
      this.$scope.showLabelHSCInstitutionDiv = true;
      this.$scope.showInputHSCGpaDiv = false;
      this.$scope.showLabelHSCGpaDiv = true;
      this.$scope.showInputHSCPassingYearDiv = false;
      this.$scope.showLabelHSCPassingYearDiv = true;
      this.$scope.showInputBachelorSelectedDiv = false;
      this.$scope.showLabelBachelorSelectedDiv = true;
      this.$scope.showInputBachelorInstitutionDiv = false;
      this.$scope.showLabelBachelorInstitutionDiv = true;
      this.$scope.showInputBachelorCGpaDiv = false;
      this.$scope.showLabelBachelorCGpaDiv = true;
      this.$scope.showInputBachelorPassingYearDiv = false;
      this.$scope.showLabelBachelorPassingYearDiv = true;
      this.$scope.showInputMastersSelectedDiv = false;
      this.$scope.showLabelMastersSelectedDiv = true;
      this.$scope.showInputMastersInstitutionDiv = false;
      this.$scope.showLabelMastersInstitutionDiv = true;
      this.$scope.showInputMastersCGpaDiv = false;
      this.$scope.showLabelMastersCGpaDiv = true;
      this.$scope.showInputMastersPassingYearDiv = false;
      this.$scope.showLabelMastersPassingYearDiv = true;
      this.$scope.showInputPhdSelectedDiv = false;
      this.$scope.showLabelPhdSelectedDiv = true;
      this.$scope.showInputPhdInstitutionDiv = false;
      this.$scope.showLabelPhdInstitutionDiv = true;
      this.$scope.showInputPhdCGpaDiv = false;
      this.$scope.showLabelPhdCGpaDiv = true;
      this.$scope.showInputPhdPassingYearDiv = false;
      this.$scope.showLabelPhdPassingYearDiv = true;
      this.$scope.showInputPublicationTitleDiv = false;
      this.$scope.showLabelPublicationTitleDiv = true;
      this.$scope.showInputPublicationTypeDiv = false;
      this.$scope.showLabelPublicationTypeDiv = true;
      this.$scope.showInputPublicationYearDiv = false;
      this.$scope.showLabelPublicationYearDiv = true;
      this.$scope.showInputPublicationPlaceDiv = false;
      this.$scope.showLabelPublicationPlaceDiv = true;
      this.$scope.showInputWebReferenceDiv = false;
      this.$scope.showLabelWebReferenceDiv = true;
      this.$scope.showInputTrainingNameDiv = false;
      this.$scope.showLabelTrainingNameDiv = true;
      this.$scope.showInputTrainingInstitutionDiv = false;
      this.$scope.showLabelTrainingInstitutionDiv = true;
      this.$scope.showInputTrainingDurationDiv = false;
      this.$scope.showLabelTrainingDurationDiv = true;
      this.$scope.showInputTrainingAccreditedYearDiv = false;
      this.$scope.showLabelTrainingAccreditedYearDiv = true;
      this.$scope.showInputAwardNameDiv = false;
      this.$scope.showLabelAwardNameDiv = true;
      this.$scope.showInputPurposeDiv = false;
      this.$scope.showLabelPurposeDiv = true;
      this.$scope.showInputYearOfRecognitionDiv = false;
      this.$scope.showLabelYearOfRecognitionDiv = true;
      this.$scope.showInputDescriptionDiv = false;
      this.$scope.showLabelDescriptionDiv = true;
      this.$scope.showInputExperienceInstitutionDiv = false;
      this.$scope.showLabelExperienceInstitutionDiv = true;
      this.$scope.showInputExperienceDesignationDiv = false;
      this.$scope.showLabelExperienceDesignationDiv = true;
      this.$scope.showInputExperienceFromMonthDiv = false;
      this.$scope.showLabelExperienceFromMonthDiv = true;
      this.$scope.showInputExperienceFromYearDiv = false;
      this.$scope.showLabelExperienceFromYearDiv = true;
      this.$scope.showInputExperienceToMonthDiv = false;
      this.$scope.showLabelExperienceToMonthDiv = true;
      this.$scope.showInputExperienceToYearDiv = false;
      this.$scope.showLabelExperienceToYearDiv = true;

      this.convertToJSon();
      this.notify.success("Data Saved");
    }

    private cancel(){
      console.log("i am in cancel()");
      this.$scope.name = "";
      this.$scope.fatherName = "";
      this.$scope.motherName = "";
      this.$scope.gender = "";
      this.$scope.birthday = "";
      this.$scope.maritalStatus = "";
      this.$scope.spouseName = "";
      this.$scope.nationalIdNo = null;
      this.$scope.spouseNationalIdNo = null;
      this.$scope.bloodGroup = "";
      this.$scope.email = "";
      this.$scope.phone = "";
      this.$scope.presentAddress = "";
      this.$scope.permanentAddress = "";

      this.$scope.employeeId = null;
      this.$scope.employeeDesignation = "";
      this.$scope.employeeEmploymentType = null;
      this.$scope.employeeDepartment = null;
      this.$scope.employeeJoiningDate = "";
      this.$scope.employeeJobPermanentDate = "";
      this.$scope.employeeExtensionNumber = null;
      this.$scope.employeeShortName = "";

      this.$scope.entry.academic[0].academicDegreeName.name = "";
      this.$scope.entry.academic[0].academicInstitution = "";
      this.$scope.entry.academic[0].academicCgpa = "";
      this.$scope.entry.academic[0].academicPassingYear = "";

      this.$scope.entry.publication[0].publicationTitle = "";
      this.$scope.entry.publication[0].publicationType = "";
      this.$scope.entry.publication[0].publicationPlace = "";
      this.$scope.entry.publication[0].publicationYear = "";
      this.$scope.entry.publication[0].publicationWebReference = "";

      this.$scope.entry.training[0].trainingInstitution = "";
      this.$scope.entry.training[0].trainingName = "";
      this.$scope.entry.training[0].trainingDuration = "";
      this.$scope.entry.training[0].trainingYear = "";

      this.$scope.entry.award[0].awardName = "";
      this.$scope.entry.award[0].purposeOfTheAward = "";
      this.$scope.entry.award[0].yearOfRecognition = "";
      this.$scope.entry.award[0].shortDescription = "";

      this.$scope.entry.experience[0].experienceInstitution = "";
      this.$scope.entry.experience[0].experienceDesignation = "";
      this.$scope.entry.experience[0].experienceFromMonth = "";
      this.$scope.entry.experience[0].experienceFromYear = "";
      this.$scope.entry.experience[0].experienceToMonth = "";
      this.$scope.entry.experience[0].experienceToYear = "";



      this.$scope.showInputNameDiv = true;
      this.$scope.showLabelNameDiv = false;
      this.$scope.showInputFatherNameDiv = true;
      this.$scope.showLabelFatherNameDiv = false;
      this.$scope.showInputMotherNameDiv = true;
      this.$scope.showLabelMotherNameDiv = false;
      this.$scope.showInputGenderDiv = true;
      this.$scope.showLabelGenderDiv = false;
      this.$scope.showInputBirthdayDiv = true;
      this.$scope.showLabelBirthdayDiv = false;
      this.$scope.showInputMaritalStatusDiv = true;
      this.$scope.showLabelMaritalStatusDiv = false;
      this.$scope.showInputSpouseNameDiv = true;
      this.$scope.showLabelSpouseNameDiv = false;
      this.$scope.showInputNationalIdNoDiv = true;
      this.$scope.showLabelNationalIdNoDiv = false;
      this.$scope.showSpouseInputNationalIdNoDiv = true;
      this.$scope.showLabelSpouseNationalIdNoDiv = false;
      this.$scope.showInputBloodGroupDiv = true;
      this.$scope.showLabelBloodGroupDiv = false;
      this.$scope.showInputEmailDiv = true;
      this.$scope.showLabelEmailDiv = false;
      this.$scope.showInputPhoneDiv = true;
      this.$scope.showLabelPhoneDiv = false;
      this.$scope.showInputPresentAddressDiv = true;
      this.$scope.showLabelPresentAddressDiv = false;
      this.$scope.showInputPermanentAddressDiv = true;
      this.$scope.showLabelPermanentAddressDiv = false;
      this.$scope.showInputEmployeeIdDiv = true;
      this.$scope.showLabelEmployeeIdDiv = false;
      this.$scope.showInputEmployeeDesignationDiv = true;
      this.$scope.showLabelEmployeeDesignationDiv = false;
      this.$scope.showInputEmploymentTypeDiv = true;
      this.$scope.showLabelEmploymentTypeDiv = false;
      this.$scope.showSpouseInputEmployeeDepartmentDiv = true;
      this.$scope.showLabelEmployeeDepartmentDiv = false;
      this.$scope.showInputEmployeeJoiningDateDiv = true;
      this.$scope.showLabelEmployeeJoiningDateDiv = false;
      this.$scope.showInputEmployeeJobPermanentDateDiv = true;
      this.$scope.showLabelEmployeeJobPermanentDateDiv = false;
      this.$scope.showInputEmployeeExtensionDiv = true;
      this.$scope.showLabelEmployeeExtensionDiv = false;
      this.$scope.showSpouseInputEmployeeShortNameDiv = true;
      this.$scope.showLabelEmployeeShortNameDiv = false;
      this.$scope.showInputSSCSelectedDiv = true;
      this.$scope.showLabelSSCSelectedDiv = false;
      this.$scope.showInputSSCInstitutionDiv = true;
      this.$scope.showLabelSSCInstitutionDiv = false;
      this.$scope.showInputSSCGpaDiv = true;
      this.$scope.showLabelSSCGpaDiv = false;
      this.$scope.showInputSSCPassingYearDiv = true;
      this.$scope.showLabelSSCPassingYearDiv = false;
      this.$scope.showInputHSCSelectedDiv = true;
      this.$scope.showLabelHSCSelectedDiv = false;
      this.$scope.showInputHSCInstitutionDiv = true;
      this.$scope.showLabelHSCInstitutionDiv = false;
      this.$scope.showInputHSCGpaDiv = true;
      this.$scope.showLabelHSCGpaDiv = false;
      this.$scope.showInputHSCPassingYearDiv = true;
      this.$scope.showLabelHSCPassingYearDiv = false;
      this.$scope.showInputBachelorSelectedDiv = true;
      this.$scope.showLabelBachelorSelectedDiv = false;
      this.$scope.showInputBachelorInstitutionDiv = true;
      this.$scope.showLabelBachelorInstitutionDiv = false;
      this.$scope.showInputBachelorCGpaDiv = true;
      this.$scope.showLabelBachelorCGpaDiv = false;
      this.$scope.showInputBachelorPassingYearDiv = true;
      this.$scope.showLabelBachelorPassingYearDiv = false;
      this.$scope.showInputMastersSelectedDiv = true;
      this.$scope.showLabelMastersSelectedDiv = false;
      this.$scope.showInputMastersInstitutionDiv = true;
      this.$scope.showLabelMastersInstitutionDiv = false;
      this.$scope.showInputMastersCGpaDiv = true;
      this.$scope.showLabelMastersCGpaDiv = false;
      this.$scope.showInputMastersPassingYearDiv = true;
      this.$scope.showLabelMastersPassingYearDiv = false;
      this.$scope.showInputPhdSelectedDiv = true;
      this.$scope.showLabelPhdSelectedDiv = false;
      this.$scope.showInputPhdInstitutionDiv = true;
      this.$scope.showLabelPhdInstitutionDiv = false;
      this.$scope.showInputPhdCGpaDiv = true;
      this.$scope.showLabelPhdCGpaDiv = false;
      this.$scope.showInputPhdPassingYearDiv = true;
      this.$scope.showLabelPhdPassingYearDiv = false;
      this.$scope.showInputPublicationTitleDiv = true;
      this.$scope.showLabelPublicationTitleDiv = false;
      this.$scope.showInputPublicationTypeDiv = true;
      this.$scope.showLabelPublicationTypeDiv = false;
      this.$scope.showInputPublicationYearDiv = true;
      this.$scope.showLabelPublicationYearDiv = false;
      this.$scope.showInputPublicationPlaceDiv = true;
      this.$scope.showLabelPublicationPlaceDiv = false;
      this.$scope.showInputWebReferenceDiv = true;
      this.$scope.showLabelWebReferenceDiv = false;
      this.$scope.showInputTrainingNameDiv = true;
      this.$scope.showLabelTrainingNameDiv = false;
      this.$scope.showInputTrainingInstitutionDiv = true;
      this.$scope.showLabelTrainingInstitutionDiv = false;
      this.$scope.showInputTrainingDurationDiv = true;
      this.$scope.showLabelTrainingDurationDiv = false;
      this.$scope.showInputTrainingAccreditedYearDiv = true;
      this.$scope.showLabelTrainingAccreditedYearDiv = false;
      this.$scope.showInputAwardNameDiv = true;
      this.$scope.showLabelAwardNameDiv = false;
      this.$scope.showInputPurposeDiv = true;
      this.$scope.showLabelPurposeDiv = false;
      this.$scope.showInputYearOfRecognitionDiv = true;
      this.$scope.showLabelYearOfRecognitionDiv = false;
      this.$scope.showInputDescriptionDiv = true;
      this.$scope.showLabelDescriptionDiv = false;
      this.$scope.showInputExperienceInstitutionDiv = true;
      this.$scope.showLabelExperienceInstitutionDiv = false;
      this.$scope.showInputExperienceDesignationDiv = true;
      this.$scope.showLabelExperienceDesignationDiv = false;
      this.$scope.showInputExperienceFromMonthDiv = true;
      this.$scope.showLabelExperienceFromMonthDiv = false;
      this.$scope.showInputExperienceFromYearDiv = true;
      this.$scope.showLabelExperienceFromYearDiv = false;
      this.$scope.showInputExperienceToMonthDiv = true;
      this.$scope.showLabelExperienceToMonthDiv = false;
      this.$scope.showInputExperienceToYearDiv = true;
      this.$scope.showLabelExperienceToYearDiv = false;
    }

    private addNewRow(divName: string){
      console.log("i am in addNewRow()");
      if(divName == 'academic') {
        var academicEntry: IAcademicEntry;
        academicEntry = {academicDegreeName: this.$scope.degreeNames[0], academicInstitution: "", academicCgpa: "", academicPassingYear: ""};
        this.$scope.entry.academic.push(academicEntry);
      }
      else if(divName == 'publication'){
        var publicationEntry: IPublicationEntry;
        publicationEntry = {publicationTitle: "", publicationType: "", publicationYear: "", publicationPlace: "", publicationWebReference: ""};
        this.$scope.entry.publication.push(publicationEntry);
      }
      else if(divName == 'training'){
        var trainingEntry: ITrainingEntry;
        trainingEntry = {trainingName: "", trainingInstitution: "", trainingDuration: "", trainingYear: ""};
        this.$scope.entry.training.push(trainingEntry);
      }
      else if(divName == 'award'){
        var awarEntry: IAwardEntry;
        awarEntry = {awardName: "", purposeOfTheAward: "", yearOfRecognition: "", shortDescription: ""};
        this.$scope.entry.award.push(awarEntry);
      }
      else if(divName == 'experience'){
        var enperienceEntry: IExperienceEntry;
        enperienceEntry = {experienceInstitution: "", experienceDesignation: "", experienceFromMonth: "", experienceFromYear: "", experienceToMonth: "", experienceToYear: "" };
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

    public convertToJSon(): ng.IPromise<any>{
      console.log("I am in convertToJSon()");
      var defer = this.$q.defer();
      var JsonObject = {};
      var JsonArray = [];
      var item: any = {};

      var personalArray = [];
      var personalItem: any = {};
      personalItem['name'] = this.$scope.name;
      personalItem['fatherName'] = this.$scope.fatherName;
      personalItem['gender'] = this.$scope.gender;
      personalItem['motherName'] = this.$scope.motherName;
      personalItem['bloodGroup'] = this.$scope.bloodGroup;
      personalItem['dateOfBirth'] = this.$scope.birthday;
      personalItem['maritalStatus'] = this.$scope.maritalStatus;
      personalItem['spouseName'] = this.$scope.spouseName;
      personalItem['nationalIdNo'] = this.$scope.nationalIdNo;
      personalItem['spouseNationalIdNo'] = this.$scope.spouseNationalIdNo;
      personalItem['email'] = this.$scope.email;
      personalItem['phone'] = this.$scope.phone;
      personalItem['presentAddress'] = this.$scope.presentAddress;
      personalItem['permanentAddress'] = this.$scope.permanentAddress;
      personalArray.push(personalItem);
      item['personal'] = personalArray;

      var jobArray = [];
      var jobItem: any = {};
      jobItem['employee_id'] = this.$scope.employeeId;
      jobItem['employee_designation'] = this.$scope.employeeDesignation;
      jobItem['employment_type'] = this.$scope.employeeEmploymentType;
      jobItem['employee_dept'] = this.$scope.employeeDepartment;
      jobItem['employee_joining_date'] = this.$scope.employeeJoiningDate;
      jobItem['employee_job_permanent_date'] = this.$scope.employeeJobPermanentDate;
      jobItem['employee_ext_no'] = this.$scope.employeeExtensionNumber;
      jobItem['employee_short_name'] = this.$scope.employeeShortName;
      jobArray.push(jobItem);
      item['job'] = jobArray;

      var academicArray = [];
      for(var i = 0; i < this.$scope.entry.academic.length; i++) {
        var academicItem: any = {};
        academicItem['degreeName'] = this.$scope.entry.academic[i].academicDegreeName;
        academicItem['degreeInstitute'] = this.$scope.entry.academic[i].academicInstitution;
        academicItem['degreeCgpa'] = this.$scope.entry.academic[i].academicCgpa;
        academicItem['degreePassingYear'] = this.$scope.entry.academic[i].academicPassingYear;
        academicArray.push(academicItem);
      }
      item['academic'] = academicArray;

      var publicationArray = [];
      for(var i = 0; i < this.$scope.entry.publication.length; i++){
        var publicationItem: any = {};
        publicationItem['publicationTitle'] = this.$scope.entry.publication[i].publicationTitle;
        publicationItem['publicationType'] = this.$scope.entry.publication[i].publicationType;
        publicationItem['publicationYear'] = this.$scope.entry.publication[i].publicationYear;
        publicationItem['publicationPlace'] = this.$scope.entry.publication[i].publicationPlace;
        publicationItem['publicationWebReference'] = this.$scope.entry.publication[i].publicationWebReference;
        publicationArray.push(publicationItem);
      }
      item['publication'] = publicationArray;

      var trainingArray = [];
      for(var i = 0; i < this.$scope.entry.training.length; i++){
        var trainingItem: any = {};
        trainingItem['trainingName'] = this.$scope.entry.training[i].trainingName;
        trainingItem['trainingInstitution'] = this.$scope.entry.training[i].trainingInstitution;
        trainingItem['trainingDuration'] = this.$scope.entry.training[i].trainingDuration;
        trainingItem['trainingYear'] = this.$scope.entry.training[i].trainingYear;
        trainingArray.push(trainingItem);
      }
      item['training'] = trainingArray;

      var awardArray = [];
      for(var i = 0; i < this.$scope.entry.award.length; i++){
        var awardItem: any = {};
        awardItem['awardName'] = this.$scope.entry.award[i].awardName;
        awardItem['purposeOfTheAward'] = this.$scope.entry.award[i].purposeOfTheAward;
        awardItem['yearOfRecognition'] = this.$scope.entry.award[i].yearOfRecognition;
        awardItem['shortDescription'] = this.$scope.entry.award[i].shortDescription;
        awardArray.push(awardItem);
      }
      item['award'] = awardArray;

      var experienceArray = [];
      for(var i = 0; i < this.$scope.entry.experience.length; i++){
        var experienceItem: any = {};
        experienceItem['experienceInstitution'] = this.$scope.entry.experience[i].experienceInstitution;
        experienceItem['experienceDesignation'] = this.$scope.entry.experience[i].experienceDesignation;
        experienceItem['experienceFromMonth'] = this.$scope.entry.experience[i].experienceFromMonth;
        experienceItem['experienceFromYear'] = this.$scope.entry.experience[i].experienceFromYear;
        experienceItem['experienceToMonth'] = this.$scope.entry.experience[i].experienceToMonth;
        experienceItem['experienceToYear'] = this.$scope.entry.experience[i].experienceToYear;
        experienceArray.push(experienceItem);
      }
      item['experience'] = experienceArray;

      JsonArray.push(item);

      JsonObject['entries'] = JsonArray;

      console.log("Json Data");
      console.log(JsonObject);

      return defer.promise;
    }

    private addDate(): void {
      setTimeout(function () {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
      }, 100);
    }
  }

  UMS.controller("EmployeeInformation",EmployeeInformation);
}