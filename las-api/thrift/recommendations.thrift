namespace java org.easycloud.las.api.services.thrift
namespace php services

const string VERSION = "1.0.0"

struct Recommendation {
	1: required string itemId,
	2: required byte itemType,
	3: required i32 prefValue,
}

struct UserRecommendations {
	1: required string userCode,
	2: optional list<Recommendation> recommendations,
}

struct VisitRecord {
	1: required string houseCode,
	2: required byte houseType,
	3: required i64 visitDttm,
}

struct UserVisitRecord {
	1: required string userCode,
	2: required byte userType,
	3: optional list<VisitRecord> visitRecords,
}

enum UserType {
	ALL = 0,
	ANONYMOUS = 1,
	LOGIN = 2,
}

enum HouseType {
	ALL = 0,
	SELL = 1,
	RENT = 2,
}

service LasService {
    list<UserVisitRecord> listUserVisitRecords(1:required i32 start,
    										2:required i32 limit),

    list<VisitRecord> findVisitRecords(1:required string userCode,
    									2:byte houseType),

	list<UserRecommendations> listItemBasedRecommendations(1:required i32 start,
										2:required i32 limit),

	list<Recommendation> findItemBasedRecommendations(1:required string userCode),
}