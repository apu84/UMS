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
        preAddressHouse: string;
        preAddressRoad: string;
        preAddressThana: IThana;
        preAddressPostOfficeNo: string;
        preAddressDistrict: IDistrict;
        preAddressDivision: IDivision;
        preAddressCountry: ICountry;
        perAddressHouse: string;
        perAddressRoad: string;
        perAddressThana: IThana;
        perAddressPostOfficeNo: string;
        perAddressDistrict: IDistrict;
        perAddressDivision: IDivision;
        perAddressCountry: ICountry;
        emergencyContactName: string;
        emergencyContactRelation: IRelation;
        emergencyContactPhone: string;
        emergencyContactAddress: string;
    }
}