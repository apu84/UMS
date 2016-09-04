declare class URI {
  constructor();
  constructor(uri:string);
  pathname():string;
  pathname(pathname:string):URI;
  username():string;
  username(pathname:string):URI;
  password():string;
  password(pathname:string):URI;
  relativeTo(uri:URI):URI;
  absoluteTo(uri:URI):URI;
  is(name:string):boolean;
  host(): string;
  search(search:string):URI;
  search(map:boolean):string;
  fragment(fragment:string):URI;
  fragment():string;
  static withinString(source:string, filter:(url:string, start?:number, end?:number, source?:string)=>void, any):string;
  normalize():void;
  readable():string;
  addSearch(key:string, value: string);
}