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

    export interface IAcademicInformationModel{
        id: string;
        employeeId: string;
        degree: IAcademicDegreeTypes;
        institution: string;
        passingYear: number;
        result: string;
    }

    export interface IPublicationInformationModel{
        id: string;
        employeeId: string;
        publicationTitle: string;
        publicationInterestGenre: string;
        publisherName: string;
        dateOfPublication: number;
        publicationType: ICommon;
        publicationWebLink: string;
        publicationISSN: string;
        publicationIssue: String;
        publicationVolume: string;
        publicationJournalName: string;
        publicationCountry: ICommon;
        status: string;
        publicationPages: String;
        appliedOn: string;
        actionTakenOn: string;
        rowNumber: number;
    }

    export interface ITrainingInformationModel{
        id: string;
        employeeId: string;
        trainingName: string;
        trainingInstitution: string;
        trainingFrom: string;
        trainingTo: string;
        trainingDuration: number;
        trainingDurationString: string;
        trainingCategory: ICommon;
    }

    export interface IAwardInformationModel{
        id: string;
        employeeId: string;
        awardName: string;
        awardInstitute: string;
        awardedYear: number;
        awardShortDescription: string;
    }

    export interface IExperienceInformationModel{
        id: string;
        employeeId: string;
        experienceInstitution: string;
        experienceDesignation: string;
        experienceFrom: string;
        experienceTo: string;
        experienceDuration: number;
        experienceDurationString: string;
        experienceCategory: ICommon;
    }

    export interface IAdditionalInformationModel{
        employeeId: string;
        roomNo: string;
        extNo: string;
        academicInitial: string;
    }

    export interface IAreaOfInterestInformationModel {
        employeeId: string;
        areaOfInterest: ICommon;
        areaOfInterestId: number;
    }

    export interface IServiceInformationModel{
        id: string;
        employeeId: string;
        department: ICommon;
        designation: ICommon;
        employmentType: ICommon;
        joiningDate: string;
        resignDate: string;
        intervalDetails: IServiceDetailsModel[];
    }

    export interface IServiceDetailsModel{
        id: string;
        interval: ICommon;
        startDate: string;
        endDate: string;
        comment: string;
        serviceId: string;
    }

    export interface IGender {
        id: string;
        name: string;
    }
    export interface ICommon{
        id: number;
        name: string;
        foreign_id: number;
    }

    export interface IAcademicDegreeTypes{
        id: number;
        type: number;
        typeName: string;
        name: string;
        shortName: string;
    }

    export interface IDepartment {
        id: string;
        shortName: string;
        longName: string;
        type: string;
    }
}