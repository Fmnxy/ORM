package me.jume.dao;

import me.jume.handler.BeanHandler;
import me.jume.handler.ResultSetHandler;
import me.jume.utils.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 本类封装了对数据库的增、删、改、查操作
 */
public class BaseDao {

     public static int executeUpdate(String sql,Object...params){
         int result=-1;  // -1表示执行失败
         Connection conn =null;
         PreparedStatement ps=null;
         try {
             conn = DBManager.getConnection();
             ps = conn.prepareStatement(sql);
             for (int i=0;i<params.length;i++){
                 ps.setObject(i+1,params[i]);
             }
             result=ps.executeUpdate();
         } catch (SQLException e) {
             try {
                 throw  new Exception("数据更新失败");
             } catch (Exception e1) {
                 e1.printStackTrace();
             }
             e.printStackTrace();
         }finally {
              DBManager.closeAll(null,ps,conn);
         }
        return result;
     }


    /**
     * 执行需要Connection的查询语句
     * @param sql           SQL语句
     * @param params        SQL语句参数列表
     * @return              查询结果集
     * @throws SQLException
     */
    public static ResultSet executeQuery(String sql, Object...params)
            throws SQLException{

        Connection conn = null;
        PreparedStatement pstmt = null;
        try
        {
            conn =DBManager.getConnection();
            //执行SQL语句预编译
            pstmt = conn.prepareStatement(sql);
            //赋值参数
            for(int i=0; i<params.length; i ++){
                pstmt.setObject(i+1, params[i]);
            }
            //执行查询
               return pstmt.executeQuery();
        }catch(SQLException e){

            throw new RuntimeException("SQL查询出错！",e);
        }finally {
            DBManager.closeAll(null,pstmt,conn);
        }

    }

    public static Object executeQuery(String sql, Object[] params, Class<?> clazz){

          ResultSetHandler rsh = new BeanHandler(clazz);
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try
            {
                conn =DBManager.getConnection();
                //执行SQL语句预编译
                pstmt = conn.prepareStatement(sql);
                //赋值参数
                for(int i=0; i<params.length; i ++){
                    pstmt.setObject(i+1, params[i]);
                }
                rs = pstmt.executeQuery();
                return rsh.handler(rs);
            }catch(SQLException e){
                throw new RuntimeException("SQL查询出错！",e);
            }finally {
                DBManager.closeAll(null,pstmt,conn);
            }

    }


}
