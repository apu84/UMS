///<reference path="ProgramSelectorModelImpl.ts"/>
module ums {
  export class NewStudentModel extends ProgramSelectorModelImpl {
     id:string;
     fullName:string;
     fatherName:string;
     motherName:string;
     semesterId:string;
     dateOfBirth:string;
     gender:string;
     mobileNo:string;
     phoneNo:string;
     bloodGroup:string;
     email:string;
     presentAddress:string;
     permanentAddress:string;
     guardianName:string;
     guardianMobileNo:string;
     guardianPhoneNo:string;
     guardianEmail:string;

     genders:any;
     bloodGroups:any;

     imageData: string;
     picture: any;

    constructor(appConstants:any, httpClient:HttpClient) {
      super(appConstants, httpClient);

      this.id = '';
      this.fullName = '';
      this.fatherName = '';
      this.motherName = '';
      this.semesterId = '';
      this.dateOfBirth = '';
      this.gender = '';
      this.mobileNo = '';
      this.phoneNo = '';
      this.bloodGroup = '';
      this.email = '';
      this.presentAddress = '';
      this.permanentAddress = '';
      this.guardianName = '';
      this.guardianMobileNo = '';
      this.guardianPhoneNo = '';
      this.guardianEmail = '';

      this.imageData = '';

      this.genders = appConstants.gender;
      this.bloodGroups = appConstants.bloodGroup;
    }
  }
}