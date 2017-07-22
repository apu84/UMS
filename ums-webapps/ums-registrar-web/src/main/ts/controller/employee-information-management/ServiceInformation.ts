module ums{
    interface IEmployeeServiceInformation extends ng.IScope{
        entry: IEntry;
        designations: Array<ICommon>;
        employmentTypes: Array<ICommon>;
        serviceIntervals: Array<ICommon>;
        serviceRegularIntervals: Array<ICommon>;
        serviceContractIntervals: Array<ICommon>;
        departments: Array<IDepartment>;
        previousServiceInformation: Array<IServiceInformationModel>;

        showInputDiv: boolean;
        showLabelDiv: boolean;

        addNewRow: Function;
        deleteRow: Function;
        submit: Function;
        enableEditMode: Function;
        changePeriodValues: Function;

        designationMap: any;
        departmentMap: any;
        employmentMap: any;
        employmentPeriodMap: any;
    }

    export interface IDepartment{
        id: string;
        shortName: string;
        longName: string;
        type: string;
    }

    interface IEntry{
        serviceInfo: Array<IServiceInformationModel>;
    }

    class EmployeeServiceInformation{
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce',
            'serviceInformationService', 'serviceInformationDetailService', 'employmentTypeService', 'departmentService', 'designationService', 'cRUDDetectionService'];

        constructor(private registrarConstants: any,
                    private $scope: IEmployeeServiceInformation,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService,
                    private serviceInformationService: ServiceInformationService,
                    private serviceInformationDetailService: ServiceInformationDetailService,
                    private employmentTypeService: EmploymentTypeService,
                    private departmentService: DepartmentService,
                    private designationService: DesignationService,
                    private cRUDDetectionService: CRUDDetectionService) {

            $scope.showInputDiv = false;
            $scope.showLabelDiv = true;
            $scope.entry = {
                serviceInfo: Array<IServiceInformationModel>()
            };
            $scope.serviceIntervals = registrarConstants.servicePeriods;
            $scope.addNewRow = this.addNewRow.bind(this);
            $scope.deleteRow =this.deleteRow.bind(this);
            $scope.submit = this.submit.bind(this);
            $scope.enableEditMode = this.enableEditMode.bind(this);
            this.initialization();
            this.addDate();

        }

        private initialization() {
            this.departmentService.getAll().then((departments: any) => {
                this.$scope.departments = departments;
                this.designationService.getAll().then((designations: any) => {
                    this.$scope.designations = designations;
                    this.employmentTypeService.getAll().then((employmentTypes: any)=>{
                        this.$scope.employmentTypes = employmentTypes;
                        this.createMap();
                        this.getServiceIntervals();
                        //this.getServiceInformation();
                    });
                });
            });
        }

        private getServiceIntervals(): void {
            this.$scope.serviceRegularIntervals = Array<ICommon>();
            this.$scope.serviceContractIntervals = Array<ICommon>();
            this.$scope.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[0]);
            this.$scope.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[1]);
            this.$scope.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[2]);
            this.$scope.serviceContractIntervals.push(this.registrarConstants.servicePeriods[3]);
        }

        private submit(): void{
            let countEmptyResignDate = 0;
            for(let i = 0; i < this.$scope.entry.serviceInfo.length; i++){
                if(this.$scope.entry.serviceInfo[i].resignDate == "" || this.$scope.entry.serviceInfo[i].resignDate == null){
                    countEmptyResignDate++;
                }
            }
            if(countEmptyResignDate > 1){
                this.notify.error("You can empty only one resign date");
            }
            else {
                // let serviceObjects = this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousServiceInformation, this.$scope.entry.serviceInfo);
                // this.convertToJson(serviceObjects).then((json: any) => {
                //     console.log(json);
                    // this.serviceInformationService.saveServiceInformation(json).then((message: any) => {
                    //     this.$scope.showInputDiv = false;
                    //     this.$scope.showLabelDiv = true;
                    // });
              //  });
            }
        }

        private getServiceInformation(): void{
            this.$scope.entry.serviceInfo = Array<IServiceInformationModel>();
            this.serviceInformationService.getServiceInformation("33333").then((services: any) =>{
               for(let i = 0; i < services.length; i++){
                   this.$scope.entry.serviceInfo[i] = services[i];
                   this.$scope.entry.serviceInfo[i].department = this.$scope.departmentMap[services[i].departmentId];
                   this.$scope.entry.serviceInfo[i].designation = this.$scope.designationMap[services[i].designationId];
                   this.$scope.entry.serviceInfo[i].employmentType = this.$scope.employmentMap[services[i].employmentId];
                   for(let j = 0; j < services[i].intervalDetails.length; j++) {
                       this.$scope.entry.serviceInfo[i].intervalDetails[j] = services[i].intervalDetails[j];
                       this.$scope.entry.serviceInfo[i].intervalDetails[j].interval = this.$scope.employmentPeriodMap[services[i].intervalDetails[j].intervalId];
                   }
               }
               this.$scope.previousServiceInformation = angular.copy(this.$scope.entry.serviceInfo);
            });
        }

        private createMap() {
            this.$scope.designationMap = {};
            this.$scope.departmentMap = {};
            this.$scope.employmentMap = {};
            this.$scope.employmentPeriodMap = {};

            for (let i = 0; i < this.$scope.designations.length; i++) {
                this.$scope.designationMap[this.$scope.designations[i].id] = this.$scope.designations[i];
            }
            for (let i = 0; i < this.$scope.departments.length; i++) {
                this.$scope.departmentMap[this.$scope.departments[i].id] = this.$scope.departments[i];
            }
            for (let i = 0; i < this.$scope.employmentTypes.length; i++) {
                this.$scope.employmentMap[this.$scope.employmentTypes[i].id] = this.$scope.employmentTypes[i];
            }
            for (let i = 0; i < this.$scope.serviceIntervals.length; i++) {
                this.$scope.employmentPeriodMap[this.$scope.serviceIntervals[i].id] = this.$scope.serviceIntervals[i];
            }
        }

        private enableEditMode(): void{
            this.$scope.showLabelDiv = false;
            this.$scope.showInputDiv = true;
        }

        private addNewRow(parameter: string, index?: number): void {
            if(parameter == "serviceInfo") {
                if(this.$scope.entry.serviceInfo.length == 0){
                    this.addNewServiceRow();
                }
                else {
                    if(this.$scope.entry.serviceInfo[this.$scope.entry.serviceInfo.length - 1].resignDate == ""){
                        this.notify.error("Please fill up the resign date first");
                    }
                    else {
                        this.addNewServiceRow();

                    }
                }
            }
            else if(parameter == "serviceDetails") {
                if(this.$scope.entry.serviceInfo[index].intervalDetails.length == 0) {
                    this.addNewServiceDetailsRow(index);
                }
                else{
                    if(this.$scope.entry.serviceInfo[index].intervalDetails[this.$scope.entry.serviceInfo[index].intervalDetails.length - 1].endDate == ""){
                        this.notify.error("Please fill up the end date first");
                    }
                    else{
                        this.addNewServiceDetailsRow(index);
                    }
                }
            }
            this.addDate();
        }

        private addNewServiceDetailsRow(index: number) {
            let serviceDetailsEntry: IServiceDetailsModel;
            serviceDetailsEntry = {
                id: null, interval: null, intervalId: null, startDate: "", endDate: "", serviceId: null
            };
            this.$scope.entry.serviceInfo[index].intervalDetails.push(serviceDetailsEntry);
        }

        private addNewServiceRow() {
            let serviceEntry: IServiceInformationModel;
            serviceEntry = {
                id: null,
                employeeId: "",
                department: null,
                departmentId: null,
                designation: null,
                employmentType: null,
                designationId: null,
                employmentId: null,
                joiningDate: "",
                resignDate: "",
                dbAction: "Create",
                intervalDetails: Array<IServiceDetailsModel>()
            };
            this.$scope.entry.serviceInfo.push(serviceEntry);
        }

        private deleteRow(parameter: any, parentIndex: number, index: number): void{
            if(parameter == "serviceInfo") {
                this.$scope.entry.serviceInfo.splice(index, 1);
            }
            else if(parameter == "serviceDetails"){
                this.$scope.entry.serviceInfo[parentIndex].intervalDetails.splice(index, 1);
            }
        }

        private convertToJson(obj: Array<IServiceInformationModel>): ng.IPromise<any>{
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
            item['service'] = obj;
            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;
            defer.resolve(JsonObject);
            return defer.promise;
        }

        private addDate(): void {
            setTimeout(function () {
                $('.datepicker-default').datepicker();
                $('.datepicker-default').on('change', function () {
                    $('.datepicker').hide();
                });
            }, 100);
        }
    }
    UMS.controller("EmployeeServiceInformation", EmployeeServiceInformation);
}