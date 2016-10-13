/**
 * Created by My Pc on 05-Oct-16.
 */
module ums{
  class AutoComplete implements ng.IDirective{
    //studentIds:any;
    static $inject=['$timeout'];
    constructor(public $timeout:ng.ITimeoutService){

    }
    public restrict:string="A";
    public scope={
      autoSuggestionList:'=suggestionList',
      addedStudents:'=x',
      firstSelected:'=firstSelected',
      secondSelected:'=secondSelected',
      showOneInputArea:'=showOneInputArea',
      addFirstSelectedValue:'&addFirst',
      addSecondSelectedValue:'&addSecond',
      addOneStudent:'&addOne',
      addRangeStudent:'&addRange'
    };

    public link = ($scope:any, element:any, attributes:any) => {


      console.log("Hello there");
      console.log($scope);
      $scope.selected={};
      var currentScope = this;

      $scope.$watch('showOneInputArea',(value,oldValue)=>{
        console.log("Showing the input area");
        console.log($scope.showOneInputArea);
        if($scope.showOneInputArea==true){
          $("#showTwo").hide();
        }else{
          $("#showOne").hide();
          console.log("Hiding");
        }
      });



      //$scope.$watch('firstSelected',(value, oldValue)=>{
      console.log("value changed");
      $( "#tags" ).autocomplete({
        source: $scope.autoSuggestionList,

        select:(event:any,ui:any)=>{
          var id= ui.item.value;
          console.log($scope);
          console.log(ui.item.value);
          $scope.addFirstSelectedValue({id:ui.item.value});

        }
      });
      //});

      //$scope.$watch('secondSelected',(value)=>{
      $( "#tags2" ).autocomplete({
        source: $scope.autoSuggestionList,

        select:(event,ui)=>{
          var id= ui.item.value;
          console.log($scope);
          console.log(ui.item.value);
          $scope.addSecondSelectedValue({id:ui.item.value});

        }
      });

      $( "#tags3" ).autocomplete({
        source: $scope.autoSuggestionList,

        select:(event:any,ui:any)=>{
          var id= ui.item.value;
          console.log($scope);
          console.log(ui.item.value);
          $scope.addFirstSelectedValue({id:ui.item.value});

        }
      });
      //});




      //$scope.$watch('addedStudents',(value)=>{
      currentScope.$timeout(()=>{
        $("#addOneBtn").click(()=>{
          console.log("in the click event");
          $scope.addOneStudent();
        });

        $("#addSrcBtn").click(()=>{
          console.log("in the click event");
          $scope.addOneStudent();
        });

        $("#addRangeBtn").click(()=>{
          console.log("in the click event");
          $scope.addRangeStudent();
        });
      });
      //});





    };

    public templateUrl:string = "./views/directive/auto-completion.html";
  }

  UMS.directive("autoComplete", ['$timeout',($timeout:ng.ITimeoutService) => new AutoComplete($timeout) ]);
}