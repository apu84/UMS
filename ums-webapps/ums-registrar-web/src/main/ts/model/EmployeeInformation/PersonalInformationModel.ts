module ums {
    export interface IPersonalInformationModel {
        employeeId: number;
        firstName: string;
        lastName: string;
        fatherName: string;
        motherName: string;
        gender: IEmpGender;
        birthday: string;
        nationality: INationality;
        religion: IReligion;
        maritalStatus: IEmpMaritalStatus;
        spouseName: string;
        nationalIdNo: number;
        bloodGroup: string;
        spouseNationalIdNo: number;
        website: string;
        organizationalEmail: string;
        personalEmail: string;
        mobile: string;
        phone: string;
        presentAddressHouse: string;
        presentAddressRoad: string;
        presentAddressPoliceStation: string;
        presentAddressPostalCode: string;
        presentAddressDistrict: string;
        presentAddressDivision: string;
        presentAddressCountry: string;
        permanentAddressHouse: string;
        permanentAddressRoad: string;
        permanentAddressPoliceStation: string;
        permanentAddressPostalCode: string;
        permanentAddressDistrict: string;
        permanentAddressDivision: string;
        permanentAddressCountry: string;
        emergencyContactName: string;
        emergencyContactRelation: string;
        emergencyContactPhone: string;
        emergencyContactAddress: string;

    }

    export interface IEmpGender {
        id: string;
        name: string;
    }

    export interface IEmpMaritalStatus {
        id: number;
        name: string;
    }

    export interface IEmpDegree {
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

    export interface IReligion{
    id: number;
    name: string;
}
}