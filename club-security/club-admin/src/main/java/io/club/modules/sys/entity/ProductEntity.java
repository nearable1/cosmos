package io.club.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统配置信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:43:36
 */
@Setter
@Getter
@TableName("product")
public class ProductEntity {
    @TableId
    private Long id;
    @NotBlank(message="名称不能为空")
    private String name;
    @NotNull(message="价格不能为空")
    private Double price;
    private String remark;

}

