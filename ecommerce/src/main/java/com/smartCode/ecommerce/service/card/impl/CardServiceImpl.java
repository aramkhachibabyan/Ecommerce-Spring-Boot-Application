package com.smartCode.ecommerce.service.card.impl;

import com.smartCode.ecommerce.exceptions.ValidationException;
import com.smartCode.ecommerce.feign.CardFeignClient;
import com.smartCode.ecommerce.model.dto.card.CardDto;
import com.smartCode.ecommerce.model.dto.card.CreateCardDto;
import com.smartCode.ecommerce.model.dto.card.ResponseCardDto;
import com.smartCode.ecommerce.service.action.ActionService;
import com.smartCode.ecommerce.service.card.CardService;
import com.smartCode.ecommerce.util.constants.Actions;
import com.smartCode.ecommerce.util.constants.Message;
import com.smartCode.ecommerce.util.constants.entityTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardFeignClient cardFeignClient;
    private final ActionService actionService;

    @Transactional
    @Override
    public ResponseCardDto createCard(CreateCardDto createCardDto) {
        if (createCardDto.getCardNumber().length()!=16){
            throw new ValidationException(Message.INVALID_CARD_NUMBER);
        }
        ResponseCardDto body = cardFeignClient.createCard(createCardDto).getBody();
        actionService.create(createCardDto.getUserId(), Actions.CREATE, entityTypes.CARD);
        return body;
    }

    @Override
    @Transactional
    public void deleteCardsByUserId(Integer userId) {
        cardFeignClient.deleteCardsByUserId(userId);
        actionService.create(userId, Actions.DELETE, entityTypes.CARD);

    }

    @Override
    @Transactional
    public ResponseCardDto deleteCardById(Integer id) {
        actionService.create(id, Actions.DELETE, entityTypes.CARD);

        return cardFeignClient.deleteCardById(id).getBody();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseCardDto> getCardsByUserId(Integer userId) {
        return cardFeignClient.getCardsByUserId(userId).getBody();
    }

    @Override
    @Transactional()
    public ResponseCardDto getCardById(Integer id) {
        return cardFeignClient.getCardById(id).getBody();
    }
}
