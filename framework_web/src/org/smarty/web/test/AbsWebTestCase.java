package org.smarty.web.test;

import org.junit.Test;
import org.smarty.core.test.AbsTestCase;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Created by kyokuryou on 15-4-2.
 */
public class AbsWebTestCase extends AbsTestCase {
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;

    @Override
    protected void setUpSpring(String... files) {
        super.setUpSpring(files);
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
    }
}
