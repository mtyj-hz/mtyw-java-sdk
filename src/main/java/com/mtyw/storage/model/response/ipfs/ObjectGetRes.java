package com.mtyw.storage.model.response.ipfs;

import java.util.Arrays;

/**
 * @author lt
 * @Date 11:05 2021/1/8
 */
public class ObjectGetRes {

    private String data;

    private ObjectLink[] links;


    static class ObjectLink {
        /**
         * 名称
         */
        private String name;

        /**
         * hash
         */
        private String hash;

        /**
         * 大小
         */
        private Long size;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "ObjectLink{" +
                    "name='" + name + '\'' +
                    ", hash='" + hash + '\'' +
                    ", size=" + size +
                    '}';
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ObjectLink[] getLinks() {
        return links;
    }

    public void setLinks(ObjectLink[] links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "ObjectGetRes{" +
                "data='" + data + '\'' +
                ", links=" + Arrays.toString(links) +
                '}';
    }
}
