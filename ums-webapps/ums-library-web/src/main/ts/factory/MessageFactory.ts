module ums {

    export class MessageFactory {
        static $inject = [];
        private responseType: string;
        private message: string;

        constructor() {
        }

        public getNotifyMessage(responseType: string, message: string): NotifyMessage {
            var msg: NotifyMessage = <NotifyMessage>{};
            msg.responseType = responseType;
            msg.message = message;
            return msg;
        }

        public getSuccessMessage(message: string): NotifyMessage {
            var msg: NotifyMessage = <NotifyMessage>{};
            msg.responseType = "success";
            msg.message = message;
            return msg;
        }

        public getErrorMessage(message: string): NotifyMessage {
            var msg: NotifyMessage = <NotifyMessage>{};
            msg.responseType = "error";
            msg.message = message;
            return msg;
        }

        static factory() {
            var instance = ($q: ng.IQService) =>
                new MessageFactory();
            return instance;
        }
    }

    UMS.factory("MessageFactory", MessageFactory.factory());
}