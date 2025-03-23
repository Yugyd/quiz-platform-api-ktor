package com.yugyd.quiz.data.ai.prompts.strategy.themes

import com.yugyd.quiz.domain.ai.prompt.ThemePromptStrategy
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel

internal class DefaultThemePromptStrategy : ThemePromptStrategy {

    override suspend fun getPrompt(theme: ThemeDetailModel): String {
        val template = """
        Imagine that you are a teacher. You need to prepare a lesson on the topic.

        Topic title: ${theme.name}
        Topic description: "${theme.description}". In the "Topic description", the block "THEORY BLOCK" contains theory. In the description, the block "PRACTICE BLOCK" contains practice.

        Generate theme in markdown format. The content must contain references to sources. The content must not contain the tags "THEORY BLOCK" and "PRACTICE BLOCK"

        Sample. The content in markdown format:

        ""${'"'}
        // Start sample
        Sample topic
        =====

        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus.

        # Contributions

        [Guide](docs/CONTRIBUTION.md)

        # Build

        ## Build debug

        - `./gradlew clean assembleDevDebug`

        ## Code integration

        * Switch the `IS_BASED_ON_PLATFORM_APP` property to `true` in the [build.gradle](app/build.gradle)
          file.

        # Code snippet

        ```kotlin
        fun main() {
            println("Hello, World!")
        }
        ```
        // End sample
        ""${'"'}
        """
        return template.trimIndent()
    }
}
