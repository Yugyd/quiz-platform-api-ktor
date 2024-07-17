#!/bin/bash
set -eo pipefail

# https://yandex.cloud/ru/docs/iam/operations/iam-token/create#api_1
curl -X POST \
  -d '{"yandexPassportOauthToken":"REPLACE_ME"}' \
  https://iam.api.cloud.yandex.net/iam/v1/tokens
