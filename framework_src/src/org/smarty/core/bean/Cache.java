package org.smarty.core.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 缓存数据容器
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class Cache implements Serializable {
	private static final long serialVersionUID = 468L;

	// 永久处理
	private boolean always;
	// 创建时间
	private Date createDate;
	// 读取时间
	private Date readDate;
	// 数据
	private Object data;

	/**
	 * 初始化缓存数据容器,并标识该容器不被永久缓存
	 */
	public Cache() {
		this.always = false;
		this.createDate = new Date();
		this.readDate = new Date();
	}

	/**
	 * 自定义初始化缓存数据容器
	 *
	 * @param data   数据
	 * @param always 永久标识
	 */
	public Cache(Object data, boolean always) {
		this.always = always;
		this.createDate = new Date();
		this.readDate = new Date();
		this.data = data;
	}

	/**
	 * 是否是永久缓存
	 *
	 * @return boolean
	 */
	public boolean isAlways() {
		return always;
	}

	/**
	 * 返回该容器创建时间
	 *
	 * @return Date对象
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 返回该容器读取时间
	 *
	 * @return Date对象
	 */
	public Date getReadDate() {
		return readDate;
	}

	/**
	 * 返回数据
	 *
	 * @return 数据
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置数据
	 *
	 * @param data 数据
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 克隆该缓存
	 *
	 * @return 缓存副本
	 */
	public Cache cloneCache() {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(this);
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			return (Cache) oi.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
