package com.framework.core;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framework.core.beans.ActivityPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TestFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        Intent intent = new Intent(getActivity(), TestA.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return inflater.inflate(R.layout.test, container, false);
    }

    /**
     * 加载插件列表
     *
     * @param plugins
     */
    private void attachPlugin(final List<ActivityPlugin> plugins) {
        for (ActivityPlugin plugin : plugins) {
            Button btn = new Button(this);
            btn.setTextColor(Color.RED);
            btn.setText(plugin.getLabel());

            llMainLayout.addView(btn);
            //添加事件
            btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    boolean isAttack = chbAttachMain.isChecked();


                    Intent it = new Intent();
                    it.setAction(plugin.getPakageName());

                    //是否附加为view
                    if (isAttack) {
                        //这里偷下懒，这是演示插件作为view附加到主程序中的
                        for (PluginBean plugin : plugins) {

                            Intent itt = new Intent();
                            itt.setAction(plugin.getPakageName());
                            ViewGroup view = (ViewGroup) (m_ActivityManager.startActivity("", itt)).getDecorView();
                            wkMain.addView(view);


                        }
                        //一次性附加完毕算了，然后把按钮都删了，看着清净，这几个不是重点
                        llMainLayout.removeAllViews();
                        chbAttachMain.setVisibility(View.GONE);
                        wkMain.setToScreen(0);
                    } else {
                        //这里，不会把插件的窗体附加到主程序中，纯粹无用的演示
                        startActivity(it);
                    }
                }
            });


        }
    }

    /**
     * 查找插件
     *
     * @return
     */
    private List<ActivityPlugin> findPlugins() {

        List<ActivityPlugin> plugins = new ArrayList<ActivityPlugin>();

        //遍历包名，来获取插件
        PackageManager pm = getActivity().getPackageManager();

        List<PackageInfo> pkgs = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo pkg : pkgs) {
            ApplicationInfo ai = pkg.applicationInfo;
            //包名
            String packageName = pkg.packageName;
            String sharedUserId = pkg.sharedUserId;

            //sharedUserId是开发时约定好的，这样判断是否为自己人
            if (!Constant.SHARED_USER_ID.equals(sharedUserId)) {
                continue;
            }

            ActivityPlugin plug = new ActivityPlugin();
            plug.setLabel(pm.getApplicationLabel(ai).toString());
            plug.setPackageName(packageName);
            plug.setPrcessName(ai.processName);

            plugins.add(plug);
        }
        return plugins;
    }
}
