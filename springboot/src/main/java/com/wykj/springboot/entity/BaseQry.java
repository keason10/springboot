package com.wykj.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class BaseQry extends BaseDO {

    @ApiModelProperty(value = "开始日期", name = "fromDate", example = "2021-07-01 00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date fromDate;
    @ApiModelProperty(value = "结束日期", name = "expiryDate", example = "2021-07-31 23:59:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date expiryDate;

    @ApiModelProperty(value = "当前页数", notes = "当前页数", name = "curPage", example = "1")
    @Max(message = "最大值", value = Integer.MAX_VALUE)
    private Integer pageIndex = 1;
    @ApiModelProperty(value = "每页条数", notes = "每页条数", name = "pageSize", example = "10")
    @Max(message = "最大值", value = Integer.MAX_VALUE)
    private Integer pageSize = 10;

    @ApiModelProperty(value = "国家", name = "nation", example = "中国")
    private String nation;
    @ApiModelProperty(value = "省份", name = "province", example = "湖北省")
    private String province;
    @ApiModelProperty(value = "城市", name = "nation", example = "武汉市")
    private String city;
    @ApiModelProperty(value = "区/县", name = "area", example = "武昌区")
    private String area;


    public void setPageIndex(Integer pageIndex) {
        if(null != pageIndex && pageIndex > 1){
            this.pageIndex = pageIndex;
        }
    }

    public void setPageSize(Integer pageSize) {
        if(null != pageSize && pageSize > 1){
            this.pageSize = pageSize;
        }
    }

    @JsonIgnore
    public Long getFrom() {
        return (pageIndex - 1) * pageSize * 1l;
    }

}
