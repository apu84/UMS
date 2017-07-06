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
        nidNo: string;
        bloodGroup: IBloodGroup;
        spouseNidNo: string;
        website: string;
        organizationalEmail: string;
        personalEmail: string;
        mobile: string;
        phone: string;
        preAddressLine1: string;
        preAddressLine2: string;
        preAddressCountry: ICountry;
        preAddressDivision: IDivision;
        preAddressDistrict: IDistrict;
        preAddressThana: IThana;
        preAddressPostCode: string;
        perAddressLine1: string;
        perAddressLine2: string;
        perAddressCountry: ICountry;
        perAddressDivision: IDivision;
        perAddressDistrict: IDistrict;
        perAddressThana: IThana;
        perAddressPostCode: string;
        emergencyContactName: string;
        emergencyContactRelation: IRelation;
        emergencyContactPhone: string;
        emergencyContactAddress: string;
    }
}