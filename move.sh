#!/bin/bash

AWS="/usr/local/bin/aws"

APPLICATION_NAME="craftdemo-app"

BUCKET_NAME="jlw-codingprojects"
FULL_BUCKET="s3://${BUCKET_NAME}/{APPLICATION_NAME}/"
SOURCE_FILE=$1

echo "Application Name: ${APPLICATION_NAME}"
echo "Full Bucket: ${FULL_BUCKET}"
echo "Source file: ${SOURCE_FILE}"

# Copies the dist to the S3 bucket
$AWS s3 cp ${SOURCE_FILE} ${FULL_BUCKET}

# Checks the result of the copy
if [ $? -eq 0 ]; then
    echo "Copy successful"
else
    echo "Copy failed!"
fi

# Finally, AWS CodePipeline automates deployment from S3 bucket to Elastic Beanstalk app