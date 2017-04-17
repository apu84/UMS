module ums{
    export interface IPublicationInformationModel{
        employeeId: number;
        publicationTitle: string;
        publicationInterestGenre: string;
        publisherName: string;
        dateOfPublication: string;
        publicationType: IPublicationType;
        publicationWebLink: string;
    }
}