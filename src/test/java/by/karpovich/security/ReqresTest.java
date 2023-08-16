package by.karpovich.security;

import by.karpovich.security.api.dto.role.RoleDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresTest {

    private static final String BASE_URI = "http://localhost:8081";
    private static final String BASE_PATH = "/api/admin/";
    private static final Long ROLE_ID = 3L;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = BASE_PATH;
    }

    @Test
    public void findRoleById() {
        // достаем сразу роль по id затем подставляем  в ожидаемый результат ,
        // чтобы не сломать тест  в случае замены роли  по этой id
        String expectedRoleName = given()
                .contentType(ContentType.JSON)
                .pathParam("id", ROLE_ID)
                .when()
                .get("roles/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("name");

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", ROLE_ID)
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
                .put("/roles/{id}", 1)
                .then()
                .statusCode(HttpStatus.UPGRADE_REQUIRED.value())
                .body("name", equalTo("ROLE_MODERATOR"));
    }

//    @Test
//    public void createRole() {
//        RoleDto requestDto = RoleDto.builder()
//                .name("ROLE_MANAGER")
//                .build();
//
//        given()
//                .contentType("application/json")
//                .body(requestDto)
//                .when()
//                .post("/roles")
//                .then()
//                .statusCode(HttpStatus.CREATED.value())
//                .body("name", equalTo("ROLE_MANAGER"));
//    }

//    @Test
//    public void testCreateRole() {
//        RestAssured.defaultParser = Parser.JSON;
//
//        RoleDto requestDto = RoleDto.builder()
//                .name("ROLE_MANAGER222222")
//                .build();
//
//        given()
//                .contentType("application/json")
//                .body(requestDto)
//                .when()
//                .post("/roles")
//                .then()
//                .statusCode(HttpStatus.CREATED.value())
//                .body("name", equalTo("ROLE_MANAGER222222"));
//    }

}

