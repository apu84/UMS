// Type definitions for jQuery.slimScroll v1.3.3
// Project: https://github.com/rochal/jQuery-slimScroll
// Definitions by: Chintan Shah <https://github.com/Promact>
// Definitions: https://github.com/borisyankov/DefinitelyTyped
/// <reference path="jquery.d.ts"/>
interface JQuery {
  tokenfield:{
    (message: string, type: string): JQuery;
    (): JQuery;
    (options:any): JQuery;
  }
}