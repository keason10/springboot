package com.wykj.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Setter;


@Setter
public class PageDO extends BaseDO {

    @Min(message = "pageIndex最小为1", value = 1)
    @Max(message = "pageIndex超过最大值", value = Integer.MAX_VALUE)
    private Long pageIndex = 1L;

    @Min(message = "pageSize最小为1", value = 1)
    @Max(message = "pageSize超过最大值", value = Integer.MAX_VALUE)
    private Long pageSize = 15L;


    public Long getPageIndex() {
        return pageIndex = (pageIndex == null) ? 1L : (pageIndex <= 0) ? 1L : pageIndex;
    }

    public Long getPageSize() {
        return pageSize = (pageSize == null) ? 15L : (pageSize <= 0) ? 15L : pageSize;
    }

    @JsonIgnore
    public Long getFrom() {
        return (pageIndex - 1) * pageSize;
    }
}
