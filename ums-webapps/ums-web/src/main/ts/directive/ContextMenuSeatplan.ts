module ums{
  class ContextMenuDirective implements ng.IDirective{
    constructor(){

    }
    public restrict:string='EA';
    public scope:any={
      mergeInitialization:'&mergeInitialization',
      splitCourseStudent:'&splitCourseStudent',
      revertSplitAction:'&revertSplitAction',
      mergeGroups:'&mergeGroups'
    };

    //public templateUrl:string="./views/directive/context-menu.html";

    public link=(scope:ng.IScope,element:JQuery,attributes:ng.IAttributes)=>{


      var  currentScope = this;

      var color = element.css("color");
        element.addClass('mouse-effect').on("contextmenu", function (event) {

          // Avoid the real one
          event.preventDefault();

          // Show contextmenu
          $(".custom-menu").finish().toggle(100).

          // In the right position (the mouse)
          css({

            top: event.pageY - $("#topbar").height()+"px" ,
            left: event.pageX - $("#sidebar").width()+"px"
          });

        });


// If the document is clicked somewhere
        element.addClass('mouse-effect').bind("mousedown", function (e) {
          // If the clicked element is not the menu
          if (!($(e.target).parents(".custom-menu").length > 0)) {
            // Hide it
            $(".custom-menu").hide(100);
          }
        });

        /*with the mouse down jquery function, we are getting the event only of right button,
         * that's why the case is 3.
         * with the line: $(this).attr('id') , we are getting the id when the right mouse button click event is triggered.*/
        var classScope = this;
        /* element.mousedown(function(event){
         switch(event.which){
         case 1:
         currentScope.scope.mergeInitialization();
         break;
         case 3:

         break;
         }
         });*/



        element.addClass('context-menu li').click(function(){
          $(".custom-menu").hide(100);
          // This is the triggered action name
          switch($(this).attr("data-action")) {

            case "split":
              console.log("split");
              currentScope.scope.splitCourseStudent();
              break;
            case "revertSplit":
              currentScope.scope.revertSplitAction();
              break;
            case "merge":
              currentScope.scope.mergeGroups();
              break;
          }
          $(".custom-menu").hide(100);
        });




    }
  }
  UMS.directive("contextMenu",()=>new ContextMenuDirective())
}