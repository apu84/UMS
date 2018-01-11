module ums {

    export interface IContributor {
        id: string,
        viewOrder: number;
        role: number;
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
        yearDateOfPublication: string;
        copyRightDate: string;
    }

    export interface IPhysicalDescription {
        pagination: string;
        illustrations: string;
        accompanyingMaterials: string;
        dimensions: string;
        paperQuality: string;
    }

    export interface IRecord {
        mfnNo: string;
        language: number;
        languageName: string;
        materialType: number;
        materialTypeName: string;
        status: number;
        statusName: string;
        bindingType: number;
        bindingTypeName: string;
        acqType: number;
        acqTypeName: string;
        title: string;
        subTitle: string;
        gmd: string;
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
        callDate: string;
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

        contributorName?: string;
        constributorRole?: string;
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
        circulationStatus: number;
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

    export interface IFilter {
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
    }

    export interface ICheckIn{
        itemCode: string;
        returnDate: string;
    }

}