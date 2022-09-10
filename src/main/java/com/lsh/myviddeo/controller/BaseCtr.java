package com.lsh.myviddeo.controller;

import cn.hutool.core.util.ObjectUtil;
import com.lsh.myviddeo.dtos.ResultDto;
import com.lsh.myviddeo.dtos.ResultDtoManager;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lushao
 * @Description $
 * @Date 15:21
 * @Version 1.0
 */
public class BaseCtr {

    protected Map<String,Object> convertLayuiPageList(List page) {
        Map<String, Object> json = new HashMap<>();
        json.put("msg", "success");
        json.put("code", "0");
        json.put("data", page);
        json.put("count", page.size());
        return json;
    }

    protected ModelAndView getView(String modelName, Map<String, Object> param) {
        ModelAndView modelAndView = new ModelAndView(modelName);
        if (!ObjectUtil.isEmpty(param)) {
            modelAndView.getModel().putAll(param);
        }
        return modelAndView;
    }
    protected ModelAndView getView(String modelName) {
        return new ModelAndView(modelName);
    }

    protected ResultDto resultDto(int idex) {
        if (idex > 0) {
            return success();
        } else {
            return fail();
        }
    }
    protected ResultDto resultDto(long idex) {
        if (idex > 0) {
            return success();
        } else {
            return fail();
        }
    }
    protected ResultDto resultDto(Long idex) {
        if (idex > 0) {
            return success();
        } else {
            return fail();
        }
    }
    protected ResultDto resultDto(boolean result) {
        if (result) {
            return success();
        } else {
            return fail();
        }
    }

    protected ResultDto success(Object result) {
        return ResultDtoManager.success(result);
    }
    protected ResultDto fail(String msg) {
        return ResultDtoManager.fail(-1,msg);
    }

    protected ResultDto<String> success() {
        return success("成功~");
    }
    protected ResultDto<String> fail() {
        return success("失败~");
    }

    protected ResultDto resultDto(int result,Object success) {
        if (result > 0) {
            return success(success);
        } else {
            return fail();
        }
    }
    protected ResultDto resultDto(int result,Object success,String fail) {
        if (result > 0) {
            return success(success);
        } else {
            if (StringUtils.isEmpty(fail)) {
                return fail();
            } else {
                return fail(fail);
            }
        }
    }
}
