module ums {
    interface IApprovePublication extends ng.IScope {
        showPendingPublicationDiv: boolean;
        showNoPendingPublicationListDiv: boolean;
        showActionTakenDate: boolean;
        showActionButtons: boolean;

        currentlySelectedEmployeeIndex: number;
        totalPendingPublications: number;

        publicationListViewCategory: string;

        data: any;
        pagination: any;

        publications: Array<IPublicationInformationModel>;
        employees: Array<IEmployee>;
        item: Array<IPublicationInformationModel>;
        currentlySelectedEmployee: IEmployee;

        resetTopBottomDivs: Function;
        view: Function;
        pageChanged: Function;
        accept: Function;
        reject: Function;
        changePublicationList: Function;
    }

    interface IEmployee{
        id: string;
        name: string;
        designation: string;
        employmentType: string;
        department: string;
        // mobileNo: string;
        // extNo: string;
        // roomNo: string;
    }

    class ApprovePublication {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce', 'publicationInformationService', 'approvePublicationService', 'pagerService'];

        constructor(private registrarConstants: any,
                    private $scope: IApprovePublication,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService,
                    private publicationInformationService: PublicationInformationService,
                    private approvePublicationService: ApprovePublicationService,
                    private pagerService: PagerService) {

            $scope.item = Array<IPublicationInformationModel>();
            $scope.publications = Array<IPublicationInformationModel>();
            $scope.employees = Array<IEmployee>();
            $scope.currentlySelectedEmployee = <IEmployee>{};
            $scope.pagination = {};
            $scope.pagination.currentPage = 1;
            $scope.totalPendingPublications = 0;
            $scope.data = { publicationListViewCategory: "", itemPerPage: 3, totalRecord: 5 };
            $scope.showActionTakenDate = false;
            $scope.showActionButtons = true;

            $scope.resetTopBottomDivs = this.resetTopBottomDivs.bind(this);
            $scope.view = this.view.bind(this);
            $scope.accept = this.accept.bind(this);
            $scope.reject = this.reject.bind(this);
            $scope.changePublicationList = this.changePublicationList.bind(this);
            $scope.pageChanged = this.pageChanged.bind(this);

            this.fetchTeachersList();

        }

        private view(selectedEmployeeIndex: number){
            const showPendingPublicationByDefault: string = '0';
            this.$scope.currentlySelectedEmployeeIndex = selectedEmployeeIndex;
            this.$scope.currentlySelectedEmployee = this.$scope.employees[this.$scope.currentlySelectedEmployeeIndex];
            this.getPublicationList(showPendingPublicationByDefault);
            $("#teachersListDiv").hide(10);
            $("#publicationListDiv").show(200);
            $("#topArrowDiv").show(200);
        }

        private resetTopBottomDivs(){
            this.fetchTeachersList();
            $("#topArrowDiv").hide(10);
            $("#publicationListDiv").hide(10);
            $("#teachersListDiv").show(200);
        }

        private getPublicationList(publicationStatus: string){
            this.publicationInformationService.getPublicationInformationWithPagination(this.$scope.employees[this.$scope.currentlySelectedEmployeeIndex].id,
                publicationStatus, this.$scope.pagination.currentPage, this.$scope.data.itemPerPage).then((publicationInformation: any) => {
                    this.$scope.publications = publicationInformation;
                    this.$scope.totalPendingPublications = this.$scope.publications.length;
                    this.$scope.data.totalRecord = this.$scope.publications.length;
                });
            console.log("totalItems: " + this.$scope.data.totalRecord);
        }

        private fetchTeachersList(){
            const publicationIsWaitingForApprovalStatus: string = '0';
            this.approvePublicationService.getTeachersList(publicationIsWaitingForApprovalStatus).then((employees: any) => {
                this.$scope.employees = employees;
                this.showTeachersListOrNoPendingDiv(employees);
            });
        }

        private showTeachersListOrNoPendingDiv(employees: any) {
            if (employees.length >= 1) {
                this.$scope.showNoPendingPublicationListDiv = false;
                this.$scope.showPendingPublicationDiv = true;
            }
            else {
                this.$scope.showPendingPublicationDiv = false;
                this.$scope.showNoPendingPublicationListDiv = true;
            }
        }

        private accept(index: number){
            const acceptStatus: string = '1';
            this.convertToJson(index, acceptStatus).then((json: any) => {
                this.approvePublicationService.updatePublicationStatus(json)
                    .then((message: any) => {
                        this.$scope.totalPendingPublications--;
                    });
            });
        }

        private reject(index: number){
            const rejectStatus: string = '2';
            this.convertToJson(index, rejectStatus).then((json: any) => {
                this.approvePublicationService.updatePublicationStatus(json)
                    .then((message: any) => {
                        this.$scope.totalPendingPublications--;
                    });
            });
        }

        private changePublicationList() {
            this.getPublicationList(this.$scope.data.publicationListViewCategory);
            if(this.$scope.data.publicationListViewCategory == '1' || this.$scope.data.publicationListViewCategory == '2'){
                this.$scope.showActionTakenDate = true;
                this.$scope.showActionButtons = false;
            }
            else{
                this.$scope.showActionButtons = true;
                this.$scope.showActionTakenDate = false;
            }
        }

        private pageChanged(pageNumber: number){
            this.$scope.pagination.currentPage = pageNumber;
            this.getPublicationList(this.$scope.publicationListViewCategory);
        }

        private convertToJson(index: number, status: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
            let publicationInformation = <IPublicationInformationModel>{};

            publicationInformation = this.$scope.publications[index];
            item['publication'] = publicationInformation;
            item['publication']['status'] = status;
            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;
            defer.resolve(JsonObject);
            return defer.promise;
        }

    }

    UMS.controller("ApprovePublication", ApprovePublication);
}