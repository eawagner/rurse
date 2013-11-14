package com.careeropts.rurse.dao.recommend;

import com.careeropts.rurse.dao.object.JobEntity;
import com.careeropts.rurse.dao.object.UserEntity;

import java.util.List;

public interface IJobRecommendEngine {

    public List<UserEntity> recommendUsers(JobEntity job, int pageNum, int perPage);

}
