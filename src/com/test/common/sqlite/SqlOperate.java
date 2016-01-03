package com.sunnyit.common.sqlite;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.sunnyit.common.annotation.TablePrimaryKey;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

/**   
* @Title: SqlOperate.java 
* @Package com.sunnyit.common.sqlite 
* @Description: TODO
* @author yk
* @date 2015��8��11�� ����4:51:53 
* @version V1.0
*/
public class SqlOperate<T> {
	
	private String db_name="safe_phone.db3";
	private SqlHelper mSqlHelper;
	private Class mclass;
	String pk="_id";
	
	boolean isAutoIncrement=false;
	
	public SqlOperate(Context context,Class cla) {
		// TODO Auto-generated constructor stub
		this.mSqlHelper=new SqlHelper(context, db_name);
		this.mclass=cla;
		this.pk=getClassPrimaryAttributeName();
	}

	/**
	* @author yk 
	* @date 2015��8��12�� ����10:31:33 
	* @Title: saveContent 
	* @Description: ���ݱ���
	* @param t
	* @return    �趨�ļ� 
	* @return boolean    �������� 
	* @throws
	 */
	public boolean saveContent(T t)
	{
		List<String> lstn=getClassAttributeName();
		Object[] objectValue=new Object[lstn.size()];
		
		String columnTitleStr="";
		String columnValueStr="";
		for(int i=0;i<lstn.size();i++)
		{
			columnTitleStr=columnTitleStr+","+lstn.get(i);
			columnValueStr=columnValueStr+",?";
			try 
			{
				Field field=mclass.getDeclaredField(lstn.get(i));
				field.setAccessible(true);
				objectValue[i]=field.get(t);
				
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String saveStr;
		if(isAutoIncrement)
		{
			saveStr="insert into "+mclass.getSimpleName()+"("+pk
					+columnTitleStr+") values(null"+columnValueStr+")";
		}
		else
		{
			columnTitleStr=columnTitleStr.substring(1);
			columnValueStr=columnValueStr.substring(1);
			saveStr="insert into "+mclass.getSimpleName()+"("
					+columnTitleStr+") values("+columnValueStr+")";
		}
		
		try 
		{
			mSqlHelper.getReadableDatabase().execSQL(saveStr, objectValue);
		}
		catch(SQLiteException  se)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean upContent(T t)
	{
		List<String> lstn=getClassAttributeName();
		Object[] objectValue=new Object[lstn.size()];
		
		String setValueStr="";
		for(int i=0;i<lstn.size();i++)
		{
			setValueStr=setValueStr+lstn.get(i)+"=?,";
			try 
			{
				Field field=mclass.getDeclaredField(lstn.get(i));
				field.setAccessible(true);
				objectValue[i]=field.get(t);
				
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String saveStr="";
		try {
			Field fieldid=mclass.getDeclaredField(pk);
			fieldid.setAccessible(true);
			setValueStr=setValueStr.substring(0, setValueStr.length()-1);
			saveStr="update "+mclass.getSimpleName()+" set "+setValueStr+" where "+pk+" = '"+fieldid.get(t).toString()+"'";
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try 
		{
			mSqlHelper.getReadableDatabase().execSQL(saveStr, objectValue);
		}
		catch(SQLiteException  se)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean execMySQL(String sqlStr)
	{
		try 
		{
			mSqlHelper.getReadableDatabase()
			.execSQL(sqlStr, new String[]{});
		}
		catch(SQLiteException  se)
		{
			return false;
		}
		return true;
	}
	
	
	public boolean DeleteContent(String ID)
	{
		try 
		{
			mSqlHelper.getReadableDatabase()
			.execSQL("delete from "+mclass.getSimpleName()
			+" where "+pk+"=?", new String[]{ID});
		}
		catch(SQLiteException  se)
		{
			return false;
		}
		return true;
	}
	
	public boolean DeleteAllContent()
	{
		try 
		{
			mSqlHelper.getReadableDatabase()
			.execSQL("delete from "+mclass.getSimpleName(), new String[]{});
		}
		catch(SQLiteException  se)
		{
			return false;
		}
		return true;
	}
	
	public boolean DeleteByCondition(String conditionStr)
	{
		try 
		{
			mSqlHelper.getReadableDatabase()
			.execSQL("delete from "+mclass.getSimpleName()+" "+conditionStr, new String[]{});
		}
		catch(SQLiteException  se)
		{
			return false;
		}
		return true;
	}
	
	/**
	* @author yk 
	* @date 2015��8��12�� ����11:49:47 
	* @Title: SelectContentByID 
	* @Description: ��ID��ȡ����
	* @param ID
	* @return    �趨�ļ� 
	* @return T    �������� 
	* @throws
	 */
	public T SelectEntityByID(String ID)
	{
		T t=null;
		Cursor cursor = null;
		try {
			t = (T) mclass.newInstance();
			Field[] fieldes=mclass.getDeclaredFields();
			for(Field field:fieldes)
			{
				field.setAccessible(true);
			}
			try 
			{
				 cursor = mSqlHelper.getReadableDatabase()
						 .rawQuery("select * from "+mclass.getSimpleName()+
								 " where "+pk+"=?"
						 , new String[]{ID});
				 while(cursor.moveToNext())
				 {
					for(Field field:fieldes)
					{
						try {
							field.set(t, cursor.getString(cursor.getColumnIndex(field.getName())));
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				 }
			}
			catch(SQLiteException  se)
			{
				return null;
			}
			finally
			{
				if(null!=cursor)
				{
					cursor.close();
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	* @author yk 
	* @date 2015��8��12�� ����11:49:23 
	* @Title: SelectContent 
	* @Description: ��ȡ��������
	* @return    �趨�ļ� 
	* @return List<T>    �������� 
	* @throws
	 */
	public List<T> SelectEntitys()
	{
		List<T> lt=new ArrayList<T>();
		Field[] fieldes=mclass.getDeclaredFields();
		Cursor cursor = null;
		for(Field field:fieldes)
		{
			field.setAccessible(true);
		}
		try 
		{
			 cursor = mSqlHelper.getReadableDatabase().rawQuery("select * from "+mclass.getSimpleName(), null);
			 while(cursor.moveToNext())
			 {
				try {
					T t = (T) mclass.newInstance();
					for(Field field:fieldes)
					{
						if(field.getGenericType().toString().equals("class java.lang.Integer")||
								field.getGenericType().toString().equals("int"))
						{
							field.set(t, cursor.getInt(cursor.getColumnIndex(field.getName())));
						}
						else
						{
							field.set(t, cursor.getString(cursor.getColumnIndex(field.getName())));
						}
					}
					lt.add(t);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		}
		catch(SQLiteException  se)
		{
			return null;
		}
		finally
		{
			if(null!=cursor)
			{
				cursor.close();
			}
		}
		return lt;
	}
	
	
	public List<T> SelectEntitysByCondition(String condition)
	{
		List<T> lt=new ArrayList<T>();
		Field[] fieldes=mclass.getDeclaredFields();
		Cursor cursor = null;
		for(Field field:fieldes)
		{
			field.setAccessible(true);
		}
		try 
		{
			 cursor = mSqlHelper.getReadableDatabase().rawQuery("select * from "+mclass.getSimpleName()+" "+condition, null);
			 while(cursor.moveToNext())
			 {
				try {
					T t = (T) mclass.newInstance();
					for(Field field:fieldes)
					{
						if(field.getGenericType().toString().equals("class java.lang.Integer")||
								field.getGenericType().toString().equals("int"))
						{
							field.set(t, cursor.getInt(cursor.getColumnIndex(field.getName())));
						}
						else
						{
							field.set(t, cursor.getString(cursor.getColumnIndex(field.getName())));
						}
					}
					lt.add(t);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		}
		catch(SQLiteException  se)
		{
			return lt;
		}
		finally
		{
			if(null!=cursor)
			{
				cursor.close();
			}
		}
		return lt;
	}
	
	public List<T> SelectEntitysBySqlCondition(String condition)
	{
		List<T> lt=new ArrayList<T>();
		Field[] fieldes=mclass.getDeclaredFields();
		Cursor cursor = null;
		for(Field field:fieldes)
		{
			field.setAccessible(true);
		}
		try 
		{
			 cursor = mSqlHelper.getReadableDatabase().rawQuery(condition, null);
			 while(cursor.moveToNext())
			 {
				try {
					T t = (T) mclass.newInstance();
					for(Field field:fieldes)
					{
						try {
							if(field.getGenericType().toString().equals("class java.lang.Integer")||
									field.getGenericType().toString().equals("int"))
							{
								field.set(t, cursor.getInt(cursor.getColumnIndex(field.getName())));
							}
							else
							{
								field.set(t, cursor.getString(cursor.getColumnIndex(field.getName())));
							}
						} catch (Exception e) {
							// TODO: handle exception
							field.set(t, "");
						}
					}
					lt.add(t);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		}
		catch(SQLiteException  se)
		{
			return lt;
		}
		finally
		{
			if(null!=cursor)
			{
				cursor.close();
			}
		}
		return lt;
	}
	
	/**
	* @author yk 
	* @date 2015��9��17�� ����11:45:13 
	* @Title: SelectOffsetEntitysByCondition 
	* @Description: ��ҳ
	* @param condition
	* @param pangSize
	* @param pageCount
	* @return    �趨�ļ� 
	* @return List<T>    �������� 
	* @throws
	 */
	public List<T> SelectOffsetEntitysByCondition(String condition,int pangSize,int pageCount)
	{
		List<T> lt=new ArrayList<T>();
		Field[] fieldes=mclass.getDeclaredFields();
		Cursor cursor = null;
		for(Field field:fieldes)
		{
			field.setAccessible(true);
		}
		try 
		{
			 cursor = mSqlHelper.getReadableDatabase().
					 rawQuery("select * from (select * from "+
							 	mclass.getSimpleName()+" "+condition+")"
							 			+ "Limit "+String.valueOf(pangSize)+" Offset "+String.valueOf(pangSize*pageCount), null);
			 while(cursor.moveToNext())
			 {
				try {
					T t = (T) mclass.newInstance();
					for(Field field:fieldes)
					{
						if(field.getGenericType().toString().equals("class java.lang.Integer")||
								field.getGenericType().toString().equals("int"))
						{
							field.set(t, cursor.getInt(cursor.getColumnIndex(field.getName())));
						}
						else
						{
							field.set(t, cursor.getString(cursor.getColumnIndex(field.getName())));
						}
					}
					lt.add(t);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		}
		catch(SQLiteException  se)
		{
			return lt;
		}
		finally
		{
			if(null!=cursor)
			{
				cursor.close();
			}
		}
		return lt;
	}
	
	/**
	* @author yk 
	* @date 2015��9��18�� ����1:51:03 
	* @Title: SelectOffsetEntitysByCondition 
	* @Description: ����
	* @param condition
	* @param pangSize
	* @param pageCount
	* @return    �趨�ļ� 
	* @return List<T>    �������� 
	* @throws
	 */
	public List<T> SelectOffsetEntitysBySqlCondition(String sqlcondition,int pangSize,int pageCount)
	{
		List<T> lt=new ArrayList<T>();
		Field[] fieldes=mclass.getDeclaredFields();
		Cursor cursor = null;
		for(Field field:fieldes)
		{
			field.setAccessible(true);
		}
		try 
		{
			 cursor = mSqlHelper.getReadableDatabase().
					 rawQuery("select * from ("+sqlcondition+")"+
					 			" Limit "+String.valueOf(pangSize)+" Offset "+String.valueOf(pangSize*pageCount), null);
			 while(cursor.moveToNext())
			 {
				try {
					T t = (T) mclass.newInstance();
					for(Field field:fieldes)
					{
						if(field.getGenericType().toString().equals("class java.lang.Integer")||
								field.getGenericType().toString().equals("int"))
						{
							field.set(t, cursor.getInt(cursor.getColumnIndex(field.getName())));
						}
						else
						{
							field.set(t, cursor.getString(cursor.getColumnIndex(field.getName())));
						}
					}
					lt.add(t);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		}
		catch(SQLiteException  se)
		{
			return lt;
		}
		finally
		{
			if(null!=cursor)
			{
				cursor.close();
			}
		}
		return lt;
	}

	/**
	* @author yk 
	* @date 2015��8��12�� ����10:06:28 
	* @Title: getSaveString 
	* @Description: ��ȡ���Ե�����(����������)
	* @return    �趨�ļ� 
	* @return List<String>    �������� 
	* @throws
	 */
	private List<String> getClassAttributeName() {
		// TODO Auto-generated method stub
		List<String> lstn=new ArrayList<String>();
		
		Field[] fieldes=mclass.getDeclaredFields();
		for(Field f:fieldes) 
		{
			f.setAccessible(true);
			/*if(!f.getName().equals("_id"))
			{
				lstn.add(f.getName());
			}*/
			
			if(f.getAnnotation(TablePrimaryKey.class)==null)
			{
				lstn.add(f.getName());
			}
			else
			{
				TablePrimaryKey PrimaryKey=f.getAnnotation(TablePrimaryKey.class);
				String s=PrimaryKey.PrimaryKeyType().name();
				if(s.equals("YES"))
				{
					isAutoIncrement=true;
				}
				else
				{
					lstn.add(f.getName());
					isAutoIncrement=false;
				}
			}
		}
		
		return lstn;
	}
	
	/**
	* @author yk 
	* @date 2015��8��21�� ����4:32:29 
	* @Title: getClassPrimaryAttributeName 
	* @Description: ����ע��������table�����������û��Ĭ�Ϸ���_id
	* @return    �趨�ļ� 
	* @return String    �������� 
	* @throws
	 */
	private String getClassPrimaryAttributeName() {
		// TODO Auto-generated method stub
		Field[] fieldes=mclass.getDeclaredFields();
		for(Field f:fieldes) 
		{
			f.setAccessible(true);
			if(f.getAnnotation(TablePrimaryKey.class)!=null)
			{
				return f.getName();
			}
		}
		
		return "_id";
	}
	
	/**
	* @author yk 
	* @date 2015��8��12�� ����10:30:52 
	* @Title: close 
	* @Description: �ر����ݷ���
	* @return void    �������� 
	* @throws
	 */
	public void close() {
		// TODO Auto-generated method stub
		mSqlHelper.getReadableDatabase().close();
	}
	
	
}


