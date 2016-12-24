///<reference path="jquery.d.ts" />



interface JQuery {
  wysihtml5:{
    message: any;
    (options?: any): JQuery;
  }
}
interface JQueryStatic {
  format(template: string, ...arguments: string[]): string;
  wysihtml5: any;
}