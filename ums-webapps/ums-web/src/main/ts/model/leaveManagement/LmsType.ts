/**
 * Created by Monjur-E-Morshed on 09-May-17.
 */
module ums {
  export interface LmsType {
    id: number;
    name: string;
    duration: number;
    durationType: number;
    durationLabel: string;
    maxDuration: number;
    maxSimultaneousDuration: number;
    salaryType: number;
    salaryTypeLabel: string;

  }
}