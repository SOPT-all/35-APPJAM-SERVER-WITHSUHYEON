package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sopt.appjam.withsuhyeon.dto.constant.res.Regions;
import sopt.appjam.withsuhyeon.dto.constant.res.RegionsResponse;
import sopt.appjam.withsuhyeon.exception.ConstantErrorCode;
import sopt.appjam.withsuhyeon.exception.PostErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Region {
    // 전체
    ALL("ALL", "전국", "전국전체"),

    // 서울 지역
    SEOUL_ALL("SEOUL_ALL", "서울", "서울전체"),
    SEOUL_GANGNAM("SEOUL_GANGNAM", "서울", "강남/역삼/삼성"),
    SEOUL_SINSA("SEOUL_SINSA", "서울", "신사/청담/압구정"),
    SEOUL_SEOCHO("SEOUL_SEOCHO", "서울", "서초/교대/사당/동작"),
    SEOUL_JAMSIL("SEOUL_JAMSIL", "서울", "잠실/송파/강동"),
    SEOUL_MYUNGDONG("SEOUL_MYUNGDONG", "서울", "을지로/명동/중구/동대문"),
    SEOUL_ITAEWON("SEOUL_ITAEWON", "서울", "서울역/이태원/용산"),
    SEOUL_JONGRO("SEOUL_JONGRO", "서울", "종로/인사동"),
    SEOUL_HONGDAE("SEOUL_HONGDAE", "서울", "홍대/합정/마포/서대문/은평"),
    SEOUL_YEUIDO("SEOUL_YEUIDO", "서울", "여의도/영등포역/목동/양천"),
    SEOUL_GURO("SEOUL_GURO", "서울", "구로/신도림/금천/관악/신림"),
    SEOUL_GIMPO("SEOUL_GIMPO", "서울", "김포공항/염창/강서"),
    SEOUL_KONDAE("SEOUL_KONDAE", "서울", "건대입구/성수/왕십리"),
    SEOUL_NOWON("SEOUL_NOWON", "서울", "성북/강북/노원/도봉/중랑"),

    // 부산 지역
    BUSAN_ALL("BUSAN_ALL", "부산", "부산전체"),
    BUSAN_HAEUNDAE("BUSAN_HAEUNDAE", "부산", "해운대/마린시티"),
    BUSAN_BEXCO("BUSAN_BEXCO", "부산", "벡스코/센텀시티"),
    BUSAN_SONGJEONG("BUSAN_SONGJEONG", "부산", "송정/기장/정관"),
    BUSAN_GWANGANRI("BUSAN_GWANGANRI", "부산", "광안리/경성대"),
    BUSAN_BUSANSTATION("BUSAN_BUSANSTATION", "부산", "부산역"),
    BUSAN_NAMPO("BUSAN_NAMPO", "부산", "자갈치/남포동/영도"),
    BUSAN_SONGDO("BUSAN_SONGDO", "부산", "송도/다대포"),
    BUSAN_YEONJE("BUSAN_YEONJE", "부산", "서면/연산/범일/연제"),
    BUSAN_DONGRAE("BUSAN_DONGRAE", "부산", "동래/온천/금정구/북구"),
    BUSAN_SASANG("BUSAN_SASANG", "부산", "사상/강서/김해공항/하단"),

    // 제주 지역
    JEEJU_ALL("JEEJU_ALL", "제주", "제주전체"),
    JEJU_CITY("JEJU_CITY", "제주", "제주시청/제주국제공항"),
    JEJU_MOSULPO("JEJU_MOSULPO", "제주", "중문/모슬포"),
    JEJU_AEWOL("JEJU_AEWOL", "제주", "애월/한림/협재/한경"),
    JEJU_SEOGWIPO("JEJU_SEOGWIPO", "제주", "서귀포시"),
    JEJU_EAST("JEJU_EAST", "제주", "표선/성산/남원"),
    JEJU_HAMDEOK("JEJU_HAMDEOK", "제주", "함덕/김녕/세화"),
    JEJU_WOODO("JEJU_WOODO", "제주", "우도"),
    JEJU_CHUJADO("JEJU_CHUJADO", "제주", "추자도"),

    // 인천 지역
    INCHEON_ALL("INCHEON_ALL", "인천", "인천전체"),
    INCHEON_SONGDO("INCHEON_SONGDO", "인천", "송도/소래포구"),
    INCHEON_AIRPORT("INCHEON_AIRPORT", "인천", "인천국제공항/강화/을왕리"),
    INCHEON_YEONGJONG("INCHEON_YEONGJONG", "인천", "영종도/월미도/동구"),
    INCHEON_JUAN("INCHEON_JUAN", "인천", "주안/간석/인천시청"),
    INCHEON_BUPYEONG("INCHEON_BUPYEONG", "인천", "청라/계양/부평"),

    // 강원 지역
    GANGWON_ALL("GANGWON_ALL", "강원", "강원전체"),
    GANGWON_GANGNEUNG("GANGWON_GANGNEUNG", "강원", "강릉"),
    GANGWON_SOKCHO("GANGWON_SOKCHO", "강원", "속초/고성"),
    GANGWON_YANGYANG("GANGWON_YANGYANG", "강원", "양양(서피비치/낙산)"),
    GANGWON_CHUNCHEON("GANGWON_CHUNCHEON", "강원", "춘천/인제/철원/화천/양구"),
    GANGWON_PYEONGCHANG("GANGWON_PYEONGCHANG", "강원", "평창/정선/영월"),

    // 경기도 지역
    GYEONGGI_ALL("GYEONGGI_ALL", "경기", "경기전체"),
    GYEONGGI_GAPYEONG("GYEONGGI_GAPYEONG", "경기", "가평/청평/양평"),
    GYEONGGI_SUWON("GYEONGGI_SUWON", "경기", "수원/화성/동탄"),
    GYEONGGI_GOYANG("GYEONGGI_GOYANG", "경기", "고양/파주/김포"),
    GYEONGGI_UISUNG("GYEONGGI_UISUNG", "경기", "의정부/포천/동두천/양주"),
    GYEONGGI_YONGIN("GYEONGGI_YONGIN", "경기", "용인");

    private final String key;
    private final String location;  // 도/특별시
    private final String subLocation; // 지역 분류

    public static Region fromValue(String value) {
        for (Region region : Region.values()) {
            if (region.getSubLocation().equals(value)) {
                return region;
            }
        }
        throw BaseException.type(PostErrorCode.POST_REGION_INVALID_INPUT);
    }

    public static String getCityFrom(Region inputRegion) {
        for (Region region : Region.values()) {
            if (region.getSubLocation().equals(inputRegion.getSubLocation())) {
                return region.getLocation();
            }
        }
        throw BaseException.type(ConstantErrorCode.NOT_FOUND_LOCATION_FROM_SUBLOCATION);
    }

    public static boolean isAllRegion(String value) {
        return value.contains("전체");
    }

    public static RegionsResponse generateRegionsResponse() {
        Map<String, List<String>> groupedRegions = Arrays.stream(Region.values())
                .collect(Collectors.groupingBy(
                        Region::getLocation,
                        LinkedHashMap::new,
                        Collectors.mapping(Region::getSubLocation, Collectors.toList())
                ));

        List<Regions> regionsList = groupedRegions.entrySet().stream()
                .map(entry -> new Regions(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new RegionsResponse(regionsList);
    }

}