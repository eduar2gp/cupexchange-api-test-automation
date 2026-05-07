package util;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.qameta.allure.Attachment;

public class AllureLoggingFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        Response response = ctx.next(requestSpec, responseSpec);

        // Log to Allure as an attachment for every request
        logTime(requestSpec.getURI(), response.getTime());

        return response;
    }

    @Attachment(value = "Latency: {uri}", type = "text/plain")
    public String logTime(String uri, long time) {
        return "Endpoint: " + uri + "\nResponse Time: " + time + " ms";
    }
}
