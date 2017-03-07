module ums {
  export interface NotificationScope extends ng.IScope {
    notifications: Array<INotification>;
    numOfUnreadNotification: number;
    baseInterval: number;  // pollingInterval  value from notification.properties
    currentInterval: number;  //current interval of the application
    tryCount: number;  // try fail counter
    delayFactor: number; // 5 Seconds will be added for each failed operation.
    setReadStatus: Function;
  }
  export interface INotification {
    payload: string;
    producedOn: string;
    consumedOn: string;
    isRead: boolean;
    id: string;
  }
  export interface NotificationEntries {
    entries: Array<INotification>;
  }
  export class Notification implements ng.IDirective {
    public scope: NotificationScope;
    private intervalPromise: ng.IPromise<any>;

    constructor(private httpClient: HttpClient,
                private $q: ng.IQService,
                private $interval: ng.IIntervalService,
                private settings: Settings) {

    }

    public restrict: string = 'AE';

    public link = ($scope: NotificationScope, element: JQuery, attributes: any) => {
      this.scope = $scope;
      this.scope.numOfUnreadNotification = 0;
      this.scope.currentInterval = 0;
      this.scope.tryCount = 0;
      this.scope.delayFactor = 5000; //5 Seconds delay for each failed operation.

      this.settings.getSettings().then((appSettings: {[key: string]: any}) => {
        if (appSettings['notification.enabled']) {
          this.scope.currentInterval = Number(appSettings['polling.interval']);
          this.scope.baseInterval = Number(appSettings['polling.interval']);

          this.getNotification();
          this.startNotificationTimer(this.scope.baseInterval);

        }
      });

      this.scope.setReadStatus = this.setReadStatus.bind(this);
    };

    public templateUrl = "./views/directive/notification.html";

    private getNotification() {
      this.httpClient.poll("notification/10/", HttpClient.MIME_TYPE_JSON,
          (response: NotificationEntries) => {
            var tempCount: number = 0;
            for (var i = 0; i < response.entries.length; i++) {
              var notification: INotification = response.entries[i];
              var producedOn: any = notification.producedOn ? moment(notification.producedOn, 'DD-MM-YYYY hh:mm:ss') : null;
              var consumedOn: any = notification.consumedOn ? moment(notification.consumedOn, 'DD-MM-YYYY hh:mm:ss') : null;
              if (producedOn = null || consumedOn == null || !consumedOn.isAfter(producedOn)) {
                notification.isRead = false;
                tempCount = tempCount + 1;
              } else {
                notification.isRead = true;
              }
            }
            if (tempCount != this.scope.numOfUnreadNotification) {
              this.scope.numOfUnreadNotification = tempCount;
            }
            this.scope.notifications = response.entries;

            //After a successful fetch we check whether the current interval and base interval are eaual or not.
            // If not, we make them equal and start the timer with the initial base Interval.
            if (this.scope.currentInterval != this.scope.baseInterval) {
              this.scope.currentInterval = this.scope.baseInterval;
              this.scope.tryCount = 0;
              this.stopNotificationTimer();
              this.startNotificationTimer(this.scope.baseInterval);
            }
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            if (response.status === 401) {
              this.stopNotificationTimer();
            }
            else if (response.status === 503 || response.status === 404) {  // Service Unavailable
              this.stopNotificationTimer();

              //For each fail operation interval will be increased by 5 seconds.
              //And it will increase for 5 times. After that, the interval will be doubled.
              if (this.scope.tryCount % 5 != 0) {
                this.scope.tryCount++;
                this.scope.currentInterval = this.scope.currentInterval + this.scope.delayFactor;
              }
              else {
                this.scope.tryCount = 0;
                this.scope.currentInterval = this.scope.currentInterval * 2;
              }

              this.startNotificationTimer(this.scope.currentInterval);

            }
          });
    }

    private startNotificationTimer(interval: number): void {
      this.intervalPromise = this.$interval(() => {
        this.getNotification();
      }, interval);
    }

    private stopNotificationTimer(): void {
      if (angular.isDefined(this.intervalPromise)) {
        this.$interval.cancel(this.intervalPromise);
        this.intervalPromise = undefined;
      }
    }

    setReadStatus(): void {
      //ToDo: Fix Me.
      //Caused a problem inside NotificationResourceHelper [Long.parseLong(notificationObject.getString("id"))]
      //During the conversion.  This is the Quick Fix for that Exception.
      for (var i = 0; i < this.scope.notifications.length; i++) {
        this.scope.notifications[i].id = this.scope.notifications[i].id + '';
      }

      this.httpClient.post('notification/read', this.scope.notifications, 'application/json')
          .success((data) => {
            delete this.scope.numOfUnreadNotification;
          }).error((data) => {
      });
    }
  }
  UMS.directive("notification",
      ['HttpClient',
        '$q',
        '$interval',
        'Settings',
        (httpClient, $q, $interval, settings: Settings) => {
          return new Notification(httpClient, $q, $interval, settings);
        }]);
}
