package entity;

import lombok.Data;


/**
 * <pre>
 * 功能说明:
 * </pre>
 *
 * @author sxw
 * @date 2019/6/7
 */

@Data
public class PageInfo {

    private Integer totalRows;

    private Integer pageSize;

    private Integer pageIndex;


}
