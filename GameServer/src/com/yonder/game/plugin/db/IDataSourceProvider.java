/**
 * 
 */
package com.yonder.game.plugin.db;

import javax.sql.DataSource;

public interface IDataSourceProvider {
	DataSource getDataSource();
}
