module ums {
  export class FileUpload {
    public static $inject = ['HttpClient'];

    constructor(private httpClient:HttpClient) {

    }

    public uploadFile(file:any, contentType:string, fileName:string, uploadUri:string):void {
      this.httpClient.post(uploadUri, file, contentType, fileName)
          .success(function () {
          })
          .error(function () {
          });
    }
  }

  UMS.service("FileUpload", FileUpload);
}