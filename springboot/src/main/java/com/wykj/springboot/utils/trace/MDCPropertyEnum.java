package com.wykj.springboot.utils.trace;

/**
 * logback traceId
 * @date 2019/10/12 17:56
 */
public enum MDCPropertyEnum {

    TRACE("TRACE", "X-B3-TraceId"),

    PARENT_SPAN("PARENT_SPAN", "X-B3-SpanId");

    private String id;

    private String name;

    MDCPropertyEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
