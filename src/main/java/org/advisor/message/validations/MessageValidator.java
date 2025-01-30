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
        return RequestMessage.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestMessage request = (RequestMessage) target;

        if (request.getSubject() == null || request.getSubject().length() < 3) {
            errors.rejectValue("subject", "length", "제목은 최소 3자 이상이어야 합니다.");
        }

        if (request.getContent() == null || request.getContent().length() < 5) {
            errors.rejectValue("content", "length", "내용은 최소 5자 이상이어야 합니다.");
        }
    }
}
