package com.suke.czx.modules.subject.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


/**
 * 题库表
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2020-05-16 14:33:41
 */
@Data
public class TbSubjectVo  implements Serializable {
	private static final long serialVersionUID = 1L;
		private Integer id;
		//类型:1选择题 2.判断题
		private String type;
		//简介
		private String title;
		//题目
		private String subject;
		//选择A/选择√
		private String choice1;
		//选择A/选择√
		private String choice2;
		//选择A/选择√
		private String choice3;
		//选择A/选择√
		private String choice4;
		//回答记录：1对，2错
		private Integer isanswer;
		//正确选择
		private String rightChoice;
		//创建时间
		private Date createTime;
		//修改时间
		private Date updateTime;
	
}
