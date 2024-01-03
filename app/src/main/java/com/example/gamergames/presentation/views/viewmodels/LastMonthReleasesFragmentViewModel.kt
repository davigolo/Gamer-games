package com.example.gamergames.presentation.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.RawgGameBO
import com.example.gamergames.domain.usecases.rawg.SearchGamesByDateUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class LastMonthReleasesFragmentViewModel : ViewModel() {

    private val getGamesByDateUseCaseImpl by lazy { SearchGamesByDateUseCaseImpl() }
    private val gameListByDateLiveData: MutableLiveData<AsyncResult<List<RawgGameBO>>> = MutableLiveData()
    private val isDataLoadedLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getDataLoadingState() = isDataLoadedLiveData as LiveData<Boolean>
    fun getGameListByDate() = gameListByDateLiveData as LiveData<AsyncResult<List<RawgGameBO>>>
    fun requestGamesByDate(dateInterval: String, orderBy: String) {
        viewModelScope.launch(Dispatchers.IO) {

            getGamesByDateUseCaseImpl.searchGamesByDateUseCase(
                dateInterval,
                orderBy,
                viewModelScope
            ) { gameList ->
                gameListByDateLiveData.postValue(gameList)
                isDataLoadedLiveData.postValue(true)
            }
        }
    }

    fun requestLastMonthDateInterval(): String {

        val calendarInstance = Calendar.getInstance()
        val firstDate = calendarInstance.time
        calendarInstance.add(Calendar.MONTH, -1)
        val secondDate = calendarInstance.time

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = dateFormatter.format(firstDate)
        val lastThirtyDays = dateFormatter.format(secondDate)

        return "$lastThirtyDays,$currentDate"
    }

    fun requestWeeklyDateInterval(): String {

        val calendarInstance = Calendar.getInstance()
        val firstDate = calendarInstance.time
        calendarInstance.add(Calendar.DATE, -7)
        val secondDate = calendarInstance.time

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = dateFormatter.format(firstDate)
        val lastWeek = dateFormatter.format(secondDate)
        return "$lastWeek,$currentDate"
    }

    fun requestNextWeekDateInterval(): String {

        val calendarInstance = Calendar.getInstance()
        val firstDate = calendarInstance.time
        calendarInstance.add(Calendar.DATE, 7)
        val secondDate = calendarInstance.time

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = dateFormatter.format(firstDate)
        val nextWeek = dateFormatter.format(secondDate)
        return "$currentDate,$nextWeek"
    }

    fun requestCurrentDate(): String {

        val calendarInstance = Calendar.getInstance().time

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")

        return dateFormatter.format(calendarInstance)
    }

    fun requestYearInterval(): String {

        val calendarInstance = Calendar.getInstance()
        val firstDate = calendarInstance.time
        calendarInstance.add(Calendar.YEAR, -1)
        val secondDate = calendarInstance.time

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val currentYear = dateFormatter.format(firstDate)
        val previousYear = dateFormatter.format(secondDate)
        return "$previousYear,$currentYear"
    }
}