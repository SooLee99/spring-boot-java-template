package io.soo.springboot.core.support.resolver.memberinfo;

import io.soo.springboot.core.enums.member.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoDto {

    private Long memberId;

    private MemberRole memberRole;

}
