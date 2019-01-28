package com.hongqiao.dao;

import java.sql.SQLException;
import java.util.List;

import com.hongqiao.dto.Text;

import javafx.util.Pair;

public interface TextDao {
	
	 /**
     * create table
     * @param tableName
     * @param items
     * @return
     * @throws SQLException
     */ 
	public Boolean crateTable(String tableName,String[] items) throws SQLException;
	
	 /**
     * search all data
     * @param tableName
     * @param items
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Text> selectAll(String tableName)throws SQLException;
	
	  /**
     * insert data to table
     * @param tableName
     * @param items
     * @param values
     * @param hid
     * @return
     * @throws SQLException
     */
    public Boolean insert(String tableName,String[] items,String[] values) throws SQLException;
    
    /**
     * search data
     * @param tableName
     * @param items
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Text> select(String tableName,String[] items,List<String> params)throws SQLException;
	
    /**
     * delete data
     * @param sql
     * @param items
     * @param params
     * @return
     * @throws SQLException
     */
    public Boolean delete(String tableName,String id) throws SQLException;
    
    /**
     * update data to table
     * @param tableName
     * @param items
     * @param values
     * @param hid
     * @return
     * @throws SQLException
     */
    public Boolean update(String tableName,List< Pair<String, String>> items) throws SQLException;
	
}
