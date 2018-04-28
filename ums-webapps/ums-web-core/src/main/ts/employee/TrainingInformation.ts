module ums {
    interface IEmployeeProfile extends ng.IScope {
        submitTrainingForm: Function;
    }

    class TrainingInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify',
            'countryService', 'divisionService', 'districtService', 'thanaService',
            'employeeInformationService', 'areaOfInterestService', 'userService', 'academicDegreeService',
            'cRUDDetectionService', '$stateParams', 'employmentTypeService', 'departmentService', 'designationService', 'FileUpload'];

        private entry: {
            training: ITrainingInformationModel[]
        };
        private training: boolean = false;
        private showTrainingInputDiv: boolean = false;
        private trainingTypes: ICommon[] = [];
        private previousTrainingInformation: ITrainingInformationModel[];
        private trainingDeletedObjects: ITrainingInformationModel[];
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


            console.log("In Training Profile-------------");

            $scope.submitTrainingForm = this.submitTrainingForm.bind(this);

            this.entry = {
                training: Array<ITrainingInformationModel>()
            };

            this.stateParams = $stateParams;
            this.trainingTypes = registrarConstants.trainingCategories;
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
                this.getTrainingInformation();
            });
        }

        private submitTrainingForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.previousTrainingInformation, angular.copy(this.entry.training), this.trainingDeletedObjects)
                .then((trainingObjects: any) => {
                    this.convertToJson('training', trainingObjects)
                        .then((json: any) => {
                            this.employeeInformationService.saveTrainingInformation(json)
                                .then((message: any) => {
                                    if(message == "Error"){}
                                    else {
                                        this.getTrainingInformation();
                                        this.showTrainingInputDiv = false;
                                    }
                                });
                        });
                });
        }

        private getTrainingInformation() {
            this.entry.training = [];
            this.trainingDeletedObjects = [];
            this.employeeInformationService.getTrainingInformation(this.userId).then((trainingInformation: any) => {
                this.entry.training = trainingInformation;
            }).then(() => {
                this.previousTrainingInformation = angular.copy(this.entry.training);
            });
        }

        private addNewRow(divName: string) {
            if (divName === 'training') {
                let trainingEntry: ITrainingInformationModel;
                trainingEntry = {
                    id: "",
                    employeeId: this.userId,
                    trainingName: "",
                    trainingInstitution: "",
                    trainingFrom: "",
                    trainingTo: "",
                    trainingDuration: null,
                    trainingDurationString: "",
                    trainingCategory: null,
                    dbAction: "Create"
                };
                this.entry.training.push(trainingEntry);
            }
        }

        private deleteRow(divName: string, index: number, parentIndex?: number) {
            if (divName === 'training') {
                if (this.entry.training[index].id != "") {
                    this.entry.training[index].dbAction = "Delete";
                    this.trainingDeletedObjects.push(this.entry.training[index]);
                }
                this.entry.training.splice(index, 1);
            }
        }

        private convertToJson(convertThis: string, obj: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
            if (convertThis === "training") {
                item['training'] = obj;
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

                    if (tabName == "training") {
                        this.entry.training[index].trainingDuration = diffDays;
                        this.entry.training[index].trainingDurationString = diffDateString;
                    }
                }
            }
        }
    }

    UMS.controller("TrainingInformation", TrainingInformation);
}