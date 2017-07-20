/**
 * Created by My Pc on 17-Jul-17.
 */
module ums {
  export interface Attachment {
    id: string,
    type: number,
    applicationId: string;
    fileName: string;
    serverFileName: string;
  }
}