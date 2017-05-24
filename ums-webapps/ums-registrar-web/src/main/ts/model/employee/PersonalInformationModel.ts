module ums {
    export interface IPersonalInformationModel {
        employeeId: string;
        firstName: string;
        lastName: string;
        fatherName: string;
        motherName: string;
        gender: IGender;
        dateOfBirth: string;
        nationality: INationality;
        religion: IReligion;
        maritalStatus: IMaritalStatus;
        spouseName: string;
        nationalIdNo: string;
        bloodGroup: IBloodGroup;
        spouseNationalIdNo: string;
        personalWebsite: string;
        organizationalEmail: string;
        personalEmail: string;
        mobile: string;
        phone: string;
        presentAddressHouse: string;
        presentAddressRoad: string;
        presentAddressThana: IThana;
        presentAddressPostOfficeNo: string;
        presentAddressDistrict: IDistrict;
        presentAddressDivision: IDivision;
        presentAddressCountry: ICountry;
        permanentAddressHouse: string;
        permanentAddressRoad: string;
        permanentAddressThana: IThana;
        permanentAddressPostOfficeNo: string;
        permanentAddressDistrict: IDistrict;
        permanentAddressDivision: IDivision;
        permanentAddressCountry: ICountry;
        emergencyContactName: string;
        emergencyContactRelation: IRelation;
        emergencyContactPhone: string;
        emergencyContactAddress: string;
    }
}