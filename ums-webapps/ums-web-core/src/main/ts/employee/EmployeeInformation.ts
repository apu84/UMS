module ums {
    interface IEmployeeInformation extends ng.IScope {
        getEmployees: Function;
        filterd: Array<Employee>;
    }

    class EmployeeInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', 'departmentService', 'employeeService', 'employeeInformationService', '$state', 'allUsers'];
        private searchBy: string = "";
        private changedUserName: string = "";
        private showSearchByUserId: boolean = false;
        private showSearchByUserName: boolean = false;
        private showSearchByDepartment: boolean = false;
        private showListOfEmployeesPanel: boolean = true;
        private showEmployeeProfilePanel: boolean = false;
        private changedDepartment: IDepartment;
        private allUser: Array<Employee>;
        private departments: IDepartment[] = [];
        private employee: Employee;
        private showInformationPanel: boolean = false;
        private indexValue: number = 0;
        private changedUserId: string = "";
        private currentPageNumber: number = 1;
        private itemsPerPage: number = 10;
        private totalItemsNumber: number = 0;
        private enablePreviousButton: boolean = false;
        private enableNextButton: boolean = false;
        private state: any;

        constructor(private registrarConstants: any,
                    private $scope: IEmployeeInformation,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private departmentService: DepartmentService,
                    private employeeService: EmployeeService,
                    private employeeInformationService: EmployeeInformationService,
                    private $state: any,
                    private allUsers: any) {

            this.state = $state;
            $scope.getEmployees = this.getEmployees.bind(this);
            this.allUser = allUsers;
            this.totalItemsNumber = this.allUsers.length;
            this.initialization();
        }

        private initialization() {
            this.departmentService.getAll().then((departments: any) => {
                this.departments = departments;
            });
        }

        private view(user: any, index?: number): void {
            this.employee = <Employee>{};
            this.employee = user;
            this.indexValue = index;
            this.checkPreviousAndNextButtons();
            this.showListOfEmployeesPanel = false;
            this.showEmployeeProfilePanel = true;
            this.showInformationPanel = true;
            Utils.expandRightDiv();
            console.log("In Employee Information.........................");
            console.log(this.employee.id);
            this.state.go("employeeInformation.employeeProfile", {id: this.employee.id});
        }

        private checkPreviousAndNextButtons(): void {
            if (this.indexValue <= 0) {
                this.enablePreviousButton = false;
            }
            else {
                this.enablePreviousButton = true;
            }
            if (this.indexValue >= this.$scope.filterd.length - 1) {
                this.enableNextButton = false;
            }
            else {
                this.enableNextButton = true;
            }
        }

        private showSearchByField(): void {
            this.employee = <Employee>{};
            if (this.searchBy == "1") {
                this.showSearchByUserName = false;
                this.showSearchByDepartment = false;
                this.showListOfEmployeesPanel = false;
                this.showInformationPanel = false;
                this.enablePreviousButton = false;
                this.enableNextButton = false;
                this.changedUserName = "";
                this.changedDepartment = null;
                this.showSearchByUserId = true;
                this.showEmployeeProfilePanel = true;
                this.changedUserId = "";
            }
            else if (this.searchBy == "2") {
                this.showSearchByUserId = false;
                this.showSearchByDepartment = false;
                this.showEmployeeProfilePanel = false;
                this.changedUserId = "";
                this.changedDepartment = null;
                this.showSearchByUserName = true;
                this.showListOfEmployeesPanel = true;
                this.changedUserName = "";
            }
            else if (this.searchBy == "3") {
                this.changedUserName = "";
                this.changedUserId = "";
                this.showSearchByUserId = false;
                this.showSearchByUserName = false;
                this.showEmployeeProfilePanel = false;
                this.showListOfEmployeesPanel = true;
                this.showSearchByDepartment = true;
            }
        }

        private getEmployees(): void {
            this.employee = <Employee>{};
            if (this.searchBy == "1") {
                if (this.findUser() == true) {
                    this.view(this.employee);
                    this.enablePreviousButton = false;
                    this.enableNextButton = false;
                }
                else {
                    this.notify.error("No User Found");
                }
            }
            else if (this.searchBy == "2") {
                this.showInformationPanel = false;
                this.showEmployeeProfilePanel = false;
                this.showListOfEmployeesPanel = true;
                if (this.changedUserName != null || this.changedUserName != "") {
                    Utils.expandRightDiv();
                }
                else {
                    this.notify.error("User name field is empty");
                }
            }
            else if (this.searchBy == "3") {
                this.showInformationPanel = false;
                this.showEmployeeProfilePanel = false;
                this.showListOfEmployeesPanel = true;
                Utils.expandRightDiv();
            }
        }

        private findUser(): boolean {
            for (let i = 0; i < this.allUser.length; i++) {
                if (this.allUser[i].id === this.changedUserId) {
                    this.employee = <Employee> {};
                    this.employee = this.allUser[i];
                    return true;
                }
            }
            return false;
        }

        private previous(): void {
            this.indexValue = this.indexValue - 1;
            this.view(this.$scope.filterd[this.indexValue], this.indexValue);
        }

        private next(): void {
            this.indexValue = this.indexValue + 1;
            this.view(this.$scope.filterd[this.indexValue], this.indexValue);
        }

        private downloadPdf(userId: string) {
            this.employeeInformationService.getEmployeeCV(userId);
        }
    }

    UMS.controller("EmployeeInformation", EmployeeInformation);
}