module ums{
  interface IEmployeeInformation extends ng.IScope{
    personal: boolean;
    academic: boolean;
    publication: boolean;
    training: boolean;
    award: boolean;





    //testData
    experience: boolean;
    firstName: string;
    lastName: string;
    fatherName: string;
    motherName: string;
    gender: string;
    birthday: string;
    maritalStatus: string;
    spouseName: string;
    nationalIdNo: number;
    email: string;
    phone: string;
    presentAddress: string;
    permanentAddress: string;

    sscSelected: string;
    sscInstitution: string;
    sscGPA: number;
    sscPassingYear: string;
    hscSelected: string;
    hscInstitution: string;
    hscGPA: number;
    hscPassingYear: string;
    bachelorSelected: string;
    bachelorInstitution: string;
    bachelorCGPA: number;
    bachelorPassingYear: string;
    mastersSelected:string;
    mastersInstitution: string;
    mastersCGPA: number;
    mastersPassingYear: string;
    phdSelected: string;
    phdInstitution: string;
    phdCGPA: number;
    phdPassingYear: string;

    publicationTitle:string;
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


    showInputFirstNameDiv: boolean;
    showLabelFirstNameDiv: boolean;
    showInputLastNameDiv: boolean;
    showLabelLastNameDiv: boolean;
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
    showInputEmailDiv: boolean;
    showLabelEmailDiv: boolean;
    showInputPhoneDiv: boolean;
    showLabelPhoneDiv: boolean;
    showInputPresentAddressDiv: boolean;
    showLabelPresentAddressDiv: boolean;
    showInputPermanentAddressDiv: boolean;
    showLabelPermanentAddressDiv: boolean;
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




    changeNav: Function;
    submit: Function;

    //test Data Function
    testData: Function;
  }

  class employeeInformation {

    public static $inject = ['appConstants', '$scope', '$q', 'notify', '$window', '$sce'];

    constructor(private appConstants: any,
                private $scope: IEmployeeInformation,
                private $q: ng.IQService,
                private notify: Notify,
                private $window: ng.IWindowService,
                private $sce: ng.ISCEService) {

      $scope.personal = true;
      $scope.showInputFirstNameDiv = true;
      $scope.showLabelFirstNameDiv = false;
      $scope.showInputLastNameDiv = true;
      $scope.showLabelLastNameDiv = false;
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
      $scope.showInputEmailDiv = true;
      $scope.showLabelEmailDiv = false;
      $scope.showInputPhoneDiv = true;
      $scope.showLabelPhoneDiv = false;
      $scope.showInputPresentAddressDiv = true;
      $scope.showLabelPresentAddressDiv = false;
      $scope.showInputPermanentAddressDiv = true;
      $scope.showLabelPermanentAddressDiv = false;
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
      $scope. showLabelHSCPassingYearDiv = false;
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


      $scope.changeNav = this.changeNav.bind(this);
      $scope.testData = this.testData.bind(this);
      $scope.submit = this.submit.bind(this);
    }

    private changeNav(navTitle: number){

      this.$scope.personal = false;
      this.$scope.academic = false;
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
        this.$scope.academic = true;
      }
      else if(navTitle == 3){
        this.$scope.publication = true;
      }
      else if(navTitle == 4){
        this.$scope.training = true;
      }
      else if(navTitle == 5){
        this.$scope.award = true;
      }
      else if(navTitle == 6){
        this.$scope.experience = true;
      }
    }




    // Used For Test Data
    private testData(){
      this.$scope.firstName = "kawsur";
      this.$scope.lastName = "Mir Md.";
      this.$scope.fatherName = "Mir Abdul Aziz";
      this.$scope.motherName = "Mst Hosne Ara";
      this.$scope.gender = "Male";
      this.$scope.birthday = "20/10/1995";
      this.$scope.maritalStatus = "Single";
      this.$scope.spouseName = ""
      this.$scope.nationalIdNo = 19952641478954758;
      this.$scope.email = "kawsur.iums@aust.edu";
      this.$scope.phone = "+8801672494863";
      this.$scope.presentAddress = "34/1 K R Road Posta Lalbagh Dhaka-1211, Bangladesh";
      this.$scope.permanentAddress = "Don't Know";

      this.$scope.sscSelected = "SSC";
      this.$scope.sscInstitution = "Birsrestha Munshi Abdur Rouf Public College";
      this.$scope.sscGPA = 5.00;
      this.$scope.sscPassingYear = "2010"
      this.$scope.hscSelected = "HSC";
      this.$scope.hscInstitution = "Birsrestha Munshi Abdur Rouf Public College";
      this.$scope.hscGPA = 5.00;
      this.$scope.hscPassingYear = "2012"
      this.$scope.bachelorSelected = "Bachelor";
      this.$scope.bachelorInstitution = "American International University-Bangladesh";
      this.$scope.bachelorCGPA = 3.79;
      this.$scope.bachelorPassingYear = "2016"
      this.$scope.mastersSelected = "Masters";
      this.$scope.mastersInstitution = "American International University-Bangladesh";
      this.$scope.mastersCGPA = 4.00;
      this.$scope.mastersPassingYear = "2018"
      this.$scope.phdSelected = "Phd";
      this.$scope.phdInstitution = "Massachusetts Institute Of Technology";
      this.$scope.phdCGPA = 4.00;
      this.$scope.phdPassingYear = "2022";

      this.$scope.publicationTitle = "IoT Species: Identifying And Communicating Between Homogeneous Devices";
      this.$scope.publicationType = "Journal";
      this.$scope.publicationYear = "2017";
      this.$scope.publicationPlace = "IEEE";
      this.$scope.webReference = "Unavailable";

      this.$scope.trainingName = "Oracle Java SE Course";
      this.$scope.trainingInstitution = "Oracle University";
      this.$scope.trainingDuration = "6";
      this.$scope.trainingAccreditedYear = "2017";

      this.$scope.awardName = "Best Student Award";
      this.$scope.purpose = "";
      this.$scope.yearOfRecognition = "2016";
      this.$scope.description = "Thanks For The Award";

      this.$scope.experienceInstitution = "American International University-Bangladesh";
      this.$scope.experienceDesignation = "Teacher's Assistant";
      this.$scope.experienceFromMonth = "09";
      this.$scope.experienceFromYear = "2016";
      this.$scope.experienceToMonth = "10";
      this.$scope.experienceToYear = "2016";



    }


    private submit(){
      this.$scope.showInputFirstNameDiv = false;
      this.$scope.showLabelFirstNameDiv = true;
      this.$scope.showInputLastNameDiv = false;
      this.$scope.showLabelLastNameDiv = true;
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
      this.$scope.showInputEmailDiv = false;
      this.$scope.showLabelEmailDiv = true;
      this.$scope.showInputPhoneDiv = false;
      this.$scope.showLabelPhoneDiv = true;
      this.$scope.showInputPresentAddressDiv = false;
      this.$scope.showLabelPresentAddressDiv = true;
      this.$scope.showInputPermanentAddressDiv = false;
      this.$scope.showLabelPermanentAddressDiv = true;
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

    }
  }

  UMS.controller("employeeInformation",employeeInformation);
}