module ums{
    export interface IPersonalInformationModel {
        general: IGeneralInformationModel;
        contact: IContactInformationModel;
        emergencyContact: IEmergencyContactInformationModel;
    }

    export interface IGeneralInformationModel{
        employeeId: string;
        name: string;
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
        type: string;
    }

    export interface IContactInformationModel{
        employeeId: string;
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
        type: string;
    }

    export interface IEmergencyContactInformationModel{
        employeeId: string;
        emergencyContactName: string;
        emergencyContactRelation: ICommon;
        emergencyContactPhone: string;
        emergencyContactAddress: string;
        type: string;
    }

    export interface IGender {
        id: string;
        name: string;
    }

    export interface ICommon{
        id: number;
        name: string;
        foreign_id?: number;
    }
}