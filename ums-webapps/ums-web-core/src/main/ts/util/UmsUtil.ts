module ums {
  export class UmsUtil {
    /**
     * File Content Types
     */
    static PDF: string="application/pdf";
    static XLS: string="application/vnd.ms-excel";
    static UNDERGRADUATE: number = 11;
    static POSTGRADUATE: number = 22;

    public static getNumberWithSuffix(n: number): string {
      var suffix: string = "";
      switch (n) {
        case 1:
          suffix = "st";
          break;
        case 2:
          suffix = "nd";
          break;
        case 3:
          suffix = "rd";
          break;
        default:
          suffix = "th";
          break;
      }
      return n + "" + suffix;
    }

    public static isEmpty(obj: any) {
      return Object.keys(obj).length === 0;
    }

    public static isEmptyString(str: string) {
      return (typeof str === 'undefined' || str === '' || str == null);
    }
    public static getFileContentType(fileType:string):string {
      var contentType:string="";
      switch (fileType)
      {
        case'pdf':
          contentType=this.PDF;
          break;
        case'xls':
          contentType=this.XLS;
          break;
        default:
          alert("Wrong file type.........");
      }
      return contentType;
    }
    public static writeFileContent(data:any,contentType:string,fileName:string){
      var file = new Blob([data], {type: contentType});
      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = function (e) {
        UmsUtil.saveAsFile(reader.result, fileName);
      }
    }
    public static  saveAsFile(url, fileName) {
      var a: any = document.createElement("a");
      document.body.appendChild(a);
      a.style = "display: none";
      a.href = url;
      a.download = fileName;
      a.click();
      window.URL.revokeObjectURL(url);
      $(a).remove();
    }
  }
}