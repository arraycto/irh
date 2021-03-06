package top.imuster.life.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleCollectionRel;

/**
 * ArticleCollectionService接口
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
public interface ArticleCollectionService extends BaseService<ArticleCollectionRel, Long> {

    /**
     * @Author hmr
     * @Description 收藏文章
     * @Date: 2020/2/9 10:59
     * @param userId
     * @param id
     * @reture: void
     **/
    Message<String> collect(Long userId, Long id);

    /**
     * @Author hmr
     * @Description 取消收藏
     * @Date: 2020/2/9 11:04
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> unCollect(Long id);

    /**
     * @Author hmr
     * @Description 从reids中获得收藏信息,并更新到每个文章表中
     * @Date: 2020/2/9 11:20
     * @param
     * @reture: void
     **/
    void transCollectCountFromRedis2Db();

    /**
     * @Author hmr
     * @Description 用户查看自己的收藏列表
     * @Date: 2020/2/11 15:37
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<ArticleInfo>>
     **/
    Message<Page<ArticleCollectionRel>> collectList(Page<ArticleCollectionRel> page, Long userId);

    /**
     * @Author hmr
     * @Description 根据用户id统计该用户收藏的文章总数
     * @Date: 2020/2/15 15:37
     * @param userId
     * @reture: java.lang.Long
     **/
    Long getCollectTotalByUserId(Long userId);

    /**
     * @Author hmr
     * @Description 查看当前用户是否收藏了文章
     * @Date: 2020/3/14 10:52
     * @param id
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.Integer>
     **/
    Message<Integer> getCollectStateByTargetId(Long id, Long userId);
}