module ums {
    export class ProfilePicture implements ng.IDirective {
        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService) {

        }

        public restrict: string = 'A';
        public scope = {
            userId: "=imgId"
        };

        public link = (scope: any, element: JQuery, attributes: any) => {
            setTimeout(() => {
                var uId = "0";
                if(scope.userId != undefined && scope.userId != null && scope.userId != ""){
                    uId = scope.userId;
                }
                this.httpClient.get("profilePicture/" + uId, '', (data: any, etag: string) => {
                    var arr = new Uint8Array(data);
                    var raw = '';
                    var i, j, subArray, chunk = 5000;
                    for (i = 0, j = arr.length; i < j; i += chunk) {
                        subArray = arr.subarray(i, i + chunk);
                        raw += String.fromCharCode.apply(null, subArray);
                    }
                    var b64 = btoa(raw);
                    var dataURL = "data:image/jpeg;base64," + b64;
                    $(element).attr('src', dataURL);
                }, (response) => {
                    //console.error(response);
                }, 'arraybuffer');
            }, 200);
        }
    }

    UMS.directive("profilePicture", ['HttpClient', '$q', (httpClient, $q) => {
        return new ProfilePicture(httpClient, $q);
    }])
}