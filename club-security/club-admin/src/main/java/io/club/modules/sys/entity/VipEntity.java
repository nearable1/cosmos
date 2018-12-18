package io.club.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.1.0 2018-01-27
 */
@Setter
@Getter
@TableName("vip")
public class VipEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空")
	private String name;
	/**
	 * 电话号码
	 */
	@NotBlank(message="电话号码不能为空")
	private String phone;
	/**
	 * 身份证
	 */
	@NotBlank(message="身份证号码不能为空")
	private String cardNo;
	/**
	 * 余额
	 */
	@NotNull(message="余额不能为空")
	private Double balance;
	/**
	 * 删除标记  -1：已删除  0：正常
	 */
	@TableLogic
	private Integer delFlag;

}
