package com.yjh.web.blog.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 首页用户登录
 *
 * @author yujunhong
 * @date 2021/04/28 04:50:50
 */
@ApiModel(value = "首页用户登录")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_login_user")
public class LoginUser {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @Excel(name = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @Excel(name = "头像")
    private String avatar;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Excel(name = "创建时间")
    private Date createTime;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @Excel(name = "邮箱")
    private String email;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @Excel(name = "昵称")
    private String nickname;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @Excel(name = "密码")
    private String password;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @Excel(name = "更新时间")
    private Date updateTime;
    /**
     * 账户名称
     */
    @ApiModelProperty(value = "账户名称")
    @Excel(name = "账户名称")
    private String username;
    /**
     * 删除标志 0未删除 1删除
     */
    @ApiModelProperty(value = "删除标志 0未删除 1删除")
    @Excel(name = "删除标志 0未删除 1删除")
    private String delFlag;
}
