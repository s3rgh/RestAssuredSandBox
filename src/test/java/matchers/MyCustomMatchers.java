package matchers;

import io.restassured.response.Response;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class MyCustomMatchers extends TypeSafeMatcher<Response> {

    @Override
    protected boolean matchesSafely(Response item) {
        return false;
    }

    @Override
    public void describeTo(Description description) {

    }
}
