package com.example.skptemp.feature.signup.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TermsListModule {

    @Singleton
    @Provides
    fun provideTermsList(): MutableList<Terms> {
        return mutableListOf(
            Terms(MutableStateFlow(false), "만 14세 이상", true, "https://www.naver.com/"),
            Terms(MutableStateFlow(false), "서비스 이용약관", true, "https://www.naver.com/"),
            Terms(MutableStateFlow(false), "개인정보 처리 방침", true, "https://www.naver.com/"),
            Terms(MutableStateFlow(false), "커뮤니티 가이드 라인", true, "https://www.naver.com/")
            // TODO: landingUrl 노션 페이지로 수정
        )
    }
}