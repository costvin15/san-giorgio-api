package com.desafio.san_giorgio_api.extension;

import java.util.UUID;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public class LocalStackExtension implements BeforeAllCallback, AfterAllCallback {
    @Container
    static LocalStackContainer localStack = new LocalStackContainer(
        DockerImageName.parse("localstack/localstack:3.0")
    );

    static final String PARTIAL_QUEUE_NAME = UUID.randomUUID().toString();
    static final String TOTAL_QUEUE_NAME = UUID.randomUUID().toString();
    static final String EXCEDEED_QUEUE_NAME = UUID.randomUUID().toString();

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!localStack.isRunning()) {
            localStack.start();
        }

        localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", PARTIAL_QUEUE_NAME);
        localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", TOTAL_QUEUE_NAME);
        localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", EXCEDEED_QUEUE_NAME);

        System.setProperty("aws.endpoint.url", localStack.getEndpointOverride(Service.SQS).toString());
        System.setProperty("aws.accesskey.id", localStack.getAccessKey());
        System.setProperty("aws.accesskey.secret", localStack.getSecretKey());
        System.setProperty("aws.region", localStack.getRegion());
        System.setProperty("aws.queues.partial.name", PARTIAL_QUEUE_NAME);
        System.setProperty("aws.queues.total.name", TOTAL_QUEUE_NAME);
        System.setProperty("aws.queues.excedeed.name", EXCEDEED_QUEUE_NAME);
    }
}
