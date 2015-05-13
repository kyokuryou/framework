package org.smarty.core.app;

import org.smarty.core.http.HttpAsyncTask;
import org.smarty.core.http.HttpMessage;
import org.smarty.core.http.HttpResponseListener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * 异步ListView抽象实现
 */
public abstract class AbsHttpListActivity extends AbsAsyncListActivity {

    protected final void httpRequest(String url) throws URISyntaxException {
        httpRequest(url, null);
    }

    protected final void httpRequest(String url, Map<String, Object> formData) throws URISyntaxException {
        HttpAsyncTask hat = new HttpAsyncTask(new URI(url), formData);
        hat.setHttpResponseListener(new HttpResponseListener() {
            @Override
            public void refresh(HttpMessage httpBean) {
                bindHttpResponse(httpBean);
                dismissProgressDialog();
            }
        });
        showProgressDialog();
        hat.execute();
    }

    protected void bindHttpResponse(HttpMessage httpBean) {

    }

}

