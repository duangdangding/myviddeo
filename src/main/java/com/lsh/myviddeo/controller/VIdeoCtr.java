package com.lsh.myviddeo.controller;

import cn.hutool.core.util.StrUtil;
import com.lsh.myviddeo.dtos.ResultDto;
import com.lsh.myviddeo.dtos.Video;
import com.lsh.myviddeo.utils.VideoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lushao
 * @Description $
 * @Date 13:38
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("video")
public class VIdeoCtr extends BaseCtr {

    @RequestMapping("search")
    public Map<String,Object> search(String keyword,String seachZd) {
        List<Video> videos = new ArrayList<>();
        if (StrUtil.isNotBlank(keyword)) {
            if (StrUtil.isNotBlank(seachZd)) {
                Integer zqType = Integer.valueOf(seachZd);
                switch (seachZd) {
                    case "1":
                        VideoUtil.yingHuaSearch(keyword,videos,zqType);
                        break;
                    case "2":
                        VideoUtil.feiJiSuSearch(keyword,videos,zqType);
                        break;
                    default:
                        break;
                }
            } else {
                VideoUtil.yingHuaSearch(keyword,videos,1);
                VideoUtil.feiJiSuSearch(keyword,videos,2);
            }
        }
        return convertLayuiPageList(videos);
    }

    @RequestMapping("videoList")
    public ModelAndView videoList(String src,Integer zdType) {
        Map<String,Object> param = new HashMap<>();
        if (zdType == 1) {
            VideoUtil.getYingHuaVideoList(src,param);
        } else if (zdType == 2) {
            VideoUtil.getFeiJiSuVideoList(src,param);
        }
        return getView("videoList",param);
    }

    @RequestMapping("playVideo")
    public ResultDto playVideo(String src,Integer zdType) {
        String playUrl = "";
        if (zdType == 1) {
            playUrl = "https://tup.yinghuacd.com/?vid=" + VideoUtil.yinghuaPlay(src);
        } else if (zdType == 2) {
            playUrl = VideoUtil.feiJiSuPlay(src);
        }
        return success(playUrl);
    }
}
