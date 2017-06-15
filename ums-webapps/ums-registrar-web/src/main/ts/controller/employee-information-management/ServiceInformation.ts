module ums{

    interface IEmployeeServiceInformation extends ng.IScope{
        entry: IEntry;
        addNewRow: Function;
        deleteRow: Function;
        getEmployees: Function;
        getSelectedEmployee: Function;
        showRightDiv: Function;

        viewMode: boolean;
        editMode: boolean;

        departmentTypes: Array<IDepartment>;
        employees: Array<IEmployee>;
        employee: IEmployee;
        department: IDepartment;

    }

    export interface IEmployee{
        id: string;
        employeeName: string;
        deptOfficeId: string;
        designation: number;
        gender: string;
    }

    export interface IDesignation {
        id: number;
        name: string;
    }

    export interface IDepartment{
        id: number;
        longName: string;
        shortName: string;
        type: number;
    }

    export interface IRoomNo{
        id: number;
        name: string;
    }

    export interface IExtNo{
        id: number;
        name: number;
    }

    export interface IAreaOfInterest{
        id: number;
        name: string;
    }

    export interface IEmploymentType{
        id: number;
        name: string;
    }

    interface IEntry {
        service: Array<IServiceInformationModel>;
    }

    class EmployeeServiceInformation{

        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce', 'employeeService', 'serviceInformationService'];
        constructor(private registrarConstants: any,
                    private $scope: IEmployeeServiceInformation,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService,
                    private employeeService: EmployeeService,
                    private serviceInformationService: EmployeeServiceInformationService) {

            $scope.entry = {
                service: new Array<IServiceInformationModel>()
            };

            $scope.addNewRow = this.addNewRow.bind(this);
            $scope.deleteRow = this.deleteRow.bind(this);
            $scope.getEmployees = this.getEmployees.bind(this);
            $scope.getSelectedEmployee = this.getSelectedEmployee.bind(this);
            $scope.showRightDiv = this.showRightDiv.bind(this);

            $scope.viewMode = false;
            $scope.editMode = false;
            this.addNewRow();
            this.getLoggedUserInfo();
            this.getDepartment();
        }

        private getLoggedUserInfo(){
            this.employeeService.getLoggedEmployeeInfo().then((user: any) =>{
            });
        }

        private getDepartment(){
            this.serviceInformationService.getAllDepartment().then((dept: any)=> {
                this.$scope.departmentTypes = dept;
            });
        }

        private getEmployees(deptId: string){
            console.log(deptId);
            this.employeeService.getEmployees(deptId).then((employees: any) => {
                console.log(employees);
               this.$scope.employees = employees;
            });
        }

        private getSelectedEmployee(){
            console.log(this.$scope.employee);
        }

        private submit(){
            console.log("i am in submit()");
            this.convertToJson();
                // .then((json: any) => {
                //     this.employeeServiceInformationService.saveServiceInformation(json)
                //         .then((message: any) => {
                //             console.log("This is message");
                //             console.log(message);
                //         });
                // });

            //this.$scope.showPermanentAddressCheckbox = false;

        }

        private showRightDiv(){
            Utils.expandRightDiv();
            console.log("Am I really executing?");
            this.$scope.viewMode = true;
        }

        private edit() {
        }

        private addNewRow(){
            console.log("i am in addNewRow()");
            let serviceEntry: IServiceInformationModel;
            serviceEntry = {
                employeeId: "",
                designation: null,
                department: null,
                academicInitial: "",
                roomNo: null,
                extNo: null,
                areaOfInterest: null,
                employmentType: null,
                contractualStartDate: "",
                contractualEndDate: "",
                probationStartDate: "",
                probationEndDate: "",
                permanentStartDate: "",
                currentStatus: "",
                resignDate: ""
            };

            this.$scope.entry.service.push(serviceEntry);
        }

        private deleteRow(index: number) {
            console.log("i am in deleteRow()");
            this.$scope.entry.service.splice(index, 1);
        }

        private convertToJson() {
            console.log("I am in convertToJSon()");
            var defer = this.$q.defer();
            var JsonObject = {};
            var JsonArray = [];
            var item: any = {};

            var serviceInformation = new Array<IServiceInformationModel>();
            for(var i = 0; i < this.$scope.entry.service.length; i++){
                serviceInformation = this.$scope.entry.service;
            }
            item['service'] = serviceInformation;

            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;

            console.log("My Json Data");
            console.log(JsonObject);

            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("EmployeeServiceInformation", EmployeeServiceInformation);
}