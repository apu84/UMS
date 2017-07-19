/**
 * Created by My Pc on 17-Jul-17.
 */
module ums {
  export interface Attachment {
    id: string,
    type: number,
    typeId: string;
    fileName: string;
    file: any
  }
}