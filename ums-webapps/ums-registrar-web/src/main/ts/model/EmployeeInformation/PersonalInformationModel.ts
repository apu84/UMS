module ums {
    export interface IPersonalInformationModel {
        employeeId: string;
        firstName: string;
        lastName: string;
        fatherName: string;
        motherName: string;
        gender: IGender;
        birthday: string;
        nationality: INationality;
        religion: IReligion;
        maritalStatus: IMaritalStatus;
        spouseName: string;
        nationalIdNo: number;
        bloodGroup: IBloodGroup;
        spouseNationalIdNo: number;
        website: string;
        organizationalEmail: string;
        personalEmail: string;
        mobile: string;
        phone: string;
        presentAddressHouse: string;
        presentAddressRoad: string;
        presentAddressPoliceStation: IThana;
        presentAddressPostalCode: string;
        presentAddressDistrict: IDistrict;
        presentAddressDivision: IDivision;
        presentAddressCountry: ICountry;
        permanentAddressHouse: string;
        permanentAddressRoad: string;
        permanentAddressPoliceStation: IThana;
        permanentAddressPostalCode: string;
        permanentAddressDistrict: IDistrict;
        permanentAddressDivision: IDivision;
        permanentAddressCountry: ICountry;
        emergencyContactName: string;
        emergencyContactRelation: string;
        emergencyContactPhone: string;
        emergencyContactAddress: string;

    }
}