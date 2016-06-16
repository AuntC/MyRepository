package com.auntcai.demos.filter.HorizontalTranslate;

import android.view.View;
import android.widget.TextView;

import com.auntcai.demos.filter.R;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/16/16.
 */
public class HTViewHolder extends HorizontalFlipperView.ViewHolder {
    TextView textView;

    public HTViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textview);
    }

    public void bindData(String s) {
        textView.setText(s);
    }
}
