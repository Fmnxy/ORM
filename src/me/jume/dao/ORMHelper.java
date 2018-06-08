package me.jume.dao;

import java.io.ObjectStreamException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.jume.annotation.Table;
import me.jume.handler.BeanHandler;
import me.jume.handler.ResultSetHandler;
import me.jume.utils.DBManager;


public class ORMHelper<E> {
	

	public int add(E element){
		if (element == null) {
			throw new IllegalArgumentException("插入对象不能为空!");
		}
		Class clazz = element.getClass(); 
		String tableName = getTableName(clazz);
		Field[] fields = clazz.getDeclaredFields();
		if (fields == null || fields.length == 0) {
			throw new RuntimeException(element + "没有属性哦");
		}

		String sql = getInsertSql(tableName,fields);

		Object[] params = getSqlParams(element,fields);
		return BaseDao.executeUpdate(sql, params);
	}

	private Object[] getSqlParams(E element, Field[] fields) {
		Object[] params = new Object[fields.length];
		for(int i=0; i<fields.length; i++){
			fields[i].setAccessible(true);
			try {
				params[i] = fields[i].get(element);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return params;
	}


	private String getInsertSql(String tableName, Field[] fields) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(tableName).append(" values(");
		for(int i=0; i<fields.length; i++){
			sql.append("?,");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");		
		return sql.toString();
	}


	private String getTableName(Class clazz) {
		boolean existsTableAnno = clazz.isAnnotationPresent(Table.class);
		if (!existsTableAnno) {
			throw new RuntimeException(clazz + "没有注解哦");
		}
		Table tableAnno = (Table) clazz.getAnnotation(Table.class);
		return tableAnno.value();
	}


	/**
	 * 删除
	 */
	 public void delete(E element){
		 if (element == null) {
			 throw new IllegalArgumentException("删除对象不能为空哦");
		 }
		 int term=element.getClass().getName().lastIndexOf(".")+1;
		 StringBuilder sql = new StringBuilder("DELETE FROM  "
				 + element.getClass().getName().substring(term) + " WHERE ");
		 Field[] fields = element.getClass().getDeclaredFields();
		 sql.append(fields[0].getName()+"=?");

		 Object[] params = new Object[1];
		 try {
			 fields[0].setAccessible(true);
			 params[0]=fields[0].get(element);

			 String strSQL = new String(sql);
			 System.out.println(strSQL);
			 BaseDao.executeUpdate(strSQL, params);

		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	 }

	/**
	 * 更新
	 * @param element
	 */
	 public void  update(E element){
		int term=element.getClass().getName().lastIndexOf(".")+1;
		StringBuffer sql = new StringBuffer("UPDATE "
				+ element.getClass().getName().substring(term) + " SET ");
		Field[] fields = element.getClass().getDeclaredFields();

		int size = fields.length;
		Field field = null;
		Object[] params = new Object[size+1];

		try {
			for (int i = 0; i < size - 1; i++) {
				field = fields[i];
				field.setAccessible(true);//运行时 把private 转成 public
				params[i] = field.get(element);
				sql.append(field.getName() + "=?,");
			}
			fields[size-1].setAccessible(true);
			params[size - 1] = fields[size - 1].get(element);
			sql.append(fields[size - 1].getName() + "=? WHERE " +fields[0].getName()+"=?");
			params[size]=params[0];

			String strSQL = new String(sql);
			System.out.println(sql);
			BaseDao.executeUpdate(strSQL, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
	 * 查询  select * from Account where id =?
	 */
    public Object load(Class<?> clazz,Object uuid){
        Object obj=null;
        try {
            obj = clazz.newInstance();
            if (obj!=null){
                Field[] fields = obj.getClass().getDeclaredFields();
                Object[] params = new Object[1];
                int term=obj.getClass().getName().lastIndexOf(".")+1;
                StringBuffer sql = new StringBuffer("SELECT * FROM  "
                        + obj.getClass().getName().substring(term) + " WHERE "+fields[0].getName()+"=?");
                params[0]=uuid;

                String strSQL = new String(sql);
                obj=BaseDao.executeQuery(strSQL,params,clazz);

                System.out.println(sql);
                return obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
