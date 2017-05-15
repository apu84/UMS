module ums {
    interface IApprovePublication extends ng.IScope {
        submit: Function;
        resetTopBottomDivs: Function;
        publications: Array<IPublicationInformationModel>;
        teachers: Array<IEmployee>;
        showPendingPublicationDiv: boolean;
        showNoPendingPublicationListDiv: boolean;
        accept: Function;
        reject: Function;
    }

    interface IEmployee{
        id: string;
        name: string;
        designation: string;
        employmentType: string;
        department: string;
    }

    class ApprovePublication {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce', 'employeeInformationService', 'approvePublicationService'];

        constructor(private registrarConstants: any,
                    private $scope: IApprovePublication,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService,
                    private employeeInformationService: EmployeeInformationService,
                    private approvePublicationService: ApprovePublicationService) {
            $scope.submit = this.submit.bind(this);
            $scope.resetTopBottomDivs = this.resetTopBottomDivs.bind(this);
            $scope.accept = this.accept.bind(this);
            $scope.reject = this.reject.bind(this);

            $scope.publications = Array<IPublicationInformationModel>();
            $scope.teachers = Array<IEmployee>();
            this.fetchTeachersListWaitingForPublicationApproval();
        }

        private submit(index: number){
            console.log("i am in submit11()");
            $("#teachersListWaitingForApprovalDiv").hide(10);
            $("#topArrowDiv").show(200);
            $("#waitingPublicationListDiv").show(200);

            this.getPublication(index);
        }

        private resetTopBottomDivs(){
            $("#topArrowDiv").hide(10);
            $("#waitingPublicationListDiv").hide(10);
            $("#teachersListWaitingForApprovalDiv").show(200);
        }

        private getPublication(index: number){
            console.log("i am in getPublicationInformation()");
            this.employeeInformationService.getSpecificTeacherPublicationInformation(this.$scope.teachers[index].id).then((publicationInformation: any) => {
                console.log("Employee's Publication Information");
                console.log(publicationInformation);
                this.$scope.publications = publicationInformation;

            });
        }

        private fetchTeachersListWaitingForPublicationApproval(){
            console.log("i am in fetchTeachersListWaitingForPublicationApproval()");
            this.approvePublicationService.getTeachersList('2').then((teachers: any) => {
                this.$scope.teachers = teachers;
                console.log(this.$scope.teachers);

                if(teachers.length >= 1){
                    this.$scope.showPendingPublicationDiv = true;
                }
                else{
                    this.$scope.showNoPendingPublicationListDiv = true;
                }

            });
        }

        private accept(index: number){
            this.convertToJson(index).then((json: any) => {
                this.approvePublicationService.updatePublicationStatus(json)
                    .then((message: any) => {
                        console.log("This is message");
                        console.log(message);
                    });
            });
        }

        private reject(){

        }

        public convertToJson(index: number): ng.IPromise<any> {
            console.log("I am in convertToJSon()");
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};

            let publicationInformation = <IPublicationInformationModel>{};

            publicationInformation = this.$scope.publications[index];

            item['publication'] = publicationInformation;

            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;

            console.log("My Json Data");
            console.log(JsonObject);

            defer.resolve(JsonObject);
            return defer.promise;
        }

    }

    UMS.controller("ApprovePublication", ApprovePublication);
}