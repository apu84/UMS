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


/// <reference path='ums-library.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/BaseUri.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/Notify.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/HttpClient.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/FileUpload.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/CookieService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/Settings.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/token.model.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/refresh.token.service.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/controller/AppController.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/controller/MainController.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/controller/ChangePassword.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/controller/Logout.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/employee/EmployeeProfile.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/employee/EmployeeInformationModel.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/employee/PagerService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/employee/CRUDDetectionService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/employee/EmployeeInformationService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/DepartmentService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/DivisionService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/DistrictService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/ThanaService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/AreaOfInterestService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/AcademicDegreeService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/EmploymentTypeService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/DesignationService.ts'/>

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
/// <reference path='../../../../ums-web-core/src/main/ts/directive/TablePaginatorWrapper.ts'/>
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
/// <reference path='../../../../ums-web-core/src/main/ts/model/User.ts'/>

/// <reference path='../../../../ums-web-core/src/main/ts/grid/GridConfig.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/GridDecorator.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/GridOptions.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/InlineNavigationOptions.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/InlineNavigationOptionsImpl.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/JqGridApi.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/grid/JqGridApiImpl.ts'/>

/// <reference path='../../../../ums-web-core/src/main/ts/util/UmsUtil.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/util/UriUtil.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/Utils.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/directive/FileInput.ts'/>

/// <reference path='data.ts'/>

/// <reference path='controller/Cataloging.ts'/>
/// <reference path='controller/RecordSearch.ts'/>
/// <reference path='controller/PatronHome.ts'/>
/// <reference path='controller/CirculationHome.ts'/>
/// <reference path='controller/CirculationCheckOut.ts'/>
/// <reference path='directive/CirculationSearchBox.ts'/>
/// <reference path='controller/PatronCheckOut.ts'/>
/// <reference path='controller/Fine.ts'/>
/// <reference path='controller/CirculationHistory.ts'/>
/// <reference path='controller/PatronDetails.ts'/>
/// <reference path='controller/CheckIn.ts'/>
/// <reference path='controller/PatronSearch.ts'/>
/// <reference path='controller/SearchLibrary.ts'/>
/// <reference path='controller/BorrowHistory.ts'/>

/// <reference path='directive/ColumnSorter.ts'/>

/// <reference path='../../../../ums-library-web/src/main/ts/service/BaseService.ts'/>
/// <reference path='../../../../ums-library-web/src/main/ts/service/SupplierService.ts'/>
/// <reference path='../../../../ums-library-web/src/main/ts/service/PublisherService.ts'/>
/// <reference path='../../../../ums-library-web/src/main/ts/service/ContributorService.ts'/>
/// <reference path='../../../../ums-library-web/src/main/ts/service/CatalogingService.ts'/>
/// <reference path='../../../../ums-library-web/src/main/ts/types/Interfaces.ts'/>
/// <reference path='../../../../ums-library-web/src/main/ts/directive/NavigationButton.ts'/>
/// <reference path='../../../../ums-library-web/src/main/ts/factory/MessageFactory.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/types/Interfaces.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/CountryService.ts'/>
/// <reference path='../../../../ums-web-core/src/main/ts/service/UserService.ts'/>
/// <reference path='../../../../ums-library-web/src/main/ts/service/CirculationService.ts'/>

