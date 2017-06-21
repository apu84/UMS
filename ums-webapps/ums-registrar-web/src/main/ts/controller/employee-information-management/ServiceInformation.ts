module ums{

    interface IEmployeeServiceInformation extends ng.IScope{
        entry: IEntry;
        addNewRow: Function;
        deleteRow: Function;
        getEmployees: Function;
        getSelectedEmployee: Function;
        showRightDiv: Function;
        enableViewMode: Function;
        enableEditMode: Function;
        changeViewOnEmploymentSelection: Function;

        viewMode: boolean;
        editMode: boolean;
        showRegularEmploymentDiv: boolean;
        showContractEmploymentDiv: boolean;

        departmentTypes: Array<IDepartment>;
        employees: Array<IEmployee>;
        employee: IEmployee;
        department: IDepartment;
        employmentTypes: Array<IEmploymentType>;
        designations: Array<IDesignation>;
        departments: Array<IDepartment>;
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
        designationName: string;
    }

    export interface IDepartment{
        id: number;
        longName: string;
        shortName: string;
        type: number;
    }

    export interface IEmploymentType{
        id: number;
        type: string;
    }

    interface IEntry {
        basic: IServiceBasicInformationModel;
        contractual: Array<IContractualModel>;
        probation: Array<IProbationModel>;
        permanent: Array<IPermanentModel>;
        contract: Array<IContractModel>;
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
                basic: <IServiceBasicInformationModel> {},
                contractual: Array<IContractualModel>(),
                probation: Array<IProbationModel>(),
                permanent: Array<IPermanentModel>(),
                contract: Array<IContractModel>()
            };
            $scope.addNewRow = this.addNewRow.bind(this);
            $scope.deleteRow = this.deleteRow.bind(this);
            // $scope.getEmployees = this.getEmployees.bind(this);
            // $scope.getSelectedEmployee = this.getSelectedEmployee.bind(this);
            $scope.showRightDiv = this.showRightDiv.bind(this);
            $scope.enableEditMode = this.enableEditMode.bind(this);
            $scope.enableViewMode = this.enableViewMode.bind(this);
            $scope.changeViewOnEmploymentSelection = this.changeViewOnEmploymentSelection.bind(this);

            $scope.showRegularEmploymentDiv = false;
            $scope.showContractEmploymentDiv = false;
            $scope.viewMode = false;
            $scope.editMode = true;

            this.getEmploymentTypes();
            this.getDepartments();
            this.getDesignations();
            //this.addNewRow("contract");
            // this.getLoggedUserInfo();
            // this.getDepartment();
            this.addDate();
            console.log("change me");
        }

        private getEmploymentTypes(){
            this.$scope.employmentTypes = Array<IEmploymentType>();
            this.employmentTypeService.getAll().then((types: any)=>{
               console.log(types);
               this.$scope.employmentTypes = types;
            });
        }

        private getDepartments(){
            this.$scope.departments = Array<IDepartment>();
            this.departmentService.getAll().then((types: any)=>{
                console.log(types);
                this.$scope.departments = types;
            });
        }

        private getDesignations(){
            this.$scope.designations = Array<IDesignation>();
            this.designationService.getAll().then((types: any)=>{
                console.log(types);
                this.$scope.designations = types;
            });
        }

        // private getLoggedUserInfo(){
        //     this.userService.fetchCurrentUserInfo().then((user: any) =>{
        //         console.log(user);
        //     });
        // }
        //
        // private getDepartment(){
        //     this.serviceInformationService.getAllDepartment().then((dept: any)=> {
        //         this.$scope.departmentTypes = dept;
        //     });
        // }
        //
        // private getEmployees(deptId: string){
        //     console.log(deptId);
        //     this.employeeService.getEmployees(deptId).then((employees: any) => {
        //         console.log(employees);
        //        this.$scope.employees = employees;
        //     });
        // }
        //
        // private getSelectedEmployee(){
        //     console.log(this.$scope.employee);
        // }

        private submit(){
            console.log("i am in submit()");
            //this.convertToJson();
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

        private addNewRow(divName: string){
            console.log("addNewRow(): " + divName);

            if(divName == "contractual") {
                let contractualEntry: IContractualModel;
                contractualEntry = {
                    designation: null,
                    department: null,
                    contractualStartDate: "",
                    contractualEndDate: "",
                    currentStatus: ""
                };

                this.$scope.entry.contractual.push(contractualEntry);
            }
            else if(divName == "probation") {
                let probationEntry: IProbationModel;
                probationEntry = {
                    designation: null,
                    department: null,
                    probationStartDate: "",
                    probationEndDate: "",
                    currentStatus: ""
                };

                this.$scope.entry.probation.push(probationEntry);
            }
            else if(divName == "permanent") {
                let permanentEntry: IPermanentModel;
                permanentEntry = {
                    designation: null,
                    department: null,
                    permanentStartDate: "",
                    permanentEndDate: "",
                    currentStatus: ""
                };

                this.$scope.entry.permanent.push(permanentEntry);
            }
            else if(divName == "contract") {
                let contractEntry: IContractModel;
                contractEntry = {
                    designation: null,
                    department: null,
                    contractStartDate: "",
                    contractEndDate: "",
                    currentStatus: ""
                };

                this.$scope.entry.contract.push(contractEntry);
            }
            // else if(divName == "areaOfInterest") {
            //     let areaOfInterestEntry: IAreaOfInterestModel;
            //     areaOfInterestEntry = {
            //         topic: null
            //     };
            //
            //     this.$scope.entry.areaOfInterest.push(areaOfInterestEntry);
            // }
        }

        private deleteRow(divName: string, index: number) {
            console.log("deleteRow()");
            if(divName == "contractual") {
                this.$scope.entry.contractual.splice(index, 1);
            }
            else if(divName == "probation"){
                this.$scope.entry.probation.splice(index, 1);
            }
            else if(divName == "permanent"){
                this.$scope.entry.permanent.splice(index, 1);
            }
            else if(divName == "contract"){
                this.$scope.entry.contract.splice(index, 1);
            }
            // else if(divName == "areaOfInterest"){
            //     this.$scope.entry.areaOfInterest.splice(index, 1);
            // }
        }

        private enableEditMode(){
            this.$scope.viewMode = false;
            this.$scope.editMode = true;
        }

        private enableViewMode(){
            this.$scope.editMode = false;
            this.$scope.viewMode = true;
        }

        private changeViewOnEmploymentSelection(){
            console.log(this.$scope.entry.basic.employmentType);

            if(this.$scope.entry.basic.employmentType.id == 1){
                this.$scope.showContractEmploymentDiv = false;
                this.$scope.showRegularEmploymentDiv = true;
            }
            else{
                this.$scope.showRegularEmploymentDiv = false;
                this.$scope.showContractEmploymentDiv = true;
            }

        }

        // private convertToJson() {
        //     console.log("I am in convertToJSon()");
        //     var defer = this.$q.defer();
        //     var JsonObject = {};
        //     var JsonArray = [];
        //     var item: any = {};
        //
        //     var serviceInformation = new Array<IServiceInformationModel>();
        //     for(var i = 0; i < this.$scope.entry.service.length; i++){
        //         serviceInformation = this.$scope.entry.service;
        //     }
        //     item['service'] = serviceInformation;
        //
        //     JsonArray.push(item);
        //     JsonObject['entries'] = JsonArray;
        //
        //     console.log("My Json Data");
        //     console.log(JsonObject);
        //
        //     defer.resolve(JsonObject);
        //     return defer.promise;
        // }

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