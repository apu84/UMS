module ums {
    interface IEmployeeProfile extends ng.IScope {
        submitServiceForm: Function;
    }

    class ServiceInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify',
            'countryService', 'divisionService', 'districtService', 'thanaService',
            'employeeInformationService', 'areaOfInterestService', 'userService', 'academicDegreeService',
            'cRUDDetectionService', '$stateParams', 'employmentTypeService', 'departmentService', 'designationService', 'FileUpload'];

        private entry: {
            serviceInfo: IServiceInformationModel[]
        };

        private service: boolean = false;
        private showServiceInputDiv: boolean = false;
        private showServiceEditButton: boolean = false;
        private previousServiceInformation: IServiceInformationModel[];
        private serviceDeletedObjects: IServiceInformationModel[];
        private serviceDetailDeletedObjects: IServiceDetailsModel[];
        private userId: string = "";
        private tabs: boolean = false;
        private stateParams: any;
        private isRegistrar: boolean;
        private test: boolean = true;
        private designations: ICommon[] = [];
        private employmentTypes: ICommon[] = [];
        private serviceIntervals: ICommon[] = [];
        private serviceRegularIntervals: ICommon[] = [];
        private serviceContractIntervals: ICommon[] = [];
        private departments: IDepartment[] = [];


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


            console.log("In Employee Profile-------------");

            $scope.submitServiceForm = this.submitServiceForm.bind(this);

            this.entry = {
                serviceInfo: Array<IServiceInformationModel>()
            };

            this.stateParams = $stateParams;
            this.userId = this.stateParams.id
            this.showServiceEditButton = this.stateParams.edit;
            this.initialization();
        }

        private initialization() {
            this.departmentService.getAll().then((departments: any) => {
                this.departments = departments;
                this.designationService.getAll().then((designations: any) => {
                    this.designations = designations;
                    this.employmentTypeService.getAll().then((employmentTypes: any) => {
                        this.employmentTypes = employmentTypes;
                        this.getServiceIntervals();
                    });
                });
            });
        }

        private getServiceIntervals(): void {
            this.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[0]);
            this.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[1]);
            this.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[2]);
            this.serviceContractIntervals.push(this.registrarConstants.servicePeriods[3]);
        }

        private submitServiceForm(): void {
            let countEmptyResignDate = 0;
            for (let i = 0; i < this.entry.serviceInfo.length; i++) {
                if (this.entry.serviceInfo[i].resignDate == "" || this.entry.serviceInfo[i].resignDate == null) {
                    countEmptyResignDate++;
                }
            }
            if (countEmptyResignDate > 1) {
                this.notify.error("You can empty only one resign date");
            }
            else {
                this.cRUDDetectionService.ObjectDetectionForServiceObjects(this.previousServiceInformation, angular.copy(this.entry.serviceInfo), this.serviceDeletedObjects, this.serviceDetailDeletedObjects)
                    .then((serviceObjects) => {
                        this.convertToJson('service', serviceObjects).then((json: any) => {
                            this.employeeInformationService.saveServiceInformation(json).then((message: any) => {
                                if (message == "Error") {
                                }
                                else {
                                    this.getServiceInformation();
                                    this.showServiceInputDiv = false;
                                }
                            });
                        });
                    });
            }
        }

        private getServiceInformation(): void {
            this.entry.serviceInfo = [];
            this.serviceDeletedObjects = [];
            this.serviceDetailDeletedObjects = [];
            this.employeeInformationService.getServiceInformation(this.userId).then((services: any) => {
                this.entry.serviceInfo = services;
            }).then(() => {
                this.previousServiceInformation = angular.copy(this.entry.serviceInfo);
            });
        }

        private addNewServiceRow(parameter: string, index?: number): void {
            if (parameter == "serviceInfo") {
                if (this.entry.serviceInfo.length == 0) {
                    this.addNewRow("service");
                }
                else {
                    if (this.entry.serviceInfo[this.entry.serviceInfo.length - 1].resignDate == "") {
                        this.notify.error("Please fill up the resign date first");
                    }
                    else {
                        this.addNewRow("service");
                    }
                }
            }
            else if (parameter == "serviceDetails") {
                if (this.entry.serviceInfo[index].intervalDetails.length == 0) {
                    this.addNewServiceDetailsRow(index);
                }
                else {
                    if (this.entry.serviceInfo[index].intervalDetails[this.entry.serviceInfo[index].intervalDetails.length - 1].endDate == "") {
                        this.notify.error("Please fill up the end date first");
                    }
                    else {
                        this.addNewServiceDetailsRow(index);
                    }
                }
            }
        }

        private addNewServiceDetailsRow(index: number) {
            let serviceDetailsEntry: IServiceDetailsModel;
            serviceDetailsEntry = {
                id: "",
                interval: null,
                startDate: "",
                endDate: "",
                comment: "",
                serviceId: null,
                dbAction: "Create"
            };
            this.entry.serviceInfo[index].intervalDetails.push(serviceDetailsEntry);
        }

        private addNewRow(divName: string) {
            if (divName = 'service') {
                let serviceEntry: IServiceInformationModel;
                serviceEntry = {
                    id: "",
                    employeeId: this.userId,
                    department: null,
                    designation: null,
                    employmentType: null,
                    joiningDate: "",
                    resignDate: "",
                    dbAction: "Create",
                    intervalDetails: Array<IServiceDetailsModel>()
                };
                this.entry.serviceInfo.push(serviceEntry);
            }
        }

        private deleteRow(divName: string, index: number, parentIndex?: number) {
            if (divName == "serviceInfo") {
                if (this.entry.serviceInfo[index].id != "") {
                    this.entry.serviceInfo[index].dbAction = "Delete";
                    this.serviceDeletedObjects.push(this.entry.serviceInfo[index]);
                }
                this.entry.serviceInfo.splice(index, 1);
            }
            else if (divName == "serviceDetails") {
                if (this.entry.serviceInfo[parentIndex].intervalDetails[index].id != "") {
                    this.entry.serviceInfo[parentIndex].intervalDetails[index].dbAction = "Delete";
                    this.serviceDetailDeletedObjects.push(this.entry.serviceInfo[parentIndex].intervalDetails[index]);
                }
                this.entry.serviceInfo[parentIndex].intervalDetails.splice(index, 1);
            }
        }

        private convertToJson(convertThis: string, obj: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
            if (convertThis === "service") {
                item['service'] = obj;
            }
            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("ServiceInformation", ServiceInformation);
}