package com.mrl.pastry.portal.model.params;

import com.mrl.pastry.common.support.ParamConverter;
import com.mrl.pastry.portal.model.entity.Blog;
import com.mrl.pastry.portal.model.enums.BlogType;
import com.mrl.pastry.portal.model.enums.EditorType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Blog params
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/10
 */
@Data
public class BlogPostParam implements ParamConverter<Blog> {

    private BlogType type = BlogType.BLOG;

    private EditorType editorType = EditorType.RICHTEXT;

    /**
     * 这里做个分组校验，blog/article
     */
    private String title;

    private String summary;

    @NotBlank(message = "内容不能为空")
    @Size(max = 500, message = "内容长度不能超过 {max}")
    private String content;

    private List<String> attachmentList;
}
