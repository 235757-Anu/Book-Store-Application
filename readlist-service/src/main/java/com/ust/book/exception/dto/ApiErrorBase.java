package com.ust.book.exception.dto;


public sealed class ApiErrorBase permits ApiError, ApiValidationError {
}