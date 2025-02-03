package org.advisor.message.validations;

import org.advisor.member.MemberUtil;
import org.advisor.message.controllers.RequestMessage;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MessageValidator implements Validator {

    private MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestMessage.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestMessage form = (RequestMessage) target;
    }
}
