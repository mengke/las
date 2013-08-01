namespace java org.easycloud.las.api.recommender.thrift
namespace php recommender

const string VERSION = "1.0.0"

struct Recommendation {
	1: required string itemId,
	2: required double prefValue,
}

struct UserRecommendations {
	1: required string userCode,
	2: optional list<Recommendation> recommendations,
}

service ItemBasedRecommendation {
	list<UserRecommendations> listItemBasedRecommendations(1:required i32 start,
										2:required i32 limit),
	list<Recommendation> findItemBasedRecommendations(1:required string userCode),
}