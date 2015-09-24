package com.yonder.game.plugin.dbmigrating;

import java.io.File;

import org.flywaydb.core.Flyway;

import com.yonder.game.plugin.IPlugin;
import com.yonder.game.plugin.db.IDataSourceProvider;

public class DBMigratingPlugin implements IPlugin {
	
	private IDataSourceProvider dataSourceProvider; 

	public DBMigratingPlugin(IDataSourceProvider dataSourceProvider) {
		this.dataSourceProvider = dataSourceProvider;
	}
	
	@Override
	public boolean start() {
		try {
			File file = new File("db/migration");
			if(!file.exists() && !file.isDirectory()){
				file.mkdirs();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
		Flyway flyway = new Flyway();
		
		flyway.setDataSource(dataSourceProvider.getDataSource());
		flyway.setInitOnMigrate(true);
		flyway.setLocations("filesystem:db/migration");
		flyway.setValidateOnMigrate(false);
		flyway.setOutOfOrder(false);
		flyway.migrate();
		
		return true;
	}

	@Override
	public boolean stop() {
		return true;
	}

}
