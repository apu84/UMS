module ums{

    interface IEmployeeServiceInformation extends ng.IScope{
        entry: IEntry;
        addNewRow: Function;
        deleteRow: Function;

        viewMode: boolean;
        editMode: boolean;
    }

    export interface IDesignation {
        id: number;
        name: string;
    }

    export interface IDepartment{
        id: number;
        name: string;
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

        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce', 'employeeServiceInformationService'];
        constructor(private registrarConstants: any,
                    private $scope: IEmployeeServiceInformation,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService,
                    private employeeServiceInformationService: EmployeeServiceInformationService) {

            $scope.entry = {
                service: new Array<IServiceInformationModel>()
            };

            $scope.addNewRow = this.addNewRow.bind(this);
            $scope.deleteRow = this.deleteRow.bind(this);

            $scope.viewMode = true;
            $scope.editMode = false;
            this.addNewRow();
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