module ums{
    export class CRUDDetectionService {
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public ObjectDetectionForCRUDOperation(baseObject: any, comparingObject: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let baseObjectsLength: number = baseObject.length;
            let comparingObjectsLength: number = comparingObject.length;
            for (let i = 0; i < baseObjectsLength; i++) {
                for (let j = 0; j < comparingObjectsLength; j++) {
                    if (angular.equals(baseObject[i], comparingObject[j])) {
                        comparingObject[j].dbAction = "No Change";
                        baseObject[i].dbAction = "No Change";
                    }
                }
            }
            for(let i = 0; i < baseObjectsLength; i++){
                for(let j = 0; j < comparingObjectsLength; j++){
                    if(angular.equals(baseObject[i], comparingObject[j]) == false){
                        if(baseObject[i].dbAction == "" && comparingObject[j].dbAction == ""){
                            comparingObject[j].dbAction = "Update";
                            baseObject[i].dbAction = "Update"
                        }
                    }
                }
            }
            for(let i = 0; i < baseObjectsLength; i++){
                if(baseObject[i].dbAction == ""){
                    baseObject[i].dbAction = "Delete";
                    comparingObject.push(baseObject[i]);
                }
            }
            defer.resolve(comparingObject);
            return defer.promise;
        }

        public isObjectEmpty(obj: Object): boolean {
            for (let key in obj) {
                if (obj.hasOwnProperty(key))
                    return false;
            }
            return true;
        }
    }

    UMS.service("cRUDDetectionService", CRUDDetectionService);
}