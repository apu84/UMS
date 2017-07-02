module ums {
  export interface StudentDue {
    id?: string;
    studentId: string;
    feeCategoryId: string;
    feeCategoryName?: string;
    payBefore: string;
    amount?: string;
    lastModified?: string;
    transactionId?: string;
    transactionStatus?: string;
    appliedOn?: string;
    verifiedOn?: string;
    description?: string;
  }

  export interface Filter {
    id?: number;
    key?: string;
    value?: any;
    label?: string;
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
      let promises: ng.IPromise<FeeCategory[]>[] = [];
      this.feeTypeService.getFeeTypes().then((feeTypes: FeeType[]) => {
        for (let i = 0; i < feeTypes.length; i++) {
          if (feeTypes[i].name === StudentDuesService.PENALTY
              || feeTypes[i].name === StudentDuesService.DUES) {
            promises.push(this.feeCategoryService.getFeeCategories(feeTypes[i].id));
          }
        }
        this.$q.all(promises).then((data) => {
          defer.resolve(data[0].concat(data[1]));
        });
      });
      return defer.promise;
    }

    public listDues(filters: Filter[], url?: string): ng.IPromise<StudentDue[]> {
      let defer: ng.IDeferred<StudentDue[]> = this.$q.defer();
      this.httpClient.post(url ? url : 'student-dues/paginated', filters ? {"entries": filters} : {},
          HttpClient.MIME_TYPE_JSON)
          .success((response: StudentDuesResponse) => {
            defer.resolve(response.entries);
          })
          .error((error) => {
            console.error(error);
            defer.resolve([]);
          });
      return defer.promise;
    }

    public addDues(due: StudentDue): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      due.amount = due.amount + '';
      this.httpClient.post('student-dues', {"entries": [due]}, HttpClient.MIME_TYPE_JSON)
          .success(() => {
            defer.resolve(true);
          })
          .error(() => defer.resolve(false));
      return defer.promise;
    }

    public updateDues(due: StudentDue): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      due.amount = due.amount + '';
      this.httpClient.put(`student-dues/updateDues/${due.studentId}`, {"entries": [due]}, HttpClient.MIME_TYPE_JSON)
          .success(() => {
            defer.resolve(true);
          })
          .error(() => defer.resolve(false));
      return defer.promise;
    }
  }

  UMS.service('StudentDuesService', StudentDuesService);
}
