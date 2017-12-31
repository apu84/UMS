module ums {
  export class TwoFAService {
    public static $inject = ['$q', '$modal'];
    private currentState: string;
    private currentDefer: ng.IDeferred<any>;

    constructor(private $q: ng.IQService,
                private $modal: any) {
    }

    public showTwoFAForm(state: string): ng.IPromise<any> {
      this.currentDefer = this.$q.defer();
      this.currentState = state;
      this.showModal(state);
      return this.currentDefer.promise;
    }

    private showModal(state: string): void {
      this.$modal.open({
        templateUrl: 'views/twofatest/two.fa.modal.test.html',
        controller: ModalController,
        resolve: {
          state: () => state,
          currentDefer: () => this.currentDefer,
          loadMyCtrl: ['$ocLazyLoad', ($ocLazyLoad) => {
            return $ocLazyLoad.load({
              files: [
                'vendors/bootstrap-datepicker/css/datepicker.css',
                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js'
              ]
            });
          }]
        }
      });
    }
  }

  UMS.service('TwoFAService', TwoFAService);
}

