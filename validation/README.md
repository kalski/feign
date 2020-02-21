Feign Validation
===================

This module adds support for validating request body.

Add `ValidationEncoder` to your `Feign.Builder` like so:

```java
GitHub github = Feign.builder()
                     .encoder(new ValidationEncoder())
                     .target(GitHub.class, "https://api.github.com");
```

Now if your request body has declared constraints (e.g. `@NotNull`), they will be validated against.
