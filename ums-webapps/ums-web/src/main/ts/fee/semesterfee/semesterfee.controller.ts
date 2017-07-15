module ums {
  export class SemesterFeeController {
    public static $inject = ['SemesterFeeService', 'PaymentService', 'StudentInfoService', 'notify'];
    private NOT_WITHIN_SLOT: string = 'Not within admission slot';
    private ADMITTED: string = 'Admission is completed';
    private READMISSION_NOT_APPLIED: string = 'No readmission application found';
    public messageText: string;
    public payments: PaymentGroup;
    public fee: SemesterFee[];
    public installmentEnabled: boolean = false;
    public currentEnrolledSemester: number;
    public firstInstallment: boolean;
    public secondInstallment: boolean;
    public regularPayment: boolean;

    constructor(private semesterFeeService: SemesterFeeService,
                private paymentService: PaymentService,
                private studentService: StudentInfoService,
                private notify: Notify) {
      studentService.getStudent().then((student: Student) => {
        if (student && student.currentEnrolledSemesterId) {
          semesterFeeService.getSemesterFeeStatus(student.currentEnrolledSemesterId).then((response: string) => {
            switch (response) {
              case SemesterFeeResponse.responseType.ADMITTED :
                this.messageText = this.ADMITTED;
                this.showPaymentStatus(student.currentEnrolledSemesterId);
                break;
              case SemesterFeeResponse.responseType.ALLOWED:
                this.admissionFee(student.currentEnrolledSemesterId);
                break;
              case SemesterFeeResponse.responseType.APPLIED:
                this.showPaymentStatus(student.currentEnrolledSemesterId);
                break;
              case SemesterFeeResponse.responseType.NOT_WITHIN_SLOT:
                this.messageText = this.NOT_WITHIN_SLOT;
                this.showPaymentStatus(student.currentEnrolledSemesterId);
                break;
            }
          });
        }
      });
    }

    public pay(): void {
      this.studentService.getStudent().then((student: Student) => {
        if (student && student.currentEnrolledSemesterId) {
          this.semesterFeeService.payFee(student.currentEnrolledSemesterId).then(
              (response: string) => {
                if (response !== SemesterFeeResponse.responseType.APPLIED) {
                  this.notify.error(response);
                }
                else {
                  this.showPaymentStatus(student.currentEnrolledSemesterId);
                }
              }
          );
        }
      });
    }

    public payFirstInstallment(): void {
      this.studentService.getStudent().then((student: Student) => {
        if (student && student.currentEnrolledSemesterId) {
          this.semesterFeeService.payFirstInstallment(student.currentEnrolledSemesterId).then(
              (response: string) => {
                if (response !== SemesterFeeResponse.responseType.APPLIED) {
                  this.notify.error(response);
                }
                else {
                  this.showPaymentStatus(student.currentEnrolledSemesterId);
                }
              }
          );
        }
      });
    }

    public paySecondInstallment(): void {
      this.studentService.getStudent().then((student: Student) => {
        if (student && student.currentEnrolledSemesterId) {
          this.semesterFeeService.paySecondInstallment(student.currentEnrolledSemesterId).then(
              (response: string) => {
                if (response !== SemesterFeeResponse.responseType.APPLIED) {
                  this.notify.error(response);
                }
                else {
                  this.showPaymentStatus(student.currentEnrolledSemesterId);
                }
              }
          );
        }
      });
    }

    public selectPaymentOption(option: number, semesterId: number) {
      this.messageText = '';
      if (option === 0) {
        this.admissionNoInstallment(semesterId);
      }
      else if (option === 1) {
        this.semesterFeeService.withInFirstInstallmentSlot(semesterId).then(
            (withinSlot) => {
              if (withinSlot) {
                this.semesterFeeService.firstInstallment(semesterId).then(
                    (fee: SemesterFee[]) => {
                      this.fee = fee;
                      this.firstInstallment = true;
                      this.regularPayment = false;
                    }
                );
              }
              else {
                this.messageText = this.NOT_WITHIN_SLOT;
              }
            }
        );
      }
    }

    private admissionFee(semesterId: number): void {
      this.semesterFeeService.getSemesterInstallmentStatus(semesterId).then(
          (semesterInstallmentStatus: string) => {
            if (semesterInstallmentStatus === SemesterFeeResponse.responseType.INSTALLMENT_AVAILABLE) {
              this.semesterFeeService.getInstallmentStatus(semesterId).then(
                  (installmentStatus: string) => {

                    switch (installmentStatus) {
                      case SemesterFeeResponse.responseType.ADMITTED:
                        this.messageText = this.ADMITTED;
                        break;
                      case SemesterFeeResponse.responseType.FIRST_INSTALLMENT_PAID:
                        this.secondInstallmentInfo(semesterId);
                        break;
                      case SemesterFeeResponse.responseType.INSTALLMENT_NOT_TAKEN:
                        this.semesterFeeService.getAdmissionStatus(semesterId).then(
                            (admissionStatus: string) => {
                              switch (admissionStatus) {
                                case SemesterFeeResponse.responseType.REGULAR_ADMISSION:
                                  this.admissionWithInstallmentOption(semesterId);
                                  break;
                                case SemesterFeeResponse.responseType.READMISSION_APPLIED:
                                  this.admissionWithInstallmentOption(semesterId);
                                  break;
                                case SemesterFeeResponse.responseType.READMISSION_NOT_APPLIED:
                                  this.messageText = this.READMISSION_NOT_APPLIED;
                                  break;
                              }
                            }
                        );
                        break;
                    }
                  });
            }
            else {
              this.semesterFeeService.getAdmissionStatus(semesterId).then(
                  (admissionStatus: string) => {
                    switch (admissionStatus) {
                      case SemesterFeeResponse.responseType.REGULAR_ADMISSION:
                        this.admissionNoInstallment(semesterId);
                        break;
                      case SemesterFeeResponse.responseType.READMISSION_APPLIED:
                        this.admissionNoInstallment(semesterId);
                        break;
                      case SemesterFeeResponse.responseType.READMISSION_NOT_APPLIED:
                        this.messageText = this.READMISSION_NOT_APPLIED;
                        break;
                    }
                  }
              );
            }
          });
    }

    private admissionWithInstallmentOption(semesterId: number): void {
      this.semesterFeeService.installmentAvailable(semesterId).then(
          (available: boolean) => {
            if (available) {
              this.semesterFeeService.withInAdmissionSlot(semesterId).then(
                  (withinSlot) => {
                    if (withinSlot) {
                      this.installmentEnabled = true;
                      this.currentEnrolledSemester = semesterId;
                    }
                    else {
                      this.messageText = this.NOT_WITHIN_SLOT;
                    }
                  });
            }
            else {
              this.admissionNoInstallment(semesterId);
            }
          });
    }

    private admissionNoInstallment(semesterId: number): void {
      this.semesterFeeService.withInAdmissionSlot(semesterId).then((withinSlot: boolean) => {
        if (withinSlot) {
          this.semesterFeeService.fee(semesterId).then(
              (fee: SemesterFee[]) => {
                this.fee = fee;
                this.regularPayment = true;
                this.secondInstallment = false;
                this.firstInstallment = false;
              }
          );
        }
        else {
          this.messageText = this.NOT_WITHIN_SLOT;
        }
      });
    }

    private secondInstallmentInfo(semesterId: number): void {
      this.semesterFeeService.withInSecondInstallmentSlot(semesterId).then(
          (withInSlot: boolean) => {
            if (withInSlot) {
              this.semesterFeeService.secondInstallment(semesterId).then(
                  (fee: SemesterFee[]) => {
                    this.fee = fee;
                    this.regularPayment = false;
                    this.secondInstallment = true;
                  }
              );
            }
            else {
              this.messageText = 'Your first installment for semester fee is paid. Second installment payment slot has not started yet or already over.';
              this.studentService.getStudent().then((student: Student) => {
                if (student && student.currentEnrolledSemesterId) {
                  this.showPaymentStatus(student.currentEnrolledSemesterId);
                }
              });
            }
          });
    }

    private showPaymentStatus(semesterId: number): void {
      this.paymentService.getSemesterFeePaymentStatus(semesterId).then((payments: Payment[]) => {
        let paymentGroup: PaymentGroup = {};
        this.payments = payments.reduce((group: PaymentGroup, payment) => {
          if (!group[payment.transactionId]) {
            group[payment.transactionId] = []
          }
          group[payment.transactionId].push(payment);
          return group;
        }, paymentGroup);
        console.log(this.payments);
      });
    }
  }

  UMS.controller('SemesterFeeController', SemesterFeeController);
}
