package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.model.Request.VO.MaterialRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.MaterialResponseVo;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.Material;
import com.chengxuxiaoba.video.service.IMaterialService;
import com.chengxuxiaoba.video.service.IVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MaterialController {

    @Autowired
    private IMaterialService materialService;

    @Autowired
    private IVoService voService;

    @PostMapping("/material")
    public Result<Boolean> createMaterial(MaterialRequestVo materialRequestVo) {
        Material material = voService.convertToMaterial(materialRequestVo);

        if (material == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        Boolean flag = materialService.createNewMaterial(material);
        return new Result<Boolean>(ResultCode.Success, flag, flag ? ResultMessage.Success : ResultMessage.Fail);

    }

    @PutMapping("/material")
    public Result<Boolean> updateMaterial(MaterialRequestVo materialRequestVo) {
        Material material =materialService.getMaterial(materialRequestVo.getId());

        if (material == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.MaterialIsNotExist);

        material=voService.convertToMaterial(materialRequestVo);

        Boolean flag = materialService.updateMaterial(material);

        return new Result<Boolean>(ResultCode.Success, flag, flag ? ResultMessage.Success : ResultMessage.Fail);
    }

    @PutMapping("/material/inactive")
    public Result<Boolean> inactiveMaterial(MaterialRequestVo materialRequestVo) {
        Boolean flag = materialService.inactiveMaterial(materialRequestVo.getId());

        return new Result<Boolean>(ResultCode.Success, flag, flag ? ResultMessage.Success : ResultMessage.Fail);
    }

    @GetMapping("/material/{id}")
    public Result<MaterialResponseVo> getMaterial(@PathVariable("id") Integer id)
    {
        Material material = materialService.getMaterial(id);

        MaterialResponseVo materialResponseVo=voService.convertToMaterialResponseVo(material, null);

        return new Result<MaterialResponseVo>(ResultCode.Success, materialResponseVo, ResultMessage.Success);
    }

    @GetMapping("/material/effective")
    public Result<List<MaterialResponseVo>> getEffectiveMaterialList()
    {
        List<Material> materialList = materialService.getEffectiveMaterialList();

        List<MaterialResponseVo> materialResponseVoList=voService.convertToMaterialResponseVo(materialList);

        return new Result<List<MaterialResponseVo>>(ResultCode.Success, materialResponseVoList, ResultMessage.Success);
    }

    @GetMapping("/material/all")
    public Result<List<MaterialResponseVo>> getAllMaterialList()
    {
        List<Material> materialList = materialService.getMaterialList();

        List<MaterialResponseVo> materialResponseVoList=voService.convertToMaterialResponseVo(materialList);

        return new Result<List<MaterialResponseVo>>(ResultCode.Success, materialResponseVoList, ResultMessage.Success);
    }
}
