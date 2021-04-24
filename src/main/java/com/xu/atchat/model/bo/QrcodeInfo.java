package com.xu.atchat.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/5 12:03
 * @description 二维码信息
 */
@Data
@AllArgsConstructor
public class QrcodeInfo {

    private String type;

    private String subject;

    private String username;

    private String encrypt;
}
