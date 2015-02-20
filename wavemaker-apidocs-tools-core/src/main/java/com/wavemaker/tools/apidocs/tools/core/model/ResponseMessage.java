/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.model;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
public class ResponseMessage {

    private long responseMessageId;
    private String code;
    private String message;

    public ResponseMessage(final long responseMessageId, final String code, final String message) {
        this.responseMessageId = responseMessageId;
        this.code = code;
        this.message = message;
    }

    public ResponseMessage() {
    }

    public long getResponseMessageId() {
        return responseMessageId;
    }

    public void setResponseMessageId(final long responseMessageId) {
        this.responseMessageId = responseMessageId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "responseMessageId=" + responseMessageId +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
