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
interface IMemoryShare {
    
    void write(int packet_id);
    void get_file_descriptor(in ParcelFileDescriptor mf, int size, int test_id, int times, IProfileService mService);
}
