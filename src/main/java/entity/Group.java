package entity;

import lombok.Data;

import java.util.List;


/**
 * <pre>
 * 功能说明:
 * </pre>
 *
 * @author sxw
 * @date 2019/6/7
 */

@Data
public class Group {
    private String id;
    private String name;

    private List<Group> packs;
}
