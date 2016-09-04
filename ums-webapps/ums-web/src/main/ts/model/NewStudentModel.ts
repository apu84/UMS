///<reference path="ProgramSelectorModel.ts"/>
module ums {
  export class NewStudentModel {
    id: string;
    fullName: string;
    fatherName: string;
    motherName: string;
    semesterId: string;
    dateOfBirth: string;
    gender: string;
    mobileNo: string;
    phoneNo: string;
    bloodGroup: string;
    email: string;
    presentAddress: string;
    permanentAddress: string;
    guardianName: string;
    guardianMobileNo: string;
    guardianPhoneNo: string;
    guardianEmail: string;

    getGenders: Function;
    getBloodGroups: Function;

    imageData: string;
    picture: any;
    programSelector: ProgramSelectorModel;

    constructor(appConstants: any, httpClient: HttpClient) {
      this.programSelector = new ProgramSelectorModel(appConstants, httpClient);
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

      this.getGenders = () => {
        return appConstants.gender;
      };
      this.getBloodGroups = () => {
        return appConstants.bloodGroup;
      };
    }
  }
}