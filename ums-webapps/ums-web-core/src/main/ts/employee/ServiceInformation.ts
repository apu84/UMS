module ums {

    import IServiceDetailsModel = ums.IServiceDetailsModel;
    import IServiceInformationModel = ums.IServiceInformationModel;

    class ServiceInformation {
        public static $inject = ['registrarConstants',
            '$q',
            'notify',
            'employeeInformationService',
            '$stateParams',
            'employmentTypeService',
            'departmentService',
            'designationService'
        ];

        private service: IServiceInformationModel[] = [];
        readonly userId: string = "";
        private stateParams: any;
        private designations: ICommon[] = [];
        private employmentTypes: ICommon[] = [];
        private serviceIntervals: ICommon[] = [];
        private serviceRegularIntervals: ICommon[] = [];
        private serviceContractIntervals: ICommon[] = [];
        private departments: IDepartment[] = [];
        private enableEdit: boolean[] = [false];
        private enableEditButton: boolean = false;

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private employeeInformationService: EmployeeInformationService,
                    private $stateParams: any,
                    private employmentTypeService: EmploymentTypeService,
                    private departmentService: DepartmentService,
                    private designationService: DesignationService) {

            this.service = [];
            this.stateParams = $stateParams;
            this.userId = this.stateParams.id;
            this.enableEditButton = this.stateParams.edit;
            this.departmentService.getAll().then((departments: any) => {
                this.departments = departments;
            });
            this.designationService.getAll().then((designations: any) => {
                this.designations = designations;
            });
            this.employmentTypeService.getAll().then((employmentTypes: any) => {
                this.employmentTypes = employmentTypes;
                this.getServiceIntervals();
            });

            this.get();
        }

        private getServiceIntervals(): void {
            this.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[0]);
            this.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[1]);
            this.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[2]);
            this.serviceContractIntervals.push(this.registrarConstants.servicePeriods[3]);
        }

        public submit(index: number, type: string): void {

        }

        private get(): void {
            this.service = [];
            this.employeeInformationService.getServiceInformation(this.userId).then((data: any) => {
                // this.service = data;
            });
        }

        public activeEditButton(index: number, canEdit: boolean): void {
            this.enableEdit[index] = canEdit;
        }

        private addNew(type: string, index?: number): void {
            if (type == "service") {
                if (this.service.length == 0) {
                    this.addNewService();
                }
                else {
                    if (this.service[this.service.length - 1].resignDate == "") {
                        this.notify.error("Please fill up the resign date first");
                    }
                    else {
                        this.addNewService();
                    }
                }
            }
            else if (type == "serviceDetails") {
                if (this.service[index].intervalDetails.length == 0) {
                    this.addNewServiceDetails(index);
                }
                else {
                    if (this.service[index].intervalDetails[this.service[index].intervalDetails.length - 1].endDate == "") {
                        this.notify.error("Please fill up the end date first");
                    }
                    else {
                        this.addNewServiceDetails(index);
                    }
                }
            }
        }

        private addNewServiceDetails(index: number) {
            let serviceDetailsEntry: IServiceDetailsModel;
            serviceDetailsEntry = {
                id: "",
                interval: null,
                startDate: "",
                endDate: "",
                comment: "",
                serviceId: null
            };
            this.service[index].intervalDetails.push(serviceDetailsEntry);
        }

        private addNewService() {
            let serviceEntry: IServiceInformationModel;
            serviceEntry = {
                id: "",
                employeeId: this.userId,
                department: null,
                designation: null,
                employmentType: null,
                joiningDate: "",
                resignDate: "",
                intervalDetails: []
            };
            this.service.push(serviceEntry);
        }


        public delete(type: string, index: number, parentIndex?: number): void {
            if (type === 'service') {
                if (this.service[index].id) {
                    this.employeeInformationService.deleteServiceInformation(this.service[index].id).then((data: any) => {
                        this.service.splice(index, 1);
                    });
                }
                else {
                    this.service.splice(index, 1);
                }
            }
            else if (type === 'serviceDetails') {
                if (this.service[parentIndex].intervalDetails[index].id) {
                    this.employeeInformationService.deleteServiceInformation(this.service[parentIndex].intervalDetails[index].id).then((data: any) => {
                        this.service[parentIndex].intervalDetails[index].splice(index, 1);
                    });
                }
                else {
                    this.service[parentIndex].intervalDetails.splice(index, 1);
                }
            }
        }

        private convertToJson(object: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = object;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("ServiceInformation", ServiceInformation);
}
