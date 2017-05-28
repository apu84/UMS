module ums{
    export interface ITrainingInformationModel{
        id: number;
        employeeId: string;
        trainingName: string;
        trainingInstitution: string;
        trainingFrom: string;
        trainingTo: string;
        trainingDuration: string;
    }
}