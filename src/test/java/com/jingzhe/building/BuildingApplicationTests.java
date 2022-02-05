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
import static org.junit.jupiter.api.Assertions.*;
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
						null, "Oulu", null, null, null)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);
		System.out.println(foundList1);
		assertEquals(1, foundList1.size());


		List<BuildingDataResponse> foundList2 = Specifications.givenSearchBuildingsGet(null, null, null,
						null, "Oulu", null, null, null)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);
		assertEquals(6, foundList2.size());

		List<BuildingDataResponse> foundList3 = Specifications.givenSearchBuildingsGet(null, null, null,
						null, "Oulu", null, 3, 2)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.jsonPath().getList(".", BuildingDataResponse.class);
		assertEquals(3, foundList3.size());


		list.forEach(item -> buildingRepository.deleteById(item.getId()).block());
	}

	@Test
	void updateHelsinkiBuildings_withGoodAccessToken_succeedTest() {
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
		String updatedInput = Specifications.readFile("Helsinki_update_request.json");
		BuildingDataResponse updated = Specifications.givenUpdateBuildingPut(orig.getId(), updatedInput, Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expectResponseOk())
				.extract()
				.as(BuildingDataResponse.class);

		assertEquals("Isokatu", updated.getStreet());
		assertNotEquals(orig.getLatitude(), updated.getLatitude());
		assertNotEquals(orig.getLongitude(), updated.getLongitude());

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
	void createBuildings_geoFailed_errorTest() {
		stubForJwks();
		stubForGeoDataWithStatus(400);

		String input = Specifications.readFile("Helsinki_request.json");
		Specifications.givenCreateBuildingsPost(input, Specifications.GOOD_ACCESS_TOKEN)
				.then()
				.spec(Specifications.expect(500, INTERNAL_SERVER_ERROR.name()));
	}

}
