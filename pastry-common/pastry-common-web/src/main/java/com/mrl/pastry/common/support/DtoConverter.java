package com.mrl.pastry.common.support;

import com.mrl.pastry.common.utils.BeanUtils;
import org.springframework.lang.NonNull;

/**
 * Dto converter
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/17
 */
public interface DtoConverter<DTO extends DtoConverter<DTO, DOMAIN>, DOMAIN> {

    /**
     * Convert from DOMAIN
     *
     * @param domain must not be null, domain data
     * @return dto
     */
    default DTO convertFrom(@NonNull DOMAIN domain) {
        BeanUtils.copyProperties(domain, this);
        return (DTO) this;
    }
}
