package com.suke.czx.modules.subject.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.modules.subject.entity.TbSubjectVo;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.writer.BeansMapper;
import org.springframework.stereotype.Service;
import com.suke.czx.modules.subject.mapper.TbSubjectMapper;
import com.suke.czx.modules.subject.entity.TbSubject;
import com.suke.czx.modules.subject.service.TbSubjectService;
import java.util.Iterator;
import java.util.List;


@Service
@Slf4j
public class TbSubjectServiceImpl extends ServiceImpl<TbSubjectMapper, TbSubject> implements TbSubjectService {

    @Override
    public IPage<TbSubject> getCustomMadePage(IPage<TbSubject> page, Wrapper<TbSubject> queryWrapper) {
        QueryWrapper qw;
        if (null == queryWrapper) {
            qw = new QueryWrapper<>();
        } else {
            qw = (QueryWrapper) queryWrapper;
        }
        // 排序
        if (ArrayUtils.isNotEmpty(page.ascs())) {
            qw.orderByAsc(page.ascs());
        }
        if (ArrayUtils.isNotEmpty(page.descs())) {
            qw.orderByDesc(page.descs());
        }

        IPage<TbSubject> sysConfigList=super.page(page,queryWrapper);
        List<TbSubject> list = sysConfigList.getRecords();
        JSONArray jsonArray =null;
        JSONObject jsonObject=null;
        StringBuffer stringBuffer =null;
        for (TbSubject tbSubject:list) {
            if ("判断题".equals(tbSubject.getType())){
                tbSubject.setOptions("<br>A对</br><br>B错</br>");
            }
            stringBuffer =new StringBuffer();
            try {
                jsonArray = JSONArray.parseArray(tbSubject.getOptions());
                Iterator<Object> iterable=jsonArray.iterator();
                while (iterable.hasNext()){
                    jsonObject=(JSONObject)iterable.next();
                    stringBuffer.append("<br>").append(jsonObject.getString("label"))
                            .append(":").append(jsonObject.getString("text")).append("</br>");
                }
                tbSubject.setOptions(stringBuffer.toString());
            } catch (Exception e) {
               log.error("TbSubjectServiceImpl 发生错误。Exception:{}",e);
            }
        }
        return sysConfigList;
    }

    @Override
    public TbSubjectVo getHandleTbSubject(Integer id) {
        TbSubject tbSubject =super.getById(id);
        TbSubjectVo tbSubjectVo=new TbSubjectVo();
        BeanUtil.copyProperties(tbSubject,tbSubjectVo);
        JSONArray jsonArray=JSONArray.parseArray(tbSubject.getOptions());
        Iterator<Object> iterable= jsonArray.iterator();
        String label=null;
        String text = null;
        while (iterable.hasNext()){
            JSONObject jsonObject=(JSONObject) iterable.next();
            label=jsonObject.getString("label");
            text = jsonObject.getString("text");
            if("A".equals(label)){
                tbSubjectVo.setChoice1(text);
            }else if("B".equals(label)){
                tbSubjectVo.setChoice2(text);
            }else if("C".equals(label)){
                tbSubjectVo.setChoice3(text);
            }else if("D".equals(label)){
                tbSubjectVo.setChoice4(text);
            }
        }
        return tbSubjectVo;
    }
}
