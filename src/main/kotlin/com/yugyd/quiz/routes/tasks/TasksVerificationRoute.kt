package com.yugyd.quiz.routes.tasks

import com.yugyd.quiz.data.themes.AiInitialException
import com.yugyd.quiz.domain.ai.exceptions.InvalidAiCredentialException
import com.yugyd.quiz.domain.ai.factory.AiKeysFactory
import com.yugyd.quiz.domain.ai.mappers.AiHeaderMapper
import com.yugyd.quiz.domain.content.models.ContentModel
import com.yugyd.quiz.domain.tasks.models.verification.TasksVerificationRequestModel
import com.yugyd.quiz.domain.tasks.service.TasksService
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.koin.mp.KoinPlatform.getKoin

internal fun Route.postTasksVerification(tasksService: TasksService) {
    install(RequestValidation) {
        validate<TasksVerificationRequestModel> { taskVerification ->
            when {
                taskVerification.quest.isBlank() -> {
                    ValidationResult.Invalid("Quest should not be empty")
                }

                taskVerification.userAnswer.isBlank() -> {
                    ValidationResult.Invalid("User answer should not be empty")
                }

                taskVerification.trueAnswer.isBlank() -> {
                    ValidationResult.Invalid("True answer should not be empty")
                }

                else -> {
                    ValidationResult.Valid
                }
            }
        }
    }

    post(path = "/verification") {
        try {
            val themeRequest = call.receive<TasksVerificationRequestModel>()

            val aiHeaderMapper = getKoin().get<AiHeaderMapper>()
            val aiAuthModel = aiHeaderMapper.map(call.request.headers)
            val aiKeysFactory = getKoin().get<AiKeysFactory>()
            val aiKeys = aiKeysFactory.create(aiAuthModel)

            if (aiKeys == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ai headers")
                return@post
            }

            val verificationModel = tasksService.verifyTask(
                content = ContentModel.DEFAULT,
                requestModel = themeRequest,
                aiKeys = aiKeys,
            )

            call.respond(HttpStatusCode.OK, verificationModel)
        } catch (error: AiInitialException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.BadRequest, error.message.orEmpty())
        } catch (error: InvalidAiCredentialException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.Unauthorized)
        } catch (error: ContentTransformationException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        } catch (error: IllegalArgumentException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        } catch (error: JsonConvertException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
