module ums {
    class AcademicInformation {
        public static $inject = ['registrarConstants', '$q', 'notify',
            'employeeInformationService', 'academicDegreeService',
            'cRUDDetectionService', '$stateParams'];

        private academic: boolean = false;
        private showAcademicInputDiv: boolean = false;
        private degreeNames: IAcademicDegreeTypes[] = [];
        private previousAcademicInformation: IAcademicInformationModel[];
        private academicDeletedObjects: IAcademicInformationModel[];
        private userId: string = "";
        private stateParams: any;
        private showAcademicEditButton: boolean = false;
        private entry: any;

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private employeeInformationService: EmployeeInformationService,
                    private academicDegreeService: AcademicDegreeService,
                    private cRUDDetectionService: CRUDDetectionService,
                    private $stateParams: any) {

            this.entry = {
                academic: Array<IAcademicInformationModel>()
            };

            this.stateParams = $stateParams;
            this.userId = this.stateParams.id;
            this.showAcademicEditButton = this.stateParams.edit;
            this.initialization();
        }

        private initialization() {
            this.academicDegreeService.getAcademicDegreeList().then((degree: any) => {
                this.degreeNames = degree;
                this.getAcademicInformation();
            });
        }

        public submit(id: number): void{
            console.log(this.entry.academic[id].degree.name);
            console.log(this.entry.academic[id].institution);
            console.log(this.entry.academic[id].passingYear);
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

    UMS.controller("AcademicInformation", AcademicInformation);
}