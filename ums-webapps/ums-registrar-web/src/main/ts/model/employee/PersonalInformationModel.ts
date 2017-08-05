module ums {
    export interface IPersonalInformationModel {
        employeeId: string;
        firstName: string;
        lastName: string;
        fatherName: string;
        motherName: string;
        gender: IGender;
        dateOfBirth: string;
        nationality: ICommon;
        religion: ICommon;
        maritalStatus: ICommon;
        spouseName: string;
        nidNo: string;
        bloodGroup: ICommon;
        spouseNidNo: string;
        website: string;
        organizationalEmail: string;
        personalEmail: string;
        mobile: string;
        phone: string;
        preAddressLine1: string;
        preAddressLine2: string;
        preAddressCountry: ICommon;
        preAddressDivision: ICommon;
        preAddressDistrict: ICommon;
        preAddressThana: ICommon;
        preAddressPostCode: string;
        perAddressLine1: string;
        perAddressLine2: string;
        perAddressCountry: ICommon;
        perAddressDivision: ICommon;
        perAddressDistrict: ICommon;
        perAddressThana: ICommon;
        perAddressPostCode: string;
        emergencyContactName: string;
        emergencyContactRelation: ICommon;
        emergencyContactPhone: string;
        emergencyContactAddress: string;
    }
}