/**
 * 
 */
package com.yonder.game.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;


public class RedisCache {
	static ThreadLocal<Jedis> cacheConnection = new ThreadLocal<Jedis>();
	private static int maxTryConnectTimes = 10;
	public static void setMaxTryConnectTimes(int maxTimes){
		maxTryConnectTimes = maxTimes;
	}
	public static void defaultMaxTimes(){
		maxTryConnectTimes = 10;//恢复默认连接数大小
	}
	/**
	 * 
	 * @return
	 */
	public static Jedis getCacheConnection() {
		if (cacheConnection.get() != null) {
			return cacheConnection.get();
		}
		Jedis connection = JedisUtil.getJedisInstance(maxTryConnectTimes);
		cacheConnection.set(connection);
		return connection;
	}
	/**
	 * 
	 */
	public static void closeCacheConnection() {
		if (cacheConnection.get() != null) {
			JedisUtil.returnResource(cacheConnection.get());
			cacheConnection.remove();
		}
	}
	/**
	 * 
	 */
	public static void returnBrokenResource(){
		if (cacheConnection.get() != null) {
			JedisUtil.returnBrokenResource(cacheConnection.get());			
		}
	}
//========================================================================================
	/**
	 * 按正则获取符合条件的key的集合
	 * @param keyPattern
	 * @return
	 */
	public static Set<String> keys(String keyPattern){
		return getCacheConnection().keys(keyPattern);
	}
	public static Pipeline pipelined(){
		return getCacheConnection().pipelined();
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static boolean exists(String key){
		Jedis jedis = getCacheConnection();
		return jedis.exists(key);
	}
	/**
	 * 
	 * @param keys
	 * @return
	 */
	public static Long del(String... keys){
		return getCacheConnection().del(keys);
	}
	public static String get(String key){
		return getCacheConnection().get(key);
	}
	public static String set(String key,String value){
		return getCacheConnection().set(key, value);
	}
	/**
	 * 
	 * @param key
	 * @param offset
	 * @return
	 */
	public static boolean getbit(String key,long offset){
		return getCacheConnection().getbit(key, offset);
	}
	
	public static Long bitcount(String key){
		return getCacheConnection().bitcount(key);
	}
	
	/**
	 * 
	 * @param key
	 * @param offset
	 * @param value
	 */
	public static void setbit(String key,long offset,boolean value){
		getCacheConnection().setbit(key, offset, value);
	}
	public static Long append(String key,String value) {
        Jedis jedis = getCacheConnection();
        return jedis.append(key,value);
    }
	
	/**
	 * 返回列表 key 的长度。
	 *如果 key 不存在，则 key 被解释为一个空列表，返回 0 .
	 *如果 key 不是列表类型，返回一个错误。
	 * @param key
	 * @return
	 */
	public static long llen(String key){		
		return getCacheConnection().llen(key);
	}
	
	public static long hlen(String key) {
		return getCacheConnection().hlen(key);
	}
	
	/**
	 * 将一个或多个值 value 插入到列表 key 的表头
	 *如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。
	 *如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
	 *当 key 存在但不是列表类型时，返回一个错误。
	 * @param key
	 * @param values
	 * @return
	 */
	public static long lpush(String key,String... values){
		return getCacheConnection().lpush(key, values);
	}
	public static String ltrim(String key,long start,long end){
		return getCacheConnection().ltrim(key, start, end);
	}
	/**
	 * 返回列表 key 中，下标为 index 的元素。
	 *下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
	 *你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 *如果 key 不是列表类型，返回一个错误。
	 * @param key
	 * @param index
	 * @return
	 */
	public static String lindex(String key,long index){
		return getCacheConnection().lindex(key, index);
	}
	/**
	 * 
	 * @param key
	 * @param start begin with 0
	 * @param end include end
	 * @return
	 */
	public static List<String> lrange(String key,long start,long end){
		return getCacheConnection().lrange(key, start, end);
	}
	/**
	 * 
	 * @param key
	 * @param field
	 */
	public static void hdel(String key,String field){
		Jedis jedis = getCacheConnection();
		jedis.hdel(key,field);
	}
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String hmset(String key,Map<String,String> value){
		Jedis jedis = getCacheConnection();
		return jedis.hmset(key, value);
	}
	/**
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static String hget(String key,String field){
		return getCacheConnection().hget(key, field);
	}
	/**
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static Long hset(String key,String field,String value){
		return getCacheConnection().hset(key, field, value);
	}
	/**
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static Long hincrBy(String key,String field,long value){
		return getCacheConnection().hincrBy(key, field, value);
	}
	/**
	 * 
	 * @param key
	 * @param expireAt second
	 * @return
	 */
	public static Long expireAt(String key,long expireAt){
		Jedis jedis = getCacheConnection();
		return jedis.expireAt(key, expireAt);
	}
	public static Long expire(String key,int seconds){
		Jedis jedis = getCacheConnection();
		return jedis.expire(key, seconds);
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static Long ttl(String key){
		return getCacheConnection().ttl(key);
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String,String> hgetAll(String key){
		Jedis jedis = getCacheConnection();
		return jedis.hgetAll(key);
	}
	public static Set<String> hkeys(String key){
		Jedis jedis = getCacheConnection();
		return jedis.hkeys(key);
	}
	
	/**
	 * 返回有序集 key 的基数
	 * @param key
	 */
	public static long zcard(String key){
		Jedis jedis = getCacheConnection();
		return jedis.zcard(key);
	}
	/**
	 * 
	 * @param key
	 * @param start 0,1,....
	 * @param end
	 * @return
	 */
	public static Set<String> zrange(String key,long start,long end){
		Jedis jedis = getCacheConnection();
		return jedis.zrange(key,start, end);		
	}
	public static Set<Tuple> zrangeWithScores(String key,long start,long end){
		Jedis jedis = getCacheConnection();		
		return jedis.zrangeWithScores(key, start, end);
	}
	/**
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> zrangeByScore(String key,double min,double max){
		Jedis jedis = getCacheConnection();
		return jedis.zrangeByScore(key,min, max);		
	}
	/**
	 * 逆序排列
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static Set<String> zrevrangeByScore(String key,long min,long max){
		Jedis jedis = getCacheConnection();		
		return jedis.zrevrangeByScore(key, max, min);
	}
	public static Set<String> zrevrangeByScore(String key,long min,long max,int offset,int count){
		Jedis jedis = getCacheConnection();		
		return jedis.zrevrangeByScore(key, max, min,offset,count);
	}
	/**
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> zrevrange(String key,long start,long end){
		Jedis jedis = getCacheConnection();		
		return jedis.zrevrange(key, start, end);
	}
	
	
	public static Set<Tuple> zrevrangeWithScores(String key,long start,long end){
		Jedis jedis = getCacheConnection();		
		return jedis.zrevrangeWithScores(key, start, end);
	}
	
	
	
	
	
	/**
	 * 
	 * @param key
	 * @param member
	 * @return 当找不到时，返回null
	 */
	public static Long zrank(String key,String member){
		return getCacheConnection().zrank(key, member);
	}
	public static Long zrevrank(String key,String member){
		return getCacheConnection().zrevrank(key, member);
	}
	public static Double zscore(String key,String member){
		return getCacheConnection().zscore(key, member);
	}
	/**
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public static Long zadd(String key,double score,String member){
		return getCacheConnection().zadd(key,score,	member);
	}
	public static Double zincrby(String key,double score,String member){
		return getCacheConnection().zincrby(key, score, member);
	}
	public static Long zremrangebyscore(String key, double min, double max){
		return getCacheConnection().zremrangeByScore(key, min, max);
	}
	/**
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public static Long zrem(String key,String... member){
		return getCacheConnection().zrem(key,member);
	}
	
	
}
