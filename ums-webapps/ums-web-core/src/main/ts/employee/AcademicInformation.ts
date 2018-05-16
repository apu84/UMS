module ums {

    class AcademicInformation {

        public static $inject = [
            'registrarConstants',
            '$q',
            'notify',
            'employeeInformationService',
            'academicDegreeService',
            '$stateParams'
        ];

        private academic: IAcademicInformationModel[] = [];
        private degreeLevel: IDegreeLevel[] = [];
        private degreeTitle: IDegreeTitle[] = [];
        private filteredDegreeTitle: IDegreeTitle[] = [];
        private year = [];
        private currentIndex: number;
        private newDegreeTitle: string = "";
        readonly userId: string = "";
        private stateParams: any;
        private enableEdit: boolean[] = [false];
        private enableEditButton: boolean = false;

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private employeeInformationService: EmployeeInformationService,
                    private academicDegreeService: AcademicDegreeService,
                    private $stateParams: any) {

            this.academic = [];
            this.year = Utils.getYearRange();
            this.degreeLevel = registrarConstants.degreeLevel;
            this.stateParams = $stateParams;
            this.userId = this.stateParams.id;
            this.enableEditButton = this.stateParams.edit;
            this.academicDegreeService.getAcademicDegreeList().then((degree: any) => {
                this.degreeTitle = degree;
                this.filteredDegreeTitle = degree;
                this.get();
            });
        }

        public submit(index: number): void {
            this.convertToJson(this.academic[index]).then((json: any) => {
                if (!this.academic[index].id) {
                    this.create(json, index);
                }
                else {
                    this.update(json, index);
                }
            });
        }


        private create(json: any, index: number) {
            this.employeeInformationService.saveAcademicInformation(json).then((data: any) => {
                this.academic[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }

        private update(json: any, index: number) {
            this.employeeInformationService.updateAcademicInformation(json).then((data: any) => {
                this.academic[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }

        private get(): void {
            this.academic = [];
            this.employeeInformationService.getAcademicInformation(this.userId).then((academicInformation: any) => {
                if (academicInformation) {
                    this.academic = academicInformation;
                }
                else {
                    this.academic = [];
                }
            });
        }

        public delete(index: number): void {
            if (this.academic[index].id) {
                this.employeeInformationService.deleteAcademicInformation(this.academic[index].id).then((data: any) => {
                    this.academic.splice(index, 1);
                });
            }
            else {
                this.academic.splice(index, 1);
            }
        }

        public activeEditButton(index: number, canEdit: boolean): void{
            this.enableEdit[index] = canEdit;
        }

        public modifyDegreeTitleSelection(index: number): void{
            this.filteredDegreeTitle = this.degreeTitle.filter((data) => {
                return this.academic[index].degreeLevel.id == data.degreeLevelId;
            });
        }

        public verifyDegreeTitle(index: number): void{
            if(this.academic[index].degreeTitle) {
                if (this.academic[index].degreeLevel.id !== this.academic[index].degreeTitle.degreeLevelId) {
                    this.academic[index].degreeLevel = null;
                    this.filteredDegreeTitle = [];
                }
            }
        }

        public createNewDegreeTitle(): void{
            this.convertToJson(this.newDegreeTitle).then((json: any)=>{
                this.employeeInformationService.saveNewDegreeTitle(json).then(() =>{
                    this.academicDegreeService.getAcademicDegreeList().then((degree: any) => {
                        this.degreeTitle = degree;
                        this.modifyDegreeTitleSelection(this.getCurrentIndex());
                    });
                });
            })
        }

        private getCurrentIndex(): number{
            return this.currentIndex;
        }

        public setCurrentIndex(index: number): void{
            this.currentIndex = index;
        }


        public addNew(): void {
            let academicEntry: IAcademicInformationModel;
            academicEntry = {
                id: "",
                employeeId: this.userId,
                degreeLevel: null,
                degreeTitle: null,
                institution: "",
                passingYear: null,
                result: "",
                major: "",
                duration: 0
            };
            this.academic.push(academicEntry);
        }

        private convertToJson(object: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = object;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("AcademicInformation", AcademicInformation);
}