package uk.co.avsoftware.core.mvi

interface MviAction
interface MviCommand
interface MviState
interface MviViewEffect
interface MviViewEvent
interface MviViewModel<Action : MviAction> {
    fun sendAction(action: Action)
}
interface MviStateReducer<ViewState, Effect : MviViewEffect> {

    fun reduce(state: ViewState, effect: Effect): ViewState
}
