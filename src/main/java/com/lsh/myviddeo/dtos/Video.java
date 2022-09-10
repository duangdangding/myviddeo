package com.lsh.myviddeo.dtos;

import lombok.Data;

/**
 * @Author lushao
 * @Description $
 * @Date 13:43
 * @Version 1.0
 */
@Data
public class Video {

    private String name;
    private String content;
    private String imgSrc;
    private String descUrl;
    private String latest;
    private Integer zqType;

}
