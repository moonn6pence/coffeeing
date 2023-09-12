package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CapsuleService {

    private final CapsuleRepository capsuleRepository;
    public CapsuleResponse getDetail(Long id) {
        Capsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        return ProductMapper.supplyCapsuleResponseBy(capsule);
    }
}
