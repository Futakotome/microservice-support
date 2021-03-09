package io.futakotome.lock.impl;

public enum RetryPolicyEnum {
    EXPONENTIAL_BACKOFF_RETRY,

    BOUNDED_EXPONENTIAL_BACKOFF_RETRY,

    RETRY_NTIMES,

    RETRY_FOREVER,

    RETRY_UNTIL_ELAPSED;

}
