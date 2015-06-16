package mb.mba.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.SafeEncoder;

/**
 * redis工具
 * @author cyyu
 *
 */
public class RedisUtil {
	
	/**  默认过期时间 */
	private static final Integer DEFAULT_EXPIRE ;
//	/**  redis服务地址  */
	private static final String URL  ;
	private static ShardedJedisPool pool;
	
	static{
		ResourceBundle prop = ResourceBundle.getBundle("redis");
		DEFAULT_EXPIRE= Integer.parseInt(prop.getString("redis.default.expire"));
		URL =prop.getString( "redis.url");
		JedisPoolConfig config = new JedisPoolConfig();
		String[] iports = URL.split(";");
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		for (String iport : iports) {
			String[] temp = iport.split(":");
			String ip = temp[0];
			Integer port = temp.length>1?Integer.parseInt(temp[1]):Protocol.DEFAULT_PORT;
			JedisShardInfo jsi = new JedisShardInfo(ip,port);
			shards.add(jsi);
		}
		pool = new ShardedJedisPool(config, shards);
	}
	
	/**
	 * 获得redis连接
	 * @return
	 */
	private static ShardedJedis getJedis(){
		return pool.getResource();
	}
	
	/**
	 * 释放redis连接
	 * @param jedis
	 */
	private static void freeConn(ShardedJedis redis){
		pool.returnResource(redis);
	}
	
	/**
	 * 清空单个缓存对象
	 * @param key
	 * @return 1:成功 0:失败
	 */
	public static Long  delete(String key){
		ShardedJedis jedis =null;
		Long result = null;
		try {
			 jedis = getJedis();
			 result = jedis.del(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if (jedis != null) {
				freeConn(jedis);
			}
		}
		return result;
	}
	
	/**
	 * 查询缓存
	 * @param key
	 * @return 
	 */
	public static  Set<String>  searchKeys(String pattern){
		Jedis jedis = null;
		Set<String>result  = null;
		try {
			jedis = new Jedis(URL);
			result = jedis.keys(pattern);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	
	/**
	 * 查看缓存类型
	 * @param key
	 * @return
	 */
	public static String  type(String key){
		ShardedJedis jedis =null;
		String result = null;
		try {
			 jedis = getJedis();
			 result = jedis.type(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if (jedis != null) {
				freeConn(jedis);
			}
		}
		return result;
	}
	
	/**
	 * 查看缓存过期时间
	 * @param key
	 * @return 秒
	 */
	public static Long  expireTime(String key){
		ShardedJedis jedis =null;
		Long result = null;
		try {
			 jedis = getJedis();
			 result = jedis.ttl(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if (jedis != null) {
				freeConn(jedis);
			}
		}
		return result;
	}
	
	
	/**
	 * 读取字符串缓存
	 * @param key
	 * @return
	 */
	public static String  get(String key){
		ShardedJedis jedis =null;
		String result = null;
		try {
			 jedis = getJedis();
			 result = jedis.get(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if (jedis != null) {
				freeConn(jedis);
			}
		}
		return result;
	}
	
	/**
	 * 读取单个对象缓存
	 * @param key
	 * @return
	 */
	public static <T extends Serializable> T  getVo(String key){
		ShardedJedis jedis =null;
		T result = null;
		try {
			 jedis = getJedis();
			result = unserializable(jedis.get(SafeEncoder.encode(key)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if (jedis != null) {
				freeConn(jedis);
			}
		}
		return result;
	}
	
	/**
	 * 缓存单个实体
	 * @param key
	 * @param t
	 */
	public static <T extends Serializable>  void setVo(String key ,T t){
		ShardedJedis jedis =null;
		try {
			jedis = getJedis();
			jedis.set(SafeEncoder.encode(key), serializable(t));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if (jedis != null) {
				freeConn(jedis);
			}
		}
	
	}
	
	/**
	 * 添加简单对象缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public static String set(String key,Object value){
		return set(key, value, DEFAULT_EXPIRE);
	}
	
	/**
	 * 添加简单对象缓存
	 * @param key
	 * @param value
	 * @param second 过期时间（秒）
	 * @return
	 */
	public static String set(String key,Object value,Integer second){
		if (value == null ) {
			return null;
		}
		ShardedJedis jedis =null;
		String result = null;
		try {
			jedis = getJedis();
			result =  jedis.setex(key, second, value.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if (jedis != null) {
				freeConn(jedis);
			}
		}
		return result;
	}
	
	/**
	 * 获取单个vo对象
	 * @param key  如：表名
	 * @param field 如：表中的主键 联合主键用冒号隔开
	 * @param cls vo的class
	 */
	public static <T extends Serializable> T getVo(final String key,final String field){
		ShardedJedis jedis = null;
		T result = null;
		try {
			jedis = getJedis();
			Long isExists = jedis.ttl(key);
			if (isExists == -2	// 数据不存在时
					|| isExists == 0) { // 数据失效时
				jedis.del(key); // 清空这个key的数据
				return null;
			}
			byte[] b = jedis.hget(SafeEncoder.encode(key), SafeEncoder.encode(field));
			result = unserializable(b);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if (jedis != null) {
				freeConn(jedis);
			}
		}
		return result;
	}
	
	/**
	 * 添加单个vo对象
	 * @param key  如：表名
	 * @param field 如：表中的主键 联合主键用冒号隔开
	 */
	public static <T extends Serializable> Long setVo(final String key,final String field,T value){
		return setVo(key, field, value, DEFAULT_EXPIRE);
	}
	
	/**
	 * 添加单个vo对象
	 * @param key  如：表名
	 * @param field 如：表中的主键 联合主键用冒号隔开
	 * @seconds 过期时间
	 */
	public static <T extends Serializable> Long setVo(final String key,final String field,T value,Integer seconds){
		ShardedJedis jedis = null;
		Long result = null;
		if (field==null || value == null) {
			return null;
		}
		try {
			 jedis = getJedis();
			Long isExists = jedis.ttl(key);
			result = jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(field),serializable(value));
			if (isExists == -2	// 数据不存在时
					|| isExists == 0) { // 数据失效时，进行有效期控制
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if (jedis != null) {
				freeConn(jedis);
			}
		}
		return result;
	}
	
	/**
	 * 将可序列化对象添加进set缓存
	 * @param key
	 * @param v
	 * @return
	 */
	public <T extends Serializable> Long sadd(String key, T v){
		ShardedJedis redis = null;
	    try {
	      redis = getJedis();
	      return redis.sadd(SafeEncoder.encode(key), serializable(v));
	    } catch (Exception e) {
	      throw new RuntimeException(e.getMessage());
	    } finally {
	      if (null != redis)
	        freeConn(redis);
	    }
	  }
	
	/**
	 * 讲可序列化对象添加到list缓存
	 * @param key
	 * @param v
	 * @return
	 */
	public static <T extends Serializable> Long rpush(String key, T v){
		ShardedJedis redis = null;
	    try {
	      redis = getJedis();
	      return redis.rpush(SafeEncoder.encode(key), serializable(v));
	    } catch (Exception e) {
	      throw new RuntimeException(e.getMessage());
	    } finally {
	      if (null != redis)
	        freeConn(redis);
	    }
	  }
	
	/**
	 * 取出某个区间内的list缓存
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T extends Serializable> List<T> getListByIndex(String key, int start, int end){
		ShardedJedis redis = null;
	    try {
	      redis = getJedis();
	      Long isExists = redis.ttl(key);
		  if (isExists == -2	// 数据不存在时
				  || isExists == 0) { // 数据失效时
			  redis.del(key); // 清空这个key的数据
			  return null;
		  }
	      return unserializable(redis.lrange(SafeEncoder.encode(key), start, end));
	    } catch (Exception e) {
	      throw new RuntimeException(e.getMessage(), e);
	    } finally {
	      if (null != redis)
	    	  freeConn(redis);
	    }
	  }
	
	/**
	 * 取出整个list缓存
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T extends Serializable> List<T> getListAll(String key){
		ShardedJedis redis = null;
	    try {
	      redis = getJedis();
	      Long isExists = redis.ttl(key);
		  if (isExists == -2	// 数据不存在时
				  || isExists == 0) { // 数据失效时
			  redis.del(key); // 清空这个key的数据
			  return null;
		  }
	      Long size = redis.llen(key);
	      return unserializable(redis.lrange(SafeEncoder.encode(key), 0, size));
	    } catch (Exception e) {
	      throw new RuntimeException(e.getMessage(), e);
	    } finally {
	      if (null != redis)
	    	  freeConn(redis);
	    }
	  }
	
	/**
	 * 取出整个set缓存
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T extends Serializable> Set<T> getSet(String key){
		ShardedJedis redis = null;
	    try {
	      redis = getJedis();
	      Long isExists = redis.ttl(key);
		  if (isExists == -2	// 数据不存在时
				  || isExists == 0) { // 数据失效时
			  redis.del(key); // 清空这个key的数据
			  return null;
		  }
	      return unserializable(redis.smembers(SafeEncoder.encode(key)));
	    } catch (Exception e) {
	      throw new RuntimeException(e.getMessage(), e);
	    } finally {
	      if (null != redis)
	    	  freeConn(redis);
	    }
	  }
	
	/**
	 * 取出整个hashMap
	 * @param key
	 * @param field
	 * @return
	 */
	public static <T extends Serializable> Map<String ,T> getHash(final String key){
		
		ShardedJedis redis = null;
	    try {
	      redis = getJedis();
	      Long isExists = redis.ttl(key);
		  if (isExists == -2	// 数据不存在时
				  || isExists == 0) { // 数据失效时
			  redis.del(key); // 清空这个key的数据
			  return null;
		  }
	      return unserializable(redis.hgetAll(SafeEncoder.encode(key)));
	    } catch (Exception e) {
	      throw new RuntimeException(e.getMessage(), e);
	    } finally {
	      if (null != redis)
	    	  freeConn(redis);
	    }
	}
	
	/**
	 * 将hash保存进redis
	 * @param key
	 * @param map
	 * @return
	 */
	public static <T extends Serializable> void setHash(final String key,Map<String, T> map){
		setHash(key, map, DEFAULT_EXPIRE);
	}
	
	/**
	 * 将hash保存进redis
	 * @param key
	 * @param map
	 * @param seconds 秒
	 * @return
	 */
	public static <T extends Serializable> void setHash(final String key,Map<String, T> map,Integer seconds){
		ShardedJedis redis = null;
	    try {
	      redis = getJedis();
	      byte[] in_key = SafeEncoder.encode(key);
	      for (Entry<String, T> entry : map.entrySet()) {
	    	  redis.hset(in_key, SafeEncoder.encode(entry.getKey()), serializable(entry.getValue()));
			}
	      redis.expire(in_key, seconds);
	    } catch (Exception e) {
	      throw new RuntimeException(e.getMessage(), e);
	    } finally {
	      if (null != redis)
	    	  freeConn(redis);
	    }
	}
	
	/**
	 * 将list保存进redis
	 * @param key
	 * @param list
	 */
	public static <T extends Serializable> void  setList(final String key,List<T> list){
		setList(key, list, DEFAULT_EXPIRE);
	}
	/**
	 * 将list保存进redis
	 * @param key
	 * @param list
	 * @param seconds 秒
	 */
	public static <T extends Serializable> void  setList(final String key,List<T> list,Integer seconds){
		ShardedJedis redis = null;
	    try {
	      redis = getJedis();
	      byte[] in_key = SafeEncoder.encode(key);
	      for (T t : list) {
			redis.rpush(in_key, serializable(t));
	      }
	      redis.expire(in_key, seconds);
	    } catch (Exception e) {
	      throw new RuntimeException(e.getMessage(), e);
	    } finally {
	      if (null != redis)
	    	  freeConn(redis);
	    }
	}
	
	/**
	 * 将set保存进redis
	 * @param key
	 * @param set
	 */
	public static <T extends Serializable> void setSet(final String key,Set<T> set){
		setSet(key, set, DEFAULT_EXPIRE);
	}
	
	/**
	 * 将set保存进redis
	 * @param key
	 * @param set
	 * @param seconds 秒
	 */
	public static <T extends Serializable> void setSet(final String key,Set<T> set,Integer seconds){
		ShardedJedis redis = null;
	    try {
	      redis = getJedis();
	      byte[] in_key = SafeEncoder.encode(key);
	      for (T t : set) {
	    	  redis.sadd(in_key, serializable(t));
	      }
	      redis.expire(in_key, seconds);
	    } catch (Exception e) {
	      throw new RuntimeException(e.getMessage(), e);
	    } finally {
	      if (null != redis)
	    	  freeConn(redis);
	    }
	}
	
	/**
	   * 序列化
	   * @param t
	   * @return
	   */
	  public static <T extends Serializable> byte[] serializable(T t){
		  if (t == null) {
			return null;
		  }
		  ByteArrayOutputStream baos = null;
		  ObjectOutputStream oos = null;
		  try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(t);
			return baos.toByteArray();
		  } catch (Exception e) {
			e.printStackTrace();
		  }finally{
			  try {
				if (oos != null) {
					oos.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		  }
		  return null;
	  }
	  
	  /**
	   * 反序列化
	   * @param b
	   * @return
	   */
	  @SuppressWarnings("unchecked")
	  public static <T extends Serializable> T unserializable(byte[] b){
		  if (b == null) {
			return null;
		  }
		  ByteArrayInputStream bais = null;
		  ObjectInputStream ois = null;
		  try {
			bais = new ByteArrayInputStream(b);
			ois = new ObjectInputStream(bais);
			return (T) ois.readObject();
		  } catch (Exception e) {
				e.printStackTrace();
		  }finally{
			  try {
				if (ois != null) {
					ois.close();
				}
				if (bais != null) {
					bais.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		  }
		  return null;
	  }
	  
	  /**
	   * 批量反序列化
	   * @param list
	   * @return
	   */
	  @SuppressWarnings("unchecked")
	  public static <T extends Serializable> List<T> unserializable(List<byte[]> list){
		  List<T> result = new ArrayList<T>();
		  if (list == null || list.size() == 0) {
			return null;
		  }
		  for (byte[] b : list) {
			if (b != null) {
				result.add((T) unserializable(b));
			}
		  }
		  return result;
	  }
	  
	  /**
	   * 批量反序列化Set
	   * @param set
	   * @return
	   */
	  @SuppressWarnings("unchecked")
	  public static <T extends Serializable> Set<T> unserializable(Set<byte[]> set){
		  Set<T> result = new HashSet<T>();
		  if (set == null || set.size() == 0) {
			return null;
		  }
		  for (byte[] b : set) {
			if (b != null) {
				result.add((T) unserializable(b));
			}
		  }
		  return result;
	  }
	  
	  /**
	   * 批量反序列化Hash
	   * @param set
	   * @return
	   */
	  @SuppressWarnings("unchecked")
	  public static <T extends Serializable> Map<String,T> unserializable(Map<byte[],byte[]> hash){
		  Map<String,T> result = new HashMap<String,T>();
		  if (hash == null || hash.size() == 0) {
			return null;
		  }
		  for (Entry<byte[], byte[]> e : hash.entrySet()) {
			if (e != null) {
				result.put(SafeEncoder.encode(e.getKey()),(T) unserializable(e.getValue()));
			}
		  }
		  return result;
	  }
}
