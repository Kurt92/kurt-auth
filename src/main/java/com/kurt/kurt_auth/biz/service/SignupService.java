package com.kurt.kurt_auth.biz.service;


import com.kurt.kurt_auth.biz.dto.SignupDto;
import com.kurt.kurt_auth.biz.entity.UserMng;
import com.kurt.kurt_auth.biz.entity.UserMngRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserMngRepository userMngRepository;

    public void signup(SignupDto.Request signupDto) throws Exception {

        UserMng userMng = UserMng.builder()
                .accountId(signupDto.getAccountId())
                .accountPass(signupDto.getPassword())
                .email(signupDto.getEmail())
                .build();

        userMngRepository.save(userMng);
    }



    public Boolean duplicateCheck(String accountId) {


        return userMngRepository.existsByAccountId(accountId);
    }

}
