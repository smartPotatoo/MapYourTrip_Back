package com.smartpotatoo.map_your_trip_be.common.exception;

import com.smartpotatoo.map_your_trip_be.common.error.ErrorCodeIfs;

public interface ApiExceptionItf{
    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
