/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.android.networkstack.tethering.apishim.api30;

import android.net.INetd;
import android.net.util.SharedLog;
import android.os.RemoteException;
import android.os.ServiceSpecificException;

import androidx.annotation.NonNull;

import com.android.networkstack.tethering.BpfCoordinator.Dependencies;
import com.android.networkstack.tethering.BpfCoordinator.Ipv6ForwardingRule;

/**
 * Bpf coordinator class for API shims.
 */
public class BpfCoordinatorShimImpl
        extends com.android.networkstack.tethering.apishim.common.BpfCoordinatorShim {
    private static final String TAG = "api30.BpfCoordinatorShimImpl";

    @NonNull
    private final SharedLog mLog;
    @NonNull
    private final INetd mNetd;

    public BpfCoordinatorShimImpl(@NonNull final Dependencies deps) {
        mLog = deps.getSharedLog().forSubComponent(TAG);
        mNetd = deps.getNetd();
    }

    @Override
    public boolean isInitialized() {
        return true;
    };

    @Override
    public boolean tetherOffloadRuleAdd(@NonNull final Ipv6ForwardingRule rule) {
        try {
            mNetd.tetherOffloadRuleAdd(rule.toTetherOffloadRuleParcel());
        } catch (RemoteException | ServiceSpecificException e) {
            mLog.e("Could not add IPv6 forwarding rule: ", e);
            return false;
        }

        return true;
    };

    @Override
    public String toString() {
        return "Netd used";
    }
}