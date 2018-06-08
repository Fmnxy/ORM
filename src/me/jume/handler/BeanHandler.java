package me.jume.handler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class BeanHandler implements ResultSetHandler {

    private Class<?> beanClass;

    public BeanHandler(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public Object handler(ResultSet rs) {

        try {
            if (!rs.next()){
                return null;
            }
            // 用于封装数据的bean
            Object bean = beanClass.newInstance();
            ResultSetMetaData meta = rs.getMetaData();  //获取数据库字段名
            int columnCount = meta.getColumnCount();    //获取列的数量
            for (int i=0;i<columnCount;i++){
                String columnName = meta.getColumnName(i + 1);
                Object columnValue = rs.getObject(columnName);
                PropertyDescriptor pd = new PropertyDescriptor(columnName,
                        bean.getClass());
                Method method = pd.getWriteMethod();
                method.invoke(bean, columnValue);
            }
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
