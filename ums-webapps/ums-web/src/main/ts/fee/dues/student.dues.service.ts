module ums {
  export interface StudentDue {
    id: string;
    studentId: string;
    feeCategoryId: string;
    feeCategoryName: string;
    payBefore: string;
    amount: number;
    lastModified: string;
    transactionId: string;
    transactionStatus: string;
    appliedOn: string;
    verifiedOn: string;
  }

  interface StudentDuesResponse {
    entries: StudentDue[];
  }

  export class StudentDuesService {
    public static $inject = ['$q', 'HttpClient', 'FeeTypeService', 'FeeCategoryService'];
    public static DUES: string = "DUES";
    public static PENALTY: string = "PENALTY";

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient,
                private feeTypeService: FeeTypeService,
                private feeCategoryService: FeeCategoryService) {

    }

    public getDues(): ng.IPromise<StudentDue[]> {
      let defer: ng.IDeferred<StudentDue[]> = this.$q.defer();
      this.httpClient.get('student-dues', HttpClient.MIME_TYPE_JSON,
          (response: StudentDuesResponse) => defer.resolve(response.entries));
      return defer.promise;
    }

    public payDues(dues: string[]): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.put('student-dues/payDues', {"entries": dues}, HttpClient.MIME_TYPE_JSON)
          .success(() => defer.resolve(true))
          .error(() => defer.resolve(false));
      return defer.promise;
    }

    public getFeeCategories(): ng.IPromise<FeeCategory[]> {
      let defer: ng.IDeferred<FeeCategory[]> = this.$q.defer();
      this.feeTypeService.getFeeTypes().then((feeTypes: FeeType[]) => {
        for (let i = 0; i < feeTypes.length; i++) {
          if (feeTypes[i].name === StudentDuesService.DUES
              || feeTypes[i].name === StudentDuesService.PENALTY) {
            this.feeCategoryService.getFeeCategories(feeTypes[i].id).then(
                (feeCategories: FeeCategory[]) => defer.resolve(feeCategories)
            );
          }
        }
      });
      return defer.promise;
    }

    public listDues(): ng.IPromise<StudentDue[]> {
      let defer: ng.IDeferred<StudentDue[]> = this.$q.defer();
      return defer.promise;
    }
  }

  UMS.service('StudentDuesService', StudentDuesService);
}
