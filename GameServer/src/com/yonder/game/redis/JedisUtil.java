/**
 * 
 */
package com.yonder.game.redis;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisUtil {

	private static final Logger LOGGER = Logger.getLogger(JedisUtil.class);  
    private static final String FILE_NAME = "redis.properties"; 
    private static final int MaxTryTimes = 10;//
    private static int DEFAULT_DB_INDEX = 0;  
    private static String password= "";
  
    private static JedisPool jedisPool = null;  
  
    private JedisUtil() {  
        // private constructor  
    	
    }  
    /**
     * 初始化
     */
    public static void initialPool() {  
    	if(jedisPool != null){
    		jedisPool.destroy();
    	}
        final Properties configurations = new Properties();  
        String filePath;  
        String address = "";  
        int port = 6379;  
  
        try {  
            //filePath = JedisUtil.class.getResource("/").getPath() + "/" + FILE_NAME; 
        	filePath = "./"+FILE_NAME;
            File file = new File(filePath);  
            
  
            configurations.load(new FileInputStream(file));  
            address = configurations.getProperty("redis.ip");  
            port = Integer.valueOf(configurations.getProperty("redis.port"));  
  
            String strDbIndex = configurations.getProperty("db_index");  
            if (strDbIndex != null) {  
                DEFAULT_DB_INDEX = Integer.valueOf(strDbIndex);  
            }  
            LOGGER.info("Redis server info: " + address + ":" + port);  
  
            final JedisPoolConfig config = new JedisPoolConfig();  
            String strMaxActive = configurations.getProperty("jedis.pool.maxActive");  
            if (strMaxActive != null) {  
            	config.setMaxTotal(Integer.valueOf(strMaxActive));
                //config.setMaxActive(Integer.valueOf(strMaxActive));  
            }  
            String strMaxIdle = configurations.getProperty("jedis.pool.maxIdle");  
            if (strMaxIdle != null) {  
                config.setMaxIdle(Integer.valueOf(strMaxIdle));  
            }  
            String strMaxWait = configurations.getProperty("jedis.pool.maxWait");  
            if (strMaxWait != null) {  
            	config.setMaxWaitMillis(Integer.valueOf(strMaxWait));
                //config.setMaxWait(Integer.valueOf(strMaxWait));  
            }            
            //config.setTestOnBorrow(false);  jedis.pool.testOnBorrow
            Boolean testOnBorrow = Boolean.valueOf(configurations.getProperty("jedis.pool.testOnBorrow"));
            config.setTestOnBorrow(testOnBorrow);    
            Boolean testOnReturn = Boolean.valueOf(configurations.getProperty("jedis.pool.testOnReturn"));
            config.setTestOnReturn(testOnReturn);    
  
            String strTimeout = configurations.getProperty("jedis.pool.connectTimeout");  
  
            int timeout = 2000;  
            if (strTimeout != null) {  
                timeout = Integer.valueOf(strTimeout);  
            }  
            password= configurations.getProperty("redis.password","");
            if(password!=null && !"".equals(password)){
            	jedisPool = new JedisPool(config, address, port, timeout,password);  
            }else{
            	jedisPool = new JedisPool(config, address, port, timeout);
            }
            //LOGGER.info("jedis pool info:"+address+":"+port+",maxActive="+config.getMaxActive()+",maxIdle="+config.getMaxIdle()+",maxWait="+config.getMaxWait()+",testOnBorrow="+config.isTestOnBorrow()+",testOnReturn="+config.isTestOnReturn());
            LOGGER.info("jedis pool info:"+address+":"+port+",maxActive="+config.getMaxTotal()+",maxIdle="+config.getMaxIdle()+",maxWait="+config.getMaxWaitMillis()+"ms,"+",testOnBorrow="+config.getTestOnBorrow()+",testOnReturn="+config.getTestOnReturn());
        } catch (Exception e) {  
            LOGGER.error("jedis pool error!", e);
        } finally {  
  
        }  
  
    }  
    /**
     * 
     * @return
     */
    public static Jedis getJedisInstance(int maxTryTimes) {  
    	if(jedisPool==null){
        	throw new IllegalStateException("请先调用 initialPool()后，再取Jedis资源！！");
        }
        try {  
            if (jedisPool != null) {  
                Jedis resource = null;
                
                for(int tryTimes=1;resource==null&&tryTimes<=maxTryTimes;tryTimes++){
                	if(tryTimes>1000){
                		Thread.sleep(500);
                	}
                	if(tryTimes>1){
                		LOGGER.error("重试连接redis:"+tryTimes);
                	}
	                try{	                	
	                	resource = getJedisFromPool();
	                }catch(JedisConnectionException jce){
	                	if(resource!=null){
	                		jedisPool.returnBrokenResource(resource);
	                	}
	                	if(tryTimes==maxTryTimes){
	                		//最后一次重试失败抛出异常
	                		throw jce;
	                	}
	                }	                
                }
                return resource;  
            } else {  
                return null;  
            }  
        }catch(JedisConnectionException jce){
        	throw jce;
        } catch (Exception e) {  
            LOGGER.error("used conn size:"+jedisPool.toString(), e);
            //LOGGER.error("used conn size:"+jedisPool.toString());
            return null;  
        }  
    }  
    /**
     * 
     * @return
     */
    public static Jedis getJedisInstance() {  
    	return getJedisInstance(MaxTryTimes);
    }  
    private static Jedis getJedisFromPool(){
    	Jedis resource = jedisPool.getResource();  
        if(!"".equals(password)){
        	resource.auth(password);
        }
        resource.select(DEFAULT_DB_INDEX);  
        return resource;  
    }
  
    
  
    public static void returnResource(final Jedis jedis) {  
        if (jedis != null) {  
            jedisPool.returnResource(jedis);  
        }  
    }  
    public static void returnBrokenResource(final Jedis jedis){
    	if(jedis!=null){
    		jedisPool.returnBrokenResource(jedis);
    	}
    }
    public static void destory(){
    	if(jedisPool!=null){
    		jedisPool.destroy();
    	}
    }
}
