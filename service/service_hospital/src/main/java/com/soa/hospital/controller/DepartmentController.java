package com.soa.hospital.controller;

import com.soa.hospital.model.Department;
import com.soa.hospital.model.Hospital;
import com.soa.hospital.service.DepartmentService;
import com.soa.hospital.service.HospitalService;
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
@RequestMapping("/hospital/departments")
@CrossOrigin
@Api(value="科室信息",tags = "科室信息",description = "科室信息")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @Autowired
    HospitalService hospInfoService;

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
        if(hospital==null)
            return Result.wrapErrorResult("医院id错误");
        List<Department> departments = hospital.getDepartments();
        Department byId = departmentService.getById(departId);
        if(byId==null)
            return Result.wrapErrorResult("科室id错误");
        for(Department tmp:departments)
        {
            if(tmp.getId().equals(departId))
                return Result.wrapErrorResult("科室已经存在");
        }
        departments.add(byId);
        hospital.setDepartments(departments);
        hospInfoService.updateDepart(hospital);
        return Result.wrapSuccessfulResult("success!");
    }
    
}
