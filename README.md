# Context Caching

Example of problem with how Spring context caching affects subsequent integration tests.

This is the story of two integration tests, ran in this order:

1. First (duh)
2. Second (duh again)

The second integration test is set up _slightly differently_ than the first, having the following field cause havoc:

    // this field causes havoc
    @MockBean
    private SomeOtherComponent someOtherComponent;

The second integration test uses `MockRestServiceServer` to preempt the `RestTemplate`. As log statements show, the
second integration test gets a _new_ `RestTemplate` (relative to the first integration test), but once the second
integration test calls into the `MyService`, it will still use the `RestTemplate` from the first integration test (thus
having preempted the wrong `RestTemplate` instance).
