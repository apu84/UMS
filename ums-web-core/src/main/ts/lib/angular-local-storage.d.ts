declare module ls {
  interface Cookie {
    set: (key:any, value:any)=>boolean;
    add: (key:any, value:any)=>boolean;
    get: (key:any)=>any;
    remove: (key:any)=>boolean;
    clearAll: ()=>boolean;
  }

  interface Service extends Cookie {
    isSupported: ()=>boolean;
    cookie: Cookie;
  }
}
