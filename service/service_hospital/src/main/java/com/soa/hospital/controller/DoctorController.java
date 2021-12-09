package com.soa.hospital.controller;

import com.soa.hospital.model.Doctor;
import com.soa.hospital.service.DoctorService;
import com.soa.hospital.views.DoctorInfo;
import com.soa.hospital.views.ReservationVo;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-24 10:18:18
 */
@RestController
@RequestMapping("/hospital/doctors")
@CrossOrigin
@Api(value="医生信息",tags = "医生信息",description = "医生信息")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @ApiOperation(value="根据医生id获取医生信息")
    @GetMapping("getDoctorInfo/{id}")
    public Result<Doctor> getDoctorInfo(@PathVariable String id){
        Doctor doctor=doctorService.getById(id);
        if(doctor!=null)
            return Result.wrapSuccessfulResult(doctor);
        else
            return Result.wrapErrorResult("error!");
    }

    //根据医生id查询医生信息、医院信息、科室信息
    @GetMapping("/getReservationVo/{id}")
    public Result<ReservationVo> getReservationVo(@PathVariable String id){
        Doctor doctor=doctorService.getById(id);
        if(doctor==null)
            return Result.wrapErrorResult("error");
        else{
            ReservationVo reservationVo=new ReservationVo();
            reservationVo.setCost(doctor.getCost());
            reservationVo.setDepartmentID(doctor.getDepartment().getId());
            reservationVo.setDepartmentName(doctor.getDepartment().getName());
            reservationVo.setDoctorName(doctor.getName());
            reservationVo.setDoctorTitle(doctor.getTitle());
            reservationVo.setHospitalID(doctor.getHospital().getId());
            reservationVo.setHospitalName(doctor.getHospital().getName());
            return Result.wrapSuccessfulResult(reservationVo);
        }
    }

    @ApiOperation(value="根据医院id获取全院医生信息列表")
    @GetMapping("getDoctorList/{id}")
    public Result<List<Doctor>> getHospDocList(@PathVariable String id){
        List<Doctor> hospDocList = doctorService.getHospDocList(id);
        if(hospDocList!=null)
            return Result.wrapSuccessfulResult(hospDocList);
        else
            return Result.wrapErrorResult("error!");
    }

    @ApiOperation(value="根据医院id和科室id获取某医院的某科室医生信息列表")
    @GetMapping("getDoctorList/{hid}/{did}")
    public Result<List<Doctor>> getDepartDocList(@PathVariable String hid,
                                                 @PathVariable String did){
        List<Doctor> departDocList = doctorService.getDepartDocList(hid,did);
        if(departDocList!=null)
            return Result.wrapSuccessfulResult(departDocList);
        else
            return Result.wrapErrorResult("error!");
    }

    @ApiOperation(value="根据医院id和科室id添加一个医生")
    @PostMapping("addDoctor/{hid}/{did}")
    public Result addDoctor(@PathVariable String hid,
                            @PathVariable String did,
                            @RequestBody DoctorInfo doctorInfo){
        Doctor byId = doctorService.getById(doctorInfo.getId());
        if(byId!=null)
            return Result.wrapErrorResult("医生id已经存在");

        boolean flag = doctorService.addDoctor(hid,did,doctorInfo);
        if(flag)
            return Result.wrapSuccessfulResult("success");
        else
            return Result.wrapErrorResult("error");
    }

    @ApiOperation(value="根据医生id修改医生基本信息")
    @PostMapping("updateDoctor")
    public Result updateDoctor(@RequestBody DoctorInfo doctorInfo){

        Doctor byId = doctorService.getById(doctorInfo.getId());
        if(byId==null)
            return Result.wrapErrorResult("医生不存在");

        boolean flag = doctorService.updateDoctor(doctorInfo);
        if(flag)
            return Result.wrapSuccessfulResult("success");
        else
            return Result.wrapErrorResult("error");
    }
}
