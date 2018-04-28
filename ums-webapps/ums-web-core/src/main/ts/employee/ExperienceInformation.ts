module ums {
    interface IEmployeeProfile extends ng.IScope {
        submitExperienceForm: Function;
    }

    class ExperienceInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify',
            'countryService', 'divisionService', 'districtService', 'thanaService',
            'employeeInformationService', 'areaOfInterestService', 'userService', 'academicDegreeService',
            'cRUDDetectionService', '$stateParams', 'employmentTypeService', 'departmentService', 'designationService', 'FileUpload'];

        private entry: {
            experience: IExperienceInformationModel[]
        };
        private experience: boolean = false;
        private showExperienceInputDiv: boolean = false;
        private experienceTypes: ICommon[] = [];
        private previousExperienceInformation: IExperienceInformationModel[];
        private experienceDeletedObjects: IExperienceInformationModel[];
        private userId: string = "";
        private tabs: boolean = false;
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


            console.log("In Experience Profile-------------");

            $scope.submitExperienceForm = this.submitExperienceForm.bind(this);

            this.entry = {
                experience: Array<IExperienceInformationModel>()
            };

            this.stateParams = $stateParams;
            this.experienceTypes = registrarConstants.experienceCategories;
            this.initialization();
        }

        private initialization() {
            this.userService.fetchCurrentUserInfo().then((user: any) => {
                if(user.roleId == 82 || user.roleId == 81){
                    this.isRegistrar = true;
                }
                else{
                    this.isRegistrar = false;
                }
                if (this.stateParams.id == "" || this.stateParams.id == null || this.stateParams.id == undefined) {
                    this.userId = user.employeeId;
                }
                else {
                    this.userId = this.stateParams.id;
                }
                this.getExperienceInformation();
            });
        }

        private submitExperienceForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.previousExperienceInformation, angular.copy(this.entry.experience), this.experienceDeletedObjects)
                .then((experienceObjects: any) => {
                    this.convertToJson('experience', experienceObjects)
                        .then((json: any) => {
                            this.employeeInformationService.saveExperienceInformation(json)
                                .then((message: any) => {
                                    if(message == "Error"){}
                                    else {
                                        this.getExperienceInformation();
                                        this.showExperienceInputDiv = false;
                                    }
                                });
                        });
                });
        }

        private getExperienceInformation() {
            this.entry.experience = [];
            this.experienceDeletedObjects = [];
            this.employeeInformationService.getExperienceInformation(this.userId).then((experienceInformation: any) => {
                this.entry.experience = experienceInformation;
            }).then(() => {
                this.previousExperienceInformation = angular.copy(this.entry.experience);
            });
        }

        private addNewRow(divName: string) {
            if (divName === 'experience') {
                let experienceEntry: IExperienceInformationModel;
                experienceEntry = {
                    id: "",
                    employeeId: this.userId,
                    experienceInstitution: "",
                    experienceDesignation: "",
                    experienceFrom: "",
                    experienceTo: "",
                    experienceDuration: null,
                    experienceDurationString: "",
                    experienceCategory: null,
                    dbAction: "Create"
                };
                this.entry.experience.push(experienceEntry);
            }
        }

        private deleteRow(divName: string, index: number, parentIndex?: number) {
            if (divName === 'experience') {
                if (this.entry.experience[index].id != "") {
                    this.entry.experience[index].dbAction = "Delete";
                    this.experienceDeletedObjects.push(this.entry.experience[index]);
                }
                this.entry.experience.splice(index, 1);
            }
        }

        private convertToJson(convertThis: string, obj: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
            if (convertThis === "experience") {
                item['experience'] = obj;
            }
            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;
            defer.resolve(JsonObject);
            return defer.promise;
        }

        private calculateDifference(tabName: string, index: number, FromDate: string, ToDate: string): void {
            if (FromDate != "" && ToDate != "") {
                let formattedFromDate: string = moment(FromDate, "DD/MM/YYYY").format("MM/DD/YYYY");
                let fromDate: Date = new Date(formattedFromDate);
                let formattedToDate: string = moment(ToDate, "DD/MM/YYYY").format("MM/DD/YYYY");
                let toDate: Date = new Date(formattedToDate);
                if (fromDate > toDate) {
                    this.notify.error("From date is greater than to date");
                } else {
                    let diffDateString = "";
                    let year: number = 0;
                    let month: number = 0;
                    let day: number = 0;
                    let timeDiff: number = (toDate.getTime() - fromDate.getTime());
                    let diffDays: number = Math.ceil(timeDiff / (1000 * 3600 * 24));

                    if (diffDays >= 365) {
                        year = Math.floor(diffDays / 365);
                        month = Math.floor((diffDays % 365) / 30);
                        day = Math.floor((diffDays % 365) % 30);
                        diffDateString = year + " year(s) " + month + " month(s) " + day + " day(s)";
                    }
                    else if (diffDays >= 30) {
                        month = Math.floor((diffDays / 30));
                        day = Math.floor(diffDays % 30);
                        diffDateString = month + " month(s) " + day + " day(s)";
                    }
                    else {
                        diffDateString = diffDays + " day(s)";
                    }
                    if (tabName == "experience") {
                        this.entry.experience[index].experienceDuration = diffDays;
                        this.entry.experience[index].experienceDurationString = diffDateString;
                    }
                }
            }
        }
    }

    UMS.controller("ExperienceInformation", ExperienceInformation);
}