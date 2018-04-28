module ums {
    interface IEmployeeProfile extends ng.IScope {
        submitAwardForm: Function;
    }

    class AwardInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify',
            'countryService', 'divisionService', 'districtService', 'thanaService',
            'employeeInformationService', 'areaOfInterestService', 'userService', 'academicDegreeService',
            'cRUDDetectionService', '$stateParams', 'employmentTypeService', 'departmentService', 'designationService', 'FileUpload'];

        private entry: {
            award: IAwardInformationModel[],
        };
        private award: boolean = false;
        private showAwardInputDiv: boolean = false;
        private previousAwardInformation: IAwardInformationModel[];
        private awardDeletedObjects: IAwardInformationModel[];
        private userId: string = "";
        private tabs: boolean = false;
        private stateParams: any;
        private isRegistrar: boolean;
        private test: boolean = true;

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


            console.log("In Award Profile-------------");

            $scope.submitAwardForm = this.submitAwardForm.bind(this);

            this.entry = {
                award: Array<IAwardInformationModel>()
            };

            this.stateParams = $stateParams;
            this.initialization();
        }

        private initialization() {
            this.userService.fetchCurrentUserInfo().then((user: any) => {
                if(user.roleId == 82 || user.roleId == 81){
                    this.isRegistrar = true;
                }
                else{
                    this.isRegistrar = false;
                }
                if (this.stateParams.id == "" || this.stateParams.id == null || this.stateParams.id == undefined) {
                    this.userId = user.employeeId;
                }
                else {
                    this.userId = this.stateParams.id;
                }

                this.getAwardInformation();
            });
        }


        private submitAwardForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.previousAwardInformation, angular.copy(this.entry.award), this.awardDeletedObjects)
                .then((awardObjects: any) => {
                    this.convertToJson('award', awardObjects)
                        .then((json: any) => {
                            this.employeeInformationService.saveAwardInformation(json)
                                .then((message: any) => {
                                    if(message == "Error"){}
                                    else {
                                        this.getAwardInformation();
                                        this.showAwardInputDiv = false;
                                    }
                                });
                        });
                });

        }

        private getAwardInformation() {
            this.entry.award = [];
            this.awardDeletedObjects = [];
            this.employeeInformationService.getAwardInformation(this.userId).then((awardInformation: any) => {
                this.entry.award = awardInformation;
            }).then(() => {
                this.previousAwardInformation = angular.copy(this.entry.award);
            });
        }



        private addNewRow(divName: string) {
            if (divName === 'award') {
                let awardEntry: IAwardInformationModel;
                awardEntry = {
                    id: "",
                    employeeId: this.userId,
                    awardName: "",
                    awardInstitute: "",
                    awardedYear: null,
                    awardShortDescription: "",
                    dbAction: "Create"
                };
                this.entry.award.push(awardEntry);
            }
        }

        private deleteRow(divName: string, index: number, parentIndex?: number) {
            if (divName === 'award') {
                if (this.entry.award[index].id != "") {
                    this.entry.award[index].dbAction = "Delete";
                    this.awardDeletedObjects.push(this.entry.award[index]);
                }
                this.entry.award.splice(index, 1);
            }
        }

        private convertToJson(convertThis: string, obj: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
            if (convertThis === "award") {
                item['award'] = obj;
            }
            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;
            defer.resolve(JsonObject);
            return defer.promise;
        }

    }

    UMS.controller("AwardInformation", AwardInformation);
}