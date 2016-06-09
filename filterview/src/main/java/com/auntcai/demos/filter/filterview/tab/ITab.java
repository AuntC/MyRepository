package com.auntcai.demos.filter.filterview.tab;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/9/16.
 */
public interface ITab {

    interface Status {
        int OPEN = 0;
        int CLOSE = 1;
    }

    int getIndex();

    CharSequence getShowText();

    void setStatus(int status);

    int getStatus();
}
