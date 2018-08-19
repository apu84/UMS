module ums {
    interface IEmployeePublication extends ng.IScope {
        showPendingPublicationDiv: boolean;
        showNoPendingPublicationListDiv: boolean;
        showNothingDiv: boolean;
        showActionTakenDate: boolean;
        showActionButtons: boolean;
        showRevertOptions: boolean;

        currentlySelectedEmployeeIndex: number;
        currentPublicationIndex: number;
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
        changePublicationViewCategory: Function;
        revertPublicationStatusToPending: Function;
        getPublicationIndex: Function;
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

    class EmployeePublication {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce', 'employeeInformationService', 'approvePublicationService'];

        constructor(private registrarConstants: any,
                    private $scope: IEmployeePublication,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService,
                    private employeeInformationService: EmployeeInformationService,
                    private approvePublicationService: ApprovePublicationService) {

            $scope.item = Array<IPublicationInformationModel>();
            $scope.publications = Array<IPublicationInformationModel>();
            $scope.employees = Array<IEmployee>();
            $scope.currentlySelectedEmployee = <IEmployee>{};
            $scope.pagination = {};
            $scope.pagination.currentPage = 1;
            $scope.totalPendingPublications = null;
            $scope.data = { publicationListViewCategory: '0', itemPerPage: 3, totalRecord: null };
            $scope.showActionTakenDate = false;
            $scope.showActionButtons = true;
            $scope.showNothingDiv = false;
            $scope.showRevertOptions = false;

            $scope.resetTopBottomDivs = this.resetTopBottomDivs.bind(this);
            $scope.view = this.view.bind(this);
            $scope.accept = this.accept.bind(this);
            $scope.reject = this.reject.bind(this);
            $scope.changePublicationViewCategory = this.changePublicationViewCategory.bind(this);
            $scope.pageChanged = this.pageChanged.bind(this);
            $scope.revertPublicationStatusToPending = this.revertPublicationStatusToPending.bind(this);
            $scope.getPublicationIndex = this.getPublicationIndex.bind(this);

            this.fetchTeachersList();

        }

        private view(selectedEmployeeIndex: number){
            this.$scope.data.publicationListViewCategory = '0';
            this.$scope.currentlySelectedEmployeeIndex = selectedEmployeeIndex;
            this.$scope.currentlySelectedEmployee = this.$scope.employees[this.$scope.currentlySelectedEmployeeIndex];
            this.getPublicationList();
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

        private getPublicationList(){
            this.employeeInformationService.getSpecificTeacherPublicationInformation(this.$scope.employees[this.$scope.currentlySelectedEmployeeIndex].id, this.$scope.data.publicationListViewCategory)
                .then((publicationForLength: any) => {
                    this.$scope.data.totalRecord = publicationForLength.length;
                    this.$scope.publications = Array<IPublicationInformationModel>();
                    if(this.$scope.data.totalRecord == 0){
                        console.log(this.$scope.data.totalRecord);
                        this.$scope.showNothingDiv = true;
                    }
                    else {
                        this.$scope.showNothingDiv = false;
                        this.publicationWithPagination();
                    }
                });
        }

        private publicationWithPagination() {
            this.employeeInformationService.getPublicationInformationWithPagination(this.$scope.employees[this.$scope.currentlySelectedEmployeeIndex].id,
                this.$scope.data.publicationListViewCategory, this.$scope.pagination.currentPage, this.$scope.data.itemPerPage).then((publicationInformation: any) => {
                this.$scope.publications = publicationInformation;
                this.$scope.totalPendingPublications = this.$scope.publications.length;
            });
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

        private pending(index: number){
            const rejectStatus: string = '0';
            this.convertToJson(index, rejectStatus).then((json: any) => {
                this.approvePublicationService.updatePublicationStatus(json)
                    .then((message: any) => {
                        this.getPublicationList();
                        this.$scope.totalPendingPublications--;
                    });
            });
        }

        private accept(index: number){
            const acceptStatus: string = '1';
            this.convertToJson(index, acceptStatus).then((json: any) => {
                this.approvePublicationService.updatePublicationStatus(json)
                    .then((message: any) => {
                        this.getPublicationList();
                        this.$scope.totalPendingPublications--;
                    });
            });
        }

        private reject(index: number){
            const rejectStatus: string = '2';
            this.convertToJson(index, rejectStatus).then((json: any) => {
                this.approvePublicationService.updatePublicationStatus(json)
                    .then((message: any) => {
                        this.getPublicationList();
                        this.$scope.totalPendingPublications--;
                    });
            });
        }

        private changePublicationViewCategory() {
            this.getPublicationList();
            this.modifyViewOnDifferentPublicationListCategory();
        }

        private modifyViewOnDifferentPublicationListCategory() {
            if (this.$scope.data.publicationListViewCategory == '1' || this.$scope.data.publicationListViewCategory == '2') {
                this.$scope.showActionButtons = false;
                this.$scope.showActionTakenDate = true;
                this.$scope.showRevertOptions = true;
            }
            else {
                this.$scope.showActionTakenDate = false;
                this.$scope.showRevertOptions = false;
                this.$scope.showActionButtons = true;
            }
        }

        private pageChanged(pageNumber: number){
            this.$scope.pagination.currentPage = pageNumber;
            this.getPublicationList();
        }

        private getPublicationIndex(index: number){
            this.$scope.currentPublicationIndex = index;
        }

        private revertPublicationStatusToPending(){
            this.pending(this.$scope.currentPublicationIndex);
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

    UMS.controller("EmployeePublication", EmployeePublication);
}