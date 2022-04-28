package com.wykj.springboot.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wykj.springboot.entity.BaseQry;
import com.wykj.springboot.entity.PageDO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageDTO<T> extends PageDO {
    private Long total;
    private Long pagesTotal;
    private List<T> list;

    public PageDTO(Long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public PageDTO(Long total, List<T> list, Long pageIndex, Long pageSize) {
        super.setPageIndex(pageIndex);
        super.setPageSize(pageSize);
        this.total = total;
        this.list = list;
        this.pagesTotal =  (long) Math.ceil(Double.valueOf(total)  / this.getPageSize());
    }

    public PageDTO() {
    }

    /**
     * 转换为 mybatis-plus 所需分页查询条件 {@link Page}
     */
    public static <T> Page<T> ofIPage(BaseQry qry) {
        return new Page<>(qry.getPageIndex(), qry.getPageSize());
    }

    public static <T> PageDTO<T> newPage(IPage<T> iPage) {
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setPageIndex(iPage.getCurrent()); // 当前页下标
        pageDTO.setPageSize(iPage.getSize());   // 每页条数
        pageDTO.setTotal(iPage.getTotal());     // 总数量
        pageDTO.setPagesTotal((pageDTO.getTotal() + pageDTO.getPageSize() - 1) / pageDTO.getPageSize()); // 分页数
        pageDTO.setList(iPage.getRecords());
        return pageDTO;
    }
}
