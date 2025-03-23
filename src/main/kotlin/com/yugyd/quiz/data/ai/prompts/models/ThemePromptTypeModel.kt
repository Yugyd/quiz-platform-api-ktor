package com.yugyd.quiz.data.ai.prompts.models

internal enum class ThemePromptTypeModel(val identifier: String) {
    CUSTOM_NAME(identifier = "custom_name"),
    CUSTOM_NAME_AND_DESCRIPTION(identifier = "custom_name_and_description"),
    DEFAULT(identifier = "default"),
    LITE(identifier = "lite");
}
