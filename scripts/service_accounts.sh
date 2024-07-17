#!/bin/bash
set -eo pipefail

# https://yandex.cloud/ru/docs/iam/operations/sa/get-id
export FOLDER_ID=REPLACE_ME
export IAM_TOKEN=REPLACE_ME
curl -H "Authorization: Bearer ${IAM_TOKEN}" \
  "https://iam.api.cloud.yandex.net/iam/v1/serviceAccounts?folderId=${FOLDER_ID}"
