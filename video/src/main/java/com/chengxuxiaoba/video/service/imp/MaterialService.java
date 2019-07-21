package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.mapper.MaterialMapper;
import com.chengxuxiaoba.video.model.po.Material;
import com.chengxuxiaoba.video.service.IMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialService implements IMaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public Material getMaterial(Integer id)
    {
        Material material=materialMapper.getSingle(id);

        return material;
    }

    @Override
    public List<Material> getMaterialList() {
        List<Material> materialList= materialMapper.getMaterialList();

        return materialList;
    }
    @Override
    public List<Material> getEffectiveMaterialList() {
        List<Material> materialList= materialMapper.getMaterialList();

        materialList = materialList.stream()
                .filter(material -> {return material.getStatus().equals(CommonStatus.ACTIVE.getValue());}).collect(Collectors.toList());

        return materialList;
    }

    @Override
    public Boolean createNewMaterial(Material material) {
        Integer primaryKey=materialMapper.insert(material);
        return primaryKey>0;
    }

    @Override
    public Boolean updateMaterial(Material material) {
        Integer primaryKey=materialMapper.update(material);
        return primaryKey>0;
    }

    @Override
    public Boolean inactiveMaterial(Integer id) {
        Material material = materialMapper.getSingle(id);
        if(material == null)
            return false;

        material.setStatus(CommonStatus.INACTIVE.getValue());
        Integer primaryKey= materialMapper.update(material);

        return primaryKey>0;
    }
}
