package com.yonder.game.plugin.db;

import com.yonder.game.plugin.IPlugin;

public class DBPlugin implements IPlugin {

	private IDataSourceProvider dataSourceProvider; 

	public DBPlugin(IDataSourceProvider dataSourceProvider) {
		this.dataSourceProvider = dataSourceProvider;
	}
	
	@Override
	public boolean start() {
		Db.setDataSource(this.dataSourceProvider.getDataSource());
		return true;
	}

	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return true;
	}

}
