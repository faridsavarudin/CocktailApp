package dev.genci.test.di

import android.content.Context
import androidx.room.Room
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.genci.test.data.local.AppDatabase
import dev.genci.test.data.local.dao.CocktailDao
import dev.genci.test.data.network.ApiService
import dev.genci.test.data.repository.CocktailRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(OkHttpProfilerInterceptor())
            .build()
    }

    @Provides
    fun provideApi(client: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "cocktail_db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideDao(db: AppDatabase): CocktailDao = db.cocktailDao()

    @Provides
    fun provideRepository(api: ApiService, dao: CocktailDao) = CocktailRepository(api, dao)
}