module ums {
    interface IApprovePublication extends ng.IScope {
        submit: Function;
        resetTopBottomDivs: Function;
        publications: Array<IPublicationInformationModel>;
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

            $scope.publications = Array<IPublicationInformationModel>();

            //this.getPublication();
            this.fetchTeachersListWaitingForPublicationApproval();
        }

        private submit(){
            console.log("i am in submit11()");
            $("#teachersListWaitingForApprovalDiv").hide(10);
            $("#topArrowDiv").show(200);
            $("#waitingPublicationListDiv").show(200);
        }

        private resetTopBottomDivs(){
            $("#topArrowDiv").hide(10);
            $("#waitingPublicationListDiv").hide(10);
            $("#teachersListWaitingForApprovalDiv").show(200);
        }

        private getPublication(){
            console.log("i am in getPublicationInformation()");
            this.employeeInformationService.getPublicationInformation().then((publicationInformation: any) => {
                console.log("Employee's Publication Information");
                console.log(publicationInformation);
                // this.$scope.publications = publicationInformation;
            });
        }

        private fetchTeachersListWaitingForPublicationApproval(){
            console.log("i am in fetchTeachersListWaitingForPublicationApproval()");
            this.approvePublicationService.getTeachersList('2').then((publicationInformation: any) => {
                this.$scope.publications = publicationInformation;
                console.log("i am here");
                console.log(publicationInformation);

            });
        }
    }

    UMS.controller("ApprovePublication", ApprovePublication);
}