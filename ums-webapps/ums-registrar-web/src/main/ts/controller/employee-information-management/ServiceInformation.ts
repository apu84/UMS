module ums{

    interface IEmployeeServiceInformation extends ng.IScope{
        entry: IEntry;
        designations: Array<ICommon>;
        employmentTypes: Array<ICommon>;
        serviceIntervals: Array<ICommon>;

        addNewRow: Function;
        deleteRow: Function;
    }

    interface IEntry{
        serviceInfo: Array<IServiceInformationModel>;
    }

    class EmployeeServiceInformation{
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce', 'serviceInformationService', 'employmentTypeService', 'departmentService', 'designationService'];
        constructor(private registrarConstants: any,
                    private $scope: IEmployeeServiceInformation,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService,
                    private serviceInformationService: EmployeeServiceInformationService,
                    private employmentTypeService: EmploymentTypeService,
                    private departmentService: DepartmentService,
                    private designationService: DesignationService) {

            $scope.entry = {
                serviceInfo: Array<IServiceInformationModel>()
            };

            $scope.serviceIntervals = this.registrarConstants.servicePeriods;

            $scope.addNewRow = this.addNewRow.bind(this);
            $scope.deleteRow =this.deleteRow.bind(this);
            this.getEmploymentTypes();
            this.getDesignationTypes();
            this.addDate();
        }

        private getEmploymentTypes(){
            this.employmentTypeService.getAll().then((employmentTypes: any)=>{
               this.$scope.employmentTypes = employmentTypes;
            });
        }

        private getDesignationTypes(){
            this.designationService.getAll().then((designations: any) => {
               this.$scope.designations = designations;
            });
        }

        private addNewRow(parameter: string, index?: number) {
            if(parameter == "serviceInfo") {
                let serviceEntry: IServiceInformationModel;
                serviceEntry = {
                    employeeId: "",
                    designation: null,
                    employmentType: null,
                    designationId: null,
                    employmentTypeId: null,
                    joiningDate: "",
                    resignDate: "",
                    roomNo: "",
                    extNo: "",
                    academicInitial: "",
                    intervalDetails: Array<IServiceDetailsModel>()
                };
                this.$scope.entry.serviceInfo.push(serviceEntry);
            }

            else if(parameter == "serviceDetails") {
                let serviceDetailsEntry: IServiceDetailsModel;
                serviceDetailsEntry = {
                    interval: null,
                    intervalId: null,
                    startDate: "",
                    endDate: "",
                    expEndDate: ""
                };
                this.$scope.entry.serviceInfo[index].intervalDetails.push(serviceDetailsEntry);
                console.log(this.$scope.entry.serviceInfo);
            }
            this.addDate();
        }

        private deleteRow(parameter: any, index: number) {
            this.$scope.entry.serviceInfo.splice(index, 1);
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