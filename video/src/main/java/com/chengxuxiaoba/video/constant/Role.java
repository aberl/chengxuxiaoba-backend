package com.chengxuxiaoba.video.constant;

import com.chengxuxiaoba.video.util.ListUtil;

import java.util.*;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(1), MEMBER(2), VIPMEMBER(3);

    private final Integer value;

    private Role(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static List<Role> getAll() {
        return new ArrayList<>(Arrays.asList(Role.ADMIN, Role.MEMBER, Role.VIPMEMBER));
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
