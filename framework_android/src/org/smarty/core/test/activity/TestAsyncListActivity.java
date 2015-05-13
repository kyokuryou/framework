package org.smarty.core.test.activity;

import android.widget.ListView;
import org.smarty.core.app.AbsHttpListActivity;
import org.smarty.core.handler.Proxy;
import org.smarty.core.http.HttpMessage;
import org.smarty.core.test.controller.TestAsyncListController;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kyokuryou on 15-3-16.
 */
@Proxy(TestAsyncListController.class)
public class TestAsyncListActivity extends AbsHttpListActivity {

    @Override
    protected void setViewInit() {
        try {
            Map<String, Object> formData = new HashMap<String, Object>();
            formData.put("id", "1");
            httpRequest("http://192.168.13.30/index.php", formData);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void bindHttpResponse(HttpMessage httpBean) {
        System.out.println(httpBean.getData());
//        TestList tl = EntryUtil.parseEntry(httpBean, TestList.class);
//        List<SiteEntry> ses = tl.getSiteList();
//        setHttpAsyncAdapter(talc.getHttpAsyncAdapter(ses));
    }

    @Override
    protected int getListLayout() {
        return super.getListLayout();
    }

    @Override
    protected void bindItemListener(ListView listView) {
        super.bindItemListener(listView);
    }

    @Override
    protected void bindListener() {
        super.bindListener();
    }

    @Override
    protected void setContentView() {
        super.setContentView();
    }

    @Override
    public void showProgressDialog() {
        super.showProgressDialog();
    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
    }
}
