///<reference path="jquery.d.ts" />

interface settings {
  life: any;
  theme:any;
  sticky: any;
  verticalEdge: any;
  horizontalEdge: any;
  zindex: any;
}

interface JQuery {
  notific8:{
    message: any;
    (options?: any): JQuery;
  }
}
interface JQueryStatic {
  format(template: string, ...arguments: string[]): string;
  notific8: any;
}