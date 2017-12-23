module ums {
    interface ICirculation extends ng.IScope{
        checkoutSubmit: Function;
    }

    export class Circulation {
        public static $inject = ['HttpClient', '$state', '$scope', '$q', 'notify']; // 'userService'

        private text: string = "Hello World";
        private searchValue: string;
        private state: any;
        private circulationType: string;
        private user: User;
        private itemCode: string;
        private itemDueDate: string;
        constructor(private httpClient: HttpClient,
                    private $state: any,
                    private $scope: any,
                    private $q: ng.IQService,
                    private notify: Notify) {

            $scope.checkoutSubmit = this.checkoutSubmit.bind(this);
        }

        private doSearch(circulationType: string): void{
            if(circulationType == 'checkOut'){
                this.checkout(this.searchValue);
            }
            else if(circulationType == 'checkIn'){
                this.checkIn(this.searchValue);
            }
            else if(circulationType == 'searchPatron'){
                this.searchPatron(this.searchValue);
            }
            else{
                this.notify.error("Something went wrong!");
            }
        }

        private checkout(patronId: string): void{/*
            this.userService.fetchCurrentUserInfo().then((data: any) => {
                this.user = data;
                console.log(this.user);
            });*/
        }

        private checkIn(itemId: string): void{

        }

        private searchPatron(patronId: string): void{

        }

        private checkoutSubmit(): void{
            if(this.checkUser()){
                if((this.itemCode != undefined && this.itemCode != null && this.itemCode != "") && (this.itemDueDate != undefined && this.itemDueDate != null &&  this.itemDueDate != "")){
                    this.notify.success("success");
                }
                else{
                    this.notify.error("Please fill the form properly");
                }
            }
            else{
                this.notify.error("No user available! Search Patron Again.");
            }
        }

        private checkUser(): boolean{
            if(this.user == null)
                return false;
            else
                return true;
        }

    }

    UMS.controller("Circulation", Circulation);
}