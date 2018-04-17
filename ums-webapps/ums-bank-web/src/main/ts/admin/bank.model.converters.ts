module ums {
  export interface Converter<T> {
    serialize(obj: T): any;

    deserialize(obj: any): T;
  }

  export class IdentityConverter<T> implements Converter<T> {
    serialize(obj: T): any {
      return obj;
    }

    deserialize(obj: any): T {
      return obj;
    }
  }
}