package com.soa.hospital.controller;

import com.soa.hospital.model.Department;
import com.soa.hospital.model.Hospital;
import com.soa.hospital.repository.HospInfoRepository;
import com.soa.hospital.service.DepartmentService;
import com.soa.hospital.service.HospInfoService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ program: demo
 * @ description: department
 * @ author: ShenBo
 * @ date: 2021-11-18 14:38:29
 */
@RestController
@RequestMapping("/department")
@CrossOrigin
@Api(value="科室信息",tags = "科室信息",description = "科室信息")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @Autowired
    HospInfoService hospInfoService;

    @ApiOperation(value="根据科室id获取科室信息")
    @GetMapping("getDepartInfo/{id}")
    public Result<Department> getDepartInfo(@PathVariable String id) {
        Department department = departmentService.getById(id);
        if(department!=null)
            return Result.wrapSuccessfulResult(department);
        else
            return Result.wrapErrorResult("error!");
    }

    @ApiOperation(value="根据医院id查询科室信息列表")
    @GetMapping("getDepartListInfo/{id}")
    public Result<List<Department>> getDepartListInfo(@PathVariable String id) {
        List<Department> departments = departmentService.getListById(id);
        if(departments!=null)
            return Result.wrapSuccessfulResult(departments);
        else
            return Result.wrapErrorResult("error!");
    }

    @ApiOperation(value="根据医院id和科室id为医院添加科室")
    @PostMapping("addDepartment/{hosId}/{departId}")
    public Result addDepartment(@PathVariable String hosId,
                                @PathVariable String departId){
        Hospital hospital = hospInfoService.getById(hosId);
        List<Department> departments = hospital.getDepartments();
        departments.add(departmentService.getById(departId));
        hospital.setDepartments(departments);
        hospInfoService.updateDepart(hospital);
        return Result.wrapSuccessfulResult("success!");
    }

}
