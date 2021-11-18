package com.soa.hospital.controller;

import com.soa.hospital.model.Department;
import com.soa.hospital.service.DepartmentService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value="根据id获取科室信息")
    @GetMapping("getDepartInfo/{id}")
    public Result<Department> getDepartInfo(@PathVariable String id) {
        Department department = departmentService.getById(id);
        if(department!=null)
            return Result.wrapSuccessfulResult(department);
        else
            return Result.wrapErrorResult("error!");
    }

}
