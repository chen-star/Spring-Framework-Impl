package com.alex.entity.dto;

import lombok.Data;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 16:43
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;
}
