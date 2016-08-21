module ums {
  export interface NotificationScope extends ng.IScope {
    notifications: Array<Notification>;
    numOfUnreadNotification: number;
    setReadStatus: Function;
  }
  export interface Notification {
    payload: string;
    producedOn: string;
    consumedOn: string;
    isRead: boolean;
  }
  export interface NotificationEntries {
    entries: Array<Notification>;
  }
  export class Notification implements ng.IDirective {
    private scope: NotificationScope;

    constructor(private httpClient: HttpClient,
                private $q: ng.IQService,
                private $interval: ng.IIntervalService) {

    }

    public restrict: string = 'E';

    public link = ($scope: NotificationScope, element: JQuery, attributes: any) => {
      this.scope = $scope;
      this.$interval(()=> {
        this.getNotification();
      }, 60000);

      this.scope.setReadStatus = this.setReadStatus.bind(this);
    };

    public templateUrl = "./views/directive/notification.html";

    private getNotification() {
      this.scope.numOfUnreadNotification = 0;
      this.httpClient.get("notification/10/", HttpClient.MIME_TYPE_JSON,
          (response: NotificationEntries)=> {
            for (var i = 0; i < response.entries.length; i++) {
              var notification: Notification = response.entries[i];
              var producedOn: any = notification.producedOn ? moment(notification.producedOn, 'DD-MM-YYYY hh:mm:ss') : null;
              var consumedOn: any = notification.consumedOn ? moment(notification.consumedOn, 'DD-MM-YYYY hh:mm:ss') : null;
              if (producedOn = null || consumedOn == null || !consumedOn.isAfter(producedOn)) {
                notification.isRead = false;
                this.scope.numOfUnreadNotification = this.scope.numOfUnreadNotification + 1;
              } else {
                notification.isRead = true;
              }
            }

            this.scope.notifications = response.entries;
          });
    }

    private setReadStatus(): void {
      delete this.scope.numOfUnreadNotification;
      this.httpClient.post('notification/read', this.scope.notifications, 'application/json')
          .success((data) => {
            console.debug(data);
          }).error((data) => {
          });
    }
  }
  UMS.directive("notification", ['HttpClient', '$q', '$interval', (httpClient, $q, $interval)=> {
    return new Notification(httpClient, $q, $interval);
  }]);
}
