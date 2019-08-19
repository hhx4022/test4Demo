package Dto;

import lombok.Data;

@Data
public class ServerDto {
    private Long id;

    /**
     * 使用人
     */
    private String applyUserName;


    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 服务器状态
     */
    private String serverStatus;

    /**
     * 申请时间
     */
    private Long applyTime;

    /**
     * 释放时间
     */
    private Long releaseTime;

    /**
     * 项目名称
     */
    private String projectName;

}
