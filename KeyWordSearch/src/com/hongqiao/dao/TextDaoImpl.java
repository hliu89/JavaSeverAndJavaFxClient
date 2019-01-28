package com.hongqiao.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hongqiao.dto.Text;
import com.hongqiao.jdbc.DBConnection;

import javafx.util.Pair;

public class TextDaoImpl implements TextDao{
	
	  /**
     * create table
     * @param tableName
     * @param items
     * @return
     * @throws SQLException
     */ 
	public Boolean crateTable(String tableName,String[] items) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBConnection.getConnection();
            DatabaseMetaData meta = conn.getMetaData();
 
            ResultSet rsTables = meta.getTables(null, null, tableName,
                    new String[] { "TABLE" });
            if (!rsTables.next()) {
                stmt = conn.createStatement();
 
                StringBuilder sql = new StringBuilder();
                sql.append(" CREATE TABLE ");
                if (StringUtils.isNotEmpty(tableName)) {
                    sql.append(tableName);
                }
                if (items != null && items.length > 0) {
                    sql.append(" ( ");
                    for (int i = 0;i < items.length;i++) {
                        sql.append(items[i]);
                        sql.append(" VARCHAR(5000), ");
                    }
                    sql.append("PRIMARY KEY(id)) ");
                }
 
                stmt.execute(sql.toString());
            }
            rsTables.close();
            return true;
        } finally {
            releaseConnection(conn, stmt, null);
        }
    }
	
	  /**
     * insert data to table
     * @param tableName
     * @param items
     * @param values
     * @param hid
     * @return
     * @throws SQLException
     */
    public Boolean insert(String tableName,String[] items,String[] values) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO ");
            if (StringUtils.isNotEmpty(tableName)) {
                sql.append(tableName);
            }
            if (items != null && items.length > 0) {
                sql.append(" ( ");
                String strItems= StringUtils.join(items, ",");
                sql.append(strItems);
                sql.append(" ) ");
                sql.append(" VALUES( ");
                for (int i = 0;i < items.length;i++) {
                    sql.append("? ");
                    if (i < items.length - 1){
                        sql.append(", ");
                    }
                }
                sql.append(") ");
            }
 
            stmt = conn
                    .prepareStatement(sql.toString());
            // values
            if (values != null && values.length > 0) {
                for (int i = 0;i < values.length;i++) {
                    stmt.setString(i+1, values[i]);
                }
            }
            return stmt.execute();
        } finally {
            conn.commit();
            releaseConnection(conn, stmt, rs);
        }
    }
    /**
     * update data to table
     * @param tableName
     * @param items
     * @param values
     * @param hid
     * @return
     * @throws SQLException
     */
    public Boolean update(String tableName,List< Pair<String, String>> items) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE ");
            if (StringUtils.isNotEmpty(tableName)) {
                sql.append(tableName+" SET ");
            }
            String where=null;
            if (items != null && items.size() > 0) {
                for (int i = 0;i < items.size();i++) {
                	if(items.get(i).getKey().equalsIgnoreCase("id")) {
                		where="WHERE id= "+items.get(i).getValue();
                		continue;
                	}
                    sql.append(items.get(i).getKey()+"= '"+items.get(i).getValue()+"'");
                    if (i < items.size() - 1){
                        sql.append(", ");
                    }
                }
            }
            sql.append(where);
            stmt = conn
                    .prepareStatement(sql.toString());
            stmt.execute();
            return true;
        } catch(Exception e) {
        	return false;
        } finally {
            conn.commit();
            releaseConnection(conn, stmt, rs);
        }
    }
    /**
     * search data
     * @param tableName
     * @param items
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Text> select(String tableName,String[] items,List<String> params)throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Text> result = new ArrayList<Text>();
        try {
            conn = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append(" Select ");
            if (items != null && items.length > 0) {
                String strItems= StringUtils.join(items, ",");
                sql.append(strItems);
            }
            sql.append(" FROM ");
            if (StringUtils.isNotEmpty(tableName)) {
                sql.append(tableName);
            }
            // exist search condition
            if (params != null && params.size() > 0) {
 
                sql.append(" WHERE ");
                for (String key:params) {
                    sql.append(key);
                    sql.append(" and ");
                }
                sql.append("1 = 1");
            }
            stmt = conn
                    .prepareStatement(sql.toString());
 
            rs = stmt.executeQuery();
            while (rs.next()) {
				Text text = new Text();
				text.setId(rs.getString("id"));
				text.setSummary(rs.getString("summary"));
				text.setTitle(rs.getString("title"));
				result.add(text);
            }
            return result;
        } finally {
            releaseConnection(conn, stmt, rs);
        }
    }
    
    /**
     * search all data
     * @param tableName
     * @param items
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Text> selectAll(String tableName)throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Text> result = new ArrayList<Text>();
        try {
            conn = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append(" Select * ");
            sql.append(" FROM ");
            if (StringUtils.isNotEmpty(tableName)) {
                sql.append(tableName);
            }
            stmt = conn
                    .prepareStatement(sql.toString());
 
            rs = stmt.executeQuery();
            while (rs.next()) {
                    Text text=new Text();
                    text.setId(rs.getString("id"));
                    text.setSummary(rs.getString("summary"));
                    text.setTitle(rs.getString("title"));
                result.add(text);
            }
            return result;
        } finally {
            releaseConnection(conn, stmt, rs);
        }
    }
 
    /**
     * delete data
     * @param sql
     * @param items
     * @param params
     * @return
     * @throws SQLException
     */
    
    public Boolean delete(String tableName,String id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM ");
            if (StringUtils.isNotEmpty(tableName)) {
                sql.append(tableName+"  where id = '"+id+"'");
            }
            
            stmt = conn
                    .prepareStatement(sql.toString());
            stmt.execute();
            return true;
        }catch(Exception e) {
        	return false;
        } finally {
            conn.commit();
            releaseConnection(conn, stmt, rs);
        }
    }
	
	private static void releaseConnection(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	}
}
