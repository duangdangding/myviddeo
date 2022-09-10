package com.lsh.myviddeo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PageCtr {

    @RequestMapping("/")
    public ModelAndView toIndex() {
        return new ModelAndView("index");
    }
//    @RequestMapping("/videoList")
//    public ModelAndView toVideoList() {
//        return new ModelAndView("videoList");
//    }

    @RequestMapping("/404")
    public ModelAndView toIndex1() {
        return new ModelAndView("error/404");
    }

}
