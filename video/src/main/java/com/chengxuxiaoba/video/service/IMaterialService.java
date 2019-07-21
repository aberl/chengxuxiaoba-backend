package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.po.Material;

import java.util.List;

public interface IMaterialService {
    Boolean createNewMaterial(Material material);

    Material getMaterial(Integer id);

    List<Material> getMaterialList();

    List<Material> getEffectiveMaterialList();

    Boolean updateMaterial(Material material);

    Boolean inactiveMaterial(Integer id);
}
