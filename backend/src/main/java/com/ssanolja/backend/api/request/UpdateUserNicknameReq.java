package com.ssanolja.backend.api.request;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserNicknameReq {

    @NotNull
    private String userEmail;

    @NotNull
    private String updatedUserNickname;
}
