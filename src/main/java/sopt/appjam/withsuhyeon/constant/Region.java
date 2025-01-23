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
    ALL("ALL", "전국", "전국 전체"),

    // 서울 지역
    SEOUL_ALL("SEOUL_ALL", "서울", "서울 전체"),
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
    BUSAN_ALL("BUSAN_ALL", "부산", "부산 전체"),
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
    JEJU_ALL("JEJU_ALL", "제주", "제주 전체"),
    JEJU_CITY("JEJU_CITY", "제주", "제주시청/제주국제공항"),
    JEJU_MOSULPO("JEJU_MOSULPO", "제주", "중문/모슬포"),
    JEJU_AEWOL("JEJU_AEWOL", "제주", "애월/한림/협재/한경"),
    JEJU_SEOGWIPO("JEJU_SEOGWIPO", "제주", "서귀포시"),
    JEJU_EAST("JEJU_EAST", "제주", "표선/성산/남원"),
    JEJU_HAMDEOK("JEJU_HAMDEOK", "제주", "함덕/김녕/세화"),
    JEJU_WOODO("JEJU_WOODO", "제주", "우도"),
    JEJU_CHUJADO("JEJU_CHUJADO", "제주", "추자도"),

    // 인천 지역
    INCHEON_ALL("INCHEON_ALL", "인천", "인천 전체"),
    INCHEON_SONGDO("INCHEON_SONGDO", "인천", "송도/소래포구"),
    INCHEON_AIRPORT("INCHEON_AIRPORT", "인천", "인천국제공항/강화/을왕리"),
    INCHEON_YEONGJONG("INCHEON_YEONGJONG", "인천", "영종도/월미도/동구"),
    INCHEON_JUAN("INCHEON_JUAN", "인천", "주안/간석/인천시청"),
    INCHEON_BUPYEONG("INCHEON_BUPYEONG", "인천", "청라/계양/부평"),

    // 강원 지역
    GANGWON_ALL("GANGWON_ALL", "강원", "강원 전체"),
    GANGWON_GANGNEUNG("GANGWON_GANGNEUNG", "강원", "강릉"),
    GANGWON_SOKCHO("GANGWON_SOKCHO", "강원", "속초/고성"),
    GANGWON_YANGYANG("GANGWON_YANGYANG", "강원", "양양(서피비치/낙산)"),
    GANGWON_CHUNCHEON("GANGWON_CHUNCHEON", "강원", "춘천/인제/철원/화천/양구"),
    GANGWON_PYEONGCHANG("GANGWON_PYEONGCHANG", "강원", "평창/정선/영월"),

    // 경기도 지역
    GYEONGGI_ALL("GYEONGGI_ALL", "경기", "경기 전체"),
    GYEONGGI_GAPYEONG("GYEONGGI_GAPYEONG", "경기", "가평/청평/양평"),
    GYEONGGI_SUWON("GYEONGGI_SUWON", "경기", "수원/화성/동탄"),
    GYEONGGI_GOYANG("GYEONGGI_GOYANG", "경기", "고양/파주/김포"),
    GYEONGGI_UISUNG("GYEONGGI_UISUNG", "경기", "의정부/포천/동두천/양주"),
    GYEONGGI_YONGIN("GYEONGGI_YONGIN", "경기", "용인"),
    GYEONGGI_OSAN("GYEONGGI_OSAN", "경기", "오산/평택/안성"),
    GYEONGGI_NAMYANGJU("GYEONGGI_NAMYANGJU", "경기", "남양주/구리/성남/분당"),
    GYEONGGI_ICHEON("GYEONGGI_ICHEON", "경기", "이천/광주/여주/하남"),
    GYEONGGI_BUCHEON("GYEONGGI_BUCHEON", "경기", "부천/광명/시흥/안산"),
    GYEONGGI_ANYANG("GYEONGGI_ANYANG", "경기", "안양/의왕/군포/과천"),

    // 경상 지역 추가
    GYEONGSANG_DAEGU("GYEONGSANG_DAEGU", "경상", "대구/구미/안동/문경"),
    GYEONGSANG_GEOCHANG("GYEONGSANG_GEOCHANG", "경상", "거창/함양/합천/산청/의령/창녕/함안"),
    GYEONGSANG_GYEONGJU("GYEONGSANG_GYEONGJU", "경상", "경주"),
    GYEONGSANG_ULSAN("GYEONGSANG_ULSAN", "경상", "울산/양산/밀양"),
    GYEONGSANG_GEOJE("GYEONGSANG_GEOJE", "경상", "거제/통영"),
    GYEONGSANG_POHANG("GYEONGSANG_POHANG", "경상", "포항/영덕/울진/청송"),
    GYEONGSANG_CHANGWON("GYEONGSANG_CHANGWON", "경상", "창원/마산/진해/김해/부곡"),
    GYEONGSANG_NAMHAE("GYEONGSANG_NAMHAE", "경상", "남해/사천/하동/진주"),

    // 전라 지역 추가
    JEOLLA_JEONJU("JEOLLA_JEONJU", "전라", "전주/완주"),
    JEOLLA_GWANGJU("JEOLLA_GWANGJU", "전라", "광주/나주/함평"),
    JEOLLA_YEOSU("JEOLLA_YEOSU", "전라", "여수"),
    JEOLLA_SUNCHEON("JEOLLA_SUNCHEON", "전라", "순천/광양/담양/보성/화순/곡성/구례"),
    JEOLLA_BUAN("JEOLLA_BUAN", "전라", "부안/정읍/고창/김제"),
    JEOLLA_MUJU("JEOLLA_MUJU", "전라", "무주/남원/장수/진안/임실/순창"),
    JEOLLA_GUNSAN("JEOLLA_GUNSAN", "전라", "군산/익산"),
    JEOLLA_MOKPO("JEOLLA_MOKPO", "전라", "목포/완도/고흥/신안"),

    // 충청 지역 추가
    CHUNGCHEONG_DAEJEON("CHUNGCHEONG_DAEJEON", "충청", "대전/세종"),
    CHUNGCHEONG_CHEONAN("CHUNGCHEONG_CHEONAN", "충청", "천안/아산/도고"),
    CHUNGCHEONG_DANGJIN("CHUNGCHEONG_DANGJIN", "충청", "당진/덕산/태안/서산/안면도/홍성"),
    CHUNGCHEONG_GONGJU("CHUNGCHEONG_GONGJU", "충청", "공주/보령/부여/금산"),
    CHUNGCHEONG_CHEONGJU("CHUNGCHEONG_CHEONGJU", "충청", "청주/보은/옥천"),
    CHUNGCHEONG_CHUNGJU("CHUNGCHEONG_CHUNGJU", "충청", "충주/제천/단양");


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