package com.smashing.app.core.extension

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.then
import java.text.BreakIterator

/**
 * 문자열의 글자수를 반환합니다.
 *
 * 이모지, 결합 문자 등을 하나의 문자로 계산하여
 * 사용자가 인식하는 실제 문자 개수를 반환합니다.
 *
 * @return 글자 카운트
 */
fun String.checkLength(): Int {
    if (isEmpty()) return 0

    val iterator = BreakIterator.getCharacterInstance()
    iterator.setText(this)

    var count = 0
    while (iterator.next() != BreakIterator.DONE)
        count++

    return count
}


/**
 * 입력된 문자열의 최대 글자수를 제한합니다.
 *
 * 이모지, 결합 문자 등을 하나의 문자로 계산하여
 * maxLength를 넘을 경우 `revertAllChanges()`를 호출하여 입력을 취소합니다.
 *
 * @param maxLength 최대 글자수
 */

fun InputTransformation.checkMaxLength(maxLength: Int): InputTransformation =
    this.then(CheckMaxLength(maxLength))

private class CheckMaxLength(
    private val maxLength: Int
) : InputTransformation {

    override fun TextFieldBuffer.transformInput() {
        val length = asCharSequence().toString().checkLength()

        if (length > maxLength) {
            revertAllChanges()
        }
    }
}
