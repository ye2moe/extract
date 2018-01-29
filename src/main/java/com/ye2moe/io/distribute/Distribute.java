package com.ye2moe.io.distribute;

import java.util.List;

import com.ye2moe.io.bean.TaskBean;

/**
 * 分配task任务
 * 
 * @author ye2moe
 *
 */
public interface Distribute {
	public List<List<TaskBean>> allot(List<TaskBean> taskBeans, int maxNum);
}
