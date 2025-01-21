package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sopt.appjam.withsuhyeon.config.CategoryImageProperties;
import sopt.appjam.withsuhyeon.dto.constant.res.Categories;
import sopt.appjam.withsuhyeon.exception.ConstantErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Category {
    STUDY_CAFE("STUDY_CAFE", "스터디카페"),
    BEACH("BEACH", "바다"),
    SCHOOL("SCHOOL", "학교"),
    SKI_RESORT("SKI_RESORT", "스키장"),
    MT("MT", "엠티"),
    CAMPING_GLAMPING("CAMPING_GLAMPING", "캠핑/글램핑"),
    SPA("SPA", "찜질방"),
    COMPANY_DINNER("COMPANY_DINNER", "회식"),
    CAFE("CAFE", "카페"),
    AIRPORT("AIRPORT", "공항"),
    STUDY_ROOM("STUDY_ROOM", "자취방"),
    CHURCH_RETREAT("CHURCH_RETREAT", "절/교회 수련회"),
    OVERSEAS_TRAVEL("OVERSEAS_TRAVEL", "해외여행"),
    PARTY_ROOM("PARTY_ROOM", "파티룸"),
    WATERPARK("WATERPARK", "워터파크"),
    OTHERS("OTHERS", "기타");

    private final String key;
    private final String value;

    public static Category from(String value) {
        for (Category category : Category.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        throw BaseException.type(ConstantErrorCode.NOT_FOUND_CATEGORY);
    }

    public static List<Categories> toCategoriesResponse(CategoryImageProperties properties) {
        return List.of(Category.values())
                .stream()
                .map(category -> new Categories(
                        properties.getImageUrl(category.getKey()),
                        category.getValue()
                ))
                .collect(Collectors.toList());
    }
}
