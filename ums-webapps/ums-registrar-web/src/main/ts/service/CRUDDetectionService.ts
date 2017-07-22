module ums{
    export class CRUDDetectionService {
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public ObjectDetectionForCRUDOperation(baseObject: any, comparingObject: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let comparingObjects: any = angular.copy(comparingObject);
            let baseObjectsLength: number = baseObject.length;
            let comparingObjectsLength: number = comparingObjects.length;
            let flag: number = 0;
            for (let i = 0; i < baseObjectsLength; i++) {
                for (let j = 0; j < comparingObjectsLength; j++) {
                    if (this.objectEqualityTest(baseObject[i], comparingObjects[j]) == true) {
                        comparingObjects[j].dbAction = "No Change";
                        baseObject[i].dbAction = "No Change";
                    }
                }
            }
            for(let i = 0; i < baseObjectsLength; i++){
                for(let j = 0; j < comparingObjectsLength; j++){
                    if(this.objectEqualityTest(baseObject[i], comparingObjects[j]) == false){
                        if(comparingObjects[j].dbAction == "" || comparingObjects[j].dbAction == undefined){
                            comparingObjects[j].dbAction = "Update";
                            baseObject[i].dbAction = "Update"
                        }
                    }
                }
            }
            for(let i = 0; i < baseObjectsLength; i++){
                if(baseObject[i].dbAction == "" || baseObject[i].dbAction == undefined){
                    baseObject[i].dbAction = "Delete";
                    console.log(baseObject[i]);
                    comparingObjects.push(baseObject[i]);
                }
            }
            defer.resolve(comparingObjects);
            return defer.promise;
        }

        private objectEqualityTest(baseObj: any, comparingObj: any): boolean{
            return angular.equals(baseObj, comparingObj);
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