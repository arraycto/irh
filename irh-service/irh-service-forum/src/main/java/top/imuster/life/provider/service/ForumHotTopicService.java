package top.imuster.life.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ForumHotTopicInfo;

import java.util.HashSet;
import java.util.List;

/**
 * ForumHotTopicService接口
 * @author 黄明人
 * @since 2020-02-13 21:12:30
 */
public interface ForumHotTopicService extends BaseService<ForumHotTopicInfo, Long> {

    /**
     * @Author hmr
     * @Description 根据从redis中获得的热搜id更新数据库中的score
     * @Date: 2020/2/14 11:15
     * @param ids
     * @reture: void
     **/
    void updateHotTopicFromRedis2DB(List<HashSet<Long>> res);

    /**
     * @Author hmr
     * @Description 累计热度榜
     * @Date: 2020/2/14 12:21
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<ArticleInfo>>
     **/
    Message<List<ForumHotTopicInfo>> totalHotTopicList(Long pageSize, Long currentPage);

    /**
     * @Author hmr
     * @Description 实时热度榜
     * @Date: 2020/2/14 12:24
     * @param topic
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<ArticleInfo>>
     **/
    Message<List<ArticleInfo>> currentHotTopicList(int topic, Integer pageSize, Integer currentPage);
}