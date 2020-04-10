package com.yiyuclub.springshiro.models;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yiyu
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Permisson implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer permissonId;

    private String permissonName;

    private String permissonRemake;


}
