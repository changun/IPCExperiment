/*
 * Copyright (C) 2007 The Android Open Source Project
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

package edu.ucla.cens.ipc;
import edu.ucla.cens.ipc.IProfileService; 

/**
 * Example of defining an interface for calling on to a remote service
 * (running in another process).
 */
interface IRemoteService {
    void ipc_test_byte_array(in int packet_id, in byte[] payload);
    void ipc_test_int_array(in int packet_id, in int[] payload);
    void ipc_test_long_array(in int packet_id, in long[] payload);
    void ipc_test_float_array(in int packet_id, in float[] payload);
    void ipc_test_double_array(in int packet_id, in double[] payload);

    
    void warm_up(in int test_id, in int times, IProfileService mService);
}
