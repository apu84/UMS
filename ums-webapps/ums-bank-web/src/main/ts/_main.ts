/// <reference path='../../../../ums-web-core/src/main/ts/lib/angular.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/angular-route.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/jquery.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/jquery.validate.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/jqueryui.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/jquery.slimScroll.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/jquery.notific8.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/jquery.notify.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/select2.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/bootstrap.selectpicker.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/zabuto_calendar.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/bootstrap-switch.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/uri.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/angular-cookies.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/jquery.jqGrid.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/angular-ui-sortable.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/moment.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/jquery.dragNdrop.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/jquery.tab.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/bootstrap.modal.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/lib/handsontable.d.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/constants.ts'/>

/// <reference path='ums-bank.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/util/UmsUtil.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/util/UriUtil.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/Utils.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/BaseUri.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/Notify.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/HttpClient.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/FileUpload.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/CookieService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/AdmissionStudentService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/PaymentInfoService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/SemesterService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/Settings.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/token.model.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/refresh.token.service.ts'/>

/// <reference path='../../../../ums-web-core/src/main/ts/controller/AppController.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/controller/MainController.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/controller/ChangePassword.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/controller/Logout.ts'/>

/// <reference path='../../../../ums-web-core/src/main/ts/directive/FlushCache.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/LeftMenu.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/DisableOperation.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/Validate.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/ProgramSelector.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/FileModel.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/Image.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/UITab.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/MainNavigation.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/BindHtml.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/DatePicker.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/AutoComplete.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/Spinner.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/ProfilePicture.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/Grid.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/SelectPicker.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/ConvertToNumber.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/Modal.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/Notification.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/ConfirmationDIalog.ts'/>

/// <reference path='../../../../ums-web-core/src/main/ts/interceptor/Unauthorized.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/interceptor/ValidationExceptions.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/interceptor/ServerExceptions.ts'/>

/// <reference path='../../../../ums-web-core/src/main/ts/filter/TrustAsHtml.ts'/>

/// <reference path='../../../../ums-web-core/src/main/ts/model/ChangePasswordModel.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/model/AdmissionStudent.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/model/PaymentInfo.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/model/Semester.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/model/Employee.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/ems/profile-management/EmployeeInformationModel.ts' />
/// <reference path='../../../../ums-web-core/src/main/ts/model/User.ts'/>

/// <reference path='../../../../ums-web-core/src/main/ts/grid/GridConfig.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/GridDecorator.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/GridOptions.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/InlineNavigationOptions.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/InlineNavigationOptionsImpl.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/JqGridApi.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/JqGridApiImpl.ts'/>

/// <reference path='../../../../ums-web-core/src/main/ts/fee/filter/filter.model.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/fee/payment-status/payment.status.model.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/fee/filter/list.filter.directive.ts'/>

/// <reference path='data.ts'/>
/// <reference path='controller/admission/AdmissionFee.ts'/>
/// <reference path='fee/conclude.payment.ts'/>
/// <reference path='fee/payment.confirmation.ts'/>
/// <reference path='fee/payment.status.controller.ts'/>
/// <reference path='fee/receive.payment.controller.ts'/>
/// <reference path='fee/receive.payment.service.ts'/>
/// <reference path='fee/payment.status.service.ts'/>
/// <reference path='admin/bank.models.ts'/>
/// <reference path='admin/bank.http.service.ts'/>
/// <reference path='admin/bank.model.converters.ts'/>
/// <reference path='admin/bank.resource.ts'/>
/// <reference path='admin/bank.service.ts'/>
/// <reference path='admin/bank.controller.ts'/>
/// <reference path='admin/branch.controller.ts'/>
/// <reference path='admin/edit.branch.ts'/>
/// <reference path='admin/bank.designation.controller.ts'/>
/// <reference path='admin/edit.bank.designation.ts'/>
/// <reference path='admin/branch.users.controller.ts'/>
/// <reference path='admin/edit.user.ts'/>






