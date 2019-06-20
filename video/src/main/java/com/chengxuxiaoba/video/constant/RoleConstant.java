package com.chengxuxiaoba.video.constant;

import com.chengxuxiaoba.video.util.ListUtil;

import java.util.*;
import java.util.stream.Collectors;

public enum RoleConstant {
    ADMIN(3, "管理员"), VIPMEMBER(2, "VIP会员"), VISTOR(1, "普通会员");

    private final Integer value;

    private RoleConstant(Integer value, String text) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static List<RoleConstant> getAll() {
        return new ArrayList<>(Arrays.asList(RoleConstant.ADMIN, RoleConstant.VIPMEMBER, RoleConstant.VISTOR));
    }

    public static RoleConstant getEnum(Integer value) {
        List<RoleConstant> _list = getAll();

        _list = _list.stream().filter(role -> {
            return role.value.equals(value);
        }).collect(Collectors.toList());

        if (ListUtil.isNullOrEmpty(_list))
            return null;

        return _list.get(0);
    }

    public static Map<Integer, String> getAllMap() {
        Map<Integer, String> _map = new HashMap<>();
        List<RoleConstant> _list = getAll();
        for (RoleConstant role : _list) {
            _map.put(role.getValue(), role.toString());
        }
        return _map;
    }
}
