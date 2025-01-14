package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Category {
    AIRPORT("공항"),
    LIBRARY("도서관"),
    BEACH_VALLEY("바다/계곡"),
    SKI_RESORT("스키장"),
    COMPANY_DINNER("회식"),
    MT("엠티"),
    WATERPARK_BATH("워터파크/빠지"),
    STUDY_ROOM("자취방"),
    CHURCH_RETREAT("절/교회 수련회"),
    SPA("찜질방"),
    CAFE("카페"),
    CAMPING_GLAMPING("캠핑/글램핑"),
    PARTY_ROOM("파티룸"),
    SCHOOL("학교"),
    OVERSEAS_TRAVEL("해외여행"),
    OTHERS("기타");

    private final String value;
}
