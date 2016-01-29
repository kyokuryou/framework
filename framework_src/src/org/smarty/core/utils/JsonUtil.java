package org.smarty.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.common.BaseConstant;

/**
 * json工具
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public final class JsonUtil {
	private static Log logger = LogFactory.getLog(JsonUtil.class);

	private JsonUtil() {
	}

	private static Gson getGson() {
		GsonBuilder gb = new GsonBuilder();
		// 不导出实体中没有用@Expose注解的属性
		gb.excludeFieldsWithoutExposeAnnotation();
		// 支持Map的key为复杂对象的形式
		gb.enableComplexMapKeySerialization();
		// 时间转化为特定格式
		gb.setDateFormat(BaseConstant.DEF_DATETIME_FORMAT);
		// 序列化null字段
		gb.serializeNulls();
		// 会把字段首字母大写
		// gb.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
		// 对json结果格式化.
		// gb.setPrettyPrinting();
		// 版本号来选择是否要序列化.
		gb.setVersion(1.0);
		return gb.create();
	}

	public static String encode(Map<String, ?> src) {
		StringWriter sw = new StringWriter();
		toJsonWriter(src, sw);
		String json = sw.toString();
		logger.warn(json);
		return json;
	}

	public static String encode(Serializable src) {
		StringWriter sw = new StringWriter();
		toJsonWriter(src, sw);
		String json = sw.toString();
		logger.warn(json);
		return json;
	}

	public static Map decode(String json) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		logger.warn(json);
		return decode(element, HashMap.class);
	}

	public static <T extends Serializable> T decode(String json, Class<T> clzss) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		logger.warn(json);
		return decode(element, clzss);
	}

	public static <T extends Serializable> T decodeByKey(String json, Class<T> clzss) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		logger.warn(json);
		return decodeByKey(element, clzss);
	}

	public static <T extends Serializable> List<?> decodeArray(String json, Class<T> clzss) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		logger.warn(json);
		return decodeArray(element, clzss);
	}

	public static <T extends Serializable> T decode(JsonElement element, Class<T> clzss) {
		Gson gson = getGson();
		return gson.fromJson(element, clzss);
	}

	public static <T extends Serializable> T decodeByKey(JsonElement element, Class<T> clzss) {
		Gson gson = getGson();
		return gson.fromJson(element, clzss);
	}

	public static <T extends Serializable> List<?> decodeArray(JsonElement element, Class<T> clzss) {
		if (element == null || !element.isJsonArray()) {
			return null;
		}
		Gson gson = getGson();
		return gson.fromJson(element, getListType(element, clzss));
	}

	private static Type getListType(JsonElement element, Class clzss) {
		JsonArray arr = element.getAsJsonArray();
		JsonElement ele = arr.get(0);
		ListToken token = new ListToken();
		if (!ele.isJsonArray()) {
			token.addType(clzss);
			return token;
		}
		token.addType(getListType(ele, clzss));
		return token;
	}

	private static void toJsonWriter(Object src, Writer writer) {
		Gson gson = getGson();
		gson.toJson(src, writer);
	}

	private static final class ListToken implements ParameterizedType {
		private Type type;

		public void addType(Type type) {
			this.type = type;
		}

		@Override
		public Type[] getActualTypeArguments() {
			return new Type[]{type};
		}

		@Override
		public Type getRawType() {
			return List.class;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}
	}
}
