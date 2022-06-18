package com.kxb.springcache.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * description:
 *
 * @author: wangwenying10
 * @date: 2022/6/17 19:27
 * @version: 1.0
 */
@Data
@AllArgsConstructor
public class Student {
    private String className;
    private String name;
    private Integer age;
}
