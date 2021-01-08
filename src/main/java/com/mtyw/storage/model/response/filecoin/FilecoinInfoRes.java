package com.mtyw.storage.model.response.filecoin;


import java.io.Serializable;
import java.util.Date;


public class FilecoinInfoRes implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * id信息
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 类型0ipfs，1filecoin
     */
    private Integer type;

    /**
     * 文件路径
     */
    private String path;
    /**
     * 用户id
     */
    private Integer uid;

    /**
     * hash
     */
    private String cid;

    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 0正在上传，1可下载，2上传失败，3上传超时,4 做单进行中，5待检索,6检索中,7检索失败
     */
    private Integer status;

    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * 天数信息
     */
    private Integer days;


}
