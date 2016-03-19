///<reference path="jquery.d.ts" />

interface JQueryNotifyOptions {
  // whether to hide the notification on click
  clickToHide?: boolean;
  // whether to auto-hide the notification
  autoHide?: boolean;
  // if autoHide; hide after milliseconds
  autoHideDelay?: number;
  // show the arrow pointing at the element
  arrowShow?: boolean;
  // arrow size in pixels
  arrowSize?: number;
  // position defines the notification position though uses the defaults below
  position?: string;
  // default positions
  elementPosition?: string;
  globalPosition?: string;
  // default style
  style?: string;
  // default class (string or [string])
  className?: string;
  // show animation
  showAnimation?: string;
  // show animation duration
  showDuration?: number;
  // hide animation
  hideAnimation?: number;
  // hide animation duration
  hideDuration?: number;
  // padding between element and notification
  gap?:number
}

interface JQueryNotify {
  (message: string, type: string, option?: JQueryNotifyOptions): JQueryNotify;
  defaults(option: JQueryNotifyOptions);
}

interface JQueryStatic {
  notify: JQueryNotify;
}