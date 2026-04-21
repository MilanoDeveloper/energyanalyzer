package br.com.fiap.energyanalyzer.exception;

public class ApiError {
  private final String error;
  private final String message;

  public ApiError(String error, String message) {
    this.error = error;
    this.message = message;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }
}
