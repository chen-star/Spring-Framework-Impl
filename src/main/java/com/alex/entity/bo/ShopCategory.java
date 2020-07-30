package com.alex.entity.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 15:34
 */
@Data
public class ShopCategory {
    private Long shopCategoryId;
    private String shopCategoryName;
    private String shopCategoryDesc;
    private String shopCategoryImg;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private ShopCategory parent;
}
