module ums {
    interface IEmployeeProfile extends ng.IScope {
        submitAcademicForm: Function;
    }

    class EducationInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify',
            'countryService', 'divisionService', 'districtService', 'thanaService',
            'employeeInformationService', 'areaOfInterestService', 'userService', 'academicDegreeService',
            'cRUDDetectionService', '$stateParams', 'employmentTypeService', 'departmentService', 'designationService', 'FileUpload'];

        private entry: {
            academic: IAcademicInformationModel[]
        };
        private academic: boolean = false;
        private showAcademicInputDiv: boolean = false;
        private degreeNames: IAcademicDegreeTypes[] = [];
        private previousAcademicInformation: IAcademicInformationModel[];
        private academicDeletedObjects: IAcademicInformationModel[];
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


            console.log("In Academic Profile-------------");


            $scope.submitAcademicForm = this.submitAcademicForm.bind(this);

            this.entry = {
                academic: Array<IAcademicInformationModel>()
            }

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
                this.academicDegreeService.getAcademicDegreeList().then((degree: any) => {
                    this.degreeNames = degree;

                    this.getAcademicInformation();
                });
            });

        }


        private submitAcademicForm(): void {
            this.cRUDDetectionService.ObjectDetectionForCRUDOperation(this.previousAcademicInformation, angular.copy(this.entry.academic), this.academicDeletedObjects)
                .then((academicObjects: any) => {
                    this.convertToJson('academic', academicObjects)
                        .then((json: any) => {
                            this.employeeInformationService.saveAcademicInformation(json)
                                .then((message: any) => {
                                    if (message == "Error") {
                                    }
                                    else {
                                        this.getAcademicInformation();
                                        this.showAcademicInputDiv = false;
                                    }
                                });
                        });
                });
        }

        private getAcademicInformation() {
            this.entry.academic = [];
            this.academicDeletedObjects = [];
            this.employeeInformationService.getAcademicInformation(this.userId).then((academicInformation: any) => {
                this.entry.academic = academicInformation;
            }).then(() => {
                this.previousAcademicInformation = angular.copy(this.entry.academic);
            });
        }

        private addNewRow(divName: string) {
            if (divName === 'academic') {
                let academicEntry: IAcademicInformationModel;
                academicEntry = {
                    id: "",
                    employeeId: this.userId,
                    degree: null,
                    institution: "",
                    passingYear: null,
                    result: "",
                    dbAction: "Create"
                };
                this.entry.academic.push(academicEntry);
            }
        }

        private deleteRow(divName: string, index: number, parentIndex?: number) {
            if (divName === 'academic') {
                if (this.entry.academic[index].id != "") {
                    this.entry.academic[index].dbAction = "Delete";
                    this.academicDeletedObjects.push(this.entry.academic[index]);
                }
                this.entry.academic.splice(index, 1);
            }
        }

        private convertToJson(convertThis: string, obj: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            let JsonArray = [];
            let item: any = {};
             if (convertThis === "academic") {
                item['academic'] = obj;
            }
            JsonArray.push(item);
            JsonObject['entries'] = JsonArray;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("EducationInformation", EducationInformation);
}