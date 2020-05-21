package com.suke.czx.modules.subject.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.modules.subject.entity.TbSubject;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.modules.subject.entity.TbSubjectVo;

/**
 * 题库表
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2020-05-16 14:33:41
 */
public interface TbSubjectService extends IService<TbSubject> {

    IPage<TbSubject> getCustomMadePage(IPage<TbSubject> page, Wrapper<TbSubject> queryWrapper);

    TbSubjectVo getHandleTbSubject(Integer id);
}
