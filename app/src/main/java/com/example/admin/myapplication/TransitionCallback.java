/*
 * Copyright (C) 2019 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.admin.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Transition;

/**
 * Dummy implementations of TransitionListener methods.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public abstract class TransitionCallback implements Transition.TransitionListener {

    @Override
    public void onTransitionStart(Transition transition) {
        // no-op
    }

    @Override
    public void onTransitionEnd(Transition transition) {
        // no-op
    }

    @Override
    public void onTransitionCancel(Transition transition) {
        // no-op
    }

    @Override
    public void onTransitionPause(Transition transition) {
        // no-op
    }

    @Override
    public void onTransitionResume(Transition transition) {
        // no-op
    }
}
