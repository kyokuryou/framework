package org.core.support.fun;

import org.core.exception.InvokeMethodException;
import org.core.exception.NoSuchReflectException;
import org.core.logger.RuntimeLogger;
import org.core.utils.BeanUtil;
import org.core.utils.LogicUtil;
import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.statement.LineFunction;
import org.lilystudio.smarty4j.statement.ParameterCharacter;

import java.io.Writer;
import java.util.List;
import java.util.Map;

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
    private static final RuntimeLogger logger = new RuntimeLogger($ins.class);

    @Override
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
        if (obj == null) {
            return null;
        }
        if (LogicUtil.isNotEmpty(item)) {
            if (obj instanceof Map) {
                return ((Map) obj).get(item);
            }
            try {
                return BeanUtil.invokeGetterMethod(obj, item);
            } catch (NoSuchReflectException e) {
                logger.out(e);
            } catch (InvokeMethodException e) {
                logger.out(e);
            }
        }
        return obj;
    }


    public ParameterCharacter[] getDefinitions() {
        return new ParameterCharacter[]{
                new ParameterCharacter(ParameterCharacter.OBJECT, "value"),
                new ParameterCharacter(ParameterCharacter.STRING, null, "item")
        };
    }
}
