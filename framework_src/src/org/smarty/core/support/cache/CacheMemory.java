package org.smarty.core.support.cache;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.smarty.core.bean.Cache;
import org.smarty.core.exception.CacheNameNotExistException;
import org.smarty.core.utils.LogicUtil;

/**
 * 缓存管理
 */
final class CacheMemory {

	// 活动容器
	private final Map<String, Cache> cacheMap;

	CacheMemory(final Integer size) {
		cacheMap = new LinkedHashMap<String, Cache>(size, 0.75f, true) {
			// 容器映射增加缓存大小时,删除旧条目
			protected boolean removeEldestEntry(Map.Entry<String, Cache> eldest) {
				return size() > size;
			}
		};
	}

	/**
	 * 取出缓存
	 *
	 * @param key 名
	 * @return 缓存对象
	 */
	public Object getCache(String key) throws CacheNameNotExistException {
		Cache c;
		synchronized (cacheMap) {
			if (!cacheMap.containsKey(key)) {
				throw new CacheNameNotExistException();
			}
			c = cacheMap.get(key);
		}
		if (c == null) {
			return null;
		}
		return c.cloneCache().getData();
	}

	/**
	 * 放入缓存
	 *
	 * @param key  名
	 * @param data 值
	 */
	public void putCache(String key, Object data, boolean always) {
		if (LogicUtil.isEmpty(key) || data == null) {
			return;
		}
		synchronized (cacheMap) {
			if (!cacheMap.containsKey(key)) {
				cacheMap.put(key, new Cache(data, always));
			}
		}
	}

	/**
	 * 将值复制到缓存中
	 *
	 * @param caches 一些值
	 */
	public void putCaches(Map<String, Object> caches, boolean always) {
		synchronized (cacheMap) {
			for (Map.Entry<String, Object> cacheSet : caches.entrySet()) {
				cacheMap.put(cacheSet.getKey(), new Cache(cacheSet.getValue(), always));
			}
		}
	}

	/**
	 * 刷新缓存,当值为null时,从缓存容器中删除
	 *
	 * @param key  名
	 * @param data 值
	 */
	public void flushCache(String key, Object data) {
		if (LogicUtil.isEmpty(key)) {
			return;
		}
		synchronized (cacheMap) {
			if (data != null) {
				Cache c = cacheMap.get(key);
				cacheMap.put(key, new Cache(data, c.isAlways()));
			} else {
				cacheMap.remove(key);
			}
		}
	}

	/**
	 * 删除缓存
	 *
	 * @param key 名
	 */
	public void removeCache(String key) {
		if (LogicUtil.isEmpty(key)) {
			return;
		}
		synchronized (cacheMap) {
			cacheMap.remove(key);
		}
	}

	/**
	 * 返回KEY视图
	 *
	 * @return set
	 */
	public Set<String> getKeys() {
		synchronized (cacheMap) {
			return new HashSet<String>(cacheMap.keySet());
		}
	}
}
