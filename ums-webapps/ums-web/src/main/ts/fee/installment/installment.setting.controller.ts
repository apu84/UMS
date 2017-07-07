module ums {
  export class InstallmentSettingController {
    public static $inject = ['appConstants', 'HttpClient', 'InstallmentSettingService'];
    private installmentSetting: InstallmentSetting;
    private semesterSelector: ProgramSelectorModel;
    private dateSettings: AdmissionType[];

    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private installmentSettingService: InstallmentSettingService) {
      this.semesterSelector = new ProgramSelectorModel(this.appConstants, this.httpClient, true);
      this.semesterSelector.setProgramType(this.appConstants.programTypeEnum.UG, FieldViewTypes.selected);
      this.semesterSelector.setDepartment(null, FieldViewTypes.hidden);
      this.semesterSelector.setProgram(null, FieldViewTypes.hidden);
    }

    public findSetting(): void {
      this.installmentSettingService.getInstallmentSetting(this.semesterSelector.semesterId).then(
          (installmentSetting: InstallmentSetting) => {
            this.installmentSetting = installmentSetting;
            if (this.installmentSetting.enabled) {
              this.findDateSetting();
            }
          });
    }

    public findDateSetting(): void {
      this.installmentSettingService.getDateSetting(this.semesterSelector.semesterId).then(
          (dateSetting: AdmissionType[]) => {
            this.dateSettings = dateSetting;
          });
    }

    public updateSetting(): void {

    }
  }

  UMS.controller('InstallmentSettingController', InstallmentSettingController);
}
