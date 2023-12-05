//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class CustomException extends Exception {
    private final ErrorType errorType;

    CustomException(ErrorType errorType) {
        this.errorType = errorType;
    }

    ErrorType getErrorType() {
        return this.errorType;
    }

    static enum ErrorType {
        inputMatrixMessage,
        outOfRangeMessage,
        alreadyFoundMessage,
        differentCoordinateMessage;

        private ErrorType() {
        }
    }
}
