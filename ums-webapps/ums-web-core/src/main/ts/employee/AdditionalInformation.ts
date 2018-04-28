module ums {
    interface IEmployeeProfile extends ng.IScope {
        submitAdditionalForm: Function;
    }

    class AdditionalInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify',
            'countryService', 'divisionService', 'districtService', 'thanaService',
            'employeeInformationService', 'areaOfInterestService', 'userService', 'academicDegreeService',
            'cRUDDetectionService', '$stateParams', 'employmentTypeService', 'departmentService', 'designationService', 'FileUpload'];

        private entry: {
            additional: IAdditionalInformationModel
        };
        private additional: boolean = false;
        private showAdditionalInputDiv: boolean = false;
        private userId: string = "";
        private tabs: boolean = false;
        private stateParams: any;
        private isRegistrar: boolean;
        private test: boolean = true;
        private arrayOfAreaOfInterest: ICommon[] = [];

        constructor(private registrarConstants: any,
                    private $scope: IEmployeeProfile,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private countryService: CountryService,
                    private divisionService: DivisionService,
                    private districtService: DistrictService,
                    private thanaService: ThanaService,
                    private employeeInformationService: EmployeeInformationService,
                    private areaOfInterestService: AreaOfInterestService,
                    private userService: UserService,
                    private academicDegreeService: AcademicDegreeService,
                    private cRUDDetectionService: CRUDDetectionService,
                    private $stateParams: any,
                    private employmentTypeService: EmploymentTypeService,
                    private departmentService: DepartmentService,
                    private designationService: DesignationService,
                    private FileUpload: FileUpload) {


            console.log("In Employee Profile-------------");

            $scope.submitAdditionalForm = this.submitAdditionalForm.bind(this);

            this.entry = {
                additional: <IAdditionalInformationModel> {}
            };

            this.stateParams = $stateParams;
            this.initialization();
        }

        private initialization() {
            this.userService.fetchCurrentUserInfo().then((user: any) => {
                if (user.roleId == 82 || user.roleId == 81) {
                    this.isRegistrar = true;
                }
                else {
                    this.isRegistrar = false;
                }
                if (this.stateParams.id == "" || this.stateParams.id == null || this.stateParams.id == undefined) {
                    this.userId = user.employeeId;
                }
                else {
                    this.userId = this.stateParams.id;
                }
                this.areaOfInterestService.getAll().then((aois: any) => {
                    this.arrayOfAreaOfInterest = aois;
                    this.getAdditionalInformation();
                });
            });

        }

        private submitAdditionalForm(): void {
            this.entry.additional.employeeId = this.userId;
            this.convertToJson('additional', this.entry.additional)
                .then((json: any) => {
                    this.employeeInformationService.saveAdditionalInformation(json)
                        .then((message: any) => {
                            if (message == "Error") {
                            }
                            else {
                                this.getAdditionalInformation();
                                this.showAdditionalInputDiv = false;
                            }
                        });
                });
        }

        private getAdditionalInformation() {
            this.entry.additional = <IAdditionalInformationModel>{};
            this.employeeInformationService.getAdditionalInformation(this.userId).then((additional: any) => {
                this.entry.additional = additional[0];
                var intArray = new Array();
                for (var i = 0; i < additional[0].areaOfInterestInformation.length; i++) {
                    for (var j = 0; j < this.arrayOfAreaOfInterest.length; j++) {
                        if (additional[0].areaOfInterestInformation[i].id == this.arrayOfAreaOfInterest[j].id)
                            intArray.push(this.arrayOfAreaOfInterest[j].id);
                    }

                }
                $("#iAoi").val(intArray);
                $("#iAoi").trigger("change");
                //this.entry.additional.areaOfInterestInformation = intArray;

            });
        }


        private convertToJson(convertThis: string, obj: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
            if (convertThis === "additional") {
                item['additional'] = obj;
            }
            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("AdditionalInformation", AdditionalInformation);
}