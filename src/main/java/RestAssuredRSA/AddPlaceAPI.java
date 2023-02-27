package RestAssuredRSA;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static RestAssuredRSA.Payload.AddPlace;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class AddPlaceAPI {
    public static void main(String[] args) {
        //Validate if add place API is working fine.

        //given - all input details
        //when - submit the API - resource, http method
        //then - validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(AddPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println("Response : " + response);
        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");

        System.out.println(placeId);

        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\"70 Summer walk, USA\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("maps/api/place/update/json")
                .then().assertThat().statusCode(200).body("msg",equalTo("Address successfully completed"));
    }

}
