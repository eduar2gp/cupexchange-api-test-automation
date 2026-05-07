package util;

import cupexchange.BaseTest;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ExtentLoggingFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        Response response = ctx.next(requestSpec, responseSpec);

        if (BaseTest.test.get() != null) {
            String requestBody = requestSpec.getBody() != null ? requestSpec.getBody().toString() : "No Request Body";
            String responseBody = response.getBody().asPrettyString();

            // Log Request Details using .info()
            BaseTest.test.get().info("<b>Request URI:</b> " + requestSpec.getURI());
            BaseTest.test.get().info("<b>Request Method:</b> " + requestSpec.getMethod());

            // Use info() with HTML for the collapsible section
            BaseTest.test.get().info("<details><summary><b>View Request Payload (Click to expand)</b></summary><pre>"
                    + requestBody + "</pre></details>");

            // Log Response Details
            BaseTest.test.get().info("<b>Response Status:</b> " + response.getStatusCode());
            BaseTest.test.get().info("<details><summary><b>View Response Payload (Click to expand)</b></summary><pre>"
                    + responseBody + "</pre></details>");
        }

        return response;
    }
}