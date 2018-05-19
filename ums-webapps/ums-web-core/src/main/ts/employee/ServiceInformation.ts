module ums {

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
        private enableServiceDetailEdit: boolean[] = [false];
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

        public submit(type: string, index: number, parentIndex?: number): void {
            if (type === 'service') {
                this.convertToJson(this.service[index]).then((json: any) => {
                    if (!this.service[index].id) {
                        this.create(type, json, index);
                    }
                    else {
                        this.update(type, json, index);
                    }
                });
            }
            else if (type === 'serviceDetails') {
                if (this.validateServiceDetailForm(index, parentIndex)) {
                    this.notify.error("Missing period or start date");
                }
                else {
                    this.convertToJson(this.service[parentIndex].intervalDetails[index]).then((json: any) => {
                        if (!this.service[parentIndex].intervalDetails[index].id) {
                            this.service[parentIndex].intervalDetails[index].serviceId = this.service[parentIndex].id;
                            this.create(type, json, index, parentIndex);
                        }
                        else {
                            this.update(type, json, index, parentIndex);
                        }
                    });
                }
            }
        }

        private validateServiceDetailForm(index: number, parentIndex: number): boolean {
            return (!this.service[parentIndex].intervalDetails[index].interval || !this.service[parentIndex].intervalDetails[index].startDate);
        }

        private create(type: string, json: any, index: number, parentIndex?: number) {
            if (type === 'service') {
                this.employeeInformationService.saveServiceInformation(json).then((data: any) => {
                    this.service[index] = data;
                    this.enableEdit[index] = false;
                }).catch((reason: any) => {
                    this.enableEdit[index] = true;
                });
            }
            else if (type === 'serviceDetails') {
                this.employeeInformationService.saveServiceDetailInformation(json).then((data: any) => {
                    this.service[parentIndex].intervalDetails[index] = data;
                    this.enableServiceDetailEdit[index] = false;
                }).catch((reason: any) => {
                    this.enableServiceDetailEdit[index] = true;
                });
            }
        }

        private update(type: string, json: any, index: number, parentIndex?: number) {
            if (type === 'service') {
                this.employeeInformationService.updateServiceInformation(json).then((data: any) => {
                    this.service[index] = data;
                    this.enableEdit[index] = false;
                }).catch((reason: any) => {
                    this.enableEdit[index] = true;
                });
            }
            else if (type === 'serviceDetails') {
                this.employeeInformationService.updateServiceDetailInformation(json).then((data: any) => {
                    this.service[parentIndex].intervalDetails[index] = data;
                    this.enableServiceDetailEdit[index] = false;
                }).catch((reason: any) => {
                    this.enableServiceDetailEdit[index] = true;
                });
            }
        }

        private get(): void {
            this.service = [];
            this.employeeInformationService.getServiceInformation(this.userId).then((serviceInformation: any) => {
                if (serviceInformation) {
                    this.service = serviceInformation;
                }
                else {
                    this.service = [];
                }
            });
        }

        public activeEditButton(type: string, index: number, canEdit: boolean): void {
            if (type === 'service') {
                this.enableEdit[index] = canEdit;
            }
            else if (type === 'serviceDetails') {
                this.enableServiceDetailEdit[index] = canEdit;
            }
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
                    this.employeeInformationService.deleteServiceDetailInformation(this.service[parentIndex].intervalDetails[index].id).then((data: any) => {
                        this.service[parentIndex].intervalDetails.splice(index, 1);
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
