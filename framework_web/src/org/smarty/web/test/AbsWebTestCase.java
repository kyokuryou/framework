package org.smarty.web.test;

import org.smarty.core.test.AbsTestCase;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class AbsWebTestCase extends AbsTestCase {
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;

    protected void setUpSpring(String... files) {
        super.setUpSpring(files);
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
    }
}
