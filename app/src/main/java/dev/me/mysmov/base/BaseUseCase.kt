package dev.me.mysmov.base

interface BaseUseCase<param : Param, result : UseCaseResult> {
    fun execute(param: param): result
}

interface Param
interface UseCaseResult
