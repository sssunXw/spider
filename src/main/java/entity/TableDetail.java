package entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 功能说明:
 * </pre>
 *
 * @author sxw
 * @date 2019/6/7
 */

@Data
public class TableDetail {

    private String tableName;

    private String transferName;

    private List<String> fields;

    private List<List<String>> data = new ArrayList<>();


}
