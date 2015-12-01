package com.meizu.caiweixin.mydemo.filter.bean;

import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 11/5/15.
 */
public class FilterDistrictBean {

    public interface Status {
        public static final int NOT_LOADED = 0;
        public static final int LOADING = 1;
        public static final int LOAD_SUCCESS = 2;
        public static final int LOAD_FAILED = 3;
    }

    private String regionCode;
    private String regionName;
    private int status;
    private List<FilterDistrictChildBean> filterDistrictChildBean;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<FilterDistrictChildBean> getFilterDistrictChildBean() {
        return filterDistrictChildBean;
    }

    public void setFilterDistrictChildBean(List<FilterDistrictChildBean> filterDistrictChildBean) {
        this.filterDistrictChildBean = filterDistrictChildBean;
    }

    public static boolean needLoad(FilterDistrictBean bean) {
        return null == bean || null == bean.getFilterDistrictChildBean() || bean.getStatus() == Status.NOT_LOADED || bean.getStatus() == Status.LOAD_FAILED;
    }
}
