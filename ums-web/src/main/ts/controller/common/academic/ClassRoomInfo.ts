///<reference path="../../../model/master_data/ClassRoom.ts"/>
///<reference path="../../../service/HttpClient.ts"/>
///<reference path="../../../lib/jquery.notific8.d.ts"/>
///<reference path="../../../lib/jquery.notify.d.ts"/>
///<reference path="../../../lib/jquery.jqGrid.d.ts"/>
module ums {

  interface IClassRoomScope extends ng.IScope {
    addNewRoom: Function;
    updateRoom: Function;
    classRoom:ClassRoom;
    data:any;
    rowId:any;
  }

  export class ClassRoomInfo {
    public static $inject = ['appConstants', 'HttpClient','$scope'];
    constructor(private appConstants:any,private httpClient:HttpClient,private $scope:IClassRoomScope) {

      $.jgrid.defaults.responsive = true;
      $.jgrid.defaults.styleUI = 'Bootstrap';

      var lastSel,
          cancelEditing = function(myGrid) {
            var lrid;
            if (typeof lastSel !== "undefined") {
              // cancel editing of the previous selected row if it was in editing state.
              // jqGrid hold intern savedRow array inside of jqGrid object,
              // so it is safe to call restoreRow method with any id parameter
              // if jqGrid not in editing state
              myGrid.jqGrid('restoreRow',lastSel);

              // now we need to restore the icons in the formatter:"actions"
              lrid = $.jgrid.jqID(lastSel);
              $("tr#" + lrid + " div.ui-inline-edit, " + "tr#" + lrid + " div.ui-inline-del").show();
              $("tr#" + lrid + " div.ui-inline-save, " + "tr#" + lrid + " div.ui-inline-cancel").hide();
            }
          };

      $.extend($.jgrid.defaults, {
        ajaxRowOptions: { contentType: "application/json", type: "PUT", async: true,
          beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Authorization", 'Basic ZHByZWdpc3RyYXI6MTIzNDU=');
            jqXHR.setRequestHeader("If-Match", '*');
          },
          complete: function(res, stat) {

            if (res.status==200 || res.status==204) {
              $("#jqGrid").trigger("reloadGrid");
              //   return [true, 'OK' ];

            } else {
              return false;
            }
          }

        },
        serializeRowData: function (data) {
          var propertyName, propertyValue, dataToSend = {};
          for (propertyName in data) {
            if (data.hasOwnProperty(propertyName)) {
              propertyValue = data[propertyName];
              if ($.isFunction(propertyValue)) {
                dataToSend[propertyName] = propertyValue();
              } else {
                dataToSend[propertyName] = propertyValue;
              }
            }
          }
          return JSON.stringify(dataToSend);
        }
      });

      /* THIS IS ONE FOR FORM EDIT AND DELETE */
      /*
      $.extend($.jgrid.del, {
        ajaxEditOptions: {contentType: "application/json",async: true},
        mtype: 'DELETE'
      });
      $.extend($.jgrid.edit, {
        ajaxEditOptions: {contentType: "application/json",async: true},
        mtype: 'PUT'
      });
     */

      var thisGrid =$("#jqGrid").jqGrid({
        datatype: "json",
        url: 'https://localhost/ums-webservice-common/academic/classroom/all',
        editurl:'https://localhost/ums-webservice-common/academic/classroom',
        loadBeforeSend: function(jqXHR) {
          jqXHR.setRequestHeader("Authorization", 'Basic ZHByZWdpc3RyYXI6MTIzNDU=');
        },
        jsonReader: { repeatitems: false },
        colModel: [
          {
            label: 'Room Id',
            name: 'id',
            width: 10,
            hidden:true,
            key: true
          },
          {
            label: 'Room Number',
            name: 'roomNo',
            editable: true,
            width: 100
          },
          {
            label: 'Description',
            name: 'description',
            editable: true,
            width: 200
          },
          {
            label : 'Row',
            name: 'totalRow',
            width: 50,
            editable: true // must set editable to true if you want to make the field editable
          },
          {
            label: 'Column',
            name: 'totalColumn',
            width: 50,
            editable: true
          },
          {
            label: 'Capacity',
            name: 'capacity',
            width: 50,
            editable: true
          },
          {
            label: 'Room Type',
            name: 'roomType',
            editable: true,
            width: 100, align: 'center', formatter: 'select',
            edittype: 'select',
            editoptions: {
              value: '1:Theory;2:Sessional;0:Others',
              defaultValue: 'Theory'
            },
            stype: 'select',
            searchoptions: {
              sopt: ['eq', 'ne'],
              value: '1:Theory;2:Sessional;0:Others'
            }},


          {
            label: 'Dept./School',
            name: 'capacity',
            width: 100,
            editable: true
          },
          {
            label: 'Seat Plan',
            name: 'examSeatPlan',
            editable: true,
            width: 80, align: 'center', formatter: 'checkbox',
            edittype: 'checkbox', editoptions: {value: '1:0', defaultValue: '1'},
            stype: 'select',
            searchoptions: {
              sopt: ['eq', 'ne'],
              value: '1:Yes;0:No'
            }


          },
          {
            label: "Edit Actions",
            name: "actions",
            width: 100,
            formatter: "actions",
            formatoptions: {
              keys: true,
              editOptions: {
              },
              addOptions: {
                mtype: 'POST'
              },
              delOptions: {
                mtype: 'DELETE',
                onclickSubmit: function(rp_ge) {
                  var selrow_id = thisGrid.getGridParam('selrow');
                  var rowdata = thisGrid.getRowData(selrow_id);
                  rp_ge.url = "https://localhost/ums-webservice-common/academic/classroom" + '/' + selrow_id ;
                },
                ajaxDelOptions: {
                  contentType: "application/json",
                  beforeSend: function(jqXHR) {
                    jqXHR.setRequestHeader("Authorization", 'Basic ZHByZWdpc3RyYXI6MTIzNDU=');
                  }
                },
                serializeDelData: function(postdata) {
                  return JSON.stringify(postdata);
                }
              },
              onEdit: function (id) {
                if (typeof (lastSel) !== "undefined" && id !== lastSel) {
                  cancelEditing(thisGrid);
                }
                lastSel = id;
                $("#jqGrid").setGridParam({ editurl: "https://localhost/ums-webservice-common/academic/classroom/" + encodeURIComponent(id)});
                //$("#jqGrid").jqGrid('editRow', id, true, null, null, "https://localhost/ums-webservice-common/academic/classroom/" + encodeURIComponent(id));
              }

            }
          }
        ],
        sortname: 'id',
        loadonce: true,
        autowidth: true,
        pager: "#jqGridPager",
        rownumbers: true,
        height:500,

        rowList: [],        // disable page size dropdown
        pgbuttons: false,     // disable page control like next, back button
        pgtext: null,

        ondblClickRow: function(id, ri, ci,e) {
          if (typeof (lastSel) !== "undefined" && id !== lastSel) {
            cancelEditing($(this));
          }
          lastSel = id;
          var lrid = $.jgrid.jqID(lastSel);
          if (!e) e = window.event; // get browser independent object
          var element = e.target || e.srcElement;
          /*
          alert("abc "+element);
          var abc={
            keys: true,
            oneditfunc: function (rowId) {
              $("input, select", element).focus(); // Now set focus on selected field
            },
            url:"https://localhost/ums-webservice-common/academic/classroom/"+ encodeURIComponent(id)
          };*/

          $("#jqGrid").jqGrid('editRow',id,true,function() {
            var colModel = jQuery("#jqGrid").jqGrid ('getGridParam', 'colModel');
            var colName = colModel[ci].name;
            var input = $('#' + id + '_' + colName);
            console.log(input)
            setTimeout(function(){  input.get(0).focus(); }, 300);

          },null,"https://localhost/ums-webservice-common/academic/classroom/"+ encodeURIComponent(id));
          //$("#jqGrid").jqGrid('editRow',id,abc);
          $("tr#" + lrid + " div.ui-inline-edit, " + "tr#" + lrid + " div.ui-inline-del").hide();
          $("tr#" + lrid + " div.ui-inline-save, " + "tr#" + lrid + " div.ui-inline-cancel").show();
        }


        });
      function reload(rowid, result) {
        $("#jqGrid").trigger("reloadGrid");
      }


/*
 $('#jqGrid').jqGrid('inlineNav','#jqGridPager', {
 addtext: "Add",
 edittext: "Edit",
 savetext: "Save",
 canceltext: "Cancel",
 addParams: {
 position: "afterSelected",
 addRowParams: myEditOptions
 },
 addedrow: "last",
 editParams: myEditOptions
 });


 jQuery("#jqGrid").jqGrid('inlineNav',"#jqGridPager",
 {
 editParams:{
 aftersavefunc: function() {
 var grid = $("#table");
 grid.trigger("reloadGrid");
 alert("aftersave fired for edit");
 }//aftersavefunc
 }//editParams
 }//options
 );//inlineNav definition
      //$("#jqGrid″).jqGrid('delGridRow', selID, {url: '/QMSWebApp/DCRIndexDeleteServlet'} );
      $("#jqGrid").jqGrid('inlineNav', '#jqGridPager',
          {addParams: {
            position: "last",
             addRowParams: {
              mtype: "POST",
             url: "Service.svc/AddUser",
              keys: true,
               aftersavefunc: function(rowid, response) {alert("ifti");
          }
        }
      },
            editParams: { mtype: "PUT", keys: true, url: "Service.svc/EditUser" }
          });
      // add navigation bar with some built in actions for the grid

      $('#jqGrid').navGrid('#jqGridPager',
          {
            edit: false,
            add: false,
            del: false,
            search: true,
            refresh: true,
            view: true,
            position: "left",
            cloneToTop: false
          });


      $("#jqGrid").jqGrid('navGrid', '#pager',
          {edit: false, add: false, search: false}, {}, {},
          { // Delete parameters
            mtype: "DELETE",
            serializeDelData: function () {
              return ""; // don't send and body for the HTTP DELETE
            },
            onclickSubmit: function (params, postdata) {
              params.url = '/api/widgets/' + encodeURIComponent(postdata);
            }
          });
*/
      function refreshGrid() {
        alert("abc");
        var grid = thisGrid;
        grid.trigger("reloadGrid");
      }
    }

  }
  UMS.controller('ClassRoomInfo', ClassRoomInfo);
}

