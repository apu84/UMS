module ums{
    export class CRUDDetectionService {
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public ObjectDetectionForCRUDOperation(baseObject: any, comparingObject: any, deletedObjects?: any): ng.IPromise<any> {
            var defer = this.$q.defer();
            var baseObjectsLength: number = baseObject.length;
            var comparingObjectsLength: number = comparingObject.length;
            for (var i = 0; i < baseObjectsLength; i++) {
                for (var j = 0; j < comparingObjectsLength; j++) {
                    if (angular.equals(baseObject[i], comparingObject[j])) {
                        comparingObject[j].dbAction = "No Change";
                        baseObject[i].dbAction = "No Change";
                    }
                }
            }
            for(var i = 0; i < deletedObjects.length; i++){
                comparingObject.push(deletedObjects[i]);
            }
            for(var i = 0; i < baseObjectsLength; i++){
                for(var j = 0; j < comparingObjectsLength; j++){
                    if(!angular.equals(baseObject[i], comparingObject[j])){
                        if(baseObject[i].dbAction == "" && comparingObject[j].dbAction == ""){
                            comparingObject[j].dbAction = "Update";
                            baseObject[i].dbAction = "Update"
                        }
                    }
                }
            }
            // for(var i = 0; i < baseObjectsLength; i++){
            //     if(baseObject[i].dbAction == ""){
            //         baseObject[i].dbAction = "Delete";
            //         comparingObject.push(baseObject[i]);
            //     }
            // }
            defer.resolve(comparingObject);
            return defer.promise;
        }

        public isObjectEmpty(obj: Object): boolean {
            for (var key in obj) {
                if (obj.hasOwnProperty(key))
                    return false;
            }
            return true;
        }

        public ObjectDetectionForServiceObjects(baseObject: any, comparingObject: any, serviceDeletedObjects?: any, intervalDeletedObjects?: any): ng.IPromise<any> {
            var defer = this.$q.defer();
            var baseObjectsLength: number = baseObject.length;
            var comparingObjectsLength: number = comparingObject.length;
            for (var i = 0; i < baseObjectsLength; i++) {
                for (var j = 0; j < comparingObjectsLength; j++) {
                    if (angular.equals(baseObject[i], comparingObject[j])) {
                        comparingObject[j].dbAction = "No Change";
                        baseObject[i].dbAction = "No Change";
                        for(var k = 0; k < comparingObject[j].intervalDetails.length; k++) {
                            comparingObject[j].intervalDetails[k].dbAction = "No Change";
                            baseObject[j].intervalDetails[k].dbAction = "No Change";
                        }
                    }
                }
            }
            for(var i = 0; i < serviceDeletedObjects.length; i++){
                for(var k = 0; k < serviceDeletedObjects[i].intervalDetails.length; k++){
                    serviceDeletedObjects[i].intervalDetails[k].dbAction = "Delete";
                }
                comparingObject.push(serviceDeletedObjects[i]);
            }
            for(var i = 0; i < baseObjectsLength; i++){
                for(var j = 0; j < comparingObjectsLength; j++){
                    if(!angular.equals(baseObject[i], comparingObject[j])){ 
                        if(comparingObject[j].dbAction == ""){
                            comparingObject[j].dbAction = "Update";
                            baseObject[i].dbAction = "Update";
                            this.ObjectDetectionForCRUDOperation(baseObject[i].intervalDetails, comparingObject[j].intervalDetails, intervalDeletedObjects).then((obj: any)=>{
                                comparingObject[j].intervalDetails = obj;
                            });
                        }
                    }
                }
            }
            defer.resolve(comparingObject);
            return defer.promise;
        }
    }

    UMS.service("cRUDDetectionService", CRUDDetectionService);
}