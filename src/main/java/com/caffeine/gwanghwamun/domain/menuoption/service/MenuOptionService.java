package com.caffeine.gwanghwamun.domain.menuoption.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.menuoption.dto.request.MenuOptionCreateReqDTO;
import com.caffeine.gwanghwamun.domain.menuoption.dto.request.MenuOptionSoldOutReqDTO;
import com.caffeine.gwanghwamun.domain.menuoption.dto.request.MenuOptionUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.menuoption.dto.response.MenuOptionResDTO;
import com.caffeine.gwanghwamun.domain.menuoption.entity.MenuOption;
import com.caffeine.gwanghwamun.domain.menuoption.repository.MenuOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MenuOptionService {

    private final MenuOptionRepository menuOptionRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public MenuOptionResDTO saveOption(UUID storeId, UUID menuId, MenuOptionCreateReqDTO req) {
        menuRepository.findByIdAndNotDeleted(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        MenuOption option = MenuOption.builder()
                .menuId(menuId)
                .optionName(req.optionName())
                .price(req.price())
                .content(req.content())
                .isHidden(req.isHidden())
                .isSoldOut(req.isSoldOut())
                .build();

        return new MenuOptionResDTO(menuOptionRepository.save(option));
    }

    public Page<MenuOptionResDTO> findOptionList(UUID storeId,
                                                 UUID menuId,
                                                 Boolean includeHidden,
                                                 Boolean soldOut,
                                                 String optionName,
                                                 Pageable pageable) {
        Page<MenuOption> page =
                menuOptionRepository.searchOptions(menuId, includeHidden, soldOut, optionName, pageable);
        return page.map(MenuOptionResDTO::new);
    }

    public MenuOptionResDTO findOption(UUID storeId, UUID menuId, UUID optionId) {
        MenuOption option = menuOptionRepository.findByIdAndNotDeleted(optionId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_OPTION_NOT_FOUND));
        if (!option.getMenuId().equals(menuId)) {
            throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
        }
        return new MenuOptionResDTO(option);
    }

    @Transactional
    public MenuOptionResDTO updateOption(UUID storeId, UUID menuId, UUID optionId, MenuOptionUpdateReqDTO req) {
        MenuOption option = menuOptionRepository.findByIdAndNotDeleted(optionId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_OPTION_NOT_FOUND));
        if (!option.getMenuId().equals(menuId)) {
            throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
        }
        option.update(req.optionName(), req.price(), req.content(), req.isHidden());
        return new MenuOptionResDTO(option);
    }

    @Transactional
    public void deleteOption(UUID storeId, UUID menuId, UUID optionId, String deleter) {
        MenuOption option = menuOptionRepository.findByIdAndNotDeleted(optionId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_OPTION_NOT_FOUND));
        if (!option.getMenuId().equals(menuId)) {
            throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
        }
        option.softDelete(deleter);
    }

    @Transactional
    public MenuOptionResDTO updateSoldOut(UUID storeId, UUID menuId, UUID optionId, MenuOptionSoldOutReqDTO req) {
        MenuOption option = menuOptionRepository.findByIdAndNotDeleted(optionId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_OPTION_NOT_FOUND));
        if (!option.getMenuId().equals(menuId)) {
            throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
        }
        if (Boolean.TRUE.equals(req.isSoldOut())) {
            option.markSoldOut();
        } else {
            option.markAvailable();
        }
        return new MenuOptionResDTO(option);
    }
}