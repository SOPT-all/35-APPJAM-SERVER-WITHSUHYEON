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
    BEACH("BEACH", "바다"),
    CAFE("CAFE", "카페"),
    SCHOOL("SCHOOL", "학교"),
    PARTY_ROOM("PARTY_ROOM", "파티룸"),
    AIRPORT("AIRPORT", "공항"),
    SKI_RESORT("SKI_RESORT", "스키장"),
    CAMPING("CAMPING", "캠핑"),
    COMPANY_DINNER("COMPANY_DINNER", "회식"),
    POOL("POOL", "수영장"),
    ROOM("ROOM", "자취방"),
    MT("MT", "엠티"),
    OVERSEAS_TRAVEL("OVERSEAS_TRAVEL", "해외여행"),
    STUDY_CAFE("STUDY_CAFE", "도서관"),
    SPA("SPA", "찜질방"),
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
