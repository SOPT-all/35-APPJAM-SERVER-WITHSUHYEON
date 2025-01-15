package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Region {
    // 서울 지역
    SEOUL_GANGNAM("서울", "강남/역삼/삼성", "강남구"),
    SEOUL_SINSA("서울", "신사/청담/압구정", "강남구"),
    SEOUL_SEOCHO("서울", "서초/교대/사당/동작", "서초구, 동작구"),
    SEOUL_JAMSIL("서울", "잠실/송파/강동", "송파구, 강동구"),
    SEOUL_MYUNGDONG("서울", "을지로/명동/중구/동대문", "중구, 동대문구"),
    SEOUL_ITAEWON("서울", "서울역/이태원/용산", "용산구"),
    SEOUL_JONGRO("서울", "종로/인사동", "종로구"),
    SEOUL_HONGDAE("서울", "홍대/합정/마포/서대문/은평", "마포구, 서대문구, 은평구"),
    SEOUL_YEUIDO("서울", "여의도/영등포역/목동/양천", "영등포구, 양천구"),
    SEOUL_GURO("서울", "구로/신도림/금천/관악/신림", "구로구, 금천구, 관악구"),
    SEOUL_GIMPO("서울", "김포공항/염창/강서", "강서구"),
    SEOUL_KONDAE("서울", "건대입구/성수/왕십리", "광진구, 성동구"),
    SEOUL_NOWON("서울", "성북/강북/노원/도봉/중랑", "성북구, 강북구, 노원구, 도봉구, 중랑구"),

    // 부산 지역
    BUSAN_HAEUNDAE("부산", "해운대/마린시티", "해운대구"),
    BUSAN_BEXCO("부산", "벡스코/센텀시티", "해운대구"),
    BUSAN_SONGJEONG("부산", "송정/기장/정관", "기장군"),
    BUSAN_GWANGANRI("부산", "광안리/경성대", "수영구, 남구"),
    BUSAN_BUSANSTATION("부산", "부산역", "동구"),
    BUSAN_NAMPO("부산", "자갈치/남포동/영도", "중구, 영도구"),
    BUSAN_SONGDO("부산", "송도/다대포", "서구, 사하구"),
    BUSAN_YEONJE("부산", "서면/연산/범일/연제", "부산진구, 연제구"),
    BUSAN_DONGRAE("부산", "동래/온천/금정구/북구", "동래구, 금정구, 북구"),
    BUSAN_SASANG("부산", "사상/강서/김해공항/하단", "사상구, 강서구"),

    // 제주 지역
    JEJU_CITY("제주", "제주시청/제주국제공항", "제주시 동지역"),
    JEJU_MOSULPO("제주", "중문/모슬포", "대정읍, 안덕면"),
    JEJU_AEWOL("제주", "애월/한림/협재/한경", "애월읍, 한림읍, 한경면"),
    JEJU_SEOGWIPO("제주", "서귀포시", "서귀포시 동지역"),
    JEJU_EAST("제주", "표선/성산/남원", "표선면, 성산읍, 남원읍"),
    JEJU_HAMDEOK("제주", "함덕/김녕/세화", "조천읍, 구좌읍"),
    JEJU_WOODO("제주", "우도", "우도면"),
    JEJU_CHUJADO("제주", "추자도", "추자면"),

    // 인천 지역
    INCHEON_SONGDO("인천", "송도/소래포구", "연수구, 남동구"),
    INCHEON_AIRPORT("인천", "인천국제공항/강화/을왕리", "중구, 강화군, 옹진군"),
    INCHEON_YEONGJONG("인천", "영종도/월미도/동구", "중구, 동구"),
    INCHEON_JUAN("인천", "주안/간석/인천시청", "미추홀구"),
    INCHEON_BUPYEONG("인천", "청라/계양/부평", "서구, 계양구, 부평구"),

    // 강원 지역
    GANGWON_GANGNEUNG("강원", "강릉", "강릉시"),
    GANGWON_SOKCHO("강원", "속초/고성", "속초시, 고성군"),
    GANGWON_YANGYANG("강원", "양양(서피비치/낙산)", "양양군"),
    GANGWON_CHUNCHEON("강원", "춘천/인제/철원/화천/양구", "춘천시, 인제군, 철원군, 화천군, 양구군"),
    GANGWON_PYEONGCHANG("강원", "평창/정선/영월", "평창군, 정선군, 영월군"),

    // 경기도 지역
    GYEONGGI_GAPYEONG("경기", "가평/청평/양평", "가평군, 양평군"),
    GYEONGGI_SUWON("경기", "수원/화성/동탄", "수원시, 화성시"),
    GYEONGGI_GOYANG("경기", "고양/파주/김포", "고양시, 파주시, 김포시"),
    GYEONGGI_UISUNG("경기", "의정부/포천/동두천/양주", "의정부시, 포천시, 동두천시, 양주시, 연천군"),
    GYEONGGI_YONGIN("경기", "용인", "용인시");

    private final String province;  // 도/특별시
    private final String area;      // 지역 분류
    private final String districts; // 시/군/구
}



