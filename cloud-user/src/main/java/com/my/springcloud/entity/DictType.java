package com.my.springcloud.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Table(name = "tb_dict_type")
@Entity
@Data
public class DictType {
    /**主键ID**/
    @Id
    @Column(length=60)
    private String id;

    @Column(length=30)
    /**字典名**/
    private String type;

    /**描述**/
    private String description;

    /**创建者**/
    private String create_by;

    //角色 -- 权限关系：多对多关系;
    @OneToMany(mappedBy = "dictType")
    private Set<DictValue> DictValue;
}
