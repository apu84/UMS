module ums {
    interface IApprovePublication extends ng.IScope {
        submit: Function;
        pageChanged: Function;
        resetTopBottomDivs: Function;
        publications: Array<IPublicationInformationModel>;
        teachers: Array<IEmployee>;
        showPendingPublicationDiv: boolean;
        showNoPendingPublicationListDiv: boolean;
        accept: Function;
        reject: Function;
        currentTeacher: IEmployee;
        totalPendingPublications: number;
        changePublicationList: Function;
        currentTeacherIndex: number;

        data: any;
        pagination: any;
        publicationListViewCategory: string;
        item: Array<IPublicationInformationModel>;
    }

    interface IEmployee{
        id: string;
        name: string;
        designation: string;
        employmentType: string;
        department: string;
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
            $scope.teachers = Array<IEmployee>();
            $scope.currentTeacher = <IEmployee>{};
            $scope.submit = this.submit.bind(this);
            $scope.resetTopBottomDivs = this.resetTopBottomDivs.bind(this);
            $scope.accept = this.accept.bind(this);
            $scope.reject = this.reject.bind(this);
            $scope.changePublicationList = this.changePublicationList.bind(this);
            $scope.pageChanged = this.pageChanged.bind(this);

            $scope.data ={
                publicationListViewCategory: "",
                itemPerPage: 3,
                totalRecord: 0
            };

            $scope.pagination = {};
            $scope.pagination.currentPage = 1;

            $scope.totalPendingPublications = 0;
            this.fetchTeachersList();

        }

        private submit(index: number){
            this.$scope.currentTeacherIndex = index;
            $("#teachersListDiv").hide(10);
            $("#topArrowDiv").show(200);
            $("#publicationListDiv").show(200);

            this.getPublication(this.$scope.currentTeacherIndex, '0');
        }

        private resetTopBottomDivs(){
            this.fetchTeachersList();
            $("#topArrowDiv").hide(10);
            $("#publicationListDiv").hide(10);
            $("#teachersListDiv").show(200);
        }

        private getPublication(index: number, publicationCategory:string){
            this.$scope.currentTeacher = this.$scope.teachers[index];
            this.publicationInformationService.getSpecificTeacherPublicationInformation(this.$scope.teachers[index].id, publicationCategory).then((publicationInformation: any) => {
                this.$scope.publications = publicationInformation;
                this.$scope.totalPendingPublications = this.$scope.publications.length;
                this.$scope.data.totalRecord = this.$scope.publications.length;
            });
        }

        private fetchTeachersList(){
            this.approvePublicationService.getTeachersList('0').then((teachers: any) => {
                this.$scope.teachers = teachers;
                if(teachers.length >= 1){
                    this.$scope.showNoPendingPublicationListDiv = false;
                    this.$scope.showPendingPublicationDiv = true;
                }
                else{
                    this.$scope.showPendingPublicationDiv = false;
                    this.$scope.showNoPendingPublicationListDiv = true;
                }

            });
        }

        private accept(index: number){
            $("#i"+index).hide(10);
            this.convertToJson(index, '1').then((json: any) => {
                this.approvePublicationService.updatePublicationStatus(json)
                    .then((message: any) => {
                        this.$scope.totalPendingPublications--;
                    });
            });
        }

        private reject(index: number){
            // $("#index").hide(10);
            this.convertToJson(index, '2').then((json: any) => {
                this.approvePublicationService.updatePublicationStatus(json)
                    .then((message: any) => {
                        this.$scope.totalPendingPublications--;
                    });
            });
        }

        private changePublicationList(){
            if(this.$scope.data.publicationListViewCategory == "0"){
                this.getPublication(this.$scope.currentTeacherIndex, '0')

            }

            else if(this.$scope.data.publicationListViewCategory == "1"){
                this.getPublication(this.$scope.currentTeacherIndex, '1')
            }

            else if(this.$scope.data.publicationListViewCategory == "2"){
                this.getPublication(this.$scope.currentTeacherIndex, '2')
            }
            else{
            }
        }

        private pageChanged(pageNumber: number){
            console.log("In pageChangeMehtod . . . .................");
            console.log(pageNumber);
            this.pagerService.getPager(this.$scope.publications.length, pageNumber, 3).then((pager: any) => {
                this.$scope.pagination =  pager;
                this.$scope.pagination.currentPage = pager.currentPage;
                this.$scope.data.itemPerPage = 3;
                this.$scope.data.totalRecord = this.$scope.publications.length;
                this.$scope.item = this.$scope.publications.slice(this.$scope.pagination.startIndex, this.$scope.pagination.endIndex + 1);
            });


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