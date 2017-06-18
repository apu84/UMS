module ums {
  interface IPublicHolidays extends ng.IScope {
    holidayTypes: Array<HolidayType>;
    holidays: Array<Holidays>;
    data: any;
    user: User;

    enableEdit: boolean;
    enableButton: boolean;

    dateChanged: Function;
    save: Function;
  }


  class PublicHolidays {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q', 'notify', '$sce', '$window', '$timeout', 'holidaysService', 'holidayTypeService', 'userService'];

    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IPublicHolidays,
                private $q: ng.IQService,
                private notify: Notify,
                private $sce: ng.ISCEService,
                private $window: ng.IWindowService,
                private $timeout: ng.ITimeoutService,
                private holidaysService: HolidaysService,
                private holidayTypeService: HolidayTypeService,
                private userService: UserService) {

      var number: any = number;
      var date = new Date;
      $scope.enableEdit = false;
      $scope.enableButton = false;
      var year = date.getFullYear();
      $scope.data = {
        year: year
      };

      $scope.dateChanged = this.dateChanged.bind(this);
      $scope.save = this.save.bind(this);
      this.getHolidays();
      this.getLoggedUsersInfo();
    }


    private dateChanged() {
      this.$scope.enableButton = true;
      this.$timeout(() => {
        for (var i = 0; i < this.$scope.holidays.length; i++) {
          if (this.$scope.holidays[i].fromDate != "" && this.$scope.holidays[i].toDate != "") {
            var fromDateParts: any = this.$scope.holidays[i].fromDate.split('-');
            var fromDate = new Date(fromDateParts[2], fromDateParts[1], fromDateParts[0]);

            var toDateParts: any = this.$scope.holidays[i].toDate.split('-');
            var toDate = new Date(toDateParts[2], toDateParts[1], toDateParts[0]);

            var timeDiff: any = Math.abs(toDate.getTime() - fromDate.getTime());
            var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
            this.$scope.holidays[i].duration = diffDays + 1;
          }
        }
      }, 200);

    }

    private getLoggedUsersInfo() {
      this.userService.fetchCurrentUserInfo().then((user: User) => {
        console.log("user info");
        console.log(user);
        this.$scope.user = user;
        if (this.$scope.user.roleId == Utils.REGISTRAR)
          this.$scope.enableEdit = true;
      });
    }


    private getHolidays() {

      this.$scope.holidays = [];
      this.holidaysService.fetchHolidaysByYear(this.$scope.data.year).then((holidays: Array<Holidays>) => {
        if (holidays.length == 0)
          this.getHolidayTypes();
        else
          this.$scope.holidays = holidays;
      });
    }

    private save() {
      var entries: any = this.$scope.holidays;
      console.log(JSON.stringify(entries));
    }

    private getHolidayTypes() {
      this.$scope.holidayTypes = [];
      this.holidayTypeService.fetchAllHolidayTypes().then((holidayTypes: Array<HolidayType>) => {
        for (var i = 0; i < holidayTypes.length; i++) {
          var holidays: Holidays = <Holidays>{};
          holidays.holidayTypeId = holidayTypes[i].id;
          holidays.holidayTypeName = holidayTypes[i].name;
          holidays.moonDependency = holidayTypes[i].moonDependency;
          holidays.year = this.$scope.data.year;
          holidays.fromDate = "";
          holidays.toDate = "";
          holidays.duration = 0;
          this.$scope.holidays.push(holidays);
        }

        console.log("holiday types");
        console.log(holidayTypes);
        console.log("Holidays");
        console.log(this.$scope.holidays);
      });
    }

    private convertToJson(): ng.IPromise<any> {
      var defer = this.$q.defer();
      var jsonObject = [];
      return null;
    }

  }
  UMS.controller("PublicHolidays", PublicHolidays);
}