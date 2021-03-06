package top.imuster.goods.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.goods.api.dto.GoodsForwardDto;
import top.imuster.goods.api.dto.UserGoodsCenterDto;
import top.imuster.goods.api.pojo.ProductInfo;

import java.util.List;

/**
 * ProductInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface ProductInfoService extends BaseService<ProductInfo, Long> {

    /**
     * @Description: 按条件更新指定商品的类别
     * @Author: hmr
     * @Date: 2019/12/22 15:26
     * @param productInfo
     * @reture: void
     **/
    Integer updateProductCategoryByCondition(ProductInfo productInfo);


    /**
     * @Author hmr
     * @Description 根据商品id查询卖家邮箱
     * @Date: 2020/1/21 10:27
     * @param id
     * @reture: java.lang.Long
     **/
    Long getConsumerIdById(Long id);

    /**
     * @Author hmr
     * @Description 根据商品留言id获得商品信息
     * @Date: 2020/1/22 11:36
     * @param id
     * @reture: top.imuster.goods.api.pojo.ProductInfo
     **/
    ProductInfo getProductInfoByMessageId(Long id);

    /**
     * @Author hmr
     * @Description 根据商品id获得商品的简略信息
     * @Date: 2020/2/7 16:49
     * @param id
     * @reture: java.lang.String
     **/
    ProductInfo getProductBriefInfoById(Long id);

    /**
     * @Author hmr
     * @Description 发布商品
     * @Date: 2020/3/2 11:28
     * @param productInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    Message<String> releaseProduct(ProductInfo productInfo);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/4/12 19:44
     * @param userId
     * @param pageSize
     * @param currentPage
     * @param type 1-查看自己的    2-买家查看卖家的
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    Message<Page<ProductInfo>> list(Long userId, Integer pageSize, Integer currentPage, int type);

    /**
     * @Author hmr
     * @Description 将redis中的浏览记录更新到DB中
     * @Date: 2020/4/22 9:39
     * @param browserTimesDtos
     * @reture: void
     **/
    void transBrowserTimesFromRedis2DB(List<BrowserTimesDto> browserTimesDtos);

    /**
     * @Author hmr
     * @Description 根据id删除
     * @Date: 2020/5/3 16:25
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> deleteById(Long id, Long userId);

    /**
     * @Author hmr
     * @Description 将redis中得到的收藏记录总数保存到DB中
     * @Date: 2020/5/9 9:24
     * @param list
     * @reture: void
     **/
    void updateProductCollectTotal(List<GoodsForwardDto> list);

    /**
     * @Author hmr
     * @Description 更新product的state
     * @Date: 2020/5/11 9:57
     * @param productId
     * @param version
     * @reture: java.lang.Integer
     **/
    Integer lockProduct(Long productId, Integer version);

    /**
     * @Author hmr
     * @Description 查询发布时间最新的商品
     * @Date: 2020/5/11 20:39
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    Message<Page<ProductInfo>> getProductBriefInfoByPage(Integer currentPage, Integer pageSize);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/5/12 10:49
     * @param productId
     * @param state
     * @reture: boolean
     **/
    boolean updateProductStateById(Long productId, Integer state);

    /**
     * @Author hmr
     * @Description 根据ids获得商品简略信息
     * @Date: 2020/5/14 15:47
     * @param ids
     * @reture: java.util.List<top.imuster.goods.api.pojo.ProductInfo>
     **/
    List<ProductInfo> getProductBriefByIds(List<Long> ids);

    /**
     * @Author hmr
     * @Description 根据用户id获得用户中心的数据
     * @Date: 2020/5/26 16:33
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.goods.api.dto.UserGoodsCenterDto>
     **/
    Message<UserGoodsCenterDto> getUserCenterInfoById(Long id);
}