package shopping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductStepDefinitions {

    @Autowired
    private AcceptanceTestContext context;

    @Given("a product exists with name {string} price {long} and imageUrl {string}")
    public void aProductExistsWithNamePriceAndImageUrl(String name, long price, String imageUrl) {
        var response = RestAssured.given().spec(context.spec()).contentType(ContentType.JSON)
                .body("{\"name\":\"" + name + "\",\"price\":" + price + ",\"imageUrl\":\""
                        + imageUrl + "\"}")
                .when().post("/api/products").then().statusCode(201).extract();
        context.setCreatedProductId(response.jsonPath().getLong("id"));
    }

    @When("I create a product with name {string} price {long} and imageUrl {string}")
    public void iCreateAProductWithNamePriceAndImageUrl(String name, long price, String imageUrl) {
        context.setResponse(RestAssured.given().spec(context.documentSpec("product-create"))
                .filter(document("product-create")).contentType(ContentType.JSON)
                .body("{\"name\":\"" + name + "\",\"price\":" + price + ",\"imageUrl\":\""
                        + imageUrl + "\"}")
                .when().post("/api/products").then().extract());
        Long id = context.getResponse().jsonPath().getLong("id");
        context.setCreatedProductId(id);
    }

    @When("I find the product by id")
    public void iFindTheProductById() {
        context.setResponse(RestAssured.given().spec(context.documentSpec("product-find"))
                .filter(document("product-find")).when()
                .get("/api/products/{id}", context.getCreatedProductId()).then().extract());
    }

    @When("I update the product with name {string} price {long} and imageUrl {string}")
    public void iUpdateTheProductWithNamePriceAndImageUrl(String name, long price,
            String imageUrl) {
        context.setResponse(RestAssured.given().spec(context.documentSpec("product-update"))
                .filter(document("product-update")).contentType(ContentType.JSON)
                .body("{\"name\":\"" + name + "\",\"price\":" + price + ",\"imageUrl\":\""
                        + imageUrl + "\"}")
                .when().put("/api/products/{id}", context.getCreatedProductId()).then().extract());
    }

    @When("I delete the product")
    public void iDeleteTheProduct() {
        context.setResponse(RestAssured.given().spec(context.documentSpec("product-delete"))
                .filter(document("product-delete")).when()
                .delete("/api/products/{id}", context.getCreatedProductId()).then().extract());
    }

    @When("I find all products")
    public void iFindAllProducts() {
        context.setResponse(RestAssured.given().spec(context.documentSpec("product-find-all"))
                .filter(document("product-find-all")).when().get("/api/products").then().extract());
    }

    @And("the product response should have name {string}")
    public void theProductResponseShouldHaveName(String name) {
        assertThat(context.getResponse().jsonPath().getString("name")).isEqualTo(name);
    }
}
