namespace java org.easycloud.las.services.housevisit.thrift
namespace php housevisit

const string VERSION = "1.0.0"

struct HvRecord {
	1: required string houseCode,
	2: required byte houseType,
	3: required i64 visitDttm,
}

struct UvsRecord {
	1: required string userCode,
	2: optional byte userType,
	3: optional list<HvRecord> hvRecords,
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

service HouseVisit {
	list<UvsRecord> getUserVisitRecords(1:required i32 start,
										2:required i32 limit),
	list<HvRecord> getUserVisitRecord(1:required string userCode,
									2:byte houseType),
}