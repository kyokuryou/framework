package org.smarty.core.utils;

import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.Engine;
import org.lilystudio.smarty4j.Template;
import org.smarty.core.bean.TemplateConfig;
import org.smarty.core.io.RuntimeLogger;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * 工具类 - 模板配置
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class LilystudioUtil {
    private static RuntimeLogger logger = new RuntimeLogger(LilystudioUtil.class);

    private static Engine engine;

    static {
        Properties prop = new Properties();
        prop.put("debug", false);
        prop.put("encoding", "UTF-8");
        prop.put("template.path", "/");
        prop.put("left.delimiter", "#{");
        prop.put("right.delimiter", "}");
        prop.put("package.function", "org.smarty.core.support.fun");
        // prop.put("package.modifier", "");
        engine = new Engine(prop);
    }

    private LilystudioUtil() {
    }

    /**
     * 渲染内容.
     *
     * @param source 模板内容.
     * @param model  变量Map.
     */
    public static String render(String source, Map<String, Object> model) {
        Template template;
        try {
            template = new Template(engine, source);
        } catch (Exception e) {
            logger.out(e);
            return null;
        }

        Context context = new Context();
        if (model != null) {
            context.putAll(model);
        }
        Writer result = new StringWriter();
        template.merge(context, result);
        return result.toString();
    }

    /**
     * 渲染内容.
     *
     * @param tc    模板信息.
     * @param model 变量Map.
     */
    public static void render(TemplateConfig tc, Map<String, Object> model) {
        Template template;
        try {
            template = engine.getTemplate(tc.getSrc());
        } catch (Exception e) {
            logger.out(e);
            return;
        }
        Context context = new Context();
        context.putAll(model);
        try {
            template.merge(context, tc.getTarget());
        } catch (Exception e) {
            logger.out(e);
        }
    }
}