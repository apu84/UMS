module ums {
    interface IEmployeeProfile extends ng.IScope {
        submitPublicationForm: Function;
    }

    class PublicationInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify',
            'countryService', 'divisionService', 'districtService', 'thanaService',
            'employeeInformationService', 'areaOfInterestService', 'userService', 'academicDegreeService',
            'cRUDDetectionService', '$stateParams', 'employmentTypeService', 'departmentService', 'designationService', 'FileUpload'];

        private entry: {
            publication: IPublicationInformationModel[]
        };
        private publication: boolean = false;
        private showPublicationInputDiv: boolean = false;
        private data: any;
        private itemPerPage: number = 2;
        private currentPage: number = 1;
        private totalRecord: number = 0;
        private publicationTypes: ICommon[] = [];
        private paginatedPublication: IPublicationInformationModel[];
        private previousPublicationInformation: IPublicationInformationModel[];
        private publicationDeletedObjects: IPublicationInformationModel[];
        private userId: string = "";
        private stateParams: any;
        private isRegistrar: boolean;
        private test: boolean = true;

        constructor(private registrarConstants: any,
                    private $scope: IEmployeeProfile,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private countryService: CountryService,
                    private divisionService: DivisionService,
                    private districtService: DistrictService,
                    private thanaService: ThanaService,
                    private employeeInformationService: EmployeeInformationService,
                    private areaOfInterestService: AreaOfInterestService,
                    private userService: UserService,
                    private academicDegreeService: AcademicDegreeService,
                    private cRUDDetectionService: CRUDDetectionService,
                    private $stateParams: any,
                    private employmentTypeService: EmploymentTypeService,
                    private departmentService: DepartmentService,
                    private designationService: DesignationService,
                    private FileUpload: FileUpload) {

            $scope.submitPublicationForm = this.submitPublicationForm.bind(this);

            this.entry = {
                publication: Array<IPublicationInformationModel>()
            };

            this.data = {
                supOptions: "1",
                borderColor: ""
            };
            this.stateParams = $stateParams;
            this.publicationTypes = this.registrarConstants.publicationTypes;
            this.userId = this.stateParams.id;
            // this.showPublicationEditButton = this.stateParams.edit;
            this.initialization();
        }

        private initialization() {
            this.getPublicationInformation();
            this.getPublicationInformationWithPagination();
        }

        private submitPublicationForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.previousPublicationInformation, angular.copy(this.entry.publication), this.publicationDeletedObjects)
                .then((publicationObjects: any) => {
                    this.convertToJson('publication', publicationObjects)
                        .then((json: any) => {
                            this.employeeInformationService.savePublicationInformation(json)
                                .then((message: any) => {
                                    if (message == "Error") {
                                    }
                                    else {
                                        this.getPublicationInformation();
                                        this.getPublicationInformationWithPagination();
                                        this.showPublicationInputDiv = false;
                                    }
                                });
                        });
                });
        }

        private getPublicationInformation() {
            this.entry.publication = [];
            this.publicationDeletedObjects = [];
            this.employeeInformationService.getPublicationInformation(this.userId).then((publicationInformation: any) => {
                this.totalRecord = publicationInformation.length;
                this.entry.publication = publicationInformation;
            }).then(() => {
                this.previousPublicationInformation = angular.copy(this.entry.publication);
            });
        }

        private getPublicationInformationWithPagination() {
            this.paginatedPublication = [];
            this.employeeInformationService.getPublicationInformationViewWithPagination(this.userId, this.currentPage, this.itemPerPage).then((publicationInformationWithPagination: any) => {
                this.paginatedPublication = publicationInformationWithPagination;
            });
        }


        private pageChanged(pageNumber: number) {
            this.currentPage = pageNumber;
            this.getPublicationInformationWithPagination();
        }

        private changeItemPerPage() {
            if (this.data.customItemPerPage == "" || this.data.customItemPerPage == null) {
            }
            else {
                this.data.itemPerPage = this.data.customItemPerPage;
                this.getPublicationInformationWithPagination();
            }
        }

        private addNewRow(divName: string) {
            if (divName === 'publication') {
                let publicationEntry: IPublicationInformationModel;
                publicationEntry = {
                    id: "",
                    employeeId: this.userId,
                    publicationTitle: "",
                    publicationType: null,
                    publicationInterestGenre: "",
                    publicationWebLink: "",
                    publisherName: "",
                    dateOfPublication: null,
                    publicationISSN: "",
                    publicationIssue: "",
                    publicationVolume: "",
                    publicationJournalName: "",
                    publicationCountry: null,
                    status: "9",
                    publicationPages: "",
                    appliedOn: "",
                    actionTakenOn: "",
                    rowNumber: null,
                    dbAction: "Create"
                };
                this.entry.publication.push(publicationEntry);
            }
        }

        private deleteRow(divName: string, index: number, parentIndex?: number) {
            if (divName === 'publication') {
                if (this.entry.publication[index].id != "") {
                    this.entry.publication[index].dbAction = "Delete";
                    this.publicationDeletedObjects.push(this.entry.publication[index]);
                }
                this.entry.publication.splice(index, 1);
            }
        }

        private convertToJson(convertThis: string, obj: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
            if (convertThis === "publication") {
                item['publication'] = obj;
            }
            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("PublicationInformation", PublicationInformation);
}