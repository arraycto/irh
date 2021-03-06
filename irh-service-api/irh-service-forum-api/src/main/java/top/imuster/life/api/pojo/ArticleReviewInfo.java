package top.imuster.life.api.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@ApiModel("文章评论实体类")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ArticleReviewInfo extends BaseDomain {

	private static final long serialVersionUID = 8957837652L;

	public ArticleReviewInfo() {
		//默认无参构造方法
	}
	// 文章评论表主键
	@ApiModelProperty("文章评论表主键")
	private Long id;

	// 文章编号id
	@ApiModelProperty("文章编号id")
	@NotNull(groups = ValidateGroup.addGroup.class, message = "文章id不能为空")
	private Long articleId;

	// 用户id
	@ApiModelProperty("用户id")
	private Long userId;

	// 回复消息的id，0表示是新的留言的时候
	@ApiModelProperty("回复消息的id，0表示是新的留言的时候")
	@NotNull(groups = ValidateGroup.addGroup.class, message = "回复消息的id不能为空")
	private Long parentId;

	// 内容, max length: 2048
	@ApiModelProperty("内容")
	@NotNull(groups = ValidateGroup.addGroup.class, message = "内容不能为空")
	private String content;

	//一级留言下的回复总数
	@ApiModelProperty("一级留言下的回复总数")
	private Integer childTotalCount;

	//一级留言的id,也就是顶级parent_id,当parent_id为0时该值也为0,当parent_id不为0时,则该值为回复树的顶层回复id
	@ApiModelProperty("一级留言的id,也就是顶级parent_id,当parent_id为-1时该值也为-1,当parent_id不为-1时,则该值为回复树的顶层回复id")
	@NotNull(groups = ValidateGroup.addGroup.class, message = "一级留言的id不能为空")
	private Long firstClassId;

	@ApiModelProperty("点赞总数")
	private Long upTotal;

	//父id的作者id
	@ApiModelProperty("父id的作者id")
	private Long parentWriterId;

	//当前用户是否点赞
	@ApiModelProperty("当前用户是否点赞   1-为点赞  2-点赞")
	private Integer upState;

	//写文章的人的id
	private String artcileWriterName;

	// 1-无效 2-有效
//	private Short state;

	public String getWriterName() {
		return artcileWriterName;
	}

	public void setWriterName(String artcileWriterName) {
		this.artcileWriterName = artcileWriterName;
	}

	public Integer getUpState() {
		return upState;
	}

	public void setUpState(Integer upState) {
		this.upState = upState;
	}

	public Long getParentWriterId() {
		return parentWriterId;
	}

	public void setParentWriterId(Long parentWriterId) {
		this.parentWriterId = parentWriterId;
	}

	public Long getUpTotal() {
		return upTotal;
	}

	public void setUpTotal(Long upTotal) {
		this.upTotal = upTotal;
	}

	public Long getFirstClassId() {
		return firstClassId;
	}

	public void setFirstClassId(Long firstClassId) {
		this.firstClassId = firstClassId;
	}

	public Integer getChildTotalCount() {
		return childTotalCount;
	}

	public void setChildTotalCount(Integer childTotalCount) {
		this.childTotalCount = childTotalCount;
	}

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
	
	public Long getParentId() {
		return this.parentId;
	}
    public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
}