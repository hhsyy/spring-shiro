package com.yiyuclub.springshiro.models;

import java.io.Serializable;
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
public class IdeaUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String password;

    private String role;

    private String area;

    private String phone;

    private String salt;


}
