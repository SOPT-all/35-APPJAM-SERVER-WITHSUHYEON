package sopt.appjam.withsuhyeon.dto.home.res;

public record HomePost(
        Long postId,
        String title,
        Integer price,
        String age,
        Boolean gender,
        String date,
        Boolean matching
) {
    public static HomePost of(final Long postId, final String title, final Integer price, final String age, final Boolean gender, final String date, final Boolean matching) {
        return new HomePost(postId, title, price, age, gender, date, matching);
    }
}
