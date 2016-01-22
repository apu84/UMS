///<reference path="jquery.d.ts" />

interface settings {
}

interface JQuery {
  notify1:Function;
  notify:{
    message: any;
  }
}
interface JQueryStatic {
  format(template: string, ...arguments: string[]): string;
  notify: any;
}