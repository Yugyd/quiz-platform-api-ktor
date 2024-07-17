package com.yugyd.app

import com.yugyd.core.coroutines.coroutinesCoreModule
import com.yugyd.core.database.databaseCoreModule
import com.yugyd.core.serializationCoreModule
import com.yugyd.core.toggles.togglesCoreModule
import com.yugyd.data.ai.aiDataModule
import com.yugyd.data.ai.prompts.promptModule
import com.yugyd.data.ai.yandex.yandexGptModule
import com.yugyd.data.themes.themesDataModule
import com.yugyd.domain.ai.aiDomainModule
import com.yugyd.domain.theme.themesDomainModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

internal fun Application.configureDi() {
    install(Koin) {
        modules(
            serializationCoreModule,
            coroutinesCoreModule,
            togglesCoreModule,
            databaseCoreModule,
            themesDataModule,
            themesDomainModule,
            promptModule,
            yandexGptModule,
            aiDataModule,
            aiDomainModule,
        )
    }
}
