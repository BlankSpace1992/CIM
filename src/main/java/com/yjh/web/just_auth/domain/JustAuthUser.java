package com.yjh.web.just_auth.domain;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;

import java.io.Serializable;

/**
 * 第三方权限实体
 *
 * @author yujunhong
 * @date 2021/05/19 10:15:00
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "第三方权限实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_ja_user")
public class JustAuthUser extends AuthUser implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户第三方系统的唯一id
     */
    @ApiModelProperty(value = "用户第三方系统的唯一id")
    @TableId(value = "uuid", type = IdType.INPUT)
    private String uuid;

    /**
     * 用户授权的token信息
     */
    @TableField(exist = false)
    private AuthToken token;

    /**
     * 第三方平台返回的原始用户信息
     */
    @TableField(exist = false)
    private JSONObject rawUserInfo;

    /**
     * 自定义构造函数
     *
     * @param authUser 权限用户的实体
     * @author yujunhong
     * @date 2021/5/19 10:17
     */
    public JustAuthUser(AuthUser authUser) {
        super(authUser.getUuid(), authUser.getUsername(), authUser.getNickname(), authUser.getAvatar(),
                authUser.getBlog(), authUser.getCompany(), authUser.getLocation(), authUser.getEmail(),
                authUser.getRemark(), authUser.getGender(), authUser.getSource(), authUser.getToken(),
                authUser.getRawUserInfo());
        this.uuid = authUser.getUuid();
        this.token = authUser.getToken();
        this.rawUserInfo = authUser.getRawUserInfo();
    }
}
