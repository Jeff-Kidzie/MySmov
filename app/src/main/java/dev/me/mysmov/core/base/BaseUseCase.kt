package dev.me.mysmov.core.base

interface BaseUseCase<param : UseCaseParam, result : UseCaseResult> {
    suspend fun execute(param: param): result
}

interface UseCaseParam
interface UseCaseResult
