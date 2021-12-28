package com.soa.hospital.controller;

import com.soa.hospital.model.Apply;
import com.soa.hospital.model.Hospital;
import com.soa.hospital.service.ApplyService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ program: demo
 * @ description: apply
 * @ author: ShenBo
 * @ date: 2021-11-18 15:28:14
 */
@RestController
@RequestMapping("/hospital/applies")
@CrossOrigin
@Api(value="加入申请",tags = "加入申请",description = "加入申请")
public class ApplyController {
    @Autowired
    ApplyService applyService;

    @ApiOperation(value="提交申请信息(不用传时间，只传信息就行)")
    @PostMapping("info")
    public Result applyInfo(@RequestBody Apply apply){
        applyService.applyInfo(apply);
        return Result.wrapSuccessfulResult("Success!");
    }

    @ApiOperation(value="获取申请列表")
    @GetMapping("getApplyList")
    public Result getApplyList(){
        List<Apply> applyList = applyService.getApplyList();
        return Result.wrapSuccessfulResult(applyList);
    }

}
