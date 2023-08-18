package by.karpovich.security;

import by.karpovich.security.api.dto.role.RoleDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresTest {

    private static final String BASE_URI = "http://localhost:8081";
    private static final String BASE_PATH = "/api/admin";

    private static   Long roleId;

    public static void setRoleId(Long roleId) {
        ReqresTest.roleId = roleId;
    }

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = BASE_PATH;
    }

    @Test
    @Order(1)
    public void testCreateRole() {
        RoleDto requestDto = RoleDto.builder()
                .name("Test Role")
                .build();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post("/roles")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response();

        setRoleId(response.jsonPath().getLong("id"));
    }

    @Test
    public void findRoleById() {
        String expectedRoleName = given()
                .contentType(ContentType.JSON)
                .pathParam("id", roleId)
                .when()
                .get("/roles/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("name");

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", roleId)
                .when()
                .get("roles/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(expectedRoleName));
    }


    @Test
    public void findAllRoles() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/roles")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", not(empty()))
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);


    }

    @Test
    public void updateRole() {
        RoleDto requestDto = RoleDto.builder()
                .name("ROLE_MODERATOR")
                .build();

        given()
                .contentType("application/json")
                .body(requestDto)
                .when()
                .put("/roles/{id}", roleId)
                .then()
                .statusCode(HttpStatus.UPGRADE_REQUIRED.value())
                .body("name", equalTo("ROLE_MODERATOR"));
    }


    @Test
    public void testDeleteRoleById() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/roles/{id}", roleId)
                .then()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/roles/{id}", roleId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


}

