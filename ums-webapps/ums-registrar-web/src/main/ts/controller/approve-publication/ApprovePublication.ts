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
        currentTeacher: IEmployee;
        totalPendingPublications: number;
        changePublicationList: Function;
        currentTeacherIndex: number;

        data: any;
        publicationListViewCategory: string;
    }

    interface IEmployee{
        id: string;
        name: string;
        designation: string;
        employmentType: string;
        department: string;
    }

    class ApprovePublication {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce', 'publicationInformationService', 'approvePublicationService'];

        constructor(private registrarConstants: any,
                    private $scope: IApprovePublication,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService,
                    private publicationInformationService: PublicationInformationService,
                    private approvePublicationService: ApprovePublicationService) {

            $scope.publications = Array<IPublicationInformationModel>();
            $scope.teachers = Array<IEmployee>();
            $scope.currentTeacher = <IEmployee>{};
            $scope.submit = this.submit.bind(this);
            $scope.resetTopBottomDivs = this.resetTopBottomDivs.bind(this);
            $scope.accept = this.accept.bind(this);
            $scope.reject = this.reject.bind(this);
            $scope.changePublicationList = this.changePublicationList.bind(this);

            $scope.data ={
                publicationListViewCategory: ""
            };

            $scope.totalPendingPublications = 0;
            this.fetchTeachersList();

        }

        private submit(index: number){
            this.$scope.currentTeacherIndex = index;
            console.log("i am in submit11()");
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
            console.log("i am in getPublicationInformation method! yee");
            this.$scope.currentTeacher = this.$scope.teachers[index];
            this.publicationInformationService.getSpecificTeacherPublicationInformation(this.$scope.teachers[index].id, publicationCategory).then((publicationInformation: any) => {
                console.log("Employee's Publication Information");
                console.log(publicationInformation);
                this.$scope.publications = publicationInformation;
                this.$scope.totalPendingPublications = this.$scope.publications.length;
                console.log("this.$scope.totalPendingPublications: " + this.$scope.totalPendingPublications);
            });
        }

        private fetchTeachersList(){
            console.log("i am in fetchTeachersList()");
            this.approvePublicationService.getTeachersList('0').then((teachers: any) => {
                this.$scope.teachers = teachers;
                console.log(this.$scope.teachers);

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
            this.convertToJson(index, '1').then((json: any) => {
                this.approvePublicationService.updatePublicationStatus(json)
                    .then((message: any) => {
                        console.log(message);
                    });
            });
        }

        private reject(index: number){
            this.convertToJson(index, '2').then((json: any) => {
                this.approvePublicationService.updatePublicationStatus(json)
                    .then((message: any) => {
                        console.log("this should be hidden");
                    });
            });
        }

        private changePublicationList(){
            if(this.$scope.data.publicationListViewCategory == "0"){
                console.log("show Pending List");
                this.getPublication(this.$scope.currentTeacherIndex, '0')

            }

            else if(this.$scope.data.publicationListViewCategory == "1"){
                console.log("show Approved List");
                this.getPublication(this.$scope.currentTeacherIndex, '1')
            }

            else if(this.$scope.data.publicationListViewCategory == "2"){
                console.log("show Rejected List");
                this.getPublication(this.$scope.currentTeacherIndex, '2')
            }
            else{
                console.log("Do Nothing: " + this.$scope.data.publicationListViewCategory);
            }
        }

        private convertToJson(index: number, status: string): ng.IPromise<any> {
            console.log("I am in convertToJSon()");
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

            console.log("My Json Data");
            console.log(JsonObject);

            defer.resolve(JsonObject);
            return defer.promise;
        }

    }

    UMS.controller("ApprovePublication", ApprovePublication);
}