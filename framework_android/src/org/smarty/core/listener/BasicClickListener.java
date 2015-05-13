package org.smarty.core.listener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 *
 */
public abstract class BasicClickListener extends AbsDialogListener implements OnClickListener {
    public void onPositiveClick(DialogInterface dialog) {
        dialog.dismiss();
    }

    public void onNegativeClick(DialogInterface dialog) {
        dialog.dismiss();
    }

    public void onNeutralClick(DialogInterface dialog) {
        dialog.dismiss();
    }

    protected abstract void whichClick(int which);

    protected abstract void onItemClick(int choice, boolean checked);

    public final void onClick(DialogInterface dialog, int which) {
        if (DialogInterface.BUTTON_POSITIVE == which) {
            onPositiveClick(dialog);
        } else if (DialogInterface.BUTTON_NEGATIVE == which) {
            onNegativeClick(dialog);
        } else if (DialogInterface.BUTTON_NEUTRAL == which) {
            onNeutralClick(dialog);
        } else {
            whichClick(which);
        }
    }
}
