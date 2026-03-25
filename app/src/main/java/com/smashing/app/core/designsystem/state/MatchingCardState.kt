package com.smashing.app.core.designsystem.state

import androidx.compose.runtime.Stable
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType

/**
 * 매칭 카드 UI를 구성하기 위한 상태 모델입니다.
 *
 * [MatchingCardState]는 카드에 표시되는 데이터와 사용자 액션을 함께 보유하며,
 * 상태 타입에 따라 카드의 UI 구성과 가능한 인터랙션이 결정됩니다.
 *
 * ### 상태별 역할
 * - [Search]  : 전적/리뷰 정보를 포함한 탐색 상태
 * - [Send]    : 전적 정보와 함께 닫기(X) 액션이 가능한 요청 전송 상태
 * - [Receive] : 전적 정보와 수락/건너뛰기 액션을 제공하는 요청 수신 상태
 * - [Confirm] : 닫기(X), 카카오 링크, 결과 작성 액션을 제공하는 확정 상태
 *
 * ### 공통 규칙
 * - 모든 상태는 프로필 영역을 가지며, 프로필 클릭 시 [onProfileClick]이 호출됩니다.
 * - [Closable]을 구현한 상태만 닫기(X) 버튼을 노출합니다.
 * - [HasRecord]를 구현한 상태만 전적/리뷰 정보를 노출합니다.
 *
 * 이 구조를 통해 UI 레이어에서는 상태 타입에 따라 안전하게 분기 처리할 수 있습니다.
 */

@Stable
sealed interface MatchingCardState {
    val profileId: String
    val nickname: String
    val genderType: GenderType
    val tierType: TierType
    val onProfileClick: () -> Unit

    sealed interface Closable : MatchingCardState {
        val onCloseClick: () -> Unit
    }

    interface HasRecord : MatchingCardState {
        val winCount: Int
        val loseCount: Int
        val reviewCount: Long
    }

    @Stable
    data class Search(
        override val profileId: String,
        override val nickname: String,
        override val genderType: GenderType,
        override val tierType: TierType,
        override val onProfileClick: () -> Unit,
        override val winCount: Int,
        override val loseCount: Int,
        override val reviewCount: Long,
    ) : HasRecord

    @Stable
    data class Send(
        override val profileId: String,
        override val nickname: String,
        override val genderType: GenderType,
        override val tierType: TierType,
        override val onProfileClick: () -> Unit,
        override val onCloseClick: (() -> Unit),
        override val winCount: Int,
        override val loseCount: Int,
        override val reviewCount: Long,
    ) : HasRecord, Closable

    @Stable
    data class Receive(
        override val profileId: String,
        override val nickname: String,
        override val genderType: GenderType,
        override val tierType: TierType,
        override val onProfileClick: () -> Unit,
        override val winCount: Int,
        override val loseCount: Int,
        override val reviewCount: Long,
        val onSkipClick: () -> Unit,
        val onAcceptClick: () -> Unit,
    ) : HasRecord

    @Stable
    data class Confirm(
        override val profileId: String,
        override val nickname: String,
        override val genderType: GenderType,
        override val tierType: TierType,
        override val onProfileClick: () -> Unit,
        override val onCloseClick: (() -> Unit),
        val gameStatusType: GameResultStatusType,
        val onKakaoLinkClick: () -> Unit,
        val onConfirmClick: () -> Unit,
    ) : Closable
}
