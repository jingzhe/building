package com.jingzhe.building;

import com.jingzhe.building.api.model.BuildingDataResponse;
import com.jingzhe.building.model.BuildingInfo;
import com.jingzhe.building.respository.BuildingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.jingzhe.building.Stubs.*;
import static com.jingzhe.building.Utils.setPrecision;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@AutoConfigureWireMock(port = 0)
class BuildingApplicationTests extends IntegrationTest {

	@Autowired
	private BuildingRepository buildingRepository;

	@Test
	void createOuluBuildings_withGoodAccessToken_succeedTest() {
		stubForJwks();
		stubForGeoData("Oulu");

		String input = Specifications.readFile("Oulu_request.json");
		List<BuildingDataResponse> list = Specifications.givenCreateBuildingsPost(input, Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);

		assertEquals(6, list.size());
		assertNotNull(list.get(0).getId());
		assertEquals(65, (int)list.get(0).getLatitude());

		list.forEach(item -> buildingRepository.deleteById(item.getId()).block());
	}

	@Test
	void createHelsinkiBuildings_withGoodAccessToken_succeedTest() {
		stubForJwks();
		stubForGeoData("Helsinki");

		String input = Specifications.readFile("Helsinki_request.json");
		List<BuildingDataResponse> list = Specifications.givenCreateBuildingsPost(input, Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);

		assertEquals(2, list.size());
		assertNotNull(list.get(0).getId());
		assertEquals(60, (int)list.get(0).getLatitude());

		list.forEach(item -> buildingRepository.deleteById(item.getId()).block());
	}

	@Test
	void searchOuluBuildings_succeedTest() {
		stubForJwks();
		stubForGeoData("Oulu");

		buildingRepository.deleteAll().block();

		String input = Specifications.readFile("Oulu_request.json");
		List<BuildingDataResponse> list = Specifications.givenCreateBuildingsPost(input, Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);

		assertEquals(6, list.size());

		List<BuildingDataResponse> foundList1 = Specifications.givenSearchBuildingsGet(null, null, 5,
						null, "Oulu", null, null, null, null, null)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);
		System.out.println(foundList1);
		assertEquals(1, foundList1.size());


		List<BuildingDataResponse> foundList2 = Specifications.givenSearchBuildingsGet(null, null, null,
						null, "Oulu", null, null, null, null, null)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);
		assertEquals(6, foundList2.size());

		List<BuildingDataResponse> foundList3 = Specifications.givenSearchBuildingsGet(null, null, null,
						null, "Oulu", null, 3, 2, "name", "desc")
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);
		assertEquals(3, foundList3.size());
		BuildingDataResponse first = foundList3.get(0);
		assertEquals("4", first.getName());


		list.forEach(item -> buildingRepository.deleteById(item.getId()).block());
	}

	@Test
	void updateHelsinkiBuildings_withGoodAccessToken_succeedTest() {
		stubForJwks();
		stubForGeoData("Helsinki");
		stubForGeoData("Oulu");

		String input = Specifications.readFile("Helsinki_request.json");
		List<BuildingDataResponse> list = Specifications.givenCreateBuildingsPost(input, Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);

		assertEquals(2, list.size());

		BuildingDataResponse orig = list.get(0);
		String updatedInput = Specifications.readFile("Oulu_update_request.json");
		BuildingDataResponse updated = Specifications.givenUpdateBuildingPut(orig.getId(), updatedInput, Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.as(BuildingDataResponse.class);

		assertEquals("Isokatu", updated.getStreet());
		assertNotEquals(setPrecision(orig.getLatitude()), setPrecision(updated.getLatitude()));
		assertNotEquals(setPrecision(orig.getLongitude()), setPrecision(updated.getLongitude()));

		list.forEach(item -> buildingRepository.deleteById(item.getId()).block());
	}

	@Test
	void fetchHelsinkiBuildings_withGoodAccessToken_succeedTest() {
		stubForJwks();
		stubForGeoData("Helsinki");

		String input = Specifications.readFile("Helsinki_request.json");
		List<BuildingDataResponse> list = Specifications.givenCreateBuildingsPost(input, Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);

		assertEquals(2, list.size());

		BuildingDataResponse orig = list.get(0);
		BuildingDataResponse fetched = Specifications.givenFetchBuildingGet(orig.getId(), Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.as(BuildingDataResponse.class);

		assertEquals(orig.getStreet(), fetched.getStreet());
		assertEquals(setPrecision(orig.getLatitude()), setPrecision(fetched.getLatitude()));
		assertEquals(setPrecision(orig.getLongitude()), setPrecision(fetched.getLongitude()));

		list.forEach(item -> buildingRepository.deleteById(item.getId()).block());
	}

	@Test
	void deleteHelsinkiBuilding_withGoodAccessToken_succeedTest() {
		stubForJwks();
		stubForGeoData("Helsinki");

		String input = Specifications.readFile("Helsinki_request.json");
		List<BuildingDataResponse> list = Specifications.givenCreateBuildingsPost(input, Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);

		assertEquals(2, list.size());

		BuildingDataResponse orig = list.get(0);

		Specifications.givenBuildingDelete(orig.getId(), Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expectResponseOk());

		BuildingInfo buildingInfo = buildingRepository.findById(orig.getId()).block();
		assertNull(buildingInfo);

		list.forEach(item -> buildingRepository.deleteById(item.getId()).block());
	}

	@Test
	void createBuildings_withBadAccessToken_errorTest() {
		stubForJwks();
		stubForGeoData("Helsinki");

		String input = Specifications.readFile("Helsinki_request.json");
		Specifications.givenCreateBuildingsPost(input, Specifications.BAD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expect(500));
	}

	@Test
	void createBuildings_withWrongRequest_errorTest() {
		stubForJwks();

		String input = Specifications.readFile("Oulu_wrong_request.json");
		Specifications.givenCreateBuildingsPost(input, Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expect(400, BAD_REQUEST.name()));
	}

	@Test
	void createBuildings_geoFailed_errorTest() {
		stubForJwks();
		stubForGeoDataWithStatus(400);

		String input = Specifications.readFile("Helsinki_request.json");
		Specifications.givenCreateBuildingsPost(input, Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expect(500, INTERNAL_SERVER_ERROR.name()));
	}

	@Test
	void searchWrongSortBy_failedTest() {
		stubForJwks();

		Specifications.givenSearchBuildingsGet(null, null, 5,
						null, "Oulu", null, null, null, "wrong", null)
				.then()
				.spec(Specifications.expect(400, BAD_REQUEST.name()));
	}
}
