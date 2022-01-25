/*
 *   Copyright 2022 Benoit LETONDOR
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.benoitletondor.pixelminimalwatchfacecompanion.storage

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

private const val SHARED_PREFERENCES_FILE_NAME = "sharedPref"

private const val PREMIUM_KEY = "premium"
private const val ONBOARDING_FINISHED_KEY = "onboarding_finished"
private const val BATTERY_SYNC_ACTIVATED = "onboarding_finished"

class StorageImpl @Inject constructor(@ApplicationContext context: Context) : Storage {
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    private val batterySyncActivatedMutableFlow = MutableStateFlow(isBatterySyncActivated())

    override fun isUserPremium(): Boolean
        = sharedPreferences.getBoolean(PREMIUM_KEY, false)

    override fun setUserPremium(premium: Boolean) {
        sharedPreferences.edit {
            putBoolean(PREMIUM_KEY, premium)
        }
    }

    override fun setOnboardingFinished(finished: Boolean) {
        sharedPreferences.edit {
            putBoolean(ONBOARDING_FINISHED_KEY, finished)
        }
    }

    override fun isOnboardingFinished(): Boolean
        = sharedPreferences.getBoolean(ONBOARDING_FINISHED_KEY, false)

    override fun isBatterySyncActivatedFlow(): Flow<Boolean> = batterySyncActivatedMutableFlow

    override fun isBatterySyncActivated(): Boolean
        = sharedPreferences.getBoolean(BATTERY_SYNC_ACTIVATED, false)

    override fun setBatterySyncActivated(activated: Boolean) {
        sharedPreferences.edit {
            putBoolean(BATTERY_SYNC_ACTIVATED, activated)
        }

        batterySyncActivatedMutableFlow.value = activated
    }

}