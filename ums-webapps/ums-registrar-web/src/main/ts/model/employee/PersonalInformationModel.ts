module ums {
    export interface IPersonalInformationModel {
        employeeId: string;
        firstName: string;
        lastName: string;
        fatherName: string;
        motherName: string;
        gender: IGender;
        genderId: string;
        dateOfBirth: string;
        nationality: ICommon;
        nationalityId: number;
        religion: ICommon;
        religionId: number;
        maritalStatus: ICommon;
        maritalStatusId: number;
        spouseName: string;
        nidNo: string;
        bloodGroup: ICommon;
        bloodGroupId: number;
        spouseNidNo: string;
        website: string;
        organizationalEmail: string;
        personalEmail: string;
        mobile: string;
        phone: string;
        preAddressLine1: string;
        preAddressLine2: string;
        preAddressCountry: ICommon;
        preAddressCountryId: number;
        preAddressDivision: ICommon;
        preAddressDivisionId: number;
        preAddressDistrict: ICommon;
        preAddressDistrictId: number;
        preAddressThana: ICommon;
        preAddressThanaId: number;
        preAddressPostCode: string;
        perAddressLine1: string;
        perAddressLine2: string;
        perAddressCountry: ICommon;
        perAddressCountryId: number;
        perAddressDivision: ICommon;
        perAddressDivisionId: number;
        perAddressDistrict: ICommon;
        perAddressDistrictId: number;
        perAddressThana: ICommon;
        perAddressThanaId: number;
        perAddressPostCode: string;
        emergencyContactName: string;
        emergencyContactRelation: ICommon;
        emergencyContactRelationId: number
        emergencyContactPhone: string;
        emergencyContactAddress: string;
        areaOfInterests: Array<IAreaOfInterestInformationModel>;
    }

    export interface IAreaOfInterestInformationModel {
        areaOfInterest: ICommon;
        areaOfInterestId: number;
    }
}