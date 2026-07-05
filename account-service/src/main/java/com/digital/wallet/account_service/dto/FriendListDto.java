package com.digital.wallet.account_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendListDto {
    private List<String> friendList;
}
