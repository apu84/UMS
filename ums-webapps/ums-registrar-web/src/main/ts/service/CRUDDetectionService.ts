module ums{
    export class CRUDDetectionService {
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public ObjectDetectionForCRUDOperation(baseObject: any, comparingObject: any): any {
            let copyOfComparingArrayOfObjects: any;
            copyOfComparingArrayOfObjects = angular.copy(comparingObject);
            let baseArrayOfObjectsLength: number = baseObject.length;
            let comparingArrayOfObjectsLength: number = copyOfComparingArrayOfObjects.length;
            let flag: number = 0;
            for (let i = 0; i < baseArrayOfObjectsLength; i++) {
                for (let j = 0; j < comparingArrayOfObjectsLength; j++) {
                    if (baseObject[i].id == copyOfComparingArrayOfObjects[j].id) {
                        if (this.objectEqualityTest(baseObject[i], copyOfComparingArrayOfObjects[i]) == true) {
                            copyOfComparingArrayOfObjects[i].dbAction = "No Change";
                        } else {
                            copyOfComparingArrayOfObjects[i].dbAction = "Update";
                        }
                        flag = 1;
                        break;
                    }
                    else {
                        flag = 0;
                    }
                }
                if (flag == 0) {
                    flag = 0;
                    copyOfComparingArrayOfObjects.push(baseObject[i]);
                }
            }
            for (let i = 0; i < comparingArrayOfObjectsLength; i++) {
                if (copyOfComparingArrayOfObjects[i].id == null) {
                    copyOfComparingArrayOfObjects[i].dbAction = "Create";
                }
            }
            return copyOfComparingArrayOfObjects;
        }

        public objectEqualityTest(baseObj: any, comparingObj: any): boolean{
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