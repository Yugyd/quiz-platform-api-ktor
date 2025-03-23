package com.yugyd.quiz

import com.yugyd.quiz.core.coroutines.coroutinesCoreModule
import com.yugyd.quiz.core.database.databaseCoreModule
import com.yugyd.quiz.core.loggerCoreModule
import com.yugyd.quiz.core.serializationCoreModule
import com.yugyd.quiz.core.toggles.togglesCoreModule
import com.yugyd.quiz.data.ai.aiDataModule
import com.yugyd.quiz.data.ai.prompts.promptModule
import com.yugyd.quiz.data.ai.yandex.yandexGptModule
import com.yugyd.quiz.data.tasks.tasksDataModule
import com.yugyd.quiz.data.themes.themesDataModule
import com.yugyd.quiz.domain.ai.di.aiDomainModule
import com.yugyd.quiz.domain.tasks.di.tasksDomainModule
import com.yugyd.quiz.domain.theme.di.themesDomainModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

internal fun Application.configureDi() {
    install(Koin) {
        modules(
            serializationCoreModule,
            loggerCoreModule(environment),
            coroutinesCoreModule,
            togglesCoreModule,
            databaseCoreModule(environment.config),
            themesDataModule,
            themesDomainModule,
            tasksDataModule,
            tasksDomainModule,
            promptModule(environment.config),
            yandexGptModule,
            aiDataModule(environment.config),
            aiDomainModule,
        )
    }
}
