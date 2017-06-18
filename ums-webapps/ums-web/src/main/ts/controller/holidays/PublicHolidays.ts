module ums {
  /* interface IPublicHolidays extends ng.IScope {
   holidayTypes: Array<HolidayType>;
   holidays: Array<Holidays>;
   data: any;
   user: User;

   enableEdit: boolean;
   enableButton: boolean;

   dateChanged: Function;
   save: Function;
   }
   */

  class PublicHolidays {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', '$timeout', 'holidaysService', 'holidayTypeService', 'userService'];

    public holidayTypes: Array<HolidayType>;
    public holidays: Array<Holidays>;
    public data: any;
    public year: number;
    public user: User;

    public enableEdit: boolean;
    public enableButton: boolean;

    constructor(private appConstants: any,
                private httpClient: HttpClient,
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
      this.enableEdit = false;
      this.enableButton = false;
      var year = date.getFullYear();
      this.year = year;
      this.data = {
        year: year
      };
      this.getHolidays();
      this.getLoggedUsersInfo();
    }


    private dateChanged() {
      this.enableButton = true;
      this.$timeout(() => {
        for (var i = 0; i < this.holidays.length; i++) {
          if (this.holidays[i].fromDate != "" && this.holidays[i].toDate != "") {
            var fromDateParts: any = this.holidays[i].fromDate.split('-');
            var fromDate = new Date(fromDateParts[2], fromDateParts[1], fromDateParts[0]);

            var toDateParts: any = this.holidays[i].toDate.split('-');
            var toDate = new Date(toDateParts[2], toDateParts[1], toDateParts[0]);

            var timeDiff: any = Math.abs(toDate.getTime() - fromDate.getTime());
            var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
            this.holidays[i].duration = diffDays + 1;
          }
        }
      }, 200);

    }

    private getLoggedUsersInfo() {
      this.userService.fetchCurrentUserInfo().then((user: User) => {
        console.log("user info");
        console.log(user);
        this.user = user;
        if (this.user.roleId == Utils.REGISTRAR)
          this.enableEdit = true;
      });
    }


    private getHolidays() {

      this.holidays = [];
      this.holidaysService.fetchHolidaysByYear(this.year).then((holidays: Array<Holidays>) => {
        if (holidays.length == 0)
          this.getHolidayTypes();
        else
          this.holidays = holidays;
      });
    }


    private save() {
      var emptyDateFieldFound: boolean = this.findEmptyDates();
      if (emptyDateFieldFound)
        this.notify.error("Please fill up all the fields");
      else
        this.convertToJson().then((json) => {
        });
    }

    private findEmptyDates(): boolean {
      var found: boolean = false;
      for (var i = 0; i < this.holidays.length; i++) {
        if (this.holidays[i].fromDate == '' || this.holidays[i].toDate == '') {
          found = true;
          break;
        }
      }
      return found;
    }

    private getHolidayTypes() {
      this.holidayTypes = [];
      this.holidayTypeService.fetchAllHolidayTypes().then((holidayTypes: Array<HolidayType>) => {
        for (var i = 0; i < holidayTypes.length; i++) {
          var holidays: Holidays = <Holidays>{};
          holidays.holidayTypeId = holidayTypes[i].id;
          holidays.holidayTypeName = holidayTypes[i].name;
          holidays.moonDependency = holidayTypes[i].moonDependency;
          holidays.year = this.year;
          holidays.fromDate = "";
          holidays.toDate = "";
          holidays.duration = 0;
          this.holidays.push(holidays);
        }

      });
    }

    private convertToJson(): ng.IPromise<any> {
      var defer = this.$q.defer();
      var jsonObject = [];
      var completeJson = [];
      for (var i = 0; i < this.holidays.length; i++) {
        if (this.holidays[i].fromDate != '' && this.holidays[i].toDate != '') {
          var item: any = {};
          item['id'] = this.holidays[i].id;
          item['holidayTypeId'] = this.holidays[i].holidayTypeId;
          item['year'] = this.holidays[i].year;
          item['fromDate'] = this.holidays[i].fromDate;
          item['toDate'] = this.holidays[i].toDate;
          jsonObject.push(item);
        }
      }
      completeJson['entries'] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }

  }
  UMS.controller("PublicHolidays", PublicHolidays);
}