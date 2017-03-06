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





    changeNav: Function;

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
      $scope.changeNav = this.changeNav.bind(this);
      $scope.testData = this.testData.bind(this);
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
      this.$scope.gender = "male";
      this.$scope.birthday = "20/10/1995";
      this.$scope.maritalStatus = "single";
      this.$scope.spouseName = ""
      this.$scope.nationalIdNo = 19952641478954758;
      this.$scope.email = "kawsur.iums@aust.edu";
      this.$scope.phone = "+8801672494863";
      this.$scope.presentAddress = "34/1 K R Road Posta Lalbagh Dhaka-1211, Bangladesh";
      this.$scope.permanentAddress = "Don't Know";

      this.$scope.sscSelected = "ssc";
      this.$scope.sscInstitution = "Birsrestha Munshi Abdur Rouf Public College";
      this.$scope.sscGPA = 5.00;
      this.$scope.sscPassingYear = "2010"
      this.$scope.hscSelected = "hsc";
      this.$scope.hscInstitution = "Birsrestha Munshi Abdur Rouf Public College";
      this.$scope.hscGPA = 5.00;
      this.$scope.hscPassingYear = "2012"
      this.$scope.bachelorSelected = "bachelor";
      this.$scope.bachelorInstitution = "American International University-Bangladesh";
      this.$scope.bachelorCGPA = 3.79;
      this.$scope.bachelorPassingYear = "2016"
      this.$scope.mastersSelected = "masters";
      this.$scope.mastersInstitution = "American International University-Bangladesh";
      this.$scope.mastersCGPA = 4.00;
      this.$scope.mastersPassingYear = "2018"
      this.$scope.phdSelected = "phd";
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
  }

  UMS.controller("employeeInformation",employeeInformation);
}