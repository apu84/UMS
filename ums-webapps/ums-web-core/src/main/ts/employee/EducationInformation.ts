module ums {
    interface IEmployeeProfile extends ng.IScope {
        submitAcademicForm: Function;
    }

    class EducationInformation {
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify',
            'employeeInformationService', 'academicDegreeService',
            'cRUDDetectionService', '$stateParams'];

        private entry: {
            academic: IAcademicInformationModel[]
        };
        private academic: boolean = false;
        private showAcademicInputDiv: boolean = false;
        private degreeNames: IAcademicDegreeTypes[] = [];
        private previousAcademicInformation: IAcademicInformationModel[];
        private academicDeletedObjects: IAcademicInformationModel[];
        private userId: string = "";
        private stateParams: any;
        private showAcademicEditButton: boolean = false;

        constructor(private registrarConstants: any,
                    private $scope: IEmployeeProfile,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private employeeInformationService: EmployeeInformationService,
                    private academicDegreeService: AcademicDegreeService,
                    private cRUDDetectionService: CRUDDetectionService,
                    private $stateParams: any) {

            $scope.submitAcademicForm = this.submitAcademicForm.bind(this);

            this.entry = {
                academic: Array<IAcademicInformationModel>()
            };

            this.stateParams = $stateParams;
            this.userId = this.stateParams.id;
            this.showAcademicEditButton = this.stateParams.edit;
            console.log("Is It editable " + this.showAcademicInputDiv);
            this.initialization();
        }

        private initialization() {
            this.academicDegreeService.getAcademicDegreeList().then((degree: any) => {
                this.degreeNames = degree;
                console.log("In academic Initialization and user id ==== " + this.userId);
                this.getAcademicInformation();
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