module ums{
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

    export interface IAcademicInformationModel{
        id: string;
        employeeId: string;
        degree: IAcademicDegreeTypes;
        institution: string;
        passingYear: number;
        result: string;
        dbAction: string;
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
        dbAction: string;
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
        dbAction: string;
    }

    export interface IAwardInformationModel{
        id: string;
        employeeId: string;
        awardName: string;
        awardInstitute: string;
        awardedYear: number;
        awardShortDescription: string;
        dbAction: string;
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
        dbAction: string;
    }

    export interface IAdditionalInformationModel{
        employeeId: string;
        roomNo: string;
        extNo: string;
        academicInitial: string;
        areaOfInterestInformation: Array<ICommon>;
    }

    export interface IAreaOfInterestInformationModel {
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
        dbAction: string;
        intervalDetails: Array<IServiceDetailsModel>;
    }

    export interface IServiceDetailsModel{
        id: string;
        interval: ICommon;
        startDate: string;
        endDate: string;
        comment: string;
        serviceId: string;
        dbAction: string;
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