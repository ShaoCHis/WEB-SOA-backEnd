package sebackend.error;

public interface ServiceError {
    String getMessage();

    Integer getCode();
}