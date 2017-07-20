module ums{
    interface IEmployeeServiceInformation extends ng.IScope{
        entry: IEntry;
        designations: Array<ICommon>;
        employmentTypes: Array<ICommon>;
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
            $scope.addNewRow = this.addNewRow.bind(this);
            $scope.deleteRow =this.deleteRow.bind(this);
            $scope.submit = this.submit.bind(this);
            $scope.enableEditMode = this.enableEditMode.bind(this);

            this.getDepartment();
            this.getDesignationTypes();
            this.getEmploymentTypes();
            this.getServiceIntervals();
            this.getServiceInformation();
            this.addDate();
        }

        private getServiceIntervals(): void {
            this.$scope.serviceRegularIntervals = Array<ICommon>();
            this.$scope.serviceContractIntervals = Array<ICommon>();
            this.$scope.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[0]);
            this.$scope.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[1]);
            this.$scope.serviceRegularIntervals.push(this.registrarConstants.servicePeriods[2]);
            this.$scope.serviceContractIntervals.push(this.registrarConstants.servicePeriods[3]);
        }

        private getDepartment(): void{
            this.departmentService.getAll().then((departments: any) => {
               this.$scope.departments = departments;
            });
        }

        private getEmploymentTypes(): void{
            this.employmentTypeService.getAll().then((employmentTypes: any)=>{
               this.$scope.employmentTypes = employmentTypes;
            });
        }

        private getDesignationTypes(): void{
            this.designationService.getAll().then((designations: any) => {
               this.$scope.designations = designations;
            });
        }

        private submit(): void{
            //let serviceObjects = this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.$scope.previousAwardInformation, this.$scope.entry.serviceInfo);
            this.convertToJson().then((json: any) =>{
               this.serviceInformationService.saveServiceInformation(json).then((message: any) => {
                   this.$scope.showInputDiv = false;
                   this.$scope.showLabelDiv = true;
               });
            });
        }

        private getServiceInformation(): void{
            this.serviceInformationService.getServiceInformation().then((services: any) =>{
               console.log(services);
            });
        }

        private enableEditMode(): void{
            this.$scope.showLabelDiv = false;
            this.$scope.showInputDiv = true;
        }

        private addNewRow(parameter: string, index?: number): void {
            if(parameter == "serviceInfo") {
                let serviceEntry: IServiceInformationModel;
                serviceEntry = {id: null, employeeId: "", department: null, departmentId: null, designation: null, employmentType: null, designationId: null, employmentTypeId: null,
                    joiningDate: "", resignDate: "", dbAction: "", intervalDetails: Array<IServiceDetailsModel>()
                };
                this.$scope.entry.serviceInfo.push(serviceEntry);
            }
            else if(parameter == "serviceDetails") {
                let serviceDetailsEntry: IServiceDetailsModel;
                serviceDetailsEntry = {
                    id: null, interval: null, intervalId: null, startDate: "", endDate: "", serviceId: null
                };
                this.$scope.entry.serviceInfo[index].intervalDetails.push(serviceDetailsEntry);
            }
            this.addDate();
        }

        private deleteRow(parameter: any, parentIndex: number, index: number): void{
            if(parameter == "serviceInfo") {
                this.$scope.entry.serviceInfo.splice(index, 1);
            }
            else if(parameter == "serviceDetails"){
                this.$scope.entry.serviceInfo[parentIndex].intervalDetails.splice(index, 1);
            }
        }

        private convertToJson(): ng.IPromise<any>{
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
            item['service'] = this.$scope.entry.serviceInfo;
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