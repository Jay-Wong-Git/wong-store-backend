package com.wong.store.manager.service;

import com.wong.store.model.vo.system.ValidateCodeVo;

/**
 * @author Jay Wong
 * @date 2023/12/22 16:35
 */
public interface ValidateCodeService {
    ValidateCodeVo generateValidateCode();
}
