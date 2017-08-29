package com.meetferrytan.bakingapp.data.component;

import com.meetferrytan.bakingapp.data.util.ActivityScope;

import dagger.Component;

/**
 * Created by ferrytan on 7/29/17.
 */

@ActivityScope
@Component(dependencies = NetworkComponent.class)
public interface ActivityInjectorComponent {

}
