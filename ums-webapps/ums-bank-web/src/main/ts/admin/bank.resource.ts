module ums {
  export class BankResource<T> {
    private _uri: string;
    private _data: T;
    private _headers: any;

    constructor(uri: string, data?: T, headers?: any) {
      this._uri = uri;
      this._data = data;
      this._headers = headers;
    }

    public getUri(): string {
      return this._uri;
    }

    public getData(): T {
      return this._data;
    }

    public getHeaders(): any {
      return this._headers;
    }
  }
}