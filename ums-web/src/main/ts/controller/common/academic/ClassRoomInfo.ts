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
      $.extend($.jgrid.inlineEdit, { restoreAfterError: false });

       var lastSel,
      cancelEditing = function(myGrid) {
        var lrid;
        if (typeof lastSel !== "undefined") {
          myGrid.jqGrid('restoreRow',lastSel);
          lrid = $.jgrid.jqID(lastSel);
          $("tr#" + lrid + " div.ui-inline-edit, " + "tr#" + lrid + " div.ui-inline-del").show();
          $("tr#" + lrid + " div.ui-inline-save, " + "tr#" + lrid + " div.ui-inline-cancel").hide();
        }
      };
      $.extend($.jgrid.defaults, {
        ajaxRowOptions: { contentType: "application/json", async: true,type:"PUT",
          beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Authorization", 'Basic ZHByZWdpc3RyYXI6MTIzNDU=');
            jqXHR.setRequestHeader("If-Match", '*');
          },
          complete: function(res, stat) {
            if (res.status==200 || res.status==204) {
              $("#jqGrid").trigger("reloadGrid");
            } else {
              return [false, res.responseText ];
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
            editable: true
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


          $("#jqGrid").jqGrid('editRow',id,true,function() {
            var colModel = jQuery("#jqGrid").jqGrid ('getGridParam', 'colModel');
            var colName = colModel[ci].name;
            var input = $('#' + id + '_' + colName);
            console.log(input)
            setTimeout(function(){  input.get(0).focus(); }, 300);
          },null,"https://localhost/ums-webservice-common/academic/classroom/"+ encodeURIComponent(id));
          $("tr#" + lrid + " div.ui-inline-edit, " + "tr#" + lrid + " div.ui-inline-del").hide();
          $("tr#" + lrid + " div.ui-inline-save, " + "tr#" + lrid + " div.ui-inline-cancel").show();
        }


        });


      var addOptions = {
        keys: true,
        type: "POST",
        url: "AddUser",
        successfunc: function () {
          var $self = $(this);
          setTimeout(function () {
            $self.trigger("reloadGrid");
          }, 50);
        }
      };
      $("#jqGrid").jqGrid("inlineNav", "#jqGridPager", {
        addParams: {
          position: "last",
          addRowParams: addOptions
        }
      });

    }

  }
  UMS.controller('ClassRoomInfo', ClassRoomInfo);
}

