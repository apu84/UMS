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

        public ObjectDetectionForServiceObjects(baseObject: any, comparingObject: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let baseObjectsLength: number = baseObject.length;
            let comparingObjectsLength: number = comparingObject.length;
            for (let i = 0; i < baseObjectsLength; i++) {
                for (let j = 0; j < comparingObjectsLength; j++) {
                    if (angular.equals(baseObject[i], comparingObject[j])) {
                        comparingObject[j].dbAction = "No Change";
                        baseObject[i].dbAction = "No Change";

                        for(let k = 0; k < comparingObject[j].intervalDetails.length; k++){
                            comparingObject[j].intervalDetails[k].dbAction = "No Change";
                            baseObject[j].intervalDetails[k].dbAction = "No Change";
                        }
                    }
                }
            }
            for(let i = 0; i < baseObjectsLength; i++){
                for(let j = 0; j < comparingObjectsLength; j++){
                    if(angular.equals(baseObject[i], comparingObject[j]) == false){
                        if(baseObject[i].dbAction == "" && comparingObject[j].dbAction == ""){
                            comparingObject[j].dbAction = "Update";
                            baseObject[i].dbAction = "Update";

                            for(let m = 0; m < baseObject[i].intervalDetails.length; m++) {
                                for (let n = 0; n < comparingObject[j].intervalDetails.length; n++) {
                                    if (angular.equals(baseObject[i].intervalDetails[m], comparingObject[j].intervalDetails[n])) {
                                        comparingObject[j].intervalDetails[n].dbAction = "No Change";
                                        baseObject[i].intervalDetails[m].dbAction = "No Change";
                                    }
                                }
                            }

                            // for(let m = 0; m < baseObject[i].intervalDetails.length; m++){
                            //     for(let n = 0; n < comparingObject[j].intervalDetails.length; n++){
                            //         if(baseObject[i].intervalDetails[m].id == comparingObject[j].intervalDetails[n].id){
                            //         }
                            //         else if(n == comparingObject[j].intervalDetails.length - 1 && baseObject[i].intervalDetails[m].id != comparingObject[j].intervalDetails[n].id){
                            //             baseObject[i].intervalDetails[m].dbAction = "Delete";
                            //             comparingObject[j].intervalDetails.push(baseObject[i]);
                            //         }
                            //     }
                            // }

                            for(let m = 0; m < baseObject[i].intervalDetails.length; m++) {
                                for (let n = 0; n < comparingObject[j].intervalDetails.length; n++) {
                                    if (angular.equals(baseObject[i].intervalDetails[m], comparingObject[j].intervalDetails[n]) == false) {
                                        if(baseObject[i].intervalDetails[m].dbAction == "" && comparingObject[j].intervalDetails[n].dbAction == "") {
                                            comparingObject[j].intervalDetails[n].dbAction = "Update";
                                            baseObject[i].intervalDetails[m].dbAction = "Update";
                                        }
                                    }
                                }
                            }
                            for(let m = 0; m < baseObject[i].intervalDetails.length; m++){
                                if(baseObject[i].intervalDetails[m].dbAction == ""){
                                    baseObject[i].intervalDetails[m].dbAction = "Delete";
                                    comparingObject[j].intervalDetails.push(baseObject[i]);
                                }
                            }
                        }
                    }
                }
            }
            for(let i = 0; i < baseObjectsLength; i++){
                if(baseObject[i].dbAction == ""){
                    baseObject[i].dbAction = "Delete";
                    for(let k = 0; k < baseObject[i].intervalDetails.length; k++){
                        baseObject[i].intervalDetails[k].dbAction = "Delete";
                    }
                    comparingObject.push(baseObject[i]);
                }
            }
            defer.resolve(comparingObject);
            return defer.promise;
        }
    }

    UMS.service("cRUDDetectionService", CRUDDetectionService);
}