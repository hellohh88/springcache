package com.kxb.springcache.service;

import com.kxb.springcache.model.Student;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * description: 测试服务
 *
 * @author: wangwenying10
 * @date: 2022/6/17 16:27
 * @version: 1.0
 */
@Service
public class TestService {

    public static final String CACHE_NAME = "order";
    public static final String CACHE_NAME_STUDENT = "Student";

    @Cacheable(cacheNames = {CACHE_NAME}, key = "#id")
    public String selectOrder(Long id) {
        System.out.println("selectOrder");
        return "success";
    }

    @Cacheable(cacheNames = {CACHE_NAME_STUDENT}, key = "{#student.className, #student.name}")
    public Student select(Student student) {
        System.out.println("class1");
        return new Student("class1", "name1", 1);
    }
}
