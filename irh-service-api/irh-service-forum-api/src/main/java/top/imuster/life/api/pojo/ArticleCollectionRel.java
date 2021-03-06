package top.imuster.life.api.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
@ApiModel("文章收藏表")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ArticleCollectionRel extends BaseDomain {

	private static final long serialVersionUID = 5594240318L;

	public ArticleCollectionRel() {
		//默认无参构造方法
	}
	// 用户收藏表主键
	@ApiModelProperty("用户收藏表主键")
	private Long id;

	// 文章id
	@ApiModelProperty("文章id")
	private Long articleId;

	// 用户id
	@ApiModelProperty("用户id")
	private Long userId;

	// 1-无效  2-有效
//	private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getArticleId() {
		return this.articleId;
	}
    public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
    public void setUserId(Long userId) {
		this.userId = userId;
	}
}