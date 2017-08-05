module ums{
    export interface IPublicationInformationModel{
        id: number;
        employeeId: string;
        publicationTitle: string;
        publicationInterestGenre: string;
        publisherName: string;
        dateOfPublication: string;
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
}