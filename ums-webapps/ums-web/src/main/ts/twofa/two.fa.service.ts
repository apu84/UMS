module ums {
  export class TwoFAService {
    public static $inject = ['$q', '$modal'];
    private currentState: string;
    private currentDefer: ng.IDeferred<any>;

    constructor(private $q: ng.IQService,
                private $modal: any) {
    }

    public showTwoFAForm(state: string, lifeTime: number, remainingTime : number): ng.IPromise<any> {
      this.currentDefer = this.$q.defer();
      this.currentState = state;
      this.showModal(state, lifeTime, remainingTime);
      return this.currentDefer.promise;
    }

    private showModal(state: string, lifeTime: number, remainingTime: number ): void {
      this.$modal.open({
        templateUrl: 'views/two-fa/two.fa.modal.html',
        controller: TwoFaModalController,
        resolve: {
          state: () => state,
          lifeTime:() => lifeTime,
          remainingTime:() => remainingTime,
          currentDefer: () => this.currentDefer
        },
        backdrop: 'static'
      });
    }
  }
  UMS.service('TwoFAService', TwoFAService);
}

