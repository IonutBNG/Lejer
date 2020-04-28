package inventory.repository;

/**
 * @author Bungardean Tudor-Ionut
 */

public class RepositoryException extends Exception {
    private String message;
    public RepositoryException(String message){
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
