/*
 *
 *  ******************************************************************
 *  *  * Copyright (C) 2022
 *  *  * LogsViewModel.kt is part of Kizzy
 *  *  *  and can not be copied and/or distributed without the express
 *  *  * permission of yzziK(Vaibhav)
 *  *  *****************************************************************
 *
 *
 */

package com.my.kizzy.ui.screen.settings.logs

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.my.kizzy.domain.model.LogEvent
import com.my.kizzy.preference.Prefs
import com.my.kizzy.utils.Log.logger

class LogsViewModel: ViewModel() {
    val filterStrings = mutableStateOf("")
    var logs = logger.getLogs()
    var showCompat = mutableStateOf(Prefs[Prefs.SHOW_LOGS_IN_COMPACT_MODE,false])
    val isSearchBarVisible = mutableStateOf(false)


    fun filter(): List<LogEvent> {
        return try {
            logs.matches(filterStrings.value).toMutableStateList()
        } catch (ex: ConcurrentModificationException){
            logs
        }
    }

    private fun List<LogEvent>.matches(filter: String) = filter {
        it.matches(filter)
    }

    private fun LogEvent.matches(filter: String): Boolean {
        return this.text.contains(filter,ignoreCase = true) || this.tag.contains(filter,ignoreCase = true)
    }
}