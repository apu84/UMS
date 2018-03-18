module ums {
  export class TwoFaModalController {
    public static $inject = ['$scope', '$modalInstance','HttpClient', 'state','lifeTime','remainingTime','emailAddress','$http', 'currentDefer','notify'];

    constructor(private $scope: any,
                private $modalInstance: any,
                private httpClient: HttpClient,
                private state: string,
                private lifeTime: number,
                private remainingTime: number,
                private emailAddress: string,
                private $http: any,
                private currentDefer: ng.IDeferred<any>,
                private notify: Notify) {
      this.$scope.ok = this.ok.bind(this);
      this.$scope.testTwoFa = this.testTwoFa.bind(this);
      this.$scope.resend = this.resend.bind(this);
      this.$scope.tokenRef = {
        message: '',
        twofatoken: '',
        emailAddress: this.emailAddress
      };

      this.manageTimer(this.lifeTime, this.remainingTime);
    }

    private manageTimer(otpLifeTime: number, remainingTime: number): void {
      var remainingSeconds = 1 * remainingTime;
      var clockTimer = +localStorage.getItem("clockTimer");
      clearInterval(clockTimer);

      var minutes = parseInt((remainingSeconds / 60) + '');
      var seconds = remainingSeconds % 60;
      $("#countDownDiv").html( "00" + " : " + ("0" + minutes).slice(-2) + " : " + ("0" + seconds).slice(-2));

      var cTimer = setInterval(function() {
        var minutes = parseInt((remainingSeconds / 60) + '');
        var seconds = remainingSeconds % 60;

        if (remainingSeconds < 0) {
          clearInterval(cTimer);
          $("#twoFaInputDiv").hide();
          $("#twoFaExpireDiv").show();
        } else {
          remainingSeconds = remainingSeconds - 1;
          $("#countDownDiv").html( "00" + " : " + ("0" + minutes).slice(-2) + " : " + ("0" + seconds).slice(-2));

        }
      }, 1000);
      localStorage["clockTimer"] = cTimer;

      var progressTimer = +localStorage.getItem("progressTimer");
      clearInterval(progressTimer);

      var percentage = (100 / otpLifeTime) * (remainingTime);
      var index = remainingTime;
      var pTimer = setInterval(function() {
        percentage = percentage - (100 / otpLifeTime);
        console.log(percentage);
        index--;
        if (index > 0) {
          $('.progress-bar').css('width', percentage + '%');
          $('.progress-bar').attr('aria-valuenow', Math.round(percentage));
          $('.progress-bar').html($('.progress-bar').attr('aria-valuenow') + '%');
        } else {
          clearInterval(pTimer);
        }
      }, 1000);
      localStorage["progressTimer"] = pTimer;
    }
    private ok(): void {
      var clockTimer = +localStorage.getItem("clockTimer");
      var progressTimer = +localStorage.getItem("progressTimer");
      clearInterval(clockTimer);
      clearInterval(progressTimer);
      this.$modalInstance.dismiss('cancel');
    }

    private resend():void {
      this.httpClient.post('match-two-fa/resend',  {"state": this.state}, 'application/json').then(
          (response) => {
            console.log(response);
            var responseBody =response.data;
            this.state = responseBody.state;
            console.log(this.state);
            $("#twoFaInputDiv").show();
            $("#twoFaExpireDiv").hide();
            this.manageTimer(responseBody.lifeTime, responseBody.remainingTime);
          },
          function(response){
            console.log(response);
          }
          );
    }

    private testTwoFa(): ng.IPromise<any> {
      console.log('validating token...', this.$scope.tokenRef.twofatoken);
      this.$scope.tokenRef.message = '';
      return this.httpClient.post(`match-two-fa`, {"state": this.state, "token": this.$scope.tokenRef.twofatoken},
          HttpClient.MIME_TYPE_JSON)
          .success((response: any) => {
              this.currentDefer.resolve(response);
              this.ok();
            })
          .error((error, status) => {
            console.log(error);
            console.log(status);
            if(status==401)
              this.notify.error("Wrong OTP(One time password)");
            else {
              this.currentDefer.reject("");
              this.ok();
            }
          });
    }
  }
  UMS.controller('TwoFaModalController', TwoFaModalController);
}
