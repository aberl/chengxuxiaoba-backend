package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Material;

import java.util.List;

public interface MaterialMapper {
    Integer insert(Material material);

    Material getSingle(Integer id);

    Integer update(Material material);

    List<Material> getMaterialList();
}
