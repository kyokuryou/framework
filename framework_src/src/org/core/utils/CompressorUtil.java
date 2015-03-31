package org.core.utils;

import com.google.javascript.jscomp.CompilationLevel;
import com.googlecode.htmlcompressor.compressor.ClosureJavaScriptCompressor;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.googlecode.htmlcompressor.compressor.XmlCompressor;

/**
 * xml,html,css,js压缩工具
 */
public class CompressorUtil {
    private CompressorUtil() {
    }

    /**
     * html压缩
     *
     * @param source 源
     * @return 压缩后
     */
    public static String htmlCompressor(String source) {
        HtmlCompressor compressor = new HtmlCompressor();
        // 所有压缩不绕过
        compressor.setEnabled(true);
        // 所有多空白字符将单个空格替换
        compressor.setRemoveMultiSpaces(true);
        // 删除注释
        compressor.setRemoveComments(true);
        // 删除空白标签  默认为false
        compressor.setRemoveIntertagSpaces(true);
        // 删除没用的标签属性 默认为false
        compressor.setRemoveQuotes(false);
        // 压缩js
        compressor.setCompressJavaScript(true);
        // 设置js压缩
        compressor.setJavaScriptCompressor(new ClosureJavaScriptCompressor(CompilationLevel.ADVANCED_OPTIMIZATIONS));
        // 存在DOCTYPE声明将取代简单的<！DOCTYPE html > 默认false
        compressor.setSimpleDoctype(true);
        // 删除script标签的属性 默认false
        compressor.setRemoveScriptAttributes(false);
        // 删除style标签的属性 默认false
        compressor.setRemoveStyleAttributes(false);
        // 删除link标签的属性 默认false
        compressor.setRemoveLinkAttributes(false);
        //删除form标签的属性 默认false
        compressor.setRemoveFormAttributes(false);
        // 删除input标签的属性 默认false
        compressor.setRemoveInputAttributes(false);
        // 删除事件上JavaScript： 默认false
        compressor.setRemoveJavaScriptProtocol(false);
        // 删除URL上HTTP:  默认false
        compressor.setRemoveHttpProtocol(false);
        // 保留换行 默认false
        compressor.setPreserveLineBreaks(false);
        // 删除p,br标签周围的空白
        compressor.setRemoveSurroundingSpaces("p,br");
        return compressor.compress(source);
    }

    /**
     * xml压缩
     *
     * @param source 源
     * @return 压缩后
     */
    public static String xmlCompressor(String source) {
        XmlCompressor compressor = new XmlCompressor();
        // 所有压缩不绕过
        compressor.setEnabled(true);
        // 删除注释
        compressor.setRemoveComments(true);
        // 删除空白标签  默认为false
        compressor.setRemoveIntertagSpaces(true);
        return compressor.compress(source);
    }
}
