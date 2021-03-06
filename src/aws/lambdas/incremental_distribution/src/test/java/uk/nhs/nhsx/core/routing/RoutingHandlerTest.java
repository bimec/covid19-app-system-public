package uk.nhs.nhsx.core.routing;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.jupiter.api.Test;
import uk.nhs.nhsx.testhelper.ContextBuilder;
import uk.nhs.nhsx.core.HttpResponses;

import java.util.HashMap;
import java.util.Optional;

import static com.google.common.collect.Maps.newHashMap;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoutingHandlerTest {

    @Test
    public void noHeaders() throws Exception {
        assertThrows(RuntimeException.class, () ->
                new MyRoutingHandler("content-type").handleRequest(new APIGatewayProxyRequestEvent(), new ContextBuilder.TestContext())
        );
    }

    @Test
    public void lowercase() throws Exception {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        HashMap<String, String> headers = newHashMap();
        headers.put("Content-Type", "something");
        request.setHeaders(headers);
        new MyRoutingHandler("content-type").handleRequest(request, new ContextBuilder.TestContext());
    }

    @Test
    public void uppercase() throws Exception {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        HashMap<String, String> headers = newHashMap();
        headers.put("content-type", "something");
        request.setHeaders(headers);
        new MyRoutingHandler("Content-Type").handleRequest(request, new ContextBuilder.TestContext());
    }

    private static class MyRoutingHandler extends RoutingHandler {

        public final String header;

        private MyRoutingHandler(String header) {
            this.header = header;
        }

        @Override
        public Routing.Handler handler() {
            return request -> Optional.ofNullable(request.getHeaders().get(header))
                    .map(h -> HttpResponses.ok())
                    .orElseThrow(() -> new RuntimeException("not there"));
        }
    }
}