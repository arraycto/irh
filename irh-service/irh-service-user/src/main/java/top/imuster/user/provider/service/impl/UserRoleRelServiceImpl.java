package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.UserRoleRel;
import top.imuster.user.provider.dao.ManagementRoleRelDao;
import top.imuster.user.provider.service.UserRoleRelService;

import javax.annotation.Resource;

/**
 * UserRoleRelService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("userRoleRelService")
public class UserRoleRelServiceImpl extends BaseServiceImpl<UserRoleRel, Long> implements UserRoleRelService {

    @Resource
    private ManagementRoleRelDao managementRoleRelDao;

    @Override
    public BaseDao<UserRoleRel, Long> getDao() {
        return this.managementRoleRelDao;
    }

    @Override
    public Integer getCountByCondition(UserRoleRel userRoleRel) {
        return managementRoleRelDao.selectEntryListCount(userRoleRel);
    }
}