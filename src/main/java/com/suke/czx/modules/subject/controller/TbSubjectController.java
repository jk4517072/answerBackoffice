package com.suke.czx.modules.subject.controller;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
        IPage<TbSubject> sysConfigList = tbSubjectService.page(mpPageConvert.<TbSubject>pageParamConvert(params),queryWrapper);
        return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @PreAuthorize("hasRole('subject:tbsubject:info')")
    public R info(@PathVariable("id") Integer id){
        return R.ok().put("tbSubject", tbSubjectService.getById(id));
    }


    /**
     * 新增题库表
     */
    @SysLog("新增题库表数据")
    @RequestMapping("/save")
    @PreAuthorize("hasRole('subject:tbsubject:save')")
    public R save(@RequestBody TbSubject tbSubject){
        tbSubjectService.save(tbSubject);
        return R.ok();
    }


    /**
     * 修改
     */
    @SysLog("修改题库表数据")
    @RequestMapping("/update")
    @PreAuthorize("hasRole('subject:tbsubject:update')")
    public R update(@RequestBody TbSubject tbSubject){
		tbSubjectService.updateById(tbSubject);
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
	
}
