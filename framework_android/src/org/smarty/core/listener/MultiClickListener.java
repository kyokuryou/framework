package org.smarty.core.listener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 多选按钮多事件实现
 */
public class MultiClickListener extends BasicClickListener implements OnMultiChoiceClickListener {
    private Set<Integer> choiceSet = new HashSet<Integer>();

    @Override
    protected void whichClick(int which) {

    }

    @Override
    protected void onItemClick(int choice, boolean checked) {
        if (checked) {
            choiceSet.add(choice);
        } else {
            choiceSet.remove(choice);
        }
    }

    public void onPositiveClick(DialogInterface dialog, int[] choices) {

    }

    protected final int[] getChoice(Set<Integer> choiceSet) {
        int chs = choiceSet.size();
        int[] choice = new int[chs];
        Iterator<Integer> tt = choiceSet.iterator();
        for (int i = 0; tt.hasNext(); i++) {
            choice[i] = tt.next();
        }
        return choice;
    }

    @Override
    public final void onClick(DialogInterface dialog, int which, boolean checked) {
        onItemClick(which, checked);
    }


    @Override
    public final void onPositiveClick(DialogInterface dialog) {
        onPositiveClick(dialog, getChoice(choiceSet));
    }


}
