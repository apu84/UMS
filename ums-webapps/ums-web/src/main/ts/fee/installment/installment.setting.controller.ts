module ums {
  interface InstallmentSettingScope extends ng.IScope {
    updateSetting: Function;
  }

  export class InstallmentSettingController {
    public static $inject = ['$scope', 'appConstants', 'HttpClient', 'InstallmentSettingService', 'notify'];
    private installmentSetting: InstallmentSetting;
    private semesterSelector: ProgramSelectorModel;
    private dateSettings: DateSetting[];
    private enableSaveButton: boolean;

    constructor(private $scope: InstallmentSettingScope,
                private appConstants: any,
                private httpClient: HttpClient,
                private installmentSettingService: InstallmentSettingService,
                private notify: Notify) {
      this.semesterSelector = new ProgramSelectorModel(this.appConstants, this.httpClient, true);
      this.semesterSelector.setProgramType(this.appConstants.programTypeEnum.UG, FieldViewTypes.selected);
      this.semesterSelector.setDepartment(null, FieldViewTypes.hidden);
      this.semesterSelector.setProgram(null, FieldViewTypes.hidden);
      this.$scope.updateSetting = this.updateSetting.bind(this);
    }

    public findSetting(): void {
      this.installmentSettingService.getInstallmentSetting(this.semesterSelector.semesterId).then(
          (installmentSetting: InstallmentSetting) => {
            this.installmentSetting = installmentSetting;
            this.findDateSetting();
            this.enableSaveButton = true;
          });
    }

    public findDateSetting(): void {
      this.installmentSettingService.getDateSetting(this.semesterSelector.semesterId).then(
          (dateSetting: DateSetting[]) => {
            this.dateSettings = dateSetting;
          });
    }

    public addLateFee(setting: InstallmentDateSetting) {
      if (!setting.lateFee) {
        setting.lateFee = [];
      }
      setting.lateFee.push({
        start: undefined,
        end: undefined,
        fee: undefined
      });
    }

    public removeLateFee(setting: InstallmentDateSetting, index: number) {
      setting.lateFee.splice(index, 1);
      if (setting.lateFee.length === 0) {
        delete setting.lateFee;
      }
    }

    public updateSetting(): void {
      this.installmentSettingService.updateInstallmentSetting(this.installmentSetting)
          .then((response) => {
            if (response) {
              this.installmentSettingService.updateDateSetting(this.semesterSelector.semesterId, this.dateSettings)
                  .then((result) => {
                    if (!result) {
                      this.notify.error('Failed to save');
                    }
                    else {
                      this.findSetting();
                    }
                  });
            }
            else {
              this.notify.error('Failed to save');
            }
          })
    }
  }

  UMS.controller('InstallmentSettingController', InstallmentSettingController);
}
