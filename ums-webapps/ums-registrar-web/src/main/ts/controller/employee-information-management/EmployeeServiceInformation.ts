module ums{

    interface IEmployeeServiceInformation extends ng.IScope{
        entry: IEntry;

        showInputDiv: boolean;
        showLabelDiv: boolean;
        hideRequireSpan: boolean;
        required: boolean;

        addNewRow: Function;
        deleteRow: Function;
        testData: Function;
        edit: Function;
        submit: Function;
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

            $scope.testData = this.testData.bind(this);
            $scope.submit = this.submit.bind(this);
            $scope.edit = this.edit.bind(this);
            $scope.addNewRow = this.addNewRow.bind(this);
            $scope.deleteRow = this.deleteRow.bind(this);
            $scope.showInputDiv = false;
            $scope.showLabelDiv = true;
            this.addNewRow();

            console.log("I am in EmployeeServiceInformation.ts");

        }

        private testData() {
            this.$scope.entry.service[0].employeeId = 1;
            this.$scope.entry.service[0].employeeDesignation = 1;
            this.$scope.entry.service[0].employeeEmploymentType = 1;
            this.$scope.entry.service[0].employeeDepartment = 1;
            this.$scope.entry.service[0].employeeJoiningDate = "01/11/2017";
            this.$scope.entry.service[0].employeeContractualDate = "01/11/2017";
            this.$scope.entry.service[0].employeeProbationDate = "01/11/2017";
            this.$scope.entry.service[0].employeeJobPermanentDate = "01/11/2017";
            this.$scope.entry.service[0].employeeExtensionNumber = 724;
            this.$scope.entry.service[0].employeeShortName = "mmk";
            this.$scope.entry.service[0].employeeRoomNumber = "6A03";
            this.$scope.entry.service[0].employeeResignDate = "01/11/3017";
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
            this.$scope.showInputDiv = false;
            this.$scope.showLabelDiv = true;
            this.$scope.required = true;
            //this.$scope.showPermanentAddressCheckbox = false;

        }

        private edit() {
            this.$scope.showInputDiv = true;
            this.$scope.showLabelDiv = false;
            this.$scope.hideRequireSpan = false;
        }

        private addNewRow(){
            console.log("i am in addNewRow()");
            var serviceEntry: IServiceInformationModel;
            serviceEntry = {
                employeeId: null,
                employeeDesignation: null,
                employeeEmploymentType: null,
                employeeDepartment: null,
                employeeJoiningDate: "",
                employeeContractualDate: "",
                employeeProbationDate: "",
                employeeJobPermanentDate: "",
                employeeExtensionNumber: null,
                employeeResignDate: "",
                employeeRoomNumber: "",
                employeeShortName: ""
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