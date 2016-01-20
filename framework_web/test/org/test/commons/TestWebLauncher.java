package org.test.commons;

import java.util.Locale;
import java.util.Set;
import org.smarty.web.commons.WebLauncher;
import org.springframework.context.MessageSource;

/**
 * Created by kyokuryou on 15-4-2.
 */
public class TestWebLauncher extends WebLauncher {

    @Override
    public void setMessageSource(MessageSource messageSource) {
        super.setMessageSource(messageSource);
        String text = messageSource.getMessage("sms.order.paid.ac", new Object[0], Locale.CHINA);
        System.out.println(text);
    }

    protected Set<ClassLoader> getLauncher() {
        Set<ClassLoader> cls = super.getLauncher();
        cls.add(TestWebLauncher.class.getClassLoader());
        return cls;
    }
}
