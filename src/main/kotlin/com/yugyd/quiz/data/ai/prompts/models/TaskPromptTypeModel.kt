package com.yugyd.quiz.data.ai.prompts.models

internal enum class TaskPromptTypeModel(val identifier: String) {
    CUSTOM_NAME_AND_CONTENT(identifier = "custom_name_and_content"),
    DEFAULT(identifier = "default"),
    LITE(identifier = "lite");
}
