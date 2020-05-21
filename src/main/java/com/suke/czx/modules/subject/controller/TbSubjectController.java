package com.suke.czx.modules.subject.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.common.utils.CommonUtils;
import com.suke.czx.common.utils.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.suke.czx.modules.subject.entity.TbSubject;
import com.suke.czx.modules.subject.service.TbSubjectService;
import com.suke.czx.common.utils.R;
import lombok.AllArgsConstructor;
import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.base.AbstractController;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.Arrays;



/**
 * 题库表
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2020-05-16 14:33:41
 */
@RestController
@AllArgsConstructor
@RequestMapping("/subject/tbsubject")
public class TbSubjectController  extends AbstractController {
    private final  TbSubjectService tbSubjectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @PreAuthorize("hasRole('subject:tbsubject:list')")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        QueryWrapper<TbSubject> queryWrapper = new QueryWrapper<>();
        IPage<TbSubject> sysConfigList = tbSubjectService.getCustomMadePage(mpPageConvert.<TbSubject>pageParamConvert(params),queryWrapper);
        return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));
    }

    @RequestMapping("/testList")
    @PreAuthorize("hasRole('xcx:user:list')")
    public R getSubject(@RequestParam Map<String, Object> params){
        //查询列表数据
        QueryWrapper<TbSubject> queryWrapper = new QueryWrapper<>();
        IPage<TbSubject> sysConfigList = tbSubjectService.getCustomMadePage(mpPageConvert.<TbSubject>pageParamConvert(params),queryWrapper);
        return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @PreAuthorize("hasRole('subject:tbsubject:info')")
    public R info(@PathVariable("id") Integer id){
        return R.ok().put("tbSubject", tbSubjectService.getHandleTbSubject(id));
    }


    /**
     * 新增题库表
     */
    @SysLog("新增题库表数据")
    @RequestMapping("/save")
    @PreAuthorize("hasRole('subject:tbsubject:save')")
    public R save(@RequestBody Map<String, Object> params){
        String type= String.valueOf(params.get("type"));
        String subject= String.valueOf(params.get("subject"));
        String rightChoice=String.valueOf(params.get("rightChoice"));
        String choice1= String.valueOf(params.get("choice1"));
        String choice2= String.valueOf(params.get("choice2"));
        String choice3= String.valueOf(params.get("choice3"));
        String choice4= String.valueOf(params.get("choice4"));
        R response = checkResquestionParams(type, subject, rightChoice, choice1, choice2, choice3, choice4);
        if(response != null){
            return response;
        }
        tbSubjectService.save(handleParams(null,type, subject, rightChoice, choice1, choice2, choice3, choice4));
        return R.ok();
    }


    /**
     * 修改
     */
    @SysLog("修改题库表数据")
    @RequestMapping("/update")
    @PreAuthorize("hasRole('subject:tbsubject:update')")
    public R update(@RequestBody Map<String, Object> params){
        String id = String.valueOf(params.get("id"));
        String type= String.valueOf(params.get("type"));
        String subject= String.valueOf(params.get("subject"));
        String rightChoice=String.valueOf(params.get("rightChoice"));
        String choice1= String.valueOf(params.get("choice1"));
        String choice2= String.valueOf(params.get("choice2"));
        String choice3= String.valueOf(params.get("choice3"));
        String choice4= String.valueOf(params.get("choice4"));
        R response = checkResquestionParams(type, subject, rightChoice, choice1, choice2, choice3, choice4);
        if(response != null){
            return response;
        }
		tbSubjectService.updateById(handleParams(id,type, subject, rightChoice, choice1, choice2, choice3, choice4));
        return R.ok();
    }


    /**
     * 删除
     */
    @SysLog("删除题库表数据")
    @RequestMapping("/delete")
    @PreAuthorize("hasRole('subject:tbsubject:delete')")
    public R delete(@RequestBody Integer[] ids){
		tbSubjectService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    public R checkResquestionParams(String type,String subject,String rightChoice,String choice1,
                                    String choice2, String choice3,String choice4){
        if(CommonUtils.isNull(type) || "0".equals(type)){
            return R.error(Constant.PARAM_ERROR,"type 不能为空");
        }
        if(CommonUtils.isNull(subject)){
            return R.error(Constant.PARAM_ERROR,"subject 不能为空");
        }
        if(CommonUtils.isNull(rightChoice) || "0".equals(rightChoice)){
            return R.error(Constant.PARAM_ERROR,"rightChoice 不能为空");
        }
        if("选择题".equals(type)){
            if(CommonUtils.isNull(choice1)){
                return R.error(Constant.PARAM_ERROR,"选择A,不能为空");
            }
            if(CommonUtils.isNull(choice2)){
                return R.error(Constant.PARAM_ERROR,"选择B,不能为空");
            }
            if(CommonUtils.isNull(choice3)){
                return R.error(Constant.PARAM_ERROR,"选择C,不能为空");
            }
        }
        if("判断题".equals(type)){
            if(!"A".equals(rightChoice) && !"B".equals(rightChoice)){
                return R.error(Constant.PARAM_ERROR,"判断题的正确选择为:对和错，请修改");
            }
        }
        return null;
    }


    public TbSubject handleParams(String id,String type,String subject,String rightChoice,String choice1,
                                  String choice2, String choice3,String choice4){

        TbSubject tbSubject=new TbSubject();
        if (!CommonUtils.isNull(id)){
            tbSubject.setId(Integer.valueOf(id));
        }
        tbSubject.setType(type);
        tbSubject.setSubject(subject);
        tbSubject.setTitle(subject.length()>10? subject.substring(0,10):subject);
        tbSubject.setRightChoice(rightChoice);
        tbSubject.setIsanswer(0);
        Date now=new Date();
        tbSubject.setCreateTime(now);
        tbSubject.setUpdateTime(now);
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("label","A");
        jsonObject.put("text",choice1);
        jsonArray.add(jsonObject);
        jsonObject=new JSONObject();
        jsonObject.put("label","B");
        jsonObject.put("text",choice2);
        jsonArray.add(jsonObject);
        jsonObject=new JSONObject();
        jsonObject.put("label","C");
        jsonObject.put("text",choice3);
        jsonArray.add(jsonObject);
        if(!CommonUtils.isNull(choice4)){
            jsonObject=new JSONObject();
            jsonObject.put("label","D");
            jsonObject.put("text",choice4);
            jsonArray.add(jsonObject);
        }
        tbSubject.setOptions(jsonArray.toJSONString());
        return tbSubject;
    }
}
