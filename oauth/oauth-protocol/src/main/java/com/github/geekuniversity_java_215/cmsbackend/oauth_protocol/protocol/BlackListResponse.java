package com.github.geekuniversity_java_215.cmsbackend.oauth_protocol.protocol;

import java.util.Map;
import java.util.TreeMap;

public class BlackListResponse {

    private Map<Long,Long> list = new TreeMap<>();

    public Map<Long, Long> getList() {
        return list;
    }

    public void setList(Map<Long, Long> list) {
        this.list = list;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Long, Long> entry : list.entrySet()) {
            sb.append("{id=").append(entry.getKey()).append(", tokenId=").append(entry.getValue()).append("}, ");

        }
        sb.delete(sb.length() - 2, sb.length());

        return "[" + sb.toString() + "]";
    }
}
