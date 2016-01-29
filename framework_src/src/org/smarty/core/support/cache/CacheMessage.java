package org.smarty.core.support.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.smarty.core.common.BaseConstant;
import org.smarty.core.exception.CacheNameNotExistException;
import org.smarty.core.utils.CommonUtil;
import org.smarty.core.utils.LogicUtil;

/**
 * 缓存管理器
 */
public class CacheMessage {
	private final String dignityKey = "42d8aa7cde9c78c4757862d84620c335";
	private String dignity;

	private static final ConcurrentMap<String, CacheMemory> memory = new ConcurrentHashMap<String, CacheMemory>();

	/**
	 * 带密钥初始化管理器
	 *
	 * @param dignity 密钥
	 */
	public CacheMessage(String dignity) {
		this.dignity = dignity;
	}

	/**
	 * 验证密钥,并初始化容器
	 *
	 * @param cacheMap 容器
	 */
	public void initCacheMap(Map<String, Integer> cacheMap) {
		if (!dignityKey.equals(CommonUtil.md5(dignity))) {
			return;
		}
		if (LogicUtil.isNotEmptyMap(cacheMap)) {
			for (Map.Entry<String, Integer> me : cacheMap.entrySet()) {
				memory.put(me.getKey(), new CacheMemory(me.getValue()));
			}
		}
	}

	/**
	 * 刷新系统级容器中数据
	 *
	 * @param key   key
	 * @param value value
	 */
	public static void flushSystemCache(String key, Object value) {
		CacheMemory cm = memory.get(BaseConstant.CACHE_SYSTEM);
		cm.flushCache(key, value);
	}

	/**
	 * 放入系统级容器中数据
	 *
	 * @param key   key
	 * @param value value
	 */
	public static void putSystemCache(String key, Object value) {
		CacheMemory cm = memory.get(BaseConstant.CACHE_SYSTEM);
		cm.putCache(key, value, true);
	}

	/**
	 * 获取系统级容器中数据
	 *
	 * @param key key
	 * @return value
	 */
	public static Object getSystemCache(String key) throws CacheNameNotExistException {
		CacheMemory cm = memory.get(BaseConstant.CACHE_SYSTEM);
		return cm.getCache(key);
	}

	/**
	 * 刷新临时容器中数据
	 *
	 * @param key   key
	 * @param value value
	 */
	public static void flushCache(String key, Object value) {
		CacheMemory cm = memory.get(BaseConstant.CACHE_TEMPORARY);
		cm.flushCache(key, value);
	}

	/**
	 * 放入临时容器中数据
	 *
	 * @param key   key
	 * @param value value
	 */
	public static void putCache(String key, Object value) {
		CacheMemory cm = memory.get(BaseConstant.CACHE_TEMPORARY);
		cm.putCache(key, value, false);
	}

	/**
	 * 获取临时容器中数据
	 *
	 * @param key key
	 * @return value
	 */
	public static Object getCache(String key) throws CacheNameNotExistException {
		CacheMemory cm = memory.get(BaseConstant.CACHE_TEMPORARY);
		return cm.getCache(key);
	}
}
