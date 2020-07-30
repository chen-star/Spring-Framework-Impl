package com.alex.entity.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 15:34
 */
@Data
public class HeadLine {
    private Long lineId;
    private String lineName;
    private String lineLink;
    private String lineImg;
    private Integer priority;
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;
}
