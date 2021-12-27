package com.soa.hospital.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soa.hospital.model.Hospital;
import com.soa.hospital.repository.HospitalRepository;
import com.soa.hospital.service.HospitalService;
import com.soa.hospital.views.HospitalBaseInfo;
import com.soa.hospital.views.HospitalInfo;
import com.soa.hospital.views.PatientInfo;
import com.soa.utils.error.HospitalNotExistedError;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.soa.utils.utils.Result;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * @ program: demo
 * @ description: HospitalController
 * @ author: ShenBo
 * @ date: 2021-11-16 18:32:42
 */
@RestController
@RequestMapping("/hospital/hospitals")
@CrossOrigin
@Api(value="医院信息",tags = "医院信息",description = "医院信息")
public class HospitalController {
    @Autowired
    HospitalService hospitalService;

    @Autowired
    HospitalRepository hospitalRepository;

    @ApiOperation(value = "审核通过后，医院加入系统")
    @PostMapping(path = "/join/{id}")
    public Result<String> joinSystem(@PathVariable String id){
        Hospital hospital= hospitalService.getById(id);

        if(hospital!=null)
            return Result.wrapErrorResult(new HospitalNotExistedError());

        RestTemplate restTemplate = new RestTemplate();
        String location = "http://127.0.0.1:18080/api/hospitals/"+id;
        JSONObject json = restTemplate.getForEntity(location, JSONObject.class).getBody().getJSONObject("data");
        HospitalInfo hospitalInfo= JSON.parseObject(String.valueOf(json),HospitalInfo.class);
        return hospitalService.joinSystem(hospitalInfo);
    }

    @ApiOperation(value = "Netty监听，前端不用处理")
    @PostMapping(path = "/update")
    public Result<String> updateSystem(@RequestBody HospitalInfo body){
        return hospitalService.updateHospitalInfo(body);
    }

    @ApiOperation(value="根据id获取医院信息")
    @GetMapping("/getHospInfo/{id}")
    public Result<Hospital> getHospSet(@PathVariable String id) {
        Hospital hospital = hospitalService.getById(id);
        if(hospital!=null)
            return Result.wrapSuccessfulResult(hospital);
        else
            return Result.wrapErrorResult("error!");
    }

    @ApiOperation(value="根据医院id修改医院基本信息")
    @PostMapping("updateHospital")
    public Result updateHospital(@RequestBody HospitalBaseInfo hospital){
        boolean update = hospitalService.update(hospital);
        if(update)
            return Result.wrapSuccessfulResult("Success!");
        else
            return Result.wrapErrorResult("error!");
    }

    @ApiOperation(value="按医院id修改医院公告")
    @PostMapping("updateNoticeById")
    public Result updateNoticeById(@RequestBody HospitalBaseInfo hospital){
        hospitalService.updateNoticeById(hospital);
        return Result.wrapSuccessfulResult("Success!");
    }

    @ApiOperation(value="按医院id修改医院logo，请传递id和oss服务返回的图片url")
    @PostMapping("updateLogoById")
    public Result updateLogoById(@RequestBody HospitalBaseInfo hospital){
        hospitalService.updateLogoById(hospital);
        return Result.wrapSuccessfulResult("Success!");
    }

    @ApiOperation(value="按医院id修改医院密码")
    @PostMapping("updatePassById")
    public Result updatePassById(@RequestBody HospitalBaseInfo hospital){
        hospitalService.updatePassById(hospital);
        return Result.wrapSuccessfulResult("Success!");
    }

    @ApiOperation(value="根据科室id获取医院详细信息列表")
    @GetMapping("getHospListInfo/{id}")
    public Result<List<Hospital>> getHospListInfo(@PathVariable String id) {
        List<Hospital> hospitals = hospitalService.getHospListByDepartId(id);
        if(hospitals!=null)
            return Result.wrapSuccessfulResult(hospitals);
        else
            return Result.wrapErrorResult("error!");
    }
}
