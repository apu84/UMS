package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.ClassRoomManager;
import org.ums.resource.Resource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.StringReader;

/**
 * Created by Ifti on 25-Oct-16.
 */
@Component
@Path("/academic/classattendance")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ClassAttendanceResource {

  @Autowired
  ClassRoomManager mManager;

  @GET
  @Path("/ifti")
  public JsonObject getAll() throws Exception {
    String data =
        "[{'sId':'','sName':'','date11012016':'I','date21022016':'I','date01032016':'I','date10042016':'I','date15052016':'I','date22052016':'I','date01062016':'I','date07062016':'I','date12062016':'I','date25062016':'I','date01072016':'v','date03072016':'I','date05072016':'I','date07072016':'I','date09072016':'I','date11072016':'I','date13072016':'I'},"
            + "               {'sId':'160105001','sName':'Sadia Sultana','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105002','sName':'Md. Ferdous Wahed','date11012016':'Y','date21022016':'Y','date01032016':'N','date10042016':'Y','date15052016':'N','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105003','sName':'Tahsin Sarwar','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105004','sName':'Abid Mahmud','date11012016':'N','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105005','sName':'Sudipta Mondal','date11012016':'Y','date21022016':'N','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105006','sName':'Md. Farhan Fardus','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105007','sName':'Farah Farzana Mou','date11012016':'Y','date21022016':'Y','date01032016':'N','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105008','sName':'Afsana Akter','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105009','sName':'Md. Golam Saklaen','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105010','sName':'Mir Hasibul Hasan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'N','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105011','sName':'Syeda Meherin Haider','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105012','sName':'Zannatul Mashrekee Hossain Kristy','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105013','sName':'Ankit Dev','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'N','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105014','sName':'Farhana Islam Chowdhury Prity','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'N','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105015','sName':'Md Ashfaqur Rahman Khan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'N','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105016','sName':'Mahzabin Musfikamomo','date11012016':'N','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105017','sName':'Miftahul Islam','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105018','sName':'Rahul Khan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105019','sName':'Mir Hasibul Hasan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'N','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105020','sName':'Syeda Meherin Haider','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22062016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105021','sName':'Zannatul Mashrekee Hossain Kristy','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105022','sName':'Ankit Dev','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'N','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105023','sName':'Farhana Islam Chowdhury Prity','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'N','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105024','sName':'Md Ashfaqur Rahman Khan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'N','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105025','sName':'Mahzabin Musfikamomo','date11012016':'N','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105026','sName':'Miftahul Islam','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "      {'sId':'160105027','sName':'Rahul Khan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105028','sName':'Sadia Sultana','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105029','sName':'Md. Ferdous Wahed','date11012016':'Y','date21022016':'Y','date01032016':'N','date10042016':'Y','date15052016':'N','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105030','sName':'Tahsin Sarwar','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105031','sName':'Abid Mahmud','date11012016':'N','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105032','sName':'Sudipta Mondal','date11012016':'Y','date21022016':'N','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105033','sName':'Md. Farhan Fardus','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105034','sName':'Farah Farzana Mou','date11012016':'Y','date21022016':'Y','date01032016':'N','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105035','sName':'Afsana Akter','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105036','sName':'Md. Golam Saklaen','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105037','sName':'Mir Hasibul Hasan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'N','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105038','sName':'Syeda Meherin Haider','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105039','sName':'Zannatul Mashrekee Hossain Kristy','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105040','sName':'Ankit Dev','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'N','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105041','sName':'Farhana Islam Chowdhury Prity','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'N','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105042','sName':'Md Ashfaqur Rahman Khan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'N','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105043','sName':'Mahzabin Musfikamomo','date11012016':'N','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105044','sName':'Miftahul Islam','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105045','sName':'Rahul Khan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105046','sName':'Mir Hasibul Hasan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'N','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105047','sName':'Syeda Meherin Haider','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22062016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105048','sName':'Zannatul Mashrekee Hossain Kristy','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105049','sName':'Ankit Dev','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'N','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},"
            + "          {'sId':'160105050','sName':'Farhana Islam Chowdhury Prity','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'N','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'}]";

    // return mResourceHelper.getAll(mUriInfo);
    String header =
        " [" + "      {" + "        data: 'sId'," + "        title: 'Student Id',"
            + "        readOnly:true" + "      }," + "      {" + "        data: 'sName',"
            + "        title: 'Student Name'," + "        readOnly:true" + "      }," + "      {"
            + "        data: 'date11012016'," + "        title: '11 Jan, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date21022016'," + "        title: '21 Feb, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date01032016'," + "        title: '01 Mar, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date10042016'," + "        title: '10 Apr, 16',"
            + "        renderer: this.imageRenderer" + "      }," + "      {"
            + "        data: 'date15052016'," + "        title: '15 Mar, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date22052016'," + "        title: '22 May, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date01062016'," + "        title: '01 Jun, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date07062016'," + "        title: '07 Jun, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date12062016'," + "        title: '12 Jun, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date25062016'," + "        title: '25 Jun, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date01072016'," + "        title: '01 Jul, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date03072016'," + "        title: '03 Jul, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date05072016'," + "        title: '05 Jul, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date07072016'," + "        title: '07 Jul, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date09072016'," + "        title: '09 Jul, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date11072016'," + "        title: '11 Jul, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      },"
            + "      {" + "        data: 'date13072016'," + "        title: '13 Jul, 16',"
            + "        renderer: this.imageRenderer," + "        readOnly:true" + "      }"
            + "    ]";

    return null;

  }

}
