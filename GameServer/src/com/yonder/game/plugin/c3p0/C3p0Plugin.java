/**
 * 
 */
package com.yonder.game.plugin.c3p0;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yonder.game.plugin.IPlugin;
import com.yonder.game.plugin.db.IDataSourceProvider;

public class C3p0Plugin implements IPlugin, IDataSourceProvider  {
	private String jdbcUrl;
	private String user;
	private String password;
	private String driverClass = "com.mysql.jdbc.Driver";
	private int maxPoolSize = 100;
	private int minPoolSize = 10;
	private int initialPoolSize = 10;
	private int maxIdleTime = 20;
	private int acquireIncrement = 2;
	
	private ComboPooledDataSource dataSource;
	
	public C3p0Plugin(String jdbcUrl, String user, String password, String driverClass, Integer maxPoolSize, Integer minPoolSize, Integer initialPoolSize, Integer maxIdleTime, Integer acquireIncrement) {
		initC3p0Properties(jdbcUrl, user, password, driverClass, maxPoolSize, minPoolSize, initialPoolSize, maxIdleTime, acquireIncrement);
	}
	
	private void initC3p0Properties(String jdbcUrl, String user, String password, String driverClass, Integer maxPoolSize, Integer minPoolSize, Integer initialPoolSize, Integer maxIdleTime, Integer acquireIncrement) {
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;
		this.driverClass = driverClass != null ? driverClass : this.driverClass;
		this.maxPoolSize = maxPoolSize != null ? maxPoolSize : this.maxPoolSize;
		this.minPoolSize = minPoolSize != null ? minPoolSize : this.minPoolSize;
		this.initialPoolSize = initialPoolSize != null ? initialPoolSize : this.initialPoolSize;
		this.maxIdleTime = maxIdleTime != null ? maxIdleTime : this.maxIdleTime;
		this.acquireIncrement = acquireIncrement != null ? acquireIncrement : this.acquireIncrement;
	}
	
	public boolean start() {
		dataSource = new ComboPooledDataSource();
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		try {
			dataSource.setDriverClass(driverClass);
		} catch (PropertyVetoException e1) {
			dataSource.close();
			return false;
		}
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setMinPoolSize(minPoolSize);
		dataSource.setInitialPoolSize(initialPoolSize);
		dataSource.setMaxIdleTime(maxIdleTime);
		dataSource.setAcquireIncrement(acquireIncrement);
		return true;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public boolean stop() {
		if (dataSource != null) {
			dataSource.close();
		}
		return true;
	}
}
