package com.wykj.springboot.utils.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2019/10/12 17:56
 */
@Getter
@AllArgsConstructor
public enum TraceHttpHeader {
    /**
     * http请求发送 traceId
     */
    HTTP_HEADER_TRACE_ID("http_header_trace_id", "http请求发送traceId"),

    /**
     * spanId
     */
    HTTP_HEADER_SPAN_ID("http_header_span_id", "http请求发送spanId");
    /**
     * code编码
     */
    String code;
    /**
     * 中文信息描述
     */
    String message;

}
