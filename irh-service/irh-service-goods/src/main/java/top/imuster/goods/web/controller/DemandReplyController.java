package top.imuster.goods.web.controller;

import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.goods.api.pojo.ProductDemandReplyInfo;
import top.imuster.goods.service.ProductDemandReplyInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: DemandReplyController
 * @Description: DemandReplyController
 * @author: hmr
 * @date: 2020/5/3 15:23
 */
@RestController
@RequestMapping("/demand/reply")
public class DemandReplyController extends BaseController {

    @Resource
    ProductDemandReplyInfoService productDemandReplyInfoService;


    @GetMapping("/firstClass/{pageSize}/{currentPage}/{demandId}")
    public Message<Page<ProductDemandReplyInfo>> getFirstClassList(@PathVariable("pageSize") Integer pageSize,
                                                                   @PathVariable("currentPage") Integer currentPage,
                                                                   @PathVariable("demandId") Long demandId){
        return productDemandReplyInfoService.getFirstClassReplyListByPage(pageSize, currentPage, demandId);

    }

    @GetMapping("/child/{pageSize}/{currentPage}/{replyId}")
    public Message<Page<ProductDemandReplyInfo>> getListByReplyId(@PathVariable("pageSize") Integer pageSize,
                                                                  @PathVariable("currentPage") Integer currentPage,
                                                                  @PathVariable("replyId") Long replyId){
        return productDemandReplyInfoService.getReplyChildById(pageSize, currentPage, replyId);
    }

    @PostMapping
    @NeedLogin
    public Message<String> writeReply(@RequestBody ProductDemandReplyInfo replyInfo){
        Long userId = getCurrentUserIdFromCookie();
        replyInfo.setUserId(userId);
        productDemandReplyInfoService.insertEntry(replyInfo);
        return Message.createBySuccess();
    }

}