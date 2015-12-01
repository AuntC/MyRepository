package com.meizu.caiweixin.mydemo.filter.bean;

/**
 * Description:
 *
 * @author caiweixin
 * @since 11/4/15.
 */
public class FilterDistrictChildBean {

    public FilterDistrictChildBean(String regionCode, String regionName, String businessCircleName, boolean isAll, boolean isSelected) {
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.businessCircleName = businessCircleName;
        this.isSelected = isSelected;
        this.isAll = isAll;
    }

    private String regionCode;

    private String regionName;

    private String businessCircleName;

    private boolean isSelected;

    private boolean isAll;

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

    public String getBusinessCircleName() {
        return businessCircleName;
    }

    public void setBusinessCircleName(String businessCircleName) {
        this.businessCircleName = businessCircleName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setIsAll(boolean isAll) {
        this.isAll = isAll;
    }
}