package top.imuster.order.provider.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import top.imuster.common.core.interceptor.FeignClientInterceptor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration
@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)//激活方法上的PreAuthorize注解
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ResourceServerConfig.class);

    //公钥
    private static final String PUBLIC_KEY = "publicKey.txt";

    //定义JwtAccessTokenConverter，使用jwt令牌
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPubKey());
        return converter;
    }
    /**
     * 获取非对称加密公钥 Key
     * @return 公钥 Key
     */
    private String getPubKey() {
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStream inputStream = resource.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            return br.lines().collect(Collectors.joining("\n"));
        } catch (Exception ioe) {
            log.error("---------------------> load publicKey.txt error");
            ioe.printStackTrace();
            //return "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgaVB+OnTTnhG8/plZ2dihsA6KXnL3BuDTByPlIbCw6dz7aB2PU+/YsdQKig8HWQFq/cNpUkWBU0+d2VQwTQp/9uqdp9nK3VMSzzHkcZkFTpOK51tzFqmvP6CH2FWkVh/bJo+vfkm32XFY9L6C+NYKGJdC7FpcBIs3132d+lbRbOp4j/6keq8BaqNWwbOU+2I/UeAGA7GlHp1FSe/0e4OT/t62jwqVLXQhkTSYhcoSaal+zAr3kVveQnLXqhAeQe1n0WnhSodQpLeKrU49mqt0ciz6oXxTsZglsh/RbCv76/5tpAAxSu+N92G7P+pvlxuLo+wku6Q7IsFhR0IIS12KwIDAQAB-----END PUBLIC KEY-----";
        }
        return null;
    }

    //定义JwtTokenStore，使用jwt令牌
    @Bean
    public TokenStore jwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter);
        return jwtTokenStore;
    }

    @Bean
    FeignClientInterceptor feignClientInterceptor(){
        return new FeignClientInterceptor();
    }


    @Bean
    UrlAccessDecisionManager urlAccessDecisionManager() {
        return new UrlAccessDecisionManager();
    }

    @Bean
    UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() {
        return new UrlFilterInvocationSecurityMetadataSource();
    }

    @Autowired
    CustomizedAuthenticationEntryPoint customizedAuthenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.authenticationEntryPoint(customizedAuthenticationEntryPoint);
    }

    //Http安全配置，对每个到达系统的http请求链接进行校验
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
                        o.setAccessDecisionManager(urlAccessDecisionManager());
                        return o;
                    }
                });

        //所有请求必须认证通过
        http.authorizeRequests()
                //下边的路径放行
                .antMatchers("/goods/feign/**","/goods/category/tree", "/demand/reply/child",
                        "/swagger-resources","/swagger-resources/configuration/security",
                        "/swagger-ui.html","/webjars/**","/course/coursepic/list/**", "classpath:/resources/").permitAll()
                .anyRequest().authenticated();
    }
}