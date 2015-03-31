package org.smarty.core.bank;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.struts2.ServletActionContext;
import org.smarty.core.bank.alipay.AlipayCore;
import org.smarty.core.bank.utils.MD5Util;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AlipaySubmit extends BankSubmit {
    /**
     * 生成签名结果
     *
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public String buildRequestMysign(Map<String, String> sPara) {
        //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String prestr = AlipayCore.createLinkString(sPara);
        if ("MD5".equals(signType)) {
            return MD5Util.sign(prestr, key, inputCharset);
        }
        return "";
    }

    /**
     * 生成要请求给支付宝的参数数组
     *
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);

        //签名结果与签名方式加入请求提交参数组中
        if (service != null && !"".equals(service)) {
            sPara.put("service", service);
        }
        if (partner != null && !"".equals(partner)) {
            sPara.put("partner", partner);
        }
        if (sellerEmail != null && !"".equals(sellerEmail)) {
            sPara.put("seller_email", sellerEmail);
        }
        if (notifyUrl != null && !"".equals(notifyUrl)) {
            sPara.put("notify_url", notifyUrl);
        }
        if (returnUrl != null && !"".equals(returnUrl)) {
            sPara.put("return_url", returnUrl);
        }
        if (inputCharset != null && !"".equals(inputCharset)) {
            sPara.put("_input_charset", inputCharset);
        }
        sPara.put("sign", buildRequestMysign(sPara));
        sPara.put("sign_type", signType);
        return sPara;
    }


    /**
     * 建立请求，以表单HTML形式构造（默认）
     *
     * @param params 请求参数数组
     * @return 提交表单HTML文本
     */
    public String buildRequest(Map<String, String> params) {
        //待请求参数数组
        Map<String, Object> sPara = new HashMap<String, Object>();
        sPara.put("paramsMap", buildRequestPara(params));

        ServletContext servletContext = ServletActionContext.getServletContext();
        try {
            Configuration configuration = freemarkerManager.getConfiguration(servletContext);
            Template template = configuration.getTemplate(ftlPath, "utf-8");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, sPara);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
