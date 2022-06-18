package com.kxb.springcache.web;

import com.kxb.springcache.model.Student;
import com.kxb.springcache.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaofu
 * @Description:
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    TestService testService;

    @GetMapping(value = "/selectOrder")
    @ResponseBody
    public String selectOrder(Long id) {

        return testService.selectOrder(id);
    }

    @GetMapping(value = "/student")
    @ResponseBody
    public Student student() {
        Student student = new Student("class1", "name1", 1);
        return testService.select(student);
    }
}
