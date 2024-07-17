#!/bin/bash
set -eo pipefail

# https://yandex.cloud/ru/docs/iam/operations/api-key/create
export SERVICEACCOUNT_ID=REPLACE_ME
export IAM_TOKEN=REPLACE_ME
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $IAM_TOKEN" \
  -d "{ \"serviceAccountId\": \"$SERVICEACCOUNT_ID\" }" \
  https://iam.api.cloud.yandex.net/iam/v1/apiKeys
