package com.chengxuxiaoba.video.constant;

public enum RolePaymentTypeEnum {
    DAY("day"),MONTH("month"),SEASON("season"),YEAR("year");

    private final String value;

    private RolePaymentTypeEnum(String value){
        this.value=value;
    }

    public String getValue(){
        return this.value;
    }

    public static RolePaymentTypeEnum getEnum(String text){
        if(text == null || text.trim()=="")
            return  null;

        if(text.trim().equalsIgnoreCase(RolePaymentTypeEnum.DAY.value))
            return RolePaymentTypeEnum.DAY;
        if(text.trim().equalsIgnoreCase(RolePaymentTypeEnum.MONTH.value))
            return RolePaymentTypeEnum.MONTH;
        if(text.trim().equalsIgnoreCase(RolePaymentTypeEnum.SEASON.value))
            return RolePaymentTypeEnum.SEASON;
        if(text.trim().equalsIgnoreCase(RolePaymentTypeEnum.YEAR.value))
            return RolePaymentTypeEnum.YEAR;

        return null;
    }
}
