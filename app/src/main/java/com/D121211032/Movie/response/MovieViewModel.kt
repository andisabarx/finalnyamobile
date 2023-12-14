package com.D121211032.Movie.response

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.D121211032.Movie.core.Resource
import com.D121211032.Movie.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MovieViewModel(private val repo: MovieRepository): ViewModel() {

    fun fetchMainScreenMovies() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(Triple(repo.getUpcomingMovies(), repo.getTopRatedMovies(), repo.getPopularMovies())))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }

}

class MovieViewModelFactory(private val repo: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }

}