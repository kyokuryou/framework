package org.smarty.core.support.fun;

import java.io.Writer;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.statement.LineFunction;
import org.lilystudio.smarty4j.statement.ParameterCharacter;
import org.smarty.core.exception.InvokeMethodException;
import org.smarty.core.exception.NoSuchReflectException;
import org.smarty.core.utils.BeanUtil;
import org.smarty.core.utils.ObjectUtil;

/**
 * 输出in语法
 * <code>
 * Map a = new HashMap();
 * a.put("a-1","1");
 * <p/>
 * // 标准JavaBean
 * Bean b = new Bean();
 * b.setXX("1");
 * <p/>
 * List list = new ArrayList();
 * l.add(a);
 * l.add(b);
 * <p/>
 * Map map = new HashMap();
 * map.put("abc", list);
 * <p/>
 * String source = SELECT * FROM TABLE WHERE col in ({ins value=$abc item='a-1'})
 * TemplateUtil.render(source,map);
 * out:SELECT * FROM TABLE WHERE col in ('1')
 * </code>
 */
public class $ins extends LineFunction {
	private static final Log logger = LogFactory.getLog($ins.class);

	public void execute(Context context, Writer writer, Object[] objects) throws Exception {
		List value = (List) objects[0];
		String itemName = "";
		if (objects.length >= 2) {
			itemName = (String) objects[1];
		}
		StringBuilder s = new StringBuilder();
		for (Object obj : value) {
			Object val = getValue(obj, itemName);
			if (val == null) {
				s.append("null");
			} else if (val instanceof Number) {
				s.append(val);
			} else {
				s.append("'").append(val).append("'");
			}
			s.append(",");
		}
		s.delete(s.length() - 1, s.length());
		writer.write(s.toString());
	}

	/**
	 * 读取数据(从Map,Bean)
	 *
	 * @param obj  值
	 * @param item key名或字段名
	 * @return 值
	 */
	public Object getValue(Object obj, String item) {
		if (ObjectUtil.isEmpty(obj)) {
			return null;
		}
		if (!ObjectUtil.isEmpty(item)) {
			if (obj instanceof Map) {
				return ((Map) obj).get(item);
			}
			try {
				return BeanUtil.invokeGetterMethod(obj, item);
			} catch (NoSuchReflectException e) {
				logger.warn(e);
			} catch (InvokeMethodException e) {
				logger.warn(e);
			}
		}
		return obj;
	}


	public ParameterCharacter[] getDefinitions() {
		return new ParameterCharacter[]{new ParameterCharacter(ParameterCharacter.OBJECT, "value"), new ParameterCharacter(ParameterCharacter.STRING, null, "item")};
	}
}
