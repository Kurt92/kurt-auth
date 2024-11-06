package com.jt.sdfor_auth.biz.service;

import com.jt.sdfor_auth.biz.dto.LoginDTO;
import com.jt.sdfor_auth.biz.entity.UserMng;
import com.jt.sdfor_auth.biz.entity.UserMngRepository;
import com.jt.sdfor_auth.framework.core.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final UserMngRepository userMngRepository;

    public void login(LoginDTO.Request loginDTO) {

        UserMng userMng = userMngRepository.findByAccountIdAndAccountPass(loginDTO.getId(), loginDTO.getPassword());


        String token = jwtUtil.generateToken(userMng);


    }
}
