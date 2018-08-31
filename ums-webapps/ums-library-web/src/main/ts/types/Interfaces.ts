module ums {

    export interface IContributor {
        id: string,
        viewOrder: number;
        role: number;
        roleName: string;
        name: string;
        text: string;
        shortName: string;
        gender: string;
        countryId: string;
        countryName: string;
        address: string;
    }

    export interface INoteEntry {
        viewOrder: number;
        note: string;
    }

    export interface ISubjectEntry {
        viewOrder: number;
        subject: string;
    }

    export interface IImprint {
        publisher: string;
        publisherName: string;
        placeOfPublication: string;
        yearOfPublication: number;
        yearOfCopyRight: number;
        yearOfReprint: number;
    }

    export interface IPhysicalDescription {
        pagination: string;
        illustrations: string;
        accompanyingMaterials: string;
        dimensions: string;
    }

    export interface IRecord {
        mfnNo: string;
        language: number;
        languageName: string;
        materialType: number;
        materialTypeName: string;
        status: number;
        statusName: string;
        title: string;
        subTitle: string;
        gmd: number;
        seriesTitle: string;
        volumeNo: string;
        volumeTitle: string;
        serialIssueNo: string;
        serialNumber: string;
        serialSpecial: string;
        libraryLacks: string;
        changedTitle: string;
        isbn: string;
        issn: string;
        corpAuthorMain: string;
        corpSubBody: string;
        corpCityCountry: string;
        edition: string;
        translateTitleEdition: string;
        frequency: number;
        callNo: string;
        classNo: string;
        callYear: number;
        callEdition: string;
        callVolume: string;
        authorMark: string;
        contributorList: Array<IContributor>; //need separate channel
        imprint: IImprint;
        physicalDescription: IPhysicalDescription;
        subjectList: Array<ISubjectEntry>;
        noteList: Array<INoteEntry>;
        keywords: string;
        contributorJsonString: string;
        noteJsonString: string;
        subjectJsonString: string;
        physicalDescriptionString: string;
        totalItems: number;
        totalAvailable: number;
        totalCheckedOut: number;
        totalOnHold: number;
    }

    export interface IItem {
        mfnNo: string;
        id: string;
        copyNumber: number;
        accessionNumber: string;
        accessionDate: string;
        barcode: string;
        price: number;
        internalNote: string;
        supplier: ISupplier;
        status: number;
        statusName: string;
        currency: number;
        acqType: number;
        acqTypeName: string;
        circulationStatus: number;
        bindingType: number;
        bindingTypeName: string;
        paperQuality: string;
    }

    export interface ISupplier {
        id: string;
        text: string;
        name: string;
        email: string;
        contactPerson: string;
        contactNumber: string;
        address: string,
        note: string;
    }

    export interface IPublisher {
        id: string;
        text: string;
        name: string;
        countryId: string;
        countryName: string;
        contactPerson: string;
        phoneNumber: string,
        emailAddress: string;
    }

    export interface ISearchFilter {
        searchType: string,
        basicQueryField: string;
        basicQueryTerm: string;
        advancedQueryMap: Array<IAdvancedSearchMap>;
    }

    export interface IAdvancedSearchMap {
        key: string;
        value: string;
    }

    export interface ILibraryCirculation{
        circulationId: string;
        patronId: string;
        mfn: string;
        issueDate: string;
        dueDate: string;
        returnDate: string;
        fineStatus: number;
        fineStatusString: string;
        title: string;
        materialType: string;
        checkBoxStatus: boolean;
        itemCode: string;
        totalItems: number;
        totalAvailable: number;
        totalCheckedOut: number;
        totalOnHold: number;
        overDueStatus: boolean;
    }

    export interface ICheckIn{
        itemCode: string;
        returnDate: string;
        dueDate: string;
        fineStatus: boolean;
    }

    export interface IFine{
        id: string;
        patronId: string;
        circulationId: string;
        amount: number;
        fineCategory: number;
        description: string;
        fineAppliedDate: string;
        fineAppliedBy: string;
        fineForgivenBy: string;
        finePaymentDate: string;
        dueDate: string;
        mfn: string;
        status: number;
        statusName: string;
        checkBoxStatus: boolean;
    }

}