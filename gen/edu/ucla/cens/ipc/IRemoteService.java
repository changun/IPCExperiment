/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Cheng-Kang\\Desktop\\eclipse\\workspace\\IPCExperiement\\src\\edu\\ucla\\cens\\ipc\\IRemoteService.aidl
 */
package edu.ucla.cens.ipc;
/**
 * Example of defining an interface for calling on to a remote service
 * (running in another process).
 */
public interface IRemoteService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements edu.ucla.cens.ipc.IRemoteService
{
private static final java.lang.String DESCRIPTOR = "edu.ucla.cens.ipc.IRemoteService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an edu.ucla.cens.ipc.IRemoteService interface,
 * generating a proxy if needed.
 */
public static edu.ucla.cens.ipc.IRemoteService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof edu.ucla.cens.ipc.IRemoteService))) {
return ((edu.ucla.cens.ipc.IRemoteService)iin);
}
return new edu.ucla.cens.ipc.IRemoteService.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_ipc_test_byte_array:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
byte[] _arg1;
_arg1 = data.createByteArray();
this.ipc_test_byte_array(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_ipc_test_int_array:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int[] _arg1;
_arg1 = data.createIntArray();
this.ipc_test_int_array(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_ipc_test_long_array:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
long[] _arg1;
_arg1 = data.createLongArray();
this.ipc_test_long_array(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_ipc_test_float_array:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
float[] _arg1;
_arg1 = data.createFloatArray();
this.ipc_test_float_array(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_ipc_test_double_array:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
double[] _arg1;
_arg1 = data.createDoubleArray();
this.ipc_test_double_array(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_warm_up:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
edu.ucla.cens.ipc.IProfileService _arg2;
_arg2 = edu.ucla.cens.ipc.IProfileService.Stub.asInterface(data.readStrongBinder());
this.warm_up(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements edu.ucla.cens.ipc.IRemoteService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public void ipc_test_byte_array(int packet_id, byte[] payload) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(packet_id);
_data.writeByteArray(payload);
mRemote.transact(Stub.TRANSACTION_ipc_test_byte_array, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void ipc_test_int_array(int packet_id, int[] payload) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(packet_id);
_data.writeIntArray(payload);
mRemote.transact(Stub.TRANSACTION_ipc_test_int_array, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void ipc_test_long_array(int packet_id, long[] payload) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(packet_id);
_data.writeLongArray(payload);
mRemote.transact(Stub.TRANSACTION_ipc_test_long_array, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void ipc_test_float_array(int packet_id, float[] payload) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(packet_id);
_data.writeFloatArray(payload);
mRemote.transact(Stub.TRANSACTION_ipc_test_float_array, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void ipc_test_double_array(int packet_id, double[] payload) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(packet_id);
_data.writeDoubleArray(payload);
mRemote.transact(Stub.TRANSACTION_ipc_test_double_array, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void warm_up(int test_id, int times, edu.ucla.cens.ipc.IProfileService mService) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(test_id);
_data.writeInt(times);
_data.writeStrongBinder((((mService!=null))?(mService.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_warm_up, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_ipc_test_byte_array = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_ipc_test_int_array = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_ipc_test_long_array = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_ipc_test_float_array = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_ipc_test_double_array = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_warm_up = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
public void ipc_test_byte_array(int packet_id, byte[] payload) throws android.os.RemoteException;
public void ipc_test_int_array(int packet_id, int[] payload) throws android.os.RemoteException;
public void ipc_test_long_array(int packet_id, long[] payload) throws android.os.RemoteException;
public void ipc_test_float_array(int packet_id, float[] payload) throws android.os.RemoteException;
public void ipc_test_double_array(int packet_id, double[] payload) throws android.os.RemoteException;
public void warm_up(int test_id, int times, edu.ucla.cens.ipc.IProfileService mService) throws android.os.RemoteException;
}
