package org.smarty.core.listener;

import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.BaseKeyListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式验证输入内容,因输入为动态,暂不支持最小长度校验.
 * new RegexKeyListener("[A-Z][0-9]{0,20}");
 */
public class RegexKeyListener extends BaseKeyListener implements InputFilter {
    private Pattern pattern;

    public RegexKeyListener(String regex) {
        this(regex, false);
    }

    public RegexKeyListener(String regex, boolean ignoreCase) {
        if (ignoreCase) {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        }
        pattern = Pattern.compile(regex);
    }

    @Override
    public int getInputType() {
        return InputType.TYPE_CLASS_TEXT;
    }

    private boolean matches(CharSequence charSequence) {
        if (pattern == null) {
            throw new RuntimeException("regex is error");
        }
        Matcher m = pattern.matcher(charSequence);
        return m.matches();
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        StringBuilder sb = new StringBuilder(dest);
        sb.insert(dstart, source, start, end);
        if (matches(sb)) {
            return source;
        }
        return "";
    }
}
