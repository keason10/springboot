package com.wykj.springboot.utils.trace;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * @date 2019/10/12 17:56
 */
@Setter
@Getter
public class TraceContext implements Serializable {
    private static final long serialVersionUID = 2284957271994425682L;

    private String traceId;

    private String spanId;

    /**
     * 扩展信息,这里的所有信息都可以输出至MDC
     */
    private Map<String, String> extension = new HashMap<>();
}
