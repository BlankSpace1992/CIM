package com.yjh.common.constants;

/**
 * 返回状态码
 *
 * @author waylon
 */
public interface HttpStatus
{
    /**
     * 操作成功
     */
    public static final int SUCCESS = 200;

    /**
     * 对象创建成功
     */
    public static final int CREATED = 201;

    /**
     * 请求已经被接受
     */
    public static final int ACCEPTED = 202;

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    public static final int NO_CONTENT = 204;

    /**
     * 资源已被移除
     */
    public static final int MOVED_PERM = 301;

    /**
     * 重定向
     */
    public static final int SEE_OTHER = 303;

    /**
     * 资源没有被修改
     */
    public static final int NOT_MODIFIED = 304;

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 访问受限，授权过期
     */
    public static final int FORBIDDEN = 403;

    /**
     * 资源，服务未找到
     */
    public static final int NOT_FOUND = 404;

    /**
     * 不允许的http方法
     */
    public static final int BAD_METHOD = 405;

    /**
     * 资源冲突，或者资源被锁
     */
    public static final int CONFLICT = 409;


    /**
     * 不支持的数据，媒体类型
     */
    public static final int UNSUPPORTED_TYPE = 415;

    /**
     * 重复提交请求
     */
    public static final int REPEATED_SUBMIT_REQUEST = 444;

    /**
     * 系统内部错误
     */
    public static final int ERROR = 500;

    /**
     * 接口未实现
     */
    public static final int NOT_IMPLEMENTED = 501;

    /**
     * 业务内部错误  5001 - 5099
     */
    public static final int BUSINESS_ERROR = 5001;

    /**
     * 业务内部错误  判断导入财务数据是否已经审核
     */
    public static final int EXPORT_DATA_CHECK_ERROR = 5102;

    /**
     * 业务内部错误  判断导入财务数据是否已经存在
     */
    public static final int EXPORT_DATA_START_ERROR = 5103;

    /**
     * 业务内部错误  判断导入财务数据是否为空
     */
    public static final int EXPORT_DATA_NOT_DATA_ERROR = 5104;




}
