package com.kurt.auth.framework.util.AwsParameterStore;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

public class AwsParameterStore {
    private static final Region DEFAULT_REGION = Region.AP_NORTHEAST_2; // 서울리젼

    public static String getParameter(String parameterName) {
        // SsmClient를 리전을 포함하여 빌더로 생성
        try (SsmClient ssmClient = SsmClient.builder()
                .region(DEFAULT_REGION)
                .build()) {

            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                    .name(parameterName)
                    .withDecryption(true)
                    .build();

            GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
            return parameterResponse.parameter().value();
        }
    }
}
