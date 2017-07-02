module ums {
  export interface PaymentFeeTypeGroupResponse {
    entries: PaymentFeeTypeGroup[];
  }

  export interface PaymentFeeTypeGroup {
    feeType: string;
    feeTypeName: string;
    feeTypeDescription: string;
    transactions: PaymentTransactionGroup[];
  }

  export interface PaymentTransactionGroup {
    id: string;
    entries: ReceivePayment[];
  }

  export interface ReceivePayment {
    id: string;
    amount: string;
    feeCategory: string;
    transactionId: string;
    appliedOn: string;
    status: string;
    feeType: string;
    feeTypeName: string;
    feeTypeDescription: string;
    lastModified: string;
    semesterName: string;
    studentId: string;
  }

  export class ReceivePaymentService {
    public static $inject = ['$q', 'HttpClient'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient) {

    }

    public getPaymentsByStudent(studentId: string): ng.IPromise<PaymentFeeTypeGroup[]> {
      let defer: ng.IDeferred<PaymentFeeTypeGroup[]> = this.$q.defer();
      this.httpClient.get(`/ums-webservice-bank/receive-payment/${studentId}`, HttpClient.MIME_TYPE_JSON,
          (response: PaymentFeeTypeGroupResponse) => defer.resolve(response.entries));
      return defer.promise;
    }

    public getPaymentsByFeeType(studentId: string, feeTypeId: number): ng.IPromise<PaymentFeeTypeGroup[]> {
      let defer: ng.IDeferred<PaymentFeeTypeGroup[]> = this.$q.defer();
      this.httpClient.get(`/ums-webservice-bank/receive-payment/${studentId}/${feeTypeId}`, HttpClient.MIME_TYPE_JSON,
          (response: PaymentFeeTypeGroupResponse) => defer.resolve(response.entries));
      return defer.promise;
    }

    public receivePayment(studentId: string, payments: ReceivePayment[]): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.post(`/ums-webservice-bank/receive-payment/${studentId}`, {'entries': payments},
          HttpClient.MIME_TYPE_JSON)
          .success(() => defer.resolve(true))
          .error(() => defer.resolve(false));
      return defer.promise;
    }

    public getPayments(appliedPayments: PaymentFeeTypeGroup[], selectedTransactions: string[]): ReceivePayment[] {
      let payments: ReceivePayment[] = [];
      for (let k = 0; k < appliedPayments.length; k++) {
        let transactions: PaymentTransactionGroup[] = appliedPayments[k].transactions;
        for (let i = 0; i < selectedTransactions.length; i++) {
          for (let j = 0; j < transactions.length; j++) {
            if (transactions[j].id === selectedTransactions[i]) {
              payments.push.apply(payments, transactions[j].entries);
            }
          }
        }
      }
      return payments;
    }

    public groupPayments(payments: ReceivePayment[]): PaymentFeeTypeGroup[] {
      let paymentGroup: PaymentFeeTypeGroup[] = [];
      for (let i = 0; i < payments.length; i++) {
        let feeTypeGroup = this.getFeeType(payments[i], paymentGroup);
        if (!feeTypeGroup) {
          paymentGroup.push({
            feeType: payments[i].feeType,
            feeTypeName: payments[i].feeTypeName,
            feeTypeDescription: payments[i].feeTypeDescription,
            transactions: [{
              id: payments[i].transactionId,
              entries: [payments[i]]
            }]
          });
        } else {
          let transactionGroup = this.getTransaction(payments[i], paymentGroup);
          if (!transactionGroup) {
            feeTypeGroup.transactions.push({
              id: payments[i].transactionId,
              entries: [payments[i]]
            });
          }
          else {
            transactionGroup.entries.push(payments[i]);
          }
        }
      }
      return paymentGroup;
    }

    private getFeeType(payment: ReceivePayment, payments: PaymentFeeTypeGroup[]): PaymentFeeTypeGroup {
      for (let i = 0; i < payments.length; i++) {
        if (payments[i].feeType == payment.feeType) {
          return payments[i];
        }
      }
      return null;
    }

    private getTransaction(payment: ReceivePayment, payments: PaymentFeeTypeGroup[]): PaymentTransactionGroup {
      for (let i = 0; i < payments.length; i++) {
        for (let j = 0; j < payments[i].transactions.length; j++) {
          if (payments[i].transactions[j].id == payment.transactionId) {
            return payments[i].transactions[j];
          }
        }
      }
      return null;
    }

    public totalAmount(payments: ReceivePayment[]): number {
      let total: number = 0;
      for (let i = 0; i < payments.length; i++) {
        let amount: string = payments[i].amount;
        total = total + +amount;
      }
      return total;
    }
  }

  UMS.service('ReceivePaymentService', ReceivePaymentService);
}
