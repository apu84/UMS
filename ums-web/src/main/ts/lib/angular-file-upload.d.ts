/// <reference path='angular.d.ts'/>

declare module upload {
  export interface Params {
    url:string;
    method?:string;
    headers?:any;
    data?:any;
    file?:File;
  }

  export interface ProgressCallback {
    (event?:ProgressEvent):void;
  }

  export interface Promise<T> extends ng.IHttpPromise<T> {
    progress(callback:ProgressCallback):Promise<T>;
    then<TResult>(successCallback: (response: ng.IHttpPromiseCallbackArg<T>) => TResult, errorCallback?: (response: ng.IHttpPromiseCallbackArg<T>) => any, progressCallback?:ProgressCallback):Promise<T>
    then<TResult>(successCallback: (response: ng.IHttpPromiseCallbackArg<T>) => Promise<TResult>, errorCallback?: (response: ng.IHttpPromiseCallbackArg<T>) => any, progressCallback?:ProgressCallback):Promise<T>
  }

  export interface Upload {
    upload(params:Params):Promise<any>;
    http(params:Params):Promise<any>;
  }
}
