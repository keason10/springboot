package com.wykj.springboot.utils.trace;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * @date 2019/10/12 17:56
 */
@Slf4j
public final class TraceContextHolder {

    private static final ThreadLocal<TraceContext> CURRENT_SPAN = new InheritableThreadLocal() {
        @Override
        protected TraceContext initialValue() {
            return new TraceContext();
        }
    };


    public static ThreadLocal getThreadLocal() {
        return CURRENT_SPAN;
    }

    /**
     * Get current Trace info
     *
     * @return
     */

    public static TraceContext getContext() {
        TraceContext traceConstant = CURRENT_SPAN.get();
        if (traceConstant == null) {
            traceConstant = new TraceContext();
            String traceId = getRandomTraceId();
            String spanId = getRandomSpanId();
            traceConstant.setTraceId(traceId);
            traceConstant.setSpanId(spanId);
            MDC.put(MDCPropertyEnum.TRACE.getName(), traceConstant.getTraceId());
            MDC.put(MDCPropertyEnum.PARENT_SPAN.getName(), traceConstant.getSpanId());
            CURRENT_SPAN.set(traceConstant);
            return traceConstant;
        }
        return traceConstant;
    }

    /**
     * 设置traceId
     *
     * @param traceId
     */

    public static void setCurrentTrace(String traceId) {
        if (StringUtils.isBlank(traceId) || traceId.equals("null")) {
            traceId = getRandomTraceId();
        }
        TraceContext traceConstant = new TraceContext();
        traceConstant.setTraceId(traceId);
        traceConstant.setSpanId(getRandomSpanId());
        MDC.put(MDCPropertyEnum.TRACE.getName(), traceConstant.getTraceId());
        MDC.put(MDCPropertyEnum.PARENT_SPAN.getName(), traceConstant.getSpanId());
        CURRENT_SPAN.set(traceConstant);
    }


    /**
     * removeContext
     */
    public static void removeContext() {
        CURRENT_SPAN.remove();
    }


    /**
     * Generate a new random id;
     *
     * @return
     */
    static String getRandomTraceId() {
        return IdWorker.getIdStr();
    }

    /**
     * Generate a new random id;
     *
     * @return
     */
     static String getRandomSpanId() {
         return IdWorker.getIdStr();
    }

}
