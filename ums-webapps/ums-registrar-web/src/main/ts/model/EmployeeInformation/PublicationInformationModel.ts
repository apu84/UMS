module ums{
    export interface IPublicationInformationModel{
        employeeId: number;
        publicationTitle: string;
        publicationInterestGenre: string;
        authorsName: string;
        publisherName: string;
        dateOfPublication: string;
        publicationType: IPublicationType;
        publicationWebLink: string;
        // publicationAbstract: string;
    }
}