package com.chengxuxiaoba.video.constant;

import com.chengxuxiaoba.video.util.ListUtil;

import java.util.*;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(1, "管理员"), MEMBER(2, "普通会员"), VIPMEMBER(3, "VIP会员"), VISTOR(4, "游客");

    private final Integer value;

    private Role(Integer value, String text) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static List<Role> getAll() {
        return new ArrayList<>(Arrays.asList(Role.ADMIN, Role.MEMBER, Role.VIPMEMBER, Role.VISTOR));
    }

    public static Role getEnum(Integer value) {
        List<Role> _list = getAll();

        _list = _list.stream().filter(role -> {
            return role.value.equals(value);
        }).collect(Collectors.toList());

        if (ListUtil.isNullOrEmpty(_list))
            return null;

        return _list.get(0);
    }

    public static Map<Integer, String> getAllMap() {
        Map<Integer, String> _map = new HashMap<>();
        List<Role> _list = getAll();
        for (Role role : _list) {
            _map.put(role.getValue(), role.toString());
        }
        return _map;
    }
}
