module ums{
    export interface ITrainingInformationModel{
        id: string;
        employeeId: string;
        trainingName: string;
        trainingInstitution: string;
        trainingFrom: string;
        trainingTo: string;
        trainingDuration: number;
        trainingDurationString: string;
        trainingCategory: ICommon;
    }
}