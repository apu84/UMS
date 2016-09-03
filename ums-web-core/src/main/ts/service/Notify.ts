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

    public success(pMessage: string, autoHide?: boolean): void {
      $.notify(pMessage, 'success');
    }

    public error(pMessage: string, autoHide?: boolean): void {
      if (!autoHide) {
        $.notify(pMessage, {
          className: 'error',
          autoHide: autoHide
        });
        console.debug('here.....')
      }else {
        $.notify(pMessage, 'error');
      }
    }

    public info(pMessage: string, autoHide?: boolean): void {
      $.notify(pMessage, 'info');
    }

    public warn(pMessage: string, autoHide?: boolean): void {
      $.notify(pMessage, 'warn');
    }
  }

  UMS.service('notify', Notify);
}