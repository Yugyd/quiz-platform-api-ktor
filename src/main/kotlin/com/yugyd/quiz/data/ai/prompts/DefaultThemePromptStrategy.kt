package com.yugyd.quiz.data.ai.prompts

import com.yugyd.quiz.domain.ai.prompt.ThemePromptStrategy
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel

internal class DefaultThemePromptStrategy : ThemePromptStrategy {

    override fun getPrompt(theme: ThemeDetailModel): String {
        val template = """
        Imagine that you are a teacher. You need to prepare a lesson on the topic. The topic must contain a title, a
        brief description and content. The content should be divided into points. The content should contain examples.
        The content must contain references to sources. Generate theme in markdown format.
        
        The markdown content should be on the topic below.
        Topic Title: ${theme.name}
        Topic Short Description: ${theme.description}
        
        Sample. Custom theme in markdown format:
        Topic
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
        """

        return template.trimIndent()
    }
}
