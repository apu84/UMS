module ums{
    export interface IPublicationInformationModel{
        employeeId: string;
        publicationTitle: string;
        publicationInterestGenre: string;
        publisherName: string;
        dateOfPublication: string;
        publicationType: IPublicationType;
        publicationWebLink: string;
        //status: string;
    }
}