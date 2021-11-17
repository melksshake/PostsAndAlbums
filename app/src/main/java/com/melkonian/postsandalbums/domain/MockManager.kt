package com.melkonian.postsandalbums.domain

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class MockManager @Inject constructor() {
//    var isMockEnabled = true
    var isMockEnabled = false
}