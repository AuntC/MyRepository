package com.auntcai.demos.filter.filterview.menuViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.auntcai.demos.filter.R;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/9/16.
 */
public class Menu1 implements IMenu {

    private Context mContext;

    private View view;
    private TextView textView;

    public Menu1(Context context) {
        mContext = context;
        view = LayoutInflater.from(mContext).inflate(R.layout.filter_container_test, null);
        textView = (TextView) view.findViewById(R.id.filter_container_tv);
    }

    @Override
    public View getMenuView() {
        return view;
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }
}
