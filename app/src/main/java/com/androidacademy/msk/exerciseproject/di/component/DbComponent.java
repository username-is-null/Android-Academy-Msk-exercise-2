package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.DbModule;
import com.androidacademy.msk.exerciseproject.di.module.NetworkModule;
import com.androidacademy.msk.exerciseproject.di.module.NewsItemIdModule;
import com.androidacademy.msk.exerciseproject.di.scope.DbScope;

import dagger.Subcomponent;

@Subcomponent(modules = {DbModule.class})
@DbScope
public interface DbComponent {

    NewsItemComponent plusNewsItemComponent(NewsItemIdModule module);

    DbAndNetworkComponent plusNetworkComponent(NetworkModule module);
}
