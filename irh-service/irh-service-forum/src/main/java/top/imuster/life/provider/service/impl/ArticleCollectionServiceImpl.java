package top.imuster.life.provider.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleCollectionRel;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.provider.dao.ArticleCollectionDao;
import top.imuster.life.provider.service.ArticleCollectionService;
import top.imuster.life.provider.service.ArticleInfoService;
import top.imuster.life.provider.service.RedisArticleAttitudeService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ArticleCollectionService 实现类
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
@Service("articleCollectionService")
public class ArticleCollectionServiceImpl extends BaseServiceImpl<ArticleCollectionRel, Long> implements ArticleCollectionService {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ArticleCollectionDao articleCollectionDao;

    @Resource
    private ArticleInfoService articleInfoService;

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    RedisArticleAttitudeService redisArticleAttitudeService;

    @Override
    public BaseDao<ArticleCollectionRel, Long> getDao() {
        return this.articleCollectionDao;
    }

    @Override
    public Message<String> collect(Long userId, Long id) {
        ArticleCollectionRel condition = new ArticleCollectionRel();
        condition.setUserId(userId);
        condition.setArticleId(id);
        articleCollectionDao.insertEntry(condition);
        redisTemplate.opsForHash().increment(GlobalConstant.IRH_ARTICLE_COLLECT_MAP, String.valueOf(id), 1);
        return Message.createBySuccess();
    }

    @Override
    public Message<String> unCollect(Long id) {
        ArticleCollectionRel condition = new ArticleCollectionRel();
        condition.setId(id);
        condition.setState(1);
        int i = articleCollectionDao.updateByKey(condition);
        redisTemplate.opsForHash().increment(GlobalConstant.IRH_ARTICLE_COLLECT_MAP, String.valueOf(id), -1);
        return Message.createBySuccess();
    }

    @Override
    public void transCollectCountFromRedis2Db() {
        List<Map.Entry<Long, Long>> allCollect = redisArticleAttitudeService.getAllCollectCountFromRedis();
        if (allCollect == null || allCollect.isEmpty()) return;

        Long[] ids = new Long[allCollect.size()];
        HashMap<Long, Long> map = new HashMap<>();
        int index = 0;
        for (Map.Entry<Long, Long> entry : allCollect) {
            Long key = entry.getKey();
            ids[index] = key;
            map.put(key, entry.getValue());
        }
        List<ArticleInfo> upTotalByIds = articleInfoService.getUpTotalByIds(ids);
        upTotalByIds.stream().forEach(articleInfo -> {
            Long id = articleInfo.getId();
            Long increTotal = map.get(id);
            articleInfo.setUpTotal(articleInfo.getUpTotal()+increTotal);
            articleInfoService.updateByKey(articleInfo);
        });
    }

    @Override
    public Message<Page<ArticleCollectionRel>> collectList(Page<ArticleCollectionRel> page, Long userId) {
        ArticleCollectionRel searchCondition = page.getSearchCondition();
        if(null == searchCondition){
            searchCondition = new ArticleCollectionRel();
        }
        //默认的排序顺序为按照时间降序排列
        if(StringUtils.isBlank(searchCondition.getOrderField())){
            searchCondition.setOrderField("create_time");
            searchCondition.setOrderFieldType("DESC");
        }
        searchCondition.setUserId(userId);
        List<ArticleCollectionRel> res = articleCollectionDao.selectCollectByCondition(searchCondition);
        page.setData(res);
        return Message.createBySuccess(page);
    }

    @Override
    public Long getCollectTotalByUserId(Long userId) {
        return articleCollectionDao.selectTotalByUserId(userId);
    }

    @Override
    public Message<Integer> getCollectStateByTargetId(Long id, Long userId) {
        if(userId == null) return Message.createBySuccess(1);
        ArticleCollectionRel condition = new ArticleCollectionRel();
        condition.setArticleId(id);
        condition.setUserId(userId);
        Integer count = articleCollectionDao.selectEntryListCount(condition);
        return count == 0 ? Message.createBySuccess(1):Message.createBySuccess(2);
    }
}