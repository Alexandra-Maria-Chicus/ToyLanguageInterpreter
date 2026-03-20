package exception;

public class VariableNotDefinedExpression extends RuntimeException {
    public VariableNotDefinedExpression(String message) {
        super(message);
    }
}
