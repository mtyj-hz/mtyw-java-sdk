package com.mtyw.storage.model.response.filecoin;

public class NodeRes {
    /**
     * 节点id
     */
    private String nodeId;
    /**
     * 节点地址
     */
    private String nodeAddr;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeAddr() {
        return nodeAddr;
    }

    public void setNodeAddr(String nodeAddr) {
        this.nodeAddr = nodeAddr;
    }
}
