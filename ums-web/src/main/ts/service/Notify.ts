module ums {
  export interface NotifyMessage {
    responseType: string;
    message: string;
    response: any;
  }

  export class Notify {
    constructor() {
      $.notify.defaults({
        position: 'top center'
      });
    }

    public show(message: NotifyMessage) {
      $.notify(message.message, message.responseType.toLowerCase());
    }

    public success(pMessage: string): void {
      $.notify(pMessage, 'success');
    }

    public error(pMessage: string): void {
      $.notify(pMessage, 'error');
    }

    public info(pMessage: string): void {
      $.notify(pMessage, 'info');
    }

    public warn(pMessage: string): void {
      $.notify(pMessage, 'warn');
    }
  }

  UMS.service('notify', Notify);
}