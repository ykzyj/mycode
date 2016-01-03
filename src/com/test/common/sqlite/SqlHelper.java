package com.sunnyit.common.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**   
* @Title: SqlHelper.java 
* @Package com.sunnyit.common.sqlite 
* @Description: TODO
* @author yk
* @date 2015年8月11日 下午2:31:34 
* @version V1.0   
*/
public class SqlHelper extends SQLiteOpenHelper {
	
	    private static final int VERSION = 8;  
	  
	    public SqlHelper(Context context, String name, CursorFactory factory,  
	            int version) {  
	        //必须通过super调用父类当中的构造函数  
	        super(context, name, factory, version);  
	    }  
	      
	    public SqlHelper(Context context, String name, int version){  
	        this(context,name,null,version);  
	    }  
	  
	    public SqlHelper(Context context, String name){  
	        this(context,name,VERSION);  
	    }  
	  
	    @Override  
	    public void onCreate(SQLiteDatabase db) {  
	        // TODO Auto-generated method stub  
	        db.execSQL("create table WXMessage(id integer primary key autoincrement,"
	        		+ "icon_id integer,"
	        		+ "title varchar(50),"
	        		+ "msg text,"
	        		+ "time varchar(50))");  
	        
	        db.execSQL("create table User(userId varchar(40) primary key,"
	        		+ "userName varchar(50),"
	        		+ "realName varchar(50),"
	        		+ "roleId varchar(50),"
	        		+ "roleName varchar(50),"
	        		+ "departmentId varchar(50),"
	        		+ "departmentName varchar(50),"
	        		+ "officeId varchar(50),"
	        		+ "officeName varchar(50),"
	        		+ "localId varchar(50),"
	        		+ "localName varchar(50),"
	        		+ "dutyId varchar(50),"
	        		+ "dutyName varchar(50),"
	        		+ "password varchar(50),"
	        		+ "createTime varchar(50),"
	        		+ "telephone varchar(50),"
	        		+ "email varchar(50),"
	        		+ "realAuditState varchar(50),"
	        		+ "userType varchar(50),"
	        		+ "industryId varchar(50),"
	        		+ "industryName varchar(50),"
	        		+ "writerdepid varchar(50),"
	        		+ "companyId varchar(50),"
	        		+"isRecSend varchar(10),"
	        		+ "companyName varchar(50))"); 
	        
	        db.execSQL("create table SimpleEnterprise(e_Id varchar(40) primary key,"
	        		+ "e_companyName varchar(200),"
	        		+ "e_companyAddress text,"
	        		+ "e_managerlayer varchar(50),"
	        		+ "e_departmentId varchar(40),"
	        		+ "e_departmentName varchar(100),"
	        		+ "e_localId varchar(40),"
	        		+ "e_localName varchar(100),"
	        		+ "e_businessCode varchar(100),"
	        		+ "e_legal_representative varchar(100),"
	        		+ "e_contact_phone varchar(30),"
	        		+ "e_safe_person varchar(100),"
	        		+ "e_safe_person_phone varchar(30),"
	        		+ "e_belongIndustry varchar(50),"
	        		+ "e_mangementMethod varchar(50),"
	        		+ "e_companyProperty varchar(50),"
	        		+ "remark varchar(50))"); 
	        
	        db.execSQL("create table Industry(industryId varchar(40) primary key,"
	        		+ "industryType varchar(200),"
	        		+ "indexid varchar(40),"
	        		+ "upperOrgId varchar(50),"
	        		+ "upperOrgName varchar(200))"); 
	        
	        db.execSQL("create table UpInfo(U_Id varchar(40) primary key,"
	        		+ "InfoTable varchar(50),"
	        		+ "InfoId varchar(50),"
	        		+ "OperateType varchar(50),"
	        		+ "remark varchar(40))"); 
	        
	        db.execSQL("create table CountyArea(a_id varchar(40) primary key,"
	        		+ "a_areaname varchar(200),"
	        		+ "a_parentareaid varchar(40),"
	        		+ "a_parentareaname varchar(200),"
	        		+ "a_writerdepid varchar(50),"
	        		+ "a_level varchar(50),"
	        		+ "remark varchar(200))"); 
	        
	        db.execSQL("create table CountyDepartment(d_id varchar(40) primary key,"
	        		+ "d_departmentname varchar(200),"
	        		+ "d_parentdepartmentid varchar(40),"
	        		+ "d_parentdepartmentname varchar(40),"
	        		+ "d_belongareaid varchar(40),"
	        		+ "d_belongareaname varchar(40),"
	        		+ "d_writerdepid varchar(50),"
	        		+ "d_level varchar(50),"
	        		+ "remark varchar(200))"); 
	        
	        db.execSQL("create table SelfCheck(sc_uuId varchar(40) primary key,"
	        		+ "sc_id varchar(40),"
	        		+ "sc_companyId varchar(40),"
	        		+ "sc_checkTime varchar(40),"
	        		+ "sc_checkedDepartment varchar(50),"
	        		+ "sc_checkedJob varchar(50),"
	        		+ "sc_checkingDepartment varchar(50),"
	        		+ "sc_inspector varchar(60),"
	        		+ "sc_hiddenCondition text,"
	        		+ "sc_isHave varchar(20),"
	        		+ "sc_fixMeasure text,"
	        		+ "sc_startTime varchar(20),"
	        		+ "sc_deadline varchar(20),"
	        		+ "sc_state varchar(10),"
	        		+ "sc_completeTablePeople varchar(50),"
	        		+ "sc_completeTableTime varchar(50),"
	        		+ "sc_companyName varchar(20),"
	        		+ "sc_checkedTimes varchar(20),"
	        		+ "sc_superviseOpinion text,"
	        		+ "sc_superviseDept varchar(20),"
	        		+ "sc_superviseTime varchar(20),"
	        		+ "sco_fixresult text,"
	        		+ "sco_acceptopinion text,"
	        		+ "sco_acceptanceDepartment varchar(20),"
	        		+ "sco_acceptanceTime varchar(20),"
	        		+ "sco_accepters varchar(40),"
	        		+ "sco_completeTablePeople varchar(20),"
	        		+ "sco_completeTableTime varchar(20),"
	        		+ "sco_isAccept varchar(10),"
	        		+ "remark varchar(40))"); 
	        
	        db.execSQL("create table SelfStandCheck(sc_uuid varchar(40) primary key,"
	        		+ "sc_idd varchar(40),"
	        		+ "sc_companyId varchar(40),"
	        		+ "sc_companyName varchar(100),"
	        		+ "sc_checkTime varchar(20),"
	        		+ "sc_checkDept varchar(40),"
	        		+ "sc_deadline varchar(20),"
	        		+ "sc_state varchar(10),"
	        		+ "sc_circle varchar(10),"
	        		+ "sc_checkPerson varchar(50),"
	        		+ "sc_completeTablePeople varchar(20),"
	        		+ "sc_completeTableTime varchar(20),"
	        		+ "sc_superviseOpinion text,"
	        		+ "sc_superviseDept varchar(40),"
	        		+ "sc_superviseTime varchar(20),"
	        		+ "remark varchar(40))"); 
	        
	        db.execSQL("create table StandcheckDetail(h_id varchar(40) primary key,"
	        		+ "sc_uuId varchar(40),"
	        		+ "hf_id varchar(40),"
	        		+ "h_content_one varchar(300),"
	        		+ "h_content_two varchar(300),"
	        		+ "h_content_three varchar(300),"
	        		+ "h_content_four varchar(300),"
	        		+ "h_content_five varchar(300),"
	        		+ "h_description text,"
	        		+ "h_basis text,"
	        		+ "h_dangerLevel varchar(40),"
	        		+ "h_checkCircle varchar(40),"
	        		+ "h_reportLevel varchar(40),"
	        		+ "sc_isPass varchar(10),"
	        		+ "sc_state varchar(10),"
	        		+ "sc_type varchar(10),"
	        		+ "sc_deadline varchar(20),"
	        		+ "sc_image varchar(100),"
	        		+ "sc_fixMeasure text,"
	        		+ "sc_fixcondition text,"
	        		+ "remark varchar(40))"); 
	        
	        db.execSQL("create table PublishInfo(id varchar(40) primary key,"
	        		+ "title varchar(40),"
	        		+ "content text,"
	        		+ "publishTime varchar(40),"
	        		+ "publisher varchar(30),"
	        		+ "publishDeptId varchar(40),"
	        		+ "publishDeptName varchar(200),"
	        		+ "infoIndex varchar(30),"
	        		+ "keyword varchar(100),"
	        		+ "infoType varchar(40))"); 
	        
	        db.execSQL("create table ExcelCell(c_id varchar(40) primary key,"
	        		+ "c_title varchar(40),"
	        		+ "c_row varchar(40),"
	        		+ "c_content text,"
	        		+ "c_parentId varchar(40),"
	        		+ "c_rootId varchar(40),"
	        		+ "c_index varchar(40))"); 
	        
	        db.execSQL("create table HiddenStandardFile(hf_id varchar(40) primary key,"
	        		+ "hf_name varchar(100),"
	        		+ "hf_userObj varchar(40),"
	        		+ "hf_time varchar(40),"
	        		+ "hf_type varchar(100))"); 
	        
	        db.execSQL("create table DailyCheck(c_id varchar(40) primary key,"
	        		+ "ck_id varchar(40),"
	        		+ "ck_checkingdepartment varchar(100),"
	        		+ "ck_time varchar(30),"
	        		+ "ck_checkeddepartment varchar(100),"
	        		+ "ck_checkPlace text,"
	        		+ "ck_sceneresponsible varchar(50),"
	        		+ "ck_position varchar(40),"
	        		+ "ck_telephone varchar(40),"
	        		+ "ck_scenecondition text,"
	        		+ "haveopinion varchar(10),"
	        		+ "ck_fixnowing text,"
	        		+ "ck_fixdeadline text,"
	        		+ "ck_fixstart_time varchar(30),"
	        		+ "ck_fixend_time varchar(30),"
	        		+ "ck_checkpeople varchar(100),"
	        		+ "ck_completechecktablepeople varchar(20),"
	        		+ "ck_completechecktabletime varchar(30),"
	        		+ "ck_state varchar(20),"
	        		+ "ck_fixMeasure text,"
	        		+ "ck_fixCondition text,"
	        		+ "ck_fixMeasurePerson varchar(50),"
	        		+ "ck_fixMeasureTime varchar(30),"
	        		+ "ck_fixConditionPerson varchar(50),"
	        		+ "ck_fixConditionTime varchar(30),"
	        		+ "ck_acceptopinion text,"
	        		+ "ck_acceptanceDept varchar(100),"
	        		+ "ck_acceptanceTime varchar(30),"
	        		+ "ck_superviseOpinion text,"
	        		+ "ck_supervisePerson varchar(100),"
	        		+ "ck_superviseTime varchar(30),"
	        		+ "isSupervise varchar(10),"
	        		+ "ck_reviewcondition text,"
	        		+ "ck_reviewopinion text,"
	        		+ "ck_reviewdepartment varchar(100),"
	        		+ "ck_reviewtime varchar(30),"
	        		+ "ck_reviewprople varchar(50),"
	        		+ "ck_completereviewtablepeople varchar(30),"
	        		+ "ck_completereviewtabletime varchar(30),"
	        		+ "ck_userId varchar(40),"
	        		+ "ck_startime varchar(40),"
	        		+ "ck_endtime varchar(40),"
	        		+ "remark varchar(400))"); 
	        
	        db.execSQL("create table SpecialCheck(c_id varchar(40) primary key,"
	        		+ "ck_id varchar(40),"
	        		+ "ck_specialcheckname varchar(100),"
	        		+ "ck_checkingdepartment varchar(100),"
	        		+ "ck_checkgroupid varchar(50),"
	        		+ "ck_leader varchar(60),"
	        		+ "ck_position varchar(100),"
	        		+ "ck_time varchar(40),"
	        		+ "ck_checkeddepartment varchar(200),"
	        		+ "ck_sceneresponsible varchar(40),"
	        		+ "ck_duty varchar(40),"
	        		+ "ck_telephone varchar(30),"
	        		+ "ck_scenecondition text,"
	        		+ "haveopinion varchar(10),"
	        		+ "ck_fixnowing text,"
	        		+ "ck_fixdeadline text,"
	        		+ "ck_fixstart_time varchar(40),"
	        		+ "ck_fixend_time varchar(40),"
	        		+ "ck_completechecktablepeople varchar(60),"
	        		+ "ck_completechecktabletime varchar(40),"
	        		+ "ck_state varchar(20),"
	        		+ "ck_reviewcondition text,"
	        		+ "ck_reviewopinion text,"
	        		+ "ck_reviewdepartment varchar(100),"
	        		+ "ck_reviewtime varchar(40),"
	        		+ "ck_reviewprople varchar(100),"
	        		+ "ck_completereviewtablepeople varchar(40),"
	        		+ "ck_completereviewtabletime varchar(40),"
	        		+ "ck_repairmethod text,"
	        		+ "ck_repaircondition text,"
	        		+ "ck_userId varchar(40),"
					+ "ck_repairconditionPerson varchar(100),"
					+ "ck_repairconditionTime varchar(60),"
					+ "ck_repairconditionDept varchar(100),"
					+ "ck_repairconditionDeptTime varchar(60),"
					+ "ck_repairPerson varchar(100),"
					+ "ck_repairTime varchar(60),"
					+ "ck_duinfo text,"
					+ "ck_site text,"
					+ "ck_showReviewBook varchar(40),"
					+ "ck_startime varchar(40),"
	        		+ "ck_endtime varchar(40),"
	        		+ "remark varchar(40))"); 
	        
	        db.execSQL("create table Inspector(checkid varchar(40) primary key,"
	        		+ "ck_id varchar(40),"
	        		+ "inspectorname varchar(60),"
	        		+ "inspectorduty varchar(40),"
	        		+ "inspectortype varchar(60),"
	        		+ "remark varchar(40))"); 
	        
	        db.execSQL("create table CheckTables(sc_uuId varchar(40) primary key,"
	        		+ "h_id varchar(40),"
	        		+ "hf_id varchar(40),"
	        		+ "h_content_one varchar(300),"
	        		+ "h_content_two varchar(300),"
	        		+ "h_content_three varchar(300),"
	        		+ "h_content_four varchar(300),"
	        		+ "h_content_five varchar(300),"
	        		+ "h_description text,"
	        		+ "h_basis text,"
	        		+ "h_dangerLevel varchar(30),"
	        		+ "h_checkCircle varchar(30),"
	        		+ "h_reportLevel varchar(30),"
	        		+ "sc_tableId varchar(40),"
	        		+ "sc_tableName varchar(100),"
	        		+ "sc_userId varchar(40),"
	        		+ "h_seq varchar(30),"
	        		+ "h_checkResult varchar(300),"
	        		+ "h_punishment text,"
	        		+ "sc_createTime varchar(40))"); 
	        
	        addStandard_CK_Table(db); 
	        
	        
	        db.execSQL("create table Standard_CK_Table_Item(id varchar(40) primary key,"
	        		+ "ck_id varchar(40),"
	        		+ "itemId varchar(40),"
	        		+ "item_content_one text,"
	        		+ "item_content_two text,"
	        		+ "item_content_three text,"
	        		+ "item_content_four text,"
	        		+ "item_content_five text,"
	        		+ "item_description text,"
	        		+ "item_basis text,"
	        		+ "item_dangerLevel varchar(60),"
	        		+ "item_checkCircle varchar(90),"
	        		+ "item_reportLevel varchar(60),"
	        		+ "item_image varchar(300),"
	        		+ "item_isQualified varchar(30),"
	        		+ "item_repairType varchar(60),"
	        		+ "item_repairLimit varchar(60),"
	        		+ "item_repairState varchar(60),"
	        		+ "item_repairMethod text,"
	        		+ "item_method_people varchar(120),"
	        		+ "item_method_time varchar(40),"
	        		+ "item_repairCondition text,"
	        		+ "item_apccept_opinion text,"
	        		+ "item_apccept_dept varchar(300),"
	        		+ "item_apccept_time varchar(40),"
	        		+ "item_condition_people varchar(60),"
	        		+ "item_condition_time varchar(40),"
	        		+ "item_reviewResult varchar(30),"
	        		+ "h_seq varchar(30),"
	        		+ "item_checkResult varchar(300),"
	        		+ "item_punishment text,"
	        		+ "item_reviewImage varchar(300))"); 
	        
	        db.execSQL("create table Chemicals(c_no varchar(10) primary key,"
	        		+ "c_name text,"
	        		+ "c_aliasname text,"
	        		+ "c_showname varchar(150),"
	        		+ "c_cas varchar(40),"
	        		+ "c_isvirulen varchar(40),"
	        		+ "c_physical text,"
	        		+ "c_danger text,"
	        		+ "c_aidmeasures text,"
	        		+ "c_firecontrol text,"
	        		+ "c_leak text,"
	        		+ "c_operation text,"
	        		+ "c_contact text,"
	        		+ "c_stabreact text,"
	        		+ "c_toxicological text,"
	        		+ "c_transport text,"
	        		+ "c_treatment text)"); 
	        
	        addCheckConditionItem(db);
	        
	        addTransportFile(db);
	        addUserContacts(db);
	        
	        addInformationRemind(db);
	    }

	    @Override  
	    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {  
	        // TODO Auto-generated method stub  
	    	switch (arg1) {
			case 1:
				db.execSQL("alter table User add isRecSend varchar(10)");
		    	db.execSQL("alter table CheckTables add h_checkResult varchar(300)");
		    	db.execSQL("alter table CheckTables add h_punishment text");
		    	db.execSQL("alter table Standard_CK_Table_Item add item_checkResult varchar(300)");
		    	db.execSQL("alter table Standard_CK_Table_Item add item_punishment text");
		    	
			case 2:
				db.execSQL("alter table DailyCheck add ck_startime varchar(40)");
		    	db.execSQL("alter table DailyCheck add ck_endtime varchar(40)");
		    	db.execSQL("alter table SpecialCheck add ck_startime varchar(40)");
		    	db.execSQL("alter table SpecialCheck add ck_endtime varchar(40)");
		    	db.execSQL("alter table Standard_CK_Table add ck_startime varchar(40)");
		    	db.execSQL("alter table Standard_CK_Table add ck_endtime varchar(40)");
		    	
			case 3:
				addCheckConditionItem(db);
			case 4:
				addTransportFile(db);
		        addUserContacts(db);
		        
			case 5:
				addInformationRemind(db);
			case 6:
				db.execSQL("drop table if exists InformationRemind");
				addInformationRemind(db);
			case 7:
				db.execSQL("drop table if exists Standard_CK_Table");
				addStandard_CK_Table(db);
			default:
				break;
			}
	    }  
	    
	    private void addStandard_CK_Table(SQLiteDatabase db) {
			db.execSQL("create table Standard_CK_Table(ck_id varchar(40) primary key,"
	        		+ "ck_seq varchar(40),"
	        		+ "ck_name varchar(60),"
	        		+ "companyId varchar(40),"
	        		+ "companyName varchar(300),"
	        		+ "ck_site varchar(300),"
	        		+ "ck_people varchar(60),"
	        		+ "ck_time varchar(40),"
	        		+ "ck_state varchar(30),"
	        		+ "ck_deptId varchar(40),"
	        		+ "ck_dept varchar(100),"
	        		+ "isExistDanger varchar(30),"
	        		+ "ck_repairmethod text,"
	        		+ "ck_repaircondition text,"
	        		+ "ck_duinfo text,"
	        		+ "ck_duTime varchar(40),"
	        		+ "ck_reviewDept varchar(100),"
	        		+ "ck_reviewTime varchar(40),"
	        		+ "ck_reviewPeople varchar(60),"
	        		+ "ck_deadLine varchar(40),"
	        		+ "ck_showReviewBook varchar(30),"
	        		+ "ck_startime varchar(40),"
	        		+ "ck_endtime varchar(40),"
	        		+ "ck_legal_representative varchar(100),"
	        		+ "ck_telphone varchar(30),"
	        		+ "ck_enterpriseaddress varchar(200),"
	        		+ "ck_userId varchar(40))");
		}  
	    
	    
	    private void addCheckConditionItem(SQLiteDatabase db) {
	    	db.execSQL("create table CheckConditionItem(id varchar(40) primary key,"
	        		+ "checkNo varchar(10),"
	        		+ "checkContent text,"
	        		+ "checkImg text,"
	        		+ "checkItemState varchar(10),"
	        		+ "checkDate varchar(40),"
	        		+ "checkCkId varchar(40))"); 
		}
	    
	    private void addTransportFile(SQLiteDatabase db) {
	    	db.execSQL("create table TransportFile(id varchar(40) primary key,"
	        		+ "senderId varchar(40),"
	        		+ "sender varchar(40),"
	        		+ "receiver varchar(40),"
	        		+ "receiverId varchar(40),"
	        		+ "title text,"
	        		+ "content text,"
	        		+ "annexName varchar(100),"
	        		+ "annexPath varchar(100),"
	        		+ "sendTime varchar(60),"
	        		+ "receiveTime varchar(60),"
	        		+ "state varchar(10),"
	        		+ "remark varchar(40))"); 
		}
	    
	    private void addUserContacts(SQLiteDatabase db) {
	    	db.execSQL("create table UserContacts(id varchar(40) primary key,"
	        		+ "userId varchar(40),"
	        		+ "contactId text)"); 
		}
	    
	    private void addInformationRemind(SQLiteDatabase db) {
	    	db.execSQL("create table InformationRemind(I_ID varchar(40) primary key,"
	        		+ "I_Title varchar(60),"
	        		+ "I_Content text,"
	        		+ "I_Time varchar(40),"
	        		+ "I_HaveLook varchar(10),"
	        		+ "remark varchar(40))"); 
		}
	
}


