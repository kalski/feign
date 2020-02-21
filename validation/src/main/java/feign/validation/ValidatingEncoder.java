package feign.validation;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import java.lang.reflect.Type;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

// TODO: Document and add tests
public class ValidatingEncoder implements Encoder {

  private final Encoder encoder;
  private final Validator validator;

  public ValidatingEncoder() {
    this(Validation.buildDefaultValidatorFactory().getValidator());
  }

  public ValidatingEncoder(Validator validator) {
    this(new Encoder.Default(), validator);
  }

  public ValidatingEncoder(Encoder encoder, Validator validator) {
    this.encoder = encoder;
    this.validator = validator;
  }

  @Override
  public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {

    Set<ConstraintViolation<Object>> violations = validator.validate(object);

    if (violations.isEmpty()) {
      encoder.encode(object, bodyType, template);

      return;
    }

    throw new EncodeException("Request validation failed", new ConstraintViolationException(violations));
  }
}
