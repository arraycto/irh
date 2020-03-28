package top.imuster.auth.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.auth.service.Impl.UserAuthenService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;

import javax.annotation.Resource;

/**
 * @ClassName: UserAuthenticationController
 * @Description: 用户实名认证
 * @author: hmr
 * @date: 2020/3/27 15:05
 */
@RestController
@RequestMapping("/")
public class UserAuthenticationController extends BaseController {

    @Resource
    UserAuthenService userAuthenService;

    @ApiOperation("身份证实名认证,需要上传身份证到本地服务器之后返回一个图片的uri")
    @NeedLogin
    @PostMapping("/identityCard")
    public Message<String> realNameAuthen(@RequestParam("picUri") String picUri, @RequestParam("realName") String inputName){
        Long userId = getCurrentUserIdFromCookie();
        return userAuthenService.realNameAuthentication(userId, picUri, inputName);
    }

    @ApiOperation("一卡通实名认证")
    @NeedLogin
    @PostMapping("/oneCard")
    public Message<String> oneCardSolution(@RequestParam("picUri") String picUri, @RequestParam("realName") String inputName, @RequestParam("inputCardNo") String inputCardNo){
        Long userId = getCurrentUserIdFromCookie();
        return userAuthenService.oneCardSolution(picUri, inputName, userId, inputCardNo);
    }
}