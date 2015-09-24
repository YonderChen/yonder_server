/**
 * 
 */
package com.yonder.game.plugin.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonder.game.exception.GameException;
public class Db {
	private static final Logger logger = LoggerFactory.getLogger(Db.class);
	private static DataSource dataSource;
	
	public static void setDataSource(DataSource dataSource) {
		Db.dataSource = dataSource;
	}

	public static boolean existTable(String tableName){
		Connection conn = null;
		ResultSet tabs = null; 
		try {
			conn = dataSource.getConnection();
			DatabaseMetaData dbMetaData = conn.getMetaData();  
            String[]   types   =   { "TABLE" };  
            tabs = dbMetaData.getTables(null, null, tableName, types);  
            if (tabs.next()) {  
                return true;  
            }  
		} catch (Exception e) {
			logger.error("", e);
			throw new GameException(GameException.DataBaseError);
		} finally {
		}
		return false;
	}
	
	public static <T> List<T> findColumn(String sql, Object... params) {
		try {
			QueryRunner qr = new QueryRunner(dataSource);
			return qr.query(sql, new ColumnListHandler<T>(), params);
		} catch (SQLException e) {
			logger.error("SQL语句"+sql+"执行失败", e);
			throw new GameException(GameException.DataBaseError, "SQL语句"+sql+"执行失败");
		}
	}
	
	public static <T> T findFirstColumn(String sql, Object... params) {
		try {
			QueryRunner qr = new QueryRunner(dataSource);
			List<T> rs = qr.query(sql, new ColumnListHandler<T>(), params);
			if(rs == null || rs.size() == 0){
				return null;
			}
			return rs.get(0);
		} catch (SQLException e) {
			logger.error("SQL语句"+sql+"执行失败", e);
			throw new GameException(GameException.DataBaseError, "SQL语句"+sql+"执行失败");
		}
	}
	
	public static <T> List<T> findBean(String sql, Class<T> clazz, Object... params) {
		try {
			QueryRunner qr = new QueryRunner(dataSource);
			return qr.query(sql, new BeanListHandler<T>(clazz), params);
		} catch (SQLException e) {
			logger.error("SQL语句"+sql+"执行失败", e);
			throw new GameException(GameException.DataBaseError, "SQL语句"+sql+"执行失败");
		}
	}
	
	public static <T> T findFirstBean(String sql, Class<T> clazz, Object... params) {
		try {
			QueryRunner qr = new QueryRunner(dataSource);
			List<T> rs = qr.query(sql, new BeanListHandler<T>(clazz), params);
			if(rs == null || rs.size() == 0){
				return null;
			}
			return rs.get(0);
		} catch (SQLException e) {
			logger.error("SQL语句"+sql+"执行失败", e);
			throw new GameException(GameException.DataBaseError, "SQL语句"+sql+"执行失败");
		}
	}
	
	public static List<Map<String, Object>> findMap(String sql, Object... params) {
		try {
			QueryRunner qr = new QueryRunner(dataSource);
			return qr.query(sql, new MapListHandler(), params);
		} catch (SQLException e) {
			logger.error("SQL语句"+sql+"执行失败", e);
			throw new GameException(GameException.DataBaseError, "SQL语句"+sql+"执行失败");
		}
	}
	
	public static Map<String, Object> findMapFirst(String sql, Object... params) {
		try {
			QueryRunner qr = new QueryRunner(dataSource);
			List<Map<String, Object>> rs = qr.query(sql, new MapListHandler(), params);
			if(rs == null || rs.size() == 0){
				return null;
			}
			return rs.get(0);
		} catch (SQLException e) {
			logger.error("SQL语句"+sql+"执行失败", e);
			throw new GameException(GameException.DataBaseError, "SQL语句"+sql+"执行失败");
		}
	}
	
	public static int update(String sql, Object... params) {
		try {
			QueryRunner qr = new QueryRunner(dataSource);
			return qr.update(sql, params);
		} catch (SQLException e) {
			logger.error("SQL语句"+sql+"执行失败", e);
			throw new GameException(GameException.DataBaseError, "SQL语句"+sql+"执行失败");
		}
	}
}
