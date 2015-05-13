package org.smarty.core.app;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.smarty.core.beans.Constant;

/**
 * 菜单列表抽象实现
 */
public abstract class AbsMenuActivity extends BasicActivity {
    private ListView listViewMenu;

    protected abstract String[] getMenuItems();

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        // ThemesUtil.currentTheme(this);
        setViewInit();
        listViewMenu = (ListView) findViewById(getListLayout());
        listViewMenu.setAdapter(getAdapter());
        bindListener();
    }

    @Override
    protected void bindListener() {
        bindItemListener(listViewMenu);
    }

    protected void bindItemListener(ListView listViewMenu) {

    }

    protected int getListLayout() {
        return Constant.DEFAULT_LIST_LAYOUT;
    }

    protected ArrayAdapter<String> getAdapter() {
        return new ArrayAdapter<String>(this, Constant.DEFAULT_MENU_LAYOUT, getMenuItems());
    }

}
