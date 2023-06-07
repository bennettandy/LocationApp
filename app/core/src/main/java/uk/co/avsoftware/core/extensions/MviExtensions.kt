package uk.co.avsoftware.core.extensions

import androidx.lifecycle.SavedStateHandle
import uk.co.avsoftware.core.mvi.MviState

typealias Reducer<ACTION, STATE, COMMAND> = (ACTION, STATE) -> Pair<STATE, COMMAND?>

private fun getMviStateKey(applicationId: String) = "$applicationId.MVI_STATE"

fun <S : MviState> SavedStateHandle.getMviState(applicationId: String): S? =
    get<S>(getMviStateKey(applicationId))

fun <S : MviState> SavedStateHandle.setMviState(applicationId: String, state: S) =
    set(getMviStateKey(applicationId), state)
