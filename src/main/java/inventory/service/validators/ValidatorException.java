package inventory.service.validators;

public class ValidatorException extends Exception {
    private String message;
    public ValidatorException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
