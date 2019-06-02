package com.riversoft.weixin.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by exizhai on 9/25/2015.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Text implements Serializable {

    private String content;

}
